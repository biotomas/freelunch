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
package freelunch.core.planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;


/**
 * Abstract class implementing some useful helper methods for search planning.
 * 
 * @author Tomas Balyo Oct 15, 2012
 */
public abstract class AbstractSolver implements Solver {

    protected int[] initialState;
    protected SasProblem problem;

    /**
     * operatorsIndex[x][y] contains the operators that require var[x]=y
     */
    private final List<SasAction>[][] operatorsIndex;

    @SuppressWarnings("unchecked")
    public AbstractSolver(SasProblem problem) {
        this.problem = problem;
        problem.setActionIDs();
        // initialize the state
        int totalVars = problem.getVariables().size();
        initialState = new int[totalVars];
        for (Condition c : problem.getInitialState()) {
            initialState[c.getVariable().getId()] = c.getValue();
        }
        // initialize the operators index
        operatorsIndex = new List[totalVars][];
        for (StateVariable sv : problem.getVariables()) {
            operatorsIndex[sv.getId()] = new List[sv.getDomain()];
            for (int dom = 0; dom < sv.getDomain(); dom++) {
                operatorsIndex[sv.getId()][dom] = new ArrayList<SasAction>();
            }
        }
        // fill the operators index
        for (SasAction op : problem.getOperators()) {
            for (Condition c : op.getPreconditions()) {
                operatorsIndex[c.getVariable().getId()][c.getValue()].add(op);
            }
        }
    }

    protected Set<SasAction> getApplicableActions(int[] state) {
        Set<SasAction> actions = new HashSet<SasAction>();
        for (int var = 0; var < state.length; var++) {
            for (SasAction action : operatorsIndex[var][state[var]]) {
                if (isActionApplicable(action, state)) {
                    actions.add(action);
                }
            }
        }
        return actions;
    }

    protected void updateApplicableActionsChanges(Set<SasAction> actions, int[] newState, int[] changes) {
        // first remove actions that are no more applicable
        Iterator<SasAction> iter = actions.iterator();
        while (iter.hasNext()) {
            if (!isActionApplicable(iter.next(), newState)) {
                iter.remove();
            }
        }
        // add new applicable actions for changed state variables
        for (int i = 0; i < changes.length; i += 2) {
        	int var = changes[i];
            for (SasAction action : operatorsIndex[var][newState[var]]) {
                if (isActionApplicable(action, newState)) {
                    actions.add(action);
                }
            }
        }
    }

    /**
     * Change the list of applicable actions to be compatible with the new
     * state. The given list is expected to be compatible with the old state.
     * 
     * @param actions
     * @param oldState
     * @param newState
     */
    protected void updateApplicableActions(Set<SasAction> actions, int[] oldState, int[] newState) {
        // first remove actions that are no more applicable
        Iterator<SasAction> iter = actions.iterator();
        while (iter.hasNext()) {
            if (!isActionApplicable(iter.next(), newState)) {
                iter.remove();
            }
        }
        // add new applicable actions for changed state variables
        for (int var = 0; var < newState.length; var++) {
            if (oldState[var] != newState[var]) {
                for (SasAction action : operatorsIndex[var][newState[var]]) {
                    if (isActionApplicable(action, newState)) {
                        actions.add(action);
                    }
                }
            }
        }

    }

    protected boolean isActionApplicable(SasAction action, int[] state) {
        for (Condition c : action.getPreconditions()) {
            if (state[c.getVariable().getId()] != c.getValue()) {
                return false;
            }
        }
        return true;
    }

    protected int[] applyAction(SasAction action, int[] state) {
        int[] newState = Arrays.copyOf(state, state.length);
        for (Condition c : action.getEffects()) {
            newState[c.getVariable().getId()] = c.getValue();
        }
        return newState;
    }
    
    protected int[] applyActionInPlace(SasAction action, int[] state) {
    	int[] oldVals = new int[action.getEffects().size()*2];
    	int i = 0;
        for (Condition c : action.getEffects()) {
        	int varId = c.getVariable().getId();
        	oldVals[i++] = varId;
        	oldVals[i++] = state[varId];
            state[varId] = c.getValue();
        }
        return oldVals;
    }
    
    protected void restoreState(int[] state, int[] oldVals) {
    	for (int i = 0; i < oldVals.length; i += 2) {
    		state[oldVals[i]] = oldVals[i+1]; 
    	}
    }
    
    protected boolean isGoalState(int[] state) {
    	for (Condition c : problem.getGoal()) {
    		if (state[c.getVariable().getId()] != c.getValue()) {
    			return false;
    		}
    	}
        return true;
    }

    protected String encodeState(int[] state) {
        char[] result = new char[state.length];
        for (int i = 0; i < state.length; i++) {
            result[i] = (char) (state[i] % 256);
        }
        return String.valueOf(result);
    }

}
