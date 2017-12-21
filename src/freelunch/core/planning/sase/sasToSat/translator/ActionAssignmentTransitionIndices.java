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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.Transition;

public abstract class ActionAssignmentTransitionIndices {
    /**
     * The planning problem
     */
    protected SasProblem problem;
    /**
     * The list of all actions
     */
    protected Collection<SasAction> actions;
    /**
     * Assignment IDs indexed by variable ID and domain value
     */
    protected int[][] assignmentIds;
    /**
     * The total number of assignments
     */
    protected int totalAssignments = 0;
    /**
     * The lists of actions supporting an assignment (by their (prevailing) effect)
     * indexed by assignment IDs
     */
    protected List<SasAction>[] assignmentSupportingActions;
    /**
     * The lists of actions opposing an assignment (by their (prevailing) effect)
     * indexed by assignment IDs
     */
    protected Set<SasAction>[] assignmentOpposingActions;
    /**
     * The list of actions that have an effect that destroys the precondition of the action. 
     * indexed by action IDs
     */
    protected Set<SasAction>[] actionOpposingActions;
    /**
     * The sets of action that use a given variable. Indexed by variable IDs
     */
    protected Set<SasAction>[] actionVariableIndex;
    /**
     * Actions that have an effect that destroys a goal condition.
     */
    protected Set<SasAction> goalOpposingActions;
    /**
     * The list of all transitions.
     */
    protected Collection<Transition> transitions;
    /**
     * The list of transitions related to a given variable.
     * indexed by variable ID
     */
    protected List<Transition>[] transitionVariableIndex;
    /**
     * The list of transition related to a given action.
     * indexed by actions IDs.
     */
    protected List<Transition>[] transitionActionIndex;

    @SuppressWarnings("unchecked")
    protected void initializeActionVariableIndex() {
        int variablesCount = problem.getVariables().size();
        actionVariableIndex = new Set[variablesCount];
        for (int i = 0; i < variablesCount; i++) {
            actionVariableIndex[i] = new HashSet<SasAction>();
        }
        for (SasAction a : actions) {
            for (int varId : getActionScope(a)) {
                actionVariableIndex[varId].add(a);
            }
        }
    }
    
    protected void initializeAssignmentIDs() {
        assignmentIds = new int[problem.getVariables().size()][];
        for (StateVariable var : problem.getVariables()) {
            int dom = var.getDomain();
            assignmentIds[var.getId()] = new int[dom];
            for (int i = 0; i < dom; i++) {
                assignmentIds[var.getId()][i] = totalAssignments;
                totalAssignments++;
            }
        }
    }
    
    /**
     * Get the list of variables IDs that are used in the action 
     * @param a
     * @return
     */
    protected Collection<Integer> getActionScope(SasAction a) {
        Set<Integer> vars = new HashSet<Integer>();
        for (Condition c : a.getPreconditions()) {
            vars.add(c.getVariable().getId());
        }
        for (Condition c : a.getEffects()) {
            vars.add(c.getVariable().getId());
        }
        return vars;
    }
    
    protected Set<Integer> getEffectsScope(SasAction a) {
        Set<Integer> vars = new HashSet<Integer>();
        for (Condition c : a.getEffects()) {
            vars.add(c.getVariable().getId());
        }
        return vars;
    }

    @SuppressWarnings("unchecked")
    protected void initializeActionOpposingActions() {
        actionOpposingActions = new Set[actions.size()];
        for (SasAction a : actions) {
            actionOpposingActions[a.getId()] = new HashSet<SasAction>();
            for (Condition prec : a.getPreconditions()) {
                Set<SasAction> opponents = getOpposingActions(prec);
                actionOpposingActions[a.getId()].addAll(opponents);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void initializeAssignmentOpposingActions() {
        assignmentOpposingActions = new Set[totalAssignments];
        for (StateVariable var : problem.getVariables()) {
            int dom = var.getDomain();
            for (int i = 0; i < dom; i++) {
                int cid = assignmentIds[var.getId()][i];
                Condition c = new Condition(var, i);
                assignmentOpposingActions[cid] = getOpposingActions(c);
            }
        }
    }
    
    protected void initializeGoalOpposingActions() {
        goalOpposingActions = new HashSet<SasAction>();
        for (Condition goal : problem.getGoal()) {
            goalOpposingActions.addAll(getOpposingActions(goal));            
        }
    }

    /**
     * Get the list of actions that destroy the given condition by
     * their effect (or prevailing condition)
     * @param condition
     * @return
     */
    protected Set<SasAction> getOpposingActions(Condition condition) {
        Set<SasAction> opponents = new HashSet<SasAction>();
        candidateTest:
        for (SasAction candidate : actionVariableIndex[condition.getVariable().getId()]) {
            // if the candidate has a malicious effect
            for (Condition effect : candidate.getEffects()) {
                if (effect.getVariable().getId() == condition.getVariable().getId()) {
                    if (effect.getValue() != condition.getValue()) {
                        opponents.add(candidate);
                        continue candidateTest;
                    }
                }
            }
            // we also need to test the prevailing conditions hidden between preconditions
            for (Condition c : candidate.getPreconditions()) {
                if (c.getVariable().getId() != condition.getVariable().getId()) {
                    continue;
                }
                if (c.getValue() == condition.getValue()) {
                    continue;
                }
                // test if c is a prevailing condition
                boolean prevailing = true;
                for (Condition e : candidate.getEffects()) {
                    if (e.getVariable().getId() == c.getVariable().getId()) {
                        prevailing = false;
                        break;
                    }
                }
                if (prevailing) {
                    opponents.add(candidate);
                    continue candidateTest;
                }
            }
        }
        return opponents;
    }
    
    protected Set<SasAction> getSupportingActions(SasAction a) {
        Set<SasAction> supports = new HashSet<SasAction>();
        for (Condition p : a.getPreconditions()) {
            int condId = assignmentIds[p.getVariable().getId()][p.getValue()];
            List<SasAction> supporters = assignmentSupportingActions[condId];
            supports.addAll(supporters);
        }
        return supports;
    }
    
    @SuppressWarnings("unchecked")
    protected void initializeAssignmentSupportingActions() {
        assignmentSupportingActions = new List[totalAssignments];
        for (int i = 0; i < totalAssignments; i++) {
            assignmentSupportingActions[i] = new ArrayList<SasAction>();
        }
        for (SasAction a: actions) {
            for (Condition e : a.getEffects()) {
                int id = assignmentIds[e.getVariable().getId()][e.getValue()];
                assignmentSupportingActions[id].add(a);
            }
        }
    }

}
