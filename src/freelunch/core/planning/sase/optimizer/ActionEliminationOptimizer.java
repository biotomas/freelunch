package freelunch.core.planning.sase.optimizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;

public class ActionEliminationOptimizer {
    
    private static final int SEGMENT_SIZE = 5000;
    
    /**
     * Shortens plans by removing redundant actions. May not be safe
     * for parallel plans. Linearization of plans is recommended
     * before usage.
     * Implements the Action Elimination algorithm of Nakhost and Muller (ICAPS 2010)
     * @param problem
     * @param plan
     */
    public void optimizePlan(SasProblem problem, SasParallelPlan plan) {
        removeGoalUselessActions(problem, plan);
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        lplan = windowedReducer(problem, lplan);
        PlanningUtils.listToPlan(lplan, plan);
    }
    
    public int optimizePlanFixPoint(SasProblem problem, SasParallelPlan plan) {
        int i = 1;
        int oldSize = plan.getPlanLength();
        optimizePlan(problem, plan);
        while (oldSize != plan.getPlanLength()) {
            oldSize = plan.getPlanLength();
            optimizePlan(problem, plan);
            i++;
        }
        return i;
    }
    
    public void greedyOptimizePlanCost(SasProblem problem, SasParallelPlan plan) {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        lplan = greedyActionElimination(problem, lplan);
        PlanningUtils.listToPlan(lplan, plan);
    }
    
    private List<SasAction> windowedReducer(SasProblem problem, List<SasAction> plan) {
        if (plan.size() < SEGMENT_SIZE) {
            return actionElimination(problem, plan);
        }
        List<int[]> states = allStates(PlanningUtils.getInitialState(problem), plan);
        List<SasAction> nplan = new ArrayList<>();
        int segments = plan.size() / SEGMENT_SIZE;
        for (int segment = 0; segment <= segments; segment++) {
            int from = segment*SEGMENT_SIZE;
            int to = Math.min(plan.size(), (1+segment)*SEGMENT_SIZE);
            nplan.addAll(segmentActionElimination(states.get(from), states.get(to), plan.subList(from, to)));
        }
        return actionElimination(problem, nplan);
    }
    
    private List<SasAction> segmentActionElimination(int[] init, int[] goal, List<SasAction> lplan) {
        int[] state = init;
        for (int i = 0; i < lplan.size(); i++) {
            // try to remove the i-th action
            SasAction candAction  = lplan.get(i);
            List<SasAction> endPlan = new ArrayList<>();
            int[] tmpState = Arrays.copyOf(state, state.length);
            for (int j = i + 1; j < lplan.size(); j++) {
                SasAction a = lplan.get(j);
                if (PlanningUtils.isApplicable(tmpState, a)) {
                    endPlan.add(a);
                    PlanningUtils.applyAction(tmpState, a);
                }
            }
            if (goalCompatible(tmpState, goal)) {
                // action can be removed
                List<SasAction> tmpPlan = new ArrayList<>(lplan.subList(0, i));
                tmpPlan.addAll(endPlan);
                lplan = tmpPlan;
                i--;
            } else {
                // if not removed
                PlanningUtils.applyAction(state, candAction);
            }
        }
        return lplan;
    }
    
    private boolean goalCompatible(int[] state, int[] goal) {
        for (int i = 0; i < state.length; i++) {
            if (goal[i] >= 0 && state[i] != goal[i]) {
                return false;
            }
        }
        return true;
    }

    private List<SasAction> greedyActionElimination(SasProblem problem, List<SasAction> lplan) {
        while (true) {
            //List<Pair<Integer>> choices = new ArrayList<>();
            int bestInd = 0;
            int bestVal = -1;
            int[] state = PlanningUtils.getInitialState(problem);
            for (int i = 0; i < lplan.size(); i++) {
                // try to remove the i-th action
                int cost = canRemoveActionIterative(i, lplan, state, problem);
                if (cost >= bestVal) {
                    bestVal = cost;
                    bestInd = i;
                    //choices.add(new Pair<Integer>(i, cost));
                }
                PlanningUtils.applyAction(state, lplan.get(i));
            }
            if (bestVal < 0) {
                break;
            }
            lplan = removeActionIfPossible(bestInd, lplan, problem);
            
            /*
            if (choices.isEmpty()) {
                break;
            }
            Collections.sort(choices, new Comparator<Pair<Integer>>() {
                @Override
                public int compare(Pair<Integer> o1, Pair<Integer> o2) {
                    return o2.second - o1.second;
                }
            });
            for (Pair<Integer> p : choices) {
                List<SasAction> tmp = removeActionIfPossible(p.first, lplan, problem);
                if (tmp == null) {
                    break;
                } else {
                    lplan = tmp;                    
                }
            }
            */
        }
        return lplan;
    }
    
    /**
     * Test if the i-th action can be removed from the plan.
     * If yes the return the sum of the costs of removed action.
     * Return -1 if the action cannot be removed.
     * @param i
     * @param lplan
     * @param state
     * @param problem
     * @return
     */
    private int canRemoveActionIterative(int i, List<SasAction> lplan, int[] state, SasProblem problem) {
        // try to remove the i-th action
        int remcost = 0;
        SasAction candAction  = lplan.get(i);
        remcost += candAction.getCost();
        int[] tmpState = Arrays.copyOf(state, state.length);
        for (int j = i + 1; j < lplan.size(); j++) {
            SasAction a = lplan.get(j);
            if (PlanningUtils.isApplicable(tmpState, a)) {
                PlanningUtils.applyAction(tmpState, a);
            } else {
                remcost += a.getCost();
            }
        }
        if (PlanningUtils.goalSatisfied(tmpState, problem)) {
            // actions can be removed
            return remcost;
        } else {
            // if not removed
            return -1;
        }
    }
    
    /**
     * Remove the k-th and all dependent actions from the plan
     * or return null if not possible
     * @param k
     * @param lplan
     * @param problem
     * @return
     */
    private List<SasAction> removeActionIfPossible(int k, List<SasAction> lplan, SasProblem problem) {
        int[] state = PlanningUtils.getInitialState(problem);
        List<SasAction> endPlan = new ArrayList<>();
        for (int i = 0; i < lplan.size(); i++) {
            SasAction a = lplan.get(i);
            if (i < k) {
                PlanningUtils.applyAction(state, a);
            } else if (i > k) {
                if (PlanningUtils.isApplicable(state, a)) {
                    endPlan.add(a);
                    PlanningUtils.applyAction(state, a);
                }
            }
        }
        if (!PlanningUtils.goalSatisfied(state, problem)) {
            return null;
        }
        List<SasAction> tmpPlan = new ArrayList<>(lplan.subList(0, k));
        tmpPlan.addAll(endPlan);
        lplan = tmpPlan;
        return lplan;
    }
    

    private List<SasAction> actionElimination(SasProblem problem, List<SasAction> lplan) {
        int[] state = PlanningUtils.getInitialState(problem);
        for (int i = 0; i < lplan.size(); i++) {
            // try to remove the i-th action
            SasAction candAction  = lplan.get(i);
            List<SasAction> endPlan = new ArrayList<>();
            int[] tmpState = Arrays.copyOf(state, state.length);
            for (int j = i + 1; j < lplan.size(); j++) {
                SasAction a = lplan.get(j);
                if (PlanningUtils.isApplicable(tmpState, a)) {
                    endPlan.add(a);
                    PlanningUtils.applyAction(tmpState, a);
                }
            }
            if (PlanningUtils.goalSatisfied(tmpState, problem)) {
                // action can be removed
                List<SasAction> tmpPlan = new ArrayList<>(lplan.subList(0, i));
                tmpPlan.addAll(endPlan);
                lplan = tmpPlan;
                i--;
            } else {
                // if not removed
                PlanningUtils.applyAction(state, candAction);
            }
        }
        return lplan;
    }
    
    private List<int[]> allStates(int[] initial, List<SasAction> plan) {
        List<int[]> states = new ArrayList<>();
        int[] state = initial;
        states.add(state);
        for (SasAction a : plan) {
            state = Arrays.copyOf(state, state.length);
            PlanningUtils.applyAction(state, a);
            states.add(state);
        }
        return states;
    }
    
    /**
     * Action elimination backwards
     * @param problem
     * @param lplan
     * @return
     */
    @SuppressWarnings("unused")
    private List<SasAction> removeActionsByTrialAndErrorBackwards(SasProblem problem, List<SasAction> lplan) {
        // compute the states
        List<int[]> states = allStates(PlanningUtils.getInitialState(problem), lplan);
        // the last state
        int[] state = states.get(states.size() - 1);
        
        // try to remove actions starting from the end
        for (int i = lplan.size(); i > 0; i--) {
            // try to remove the i-th action
            List<SasAction> endPlan = new ArrayList<>();
            //tmpPlan.addAll(lplan.subList(0, i));
            int[] tmpState = Arrays.copyOf(states.get(i), state.length);
            for (int j = i + 1; j < lplan.size(); j++) {
                SasAction a = lplan.get(j);
                if (PlanningUtils.isApplicable(tmpState, a)) {
                    endPlan.add(a);
                    PlanningUtils.applyAction(tmpState, a);
                }
            }
            if (PlanningUtils.goalSatisfied(tmpState, problem)) {
                // action can be removed
                List<SasAction> tmpPlan = new ArrayList<>(lplan.subList(0, i));
                tmpPlan.addAll(endPlan);
                lplan = tmpPlan;
            }
        }
        return lplan;
    }

    /**
     * Remove action that are not required for the goal conditions
     * or following actions.
     * @param problem
     * @param plan
     */
    private void removeGoalUselessActions(SasProblem problem, SasParallelPlan plan) {
        int[] state = new int[problem.getVariables().size()+1];
        Arrays.fill(state, -10);
        for (Condition c : problem.getGoal()) {
            state[c.getVariable().getId()] = c.getValue();
        }
        List<SasAction> actionsToRemove = new ArrayList<SasAction>();
        for (int ind = plan.getPlanLength() - 1; ind >= 0; ind--) {
            actionsToRemove.clear();
            List<SasAction> segment = plan.getPlan().get(ind);
            for (int i = segment.size() - 1; i >= 0; i--) {
                SasAction a = segment.get(i);
                if (actionUsefull(a, state)) {
                    PlanningUtils.setPreconditionsOfAction(state, a);
                    continue;
                } else {
                    actionsToRemove.add(a);                    
                }
            }
            for (SasAction a : actionsToRemove) {
                segment.remove(a);
            }
        }
        plan.removeEmptySegments();
    }
    
    private boolean actionUsefull(SasAction action, int[] state) {
        effectsLoop:
        for (Condition c : action.getEffects()) {
            if (state[c.getVariable().getId()] == c.getValue()) {
                for (Condition prec : action.getPreconditions()) {
                    if (prec.equals(c)) {
                        continue effectsLoop;
                    }
                }
                return true;
            }
        }
        return false;
    }

}
