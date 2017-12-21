/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.planning.montecarlo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import freelunch.core.planning.AbstractSolver;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.TraceModel;
import freelunch.core.planning.Traceable;
import freelunch.core.planning.model.ActionInfo;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.utilities.Stopwatch;


/**
 * A MCTS planner. Creates serial plans. The tree policy and default policy can
 * be customized {@link http://videolectures.net/icaps2010_fern_mcpbprp/}.
 * 
 * @author Vojtech Bardiovsky Oct 28, 2012
 */
public class MctsSolver extends AbstractSolver implements Traceable {

    protected Random random;

    protected MctsSettings settings;
    protected MctsTraceModel traceModel;

    protected int totalNumRollouts;

    private Node root;

    public MctsSolver(SasProblem problem) {
        this(problem, new MctsSettings());
    }

    public MctsSolver(SasProblem problem, MctsSettings settings) {
        this(problem, settings, 0);
    }

    public MctsSolver(SasProblem problem, MctsSettings settings, int seed) {
        super(problem);
        this.random = new Random(seed);
        this.settings = settings;
        if (settings.isTracingEnabled()) {
            this.traceModel = new MctsTraceModel();
        }
    }

    @Override
    public SasParallelPlan solve() throws TimeoutException, NonexistentPlanException {
        if (settings.getHeuristic() == null) {
            // No heuristic set, create default one
            settings.setHeuristic(new StateVariablesEqualityHeuristic(problem));
        }

        List<SasAction> plan = new ArrayList<SasAction>();
        // TODO do not visit already visited states

        int[] currentState = Arrays.copyOf(initialState, initialState.length);

        Stopwatch watch = new Stopwatch();
        int timelimit = settings.getTimelimit();

        while (!isGoalState(currentState)) {
            if (watch.limitExceeded(timelimit)) {
                //throw new TimeoutException();
            }

            // Create a new tree
            root = new Node(null, currentState);
            updateTraceModel(root, null);
            totalNumRollouts = 0;

            // Do the predetermined amount of iterations.
            for (int i = 0; i < settings.getNumIterations(); i++) {

                // Select the node by following the tree policy starting at the root node. 
                // The selected node has no children.
                Node selectedNode = select(root);
                updateTraceModel(root, selectedNode);

                // Expand the node by adding children.
                expand(selectedNode);
                updateTraceModel(root, selectedNode);

                // Simulate rollouts from all children
                for (int child = 0; child < selectedNode.children.length; child++) {
                    Node childNode = selectedNode.children[child];
                    updateTraceModel(root, childNode);
                    // Simulate rollouts from one child and update the rewards.
                    double value = 0;
                    for (int j = 0; j < settings.numRollouts; j++) {
                        value = Math.max(value, simulate(childNode));
                    }

                    // Back propagate the essential data about the chosen path.
                    backPropagate(childNode, value);
                    updateTraceModel(root, childNode);

                    totalNumRollouts++;
                }
            }

            // Select the best node and move to its state.
            currentState = selectNextActions(root, plan);

            // Trace the new selected action
            if (settings.isTracingEnabled()) {
                List<ActionInfo> actionInfos = new ArrayList<ActionInfo>();
                for (SasAction action : plan) {
                    actionInfos.add(action.getActionInfo());
                }
                traceModel.setSelectedActions(actionInfos);
            }
        }

        // make the plan
        List<List<SasAction>> pplan = new ArrayList<List<SasAction>>();
        for (SasAction op : plan) {
            List<SasAction> step = new ArrayList<SasAction>(1);
            step.add(op);
            pplan.add(step);
        }
        return new SasParallelPlan(pplan);
    }

    /**
     * Select the actions to choose by following the tree policy starting at the
     * root node.
     * 
     * @return the last reached state.
     */
    protected int[] selectNextActions(Node node, List<SasAction> plan) {
        int numActions = 0;
        while (node.expanded && !isGoalState(node.state) && numActions < settings.numActionsAtOnce) {
            double maxReward = Double.NEGATIVE_INFINITY;
            List<Integer> indicesWithMaxReward = new ArrayList<Integer>();
            for (int i = 0; i < node.children.length; i++) {
                maxReward = Math.max(maxReward, node.children[i].totalReward);
            }
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i].totalReward == maxReward) {
                    indicesWithMaxReward.add(i);
                }
            }
            int selectedAction = indicesWithMaxReward.get(random.nextInt(indicesWithMaxReward.size()));
            plan.add(node.possibleActions[selectedAction]);
            node = node.children[selectedAction];
            updateTraceModel(root, node);
            numActions++;
            System.out.println("Chosen state with value " + maxReward);
        }
        return node.state;
    }

    /**
     * Select the node by following the tree policy starting at the root node.
     * 
     * @return a node with no children.
     */
    protected Node select(Node node) {
        node.numVisited++;
        while (node.expanded) {
            int[] numChosen = new int[node.possibleActions.length];
            double[] rewards = new double[node.possibleActions.length];

            for (int i = 0; i < rewards.length; i++) {
                numChosen[i] = node.children[i].numVisited;
                rewards[i] = node.children[i].totalReward;
            }

            int selectedAction = settings.getTreePolicy().selectAction(totalNumRollouts, numChosen, rewards);
            node = select(node.children[selectedAction]);
        }
        return node;
    }

    /**
     * Expand the node by adding children.
     */
    protected void expand(Node node) {
        node.children = new Node[node.possibleActions.length];
        for (int i = 0; i < node.possibleActions.length; i++) {
            node.children[i] = new Node(node, applyAction(node.possibleActions[i], node.state));
        }
        node.expanded = true;
    }

    /**
     * Simulate a rollout from a given node.
     * 
     * @return last value on the rollout path.
     */
    protected double simulate(Node node) {
        int[] state = node.state;
        double totalValue = settings.getHeuristic().evaluate(state);
        double maxValue = totalValue;
        double eval = 0;
        for (int i = 0; i < settings.getRolloutLength(); i++) {
            List<SasAction> possibleActions = new ArrayList<SasAction>(getApplicableActions(state));
            state = applyAction(possibleActions.get(random.nextInt(possibleActions.size())), state);
            if (isGoalState(state)) {
                // Compute the average from state evaluations until the final state and consider remaining
                // rollout states as equal to 1.0
                //return (totalValue + 1.0 * (settings.getRolloutLength() + 1 - i)) / settings.getRolloutLength();
                return 1.0;
            }
            eval = settings.getHeuristic().evaluate(state);
            totalValue += eval;
            maxValue = Math.max(maxValue, eval);
        }
        //return eval;
        //return totalValue / (settings.getRolloutLength() + 1);
        return maxValue;
    }

    /**
     * Propagate back the results of the simulation.
     */
    protected void backPropagate(Node node, double value) {
        node.totalReward = value;
        while (node.parent != null) {
            node = node.parent;
            updateReward(node);
        }
    }

    /**
     * Sets the reward for the node to the mean value of its children rewards.
     */
    protected void updateReward(Node node) {
        //double sum = 0;
        double max = node.totalReward;
        for (int i = 0; i < node.children.length; i++) {
            //sum += node.children[i].totalReward;
            max = Math.max(max, node.children[i].totalReward);
        }
        //node.totalReward = sum / node.children.length;
        node.totalReward = max;
    }

    @Override
    public String toString() {
        return "MCTS solver";
    }

    // TODO do not revisit already visited states.
    class Node {
        int[] state;
        double totalReward;
        int numVisited;
        Node parent;
        SasAction[] possibleActions;
        Node[] children;
        boolean expanded;

        public Node(Node parent, int[] state) {
            this.parent = parent;
            this.state = state;
            this.possibleActions = getApplicableActions(state).toArray(new SasAction[] {});
        }
    }

    @Override
    public BasicSettings getSettings() {
        return settings;
    }

    // Tracing ------------------------------------------------------------------------
    /**
     * Updates the tracing model with the given root node if tracing is enabled.
     */
    private void updateTraceModel(Node root, Node activeNode) {
        if (settings.isTracingEnabled()) {
            traceModel.updateModel(recursiveBuildTraceDataNode(root, activeNode, null));
        }
    }

    @Override
    public TraceModel getTraceModel() {
        return traceModel;
    }

    private MctsTraceModel.Node recursiveBuildTraceDataNode(Node node, Node activeNode, ActionInfo actionInfo) {
        MctsTraceModel.Node result = new MctsTraceModel.Node();
        if (node.expanded) {
            result.children = new MctsTraceModel.Node[node.children.length];
            for (int i = 0; i < result.children.length; i++) {
                result.children[i] = recursiveBuildTraceDataNode(node.children[i], activeNode, node.possibleActions[i].getActionInfo());
            }
        }
        result.active = (node == activeNode);
        result.actionInfo = actionInfo;
        result.label = actionInfo != null ? actionInfo.getName() + "\n" + "Reward: " + node.totalReward : "Reward: " + node.totalReward;
        result.label += "   Visited: " + node.numVisited;
        return result;
    }
}
