/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.core.planning.sase.sasToSat.translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.ArrayUtils;
import freelunch.core.utilities.IntVector;
import freelunch.core.utilities.Triple;
import freelunch.core.utilities.Tuple;

public class DirectExistStepTranslator extends TranslatorBase implements SasToSatTranslator {
    
    /**
     * Each action has a unique rank. Actions that usually follow some other
     * action in the plans should have a higher rank than the followed actions.
     */
    protected int[] actionRanks = null;

    class ActionComparator implements Comparator<SasAction> {
        @Override
        public int compare(SasAction o1, SasAction o2) {
            return actionRanks[o1.getId()] - actionRanks[o2.getId()];
        }
    }
    
    public DirectExistStepTranslator(SasProblem problem, int ranking) {
        super(problem);
        initializeActionVariables();
        initializeAssignmentVariables();
        initializeActionVariableIndex();
        initializeAssignmentOpposingActions();
        initializeAssignmentSupportingActions();
        initializeActionRanks(ranking);
        computeMutexGroupPairs();
        initializeChainVariables();
    }

    public DirectExistStepTranslator(SasProblem problem) {
        //the default ranking is method 4
        this(problem, 4);
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_initialAssignementsHold(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_atMostOneAssignmentPerVariable(solver, time);
        universal_actionRequiresItsPreconditionOrAnAction(solver, time);
        universal_mutexGroups(solver, time);
        //TODO not always necessary, find out when and why
        // not necessary for the logistics domain for example
        //universal_actionPreconditionMustNotBeDestroyed(solver, time);
        universal_preconditionChains(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_actionImpliesEffectsOrAnotherAction(solver, time);
        transition_assignmentsMustBeSupportedByActionsOrAssignments(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }
    
    protected void universal_preconditionChains(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(3);
        for (Tuple<SasAction, Integer> actionChain : actionImpliesChain) {
            vec.clear();
            vec.add(-actionVariables.getVariable(actionChain.first.getId(), time));
            vec.add(chainVariables.getVariable(actionChain.second, time));
            solver.addNewClause(vec);
        }
        for (Tuple<Integer, Integer> chainChain : chainImpliesAnotherChain) {
            vec.clear();
            vec.add(-chainVariables.getVariable(chainChain.first, time));
            vec.add(chainVariables.getVariable(chainChain.second, time));
            solver.addNewClause(vec);
        }
        for (Tuple<Integer, SasAction> chainAction : chainImpliesNotAction) {
            vec.clear();
            vec.add(-chainVariables.getVariable(chainAction.first, time));
            vec.add(-actionVariables.getVariable(chainAction.second.getId(), time));
            solver.addNewClause(vec);
        }
        for (Triple<Integer, SasAction, Integer> chainActionChain : chainImpliesActionOrChain) {
            vec.clear();
            vec.add(-chainVariables.getVariable(chainActionChain.first, time));
            vec.add(actionVariables.getVariable(chainActionChain.second.getId(), time));
            vec.add(chainVariables.getVariable(chainActionChain.third, time));
            solver.addNewClause(vec);
        }
    }

    protected List<Tuple<SasAction, Integer>> actionImpliesChain;
    protected List<Tuple<Integer, Integer>> chainImpliesAnotherChain;
    protected List<Triple<Integer, SasAction, Integer>> chainImpliesActionOrChain;
    protected List<Tuple<Integer, SasAction>> chainImpliesNotAction;
    
    protected void initializeChainVariables() {
        actionImpliesChain = new ArrayList<>();
        chainImpliesAnotherChain = new ArrayList<>();
        chainImpliesActionOrChain = new ArrayList<>();
        chainImpliesNotAction = new ArrayList<>();
        
        Comparator<SasAction> comp = new ActionComparator();
        int chainVars = 0;
        for (StateVariable var : problem.getVariables()) {
            for (int val = 0; val < var.getDomain(); val++) {
                boolean firstOpponent = true;
                List<SasAction> actions = new ArrayList<>(actionVariableIndex[var.getId()]);
                Collections.sort(actions, comp);
                for (SasAction a : actions) {
                    boolean last = (actions.get(actions.size()-1).getId() == a.getId());
                    // a is either a supporter, destroyer or prevailer
                    ActionConditionRelation status = getRelationshipStatus(a, var.getId(), val);
                    
                    if (status == ActionConditionRelation.unrelated) {
                        continue;
                    }
                    if (status == ActionConditionRelation.opponent) {
                        if (firstOpponent) {
                            firstOpponent = false;
                        } else {
                            if (!last) {
                                chainImpliesAnotherChain.add(new Tuple<Integer, Integer>(chainVars - 1, chainVars));
                            }
                        }
                        if (!last) {
                            actionImpliesChain.add(new Tuple<SasAction, Integer>(a, chainVars));
                        }
                    }
                    if (status == ActionConditionRelation.opponetRequirer) {
                        if (firstOpponent) {
                            firstOpponent = false;
                        } else {
                            if (!last) {
                                chainImpliesAnotherChain.add(new Tuple<Integer, Integer>(chainVars - 1, chainVars));
                            }
                            chainImpliesNotAction.add(new Tuple<Integer, SasAction>(chainVars - 1, a));
                        }
                        if (!last) {
                            actionImpliesChain.add(new Tuple<SasAction, Integer>(a, chainVars));
                        }
                    }
                    if (status == ActionConditionRelation.requirer) {
                        if (!firstOpponent) {
                            if (!last) {
                                chainImpliesAnotherChain.add(new Tuple<Integer, Integer>(chainVars - 1, chainVars));
                            }
                            chainImpliesNotAction.add(new Tuple<Integer, SasAction>(chainVars - 1, a));
                        }
                    }
                    if (status == ActionConditionRelation.supporter) {
                        if (!firstOpponent && !last) {
                            chainImpliesActionOrChain.add(new Triple<Integer, SasAction, Integer>(chainVars - 1, a, chainVars));
                        }
                    }
                    if (!last) {
                        chainVars++;
                    }
                }
            }
        }
        chainVariables = varManager.createNewVarGroup(2);
        chainVariables.setDimensionSize(0, chainVars);
        chainVariables.setDimensionSize(1, 1);
    }
    
    enum ActionConditionRelation {opponent, supporter, requirer, opponetRequirer, unrelated};
    
    ActionConditionRelation getRelationshipStatus(SasAction a, int varId, int value) {
        boolean isOpponent = false;
        for (Condition c : a.getEffects()) {
            if (c.getVariable().getId() == varId) {
                if (c.getValue() == value) {
                    return ActionConditionRelation.supporter;
                } else {
                    isOpponent = true;
                    break;
                }
            }
        }
        for (Condition c : a.getPreconditions()) {
            if (c.getVariable().getId() == varId && c.getValue() == value) {
                // a is requirer
                if (isOpponent) {
                    return ActionConditionRelation.opponetRequirer;
                } else {
                    return ActionConditionRelation.requirer;
                }
            }
        }
        if (isOpponent) {
            return ActionConditionRelation.opponent;
        }
        return ActionConditionRelation.unrelated;
    }
    

    protected void initializeActionRanks(int ranking) {
        TopsortActionRanker ranker = new TopsortActionRanker();
        try {
            switch (ranking) {
            case 0:
                actionRanks = ranker.getActionRanksNonRecursive(this, actions);
                break;
            case 1:
                actionRanks = ranker.getActionRanksNonRecursive(this, actions);
                ArrayUtils.invert(actionRanks, actions.size()-1);
                break;
            case 2:
                actionRanks = ranker.getActionRanksNonRecursiveOld(this, actions);
                break;
            case 3:
                actionRanks = ranker.getActionRanksNonRecursiveOld(this, actions);
                ArrayUtils.invert(actionRanks, actions.size()-1);
                break;
            case 4:
                actionRanks = ranker.getActionRanks(this, actions);
                break;
            case 5:
                actionRanks = ranker.getActionRanks(this, actions);
                ArrayUtils.invert(actionRanks, actions.size()-1);
                break;
            case 6:
                actionRanks = ArrayUtils.sequence(actions.size());
                break;
            case 7:
                actionRanks = ArrayUtils.sequence(actions.size());
                ArrayUtils.invert(actionRanks, actions.size()-1);
                break;
            case 8:
                actionRanks = ArrayUtils.randArray(actions.size(), 2014);
                break;
            }
        } catch (Throwable e) {
            actionRanks = ArrayUtils.sequence(actions.size());
        }
    }
    
    private void sortActionsByRank(SasParallelPlan plan) {
        Comparator<SasAction> comp = new ActionComparator();
        for (List<SasAction> step : plan.getPlan()) {
            Collections.sort(step, comp);
        }
    }
    
    @Override
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        SasParallelPlan plan = super.decodePlan(model, makespan);
        plan.getPlan().remove(plan.getPlanLength() - 1);
        sortActionsByRank(plan);
        return plan;
    }
    
    @Override
    public SasParallelPlan decodePlan(List<int[]> model) {
        SasParallelPlan plan = super.decodePlan(model);
        plan.getPlan().remove(plan.getPlanLength() - 1);
        sortActionsByRank(plan);
        return plan;
    }
    
    /**
     * Exist step semantics. An action requires its effects to be true in the next horizon
     * or another action in the current horizon that will change that variable further.
     * The supporting action must have a higher rank in order to avoid cyclic supports.
     * requires <i>assignmentVariables</i> and <i>actionRanks</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_actionImpliesEffectsOrAnotherAction(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPrevailConditions()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time+1));

                int varId = c.getVariable().getId();
                for (SasAction sa : actionVariableIndex[varId]) {
                    if (actionRanks[sa.getId()] > actionRanks[a.getId()]) {
                        vec.add(actionVariables.getVariable(sa.getId(), time));
                    }
                }
                solver.addNewClause(vec);
            }
            /**/
            for (Condition c : a.getEffects()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time+1));

                int varId = c.getVariable().getId();
                for (SasAction sa : actionVariableIndex[varId]) {
                    if (actionRanks[sa.getId()] > actionRanks[a.getId()]) {
                        vec.add(actionVariables.getVariable(sa.getId(), time));
                    }
                }
                solver.addNewClause(vec);
            }
        }
    }
    
    /**
     * Is there an action with that would destroy the given assignment
     * and its rank is between the from and to parameters?
     * TODO This could prevent the need for universal_actionPreconditionMustNotBeDestroyed
     * @param assignmentId
     * @param from
     * @param to
     * @return
     */
    @SuppressWarnings("unused")
    private boolean badActionInRange(int assignmentId, int from, int to) {
        for (SasAction oppa : assignmentOpposingActions[assignmentId]) {
            int oppaRank = actionRanks[oppa.getId()];
            if (from < oppaRank && oppaRank < to) {
                return true;                                
            }
        }
        return false;
    }
    
    /**
     * Exist step semantics. An action requires its preconditions to be supported by an
     * assignment transfered from the previous horizon or an action in the current horizon.
     * The supporting action must have a lower rank in order to avoid cyclic supports.
     * requires <i>assignmentVariables</i> and <i>actionRanks</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_actionRequiresItsPreconditionOrAnAction(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPreconditions()) {
                int id = assignmentIds[c.getVariable().getId()][c.getValue()];
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                // precondition holds from the previous horizon
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time));
                // precondition is supported by a current action with a lower rank
                for (SasAction sa : assignmentSupportingActions[id]) {
                    if (actionRanks[sa.getId()] < actionRanks[a.getId()]) {
                        // there must be no action between them that would destroy the precondition
                        //if (badActionInRange(id, actionRanks[sa.getId()], actionRanks[a.getId()])) {
                          //  continue;
                        //}
                        vec.add(actionVariables.getVariable(sa.getId(), time));
                    }
                }
                solver.addNewClause(vec);
            }
        }
    }
    
    /**
     * Exist step semantics. Preconditions of an action must not be destroyed by another
     * action in the same horizon if that action has a lower rank.
     * requires <i>assignmentVariables</i> and <i>actionRanks</i> and <i>assignmentOpposingActions</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_actionPreconditionMustNotBeDestroyed(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPreconditions()) {
                int id = assignmentIds[c.getVariable().getId()][c.getValue()];
                
                for (SasAction oppa : assignmentOpposingActions[id]) {
                    if (actionRanks[oppa.getId()] < actionRanks[a.getId()]) {
                        vec.clear();
                        vec.add(-actionVariables.getVariable(a.getId(), time));
                        vec.add(-actionVariables.getVariable(oppa.getId(), time));
                        solver.addNewClause(vec);
                    }
                }                
            }
        }
    }

}
