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
package freelunch.core.planning.sase.sasToSat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.Transition;

/**
 * Class for generating transitions from operators
 */
public class TransitionGenerator {

    private Map<String, Transition> transitions = null;
    private List<Transition>[] transitionsByVar = null;
    private List<Transition>[] transitionsByAction = null;
    private int id;
    private SasProblem problem;
    private Collection<SasAction> actions;

    public TransitionGenerator(SasProblem problem, Collection<SasAction> actions) {
        this.problem = problem;
        this.actions = actions;
        id = 0;
    }

    private Transition getTransitionInstance(Transition t) {
        if (transitions.containsKey(t.getDescriptor())) {
            id--;
            return transitions.get(t.getDescriptor());
        }
        transitions.put(t.getDescriptor(), t);
        int varId = t.getVar().getId();
        if (transitionsByVar[varId] == null) {
            transitionsByVar[varId] = new ArrayList<Transition>();
        }
        transitionsByVar[varId].add(t);
        return t;
    }

    /**
     * Generates transitions from operators. Sets the list
     * of generated transitions to the problem
     * and sets them to the operators also
     * @param actions
     * @return transitions
     */
    @SuppressWarnings("unchecked")
    private void generateTransitions() {
        transitions = new HashMap<String, Transition>(actions.size());
        transitionsByVar = new List[problem.getVariables().size()];
        transitionsByAction = new List[actions.size()];
        List<Transition> transitionList = new ArrayList<Transition>();

        for (SasAction op : actions) {
            transitionList.clear();
            
            for (Condition c : op.getPreconditions()) {
            	Condition eff = null;
            	for (Condition e : op.getEffects()) {
            		if (e.getVariable().getId() == c.getVariable().getId()) {
            			eff = e; 
            			break;
            		}
            	}
            	if (eff == null) {
            		// a precondition without without effect is a "prevailing condition"
                    Transition t = new Transition(c.getVariable(), c.getValue(), c.getValue(), id);
                    id++;
                    t = getTransitionInstance(t);
                    t.getSupportingActions().add(op);
                    transitionList.add(t);
            	}
            }
            
            for (Condition eff : op.getEffects()) {
                Condition precond = null;
                for (Condition p : op.getPreconditions()) {
                    if (p.getVariable().getId() == eff.getVariable().getId()) {
                        precond = p;
                        break;
                    }
                }
                int requiredValue = precond == null ? -1 : precond.getValue();
                Transition t = new Transition(eff.getVariable(), requiredValue, eff.getValue(), id);
                id++;
                t = getTransitionInstance(t);
                t.getSupportingActions().add(op);
                transitionList.add(t);
            }
            transitionsByAction[op.getId()] = new ArrayList<Transition>(transitionList); 
        }

        // add the prevailing transitions that do not belong to any action
        for (StateVariable svar : problem.getVariables()) {
            for (int val = 0; val < svar.getDomain(); val++) {
                Transition t = new Transition(svar, val, val, id);
                id++;
                t = getTransitionInstance(t);
                // no supporting actions
            }
        }
    }
    
    public List<Transition>[] getTransitionByActions() {
        if (transitionsByAction == null) {
            generateTransitions();
        }
        return transitionsByAction;
    }
    
    public List<Transition>[] getTransitionsByVariables() {
        if (transitionsByVar == null) {
            generateTransitions();
        }
        return transitionsByVar;
    }
    
    public Collection<Transition> getTransitionList() {
        if (transitions == null) {
            generateTransitions();
        }
        return transitions.values();
    }

}
