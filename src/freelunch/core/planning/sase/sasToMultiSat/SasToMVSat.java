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
package freelunch.core.planning.sase.sasToMultiSat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.Transition;
import freelunch.core.planning.model.Transition.TransitionType;
import freelunch.core.planning.sase.sasToMultiSat.MultiValuedCNF.Assignment;
import freelunch.core.planning.sase.sasToMultiSat.MultiValuedCNF.MVClause;
import freelunch.core.planning.sase.sasToSat.TransitionGenerator;

public class SasToMVSat {
    
    private SasProblem problem;
    private int variables;
    private int[] domainSizes;
    private Collection<Transition> transitions;
    private List<Transition>[] transitionVariableIndex;
    private List<Transition>[] transitionActionIndex;


    /**
     * For each SAS variable we have a multivalued-SAT variable
     * and its values are the transitions of this variable.
     * This map contains the mapping of transitions to these values.
     */
    private Map<Transition, Integer> transitionValue;
    /**
     * Actions are grouped into multivalued variables. This map 
     * contains the index of the multi-variable where an action
     * is registered.
     */
    private Map<SasAction, Integer> actionVariable;
    /**
     * Actions are grouped into multivalued variables. This map 
     * contains the value of the multi-variable which an action
     * has.
     */
    private Map<SasAction, Integer> actionValue;
    /**
     * Pairs of actions that must be explicitly encoded into multi-valued
     * clauses, because their non-interference is not provided
     * by multi-valued variables.
     */
    private List<SasAction[]> interferingActionPairs;
    
    private void initializeInterferingActionPairs() {
        interferingActionPairs = new ArrayList<SasAction[]>();
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            List<Transition> transitions = transitionVariableIndex[varId];
            for (Transition transition : transitions) {
                if (transition.getType().equals(TransitionType.PREVAILING)) {
                    continue;
                }
                if (transition.getSupportingActions().size() > 1) {
                    // all candidate actions are interfering, but some
                    // of them belong to the same multi-valued variable
                    // the others must be explicitly enforced.
                    ArrayList<SasAction> candidateActions = new ArrayList<SasAction>(transition.getSupportingActions());
                    for (int i1 = 0; i1 < candidateActions.size(); i1++) {
                        for (int i2 = i1+1; i2 < candidateActions.size(); i2++) {
                            SasAction a1 = candidateActions.get(i1);
                            SasAction a2 = candidateActions.get(i2);
                            // if the in
                            if (actionVariable.get(a1) != actionVariable.get(a2)) {
                                SasAction[] pair = new SasAction[] {a1, a2};
                                interferingActionPairs.add(pair);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void initializeValueMaps() {
        transitionValue = new HashMap<Transition, Integer>(transitions.size());
        domainSizes = new int[problem.getVariables().size()*2];
        for (StateVariable var : problem.getVariables()) {
            int i = 0;
            domainSizes[var.getId()] = transitionVariableIndex[var.getId()].size();
            for (Transition t : transitionVariableIndex[var.getId()]) {
                transitionValue.put(t, i);
                i++;
            }
        }
        
        // create an index of action by variables in them
        Map<Integer, List<SasAction>> actionsByVariable = new HashMap<Integer, List<SasAction>>(problem.getVariables().size());
        for (SasAction action : problem.getOperators()) {
            Set<Integer> actionVars = new HashSet<Integer>();
            for (Condition c : action.getPreconditions()) {
                actionVars.add(c.getVariable().getId());
            }
            for (Condition c : action.getEffects()) {
                actionVars.add(c.getVariable().getId());
            }
            for (int var : actionVars) {
                if (!actionsByVariable.containsKey(var)) {
                    actionsByVariable.put(var, new ArrayList<SasAction>());
                }
                actionsByVariable.get(var).add(action);
            }
        }
        
        actionValue = new HashMap<SasAction, Integer>(problem.getOperators().size());
        actionVariable = new HashMap<SasAction, Integer>(problem.getOperators().size());
        Set<SasAction> usedActions = new HashSet<SasAction>();
        
        // action variable IDs start after transition variables
        int varId = problem.getVariables().size();
        
        for (StateVariable var : problem.getVariables()) {
            // the value=0 is reserved for "none of these actions"
            int value = 1;
            for (SasAction a : actionsByVariable.get(var.getId())) {
                if (usedActions.contains(a)) {
                    continue;
                }
                usedActions.add(a);
                actionValue.put(a, value);
                actionVariable.put(a, varId);
                value++;
            }
            if (value > 1) {
                domainSizes[varId] = value;
                varId++;
            }
        }
        variables = varId;
        // trim the array
        domainSizes = Arrays.copyOf(domainSizes, varId);
    }
    
    private int timeShift(int var, int time) {
        return var + variables * time;
    }
    
    public SasParallelPlan extractPlan(SasProblem problem, Assignment[] solution) {
        this.problem = problem;
        initializeValueMaps();
        initializeInterferingActionPairs();
        int makespan = solution.length / variables;
        
        List<List<SasAction>> plan = new ArrayList<List<SasAction>>();
        for (int m = 0; m < makespan; m++) {
            List<SasAction> step = new ArrayList<SasAction>();
            for (int i = m*variables; i < (m+1)*variables; i++) {
                SasAction a = findAction(solution[i]);
                if (a != null) {
                    step.add(a);
                }
            }
            plan.add(step);
        }
        return new SasParallelPlan(plan);
    }
    
    private SasAction findAction(Assignment assignment) {
        int var = assignment.variable % variables;
        //int val = assignment.value;
        if (!actionVariable.containsValue(var)) 
            return null;
        //TODO inspiruj sa zo sase translatoru
        throw null;
    }
    
    public MultiValuedCNF translate(SasProblem problem, int makespan) {
        this.problem = problem;
        problem.setActionIDs();
        TransitionGenerator tgen = new TransitionGenerator(problem, problem.getOperators());
        transitions = tgen.getTransitionList();
        transitionVariableIndex = tgen.getTransitionsByVariables();
        transitionActionIndex = tgen.getTransitionByActions();
        initializeValueMaps();
        initializeInterferingActionPairs();
        MultiValuedCNF result = new MultiValuedCNF();
        result.variablesCount = makespan * variables;
        result.domains = new int[makespan*variables];
        for (int i = 0; i < makespan; i++) {
            System.arraycopy(domainSizes, 0, result.domains, i*variables, variables);
        }
        
        List<Assignment> assignments = new ArrayList<MultiValuedCNF.Assignment>();
        
        //initial state constraints
        for (Condition cond : problem.getInitialState()) {
            int varId = cond.getVariable().getId();
            List<Transition> transitions = transitionVariableIndex[varId];
            assignments.clear();
            for (Transition t : transitions) {
                if (t.getType().equals(TransitionType.MECHANICAL) || t.getOldVal() == cond.getValue()) {
                    Assignment a = new Assignment(t.getVar().getId(), transitionValue.get(t), true);
                    assignments.add(a);
                }
            }
            result.formula.add(new MVClause(assignments));
        }
        
        // goal state constraints
        for (Condition cond : problem.getGoal()) {
            int varId = cond.getVariable().getId();
            assignments.clear();
            for (Transition t : transitionVariableIndex[varId]) {
                if (t.getNewVal() == cond.getValue()) {
                    Assignment a = new Assignment(timeShift(t.getVar().getId(), makespan - 1), transitionValue.get(t), true);
                    assignments.add(a);
                }
            }
            result.formula.add(new MVClause(assignments));
        }
        
        // universal state constraints
        for (int time = 0; time < makespan; time++) {
            // transition progression
            // transition at time T + 1 -> any compatible transition at time T
            if (time + 1 < makespan) {
                for (Transition t : transitions) {
                    if (t.getType().equals(TransitionType.MECHANICAL)) {
                        continue;
                    }
                    assignments.clear();
                    Assignment notTransition = new Assignment(timeShift(t.getVar().getId(), time + 1), transitionValue.get(t), false);
                    assignments.add(notTransition);
                    
                    int varId = t.getVar().getId();
                    for (Transition pt : transitionVariableIndex[varId]) {
                        if (pt.getNewVal() == t.getOldVal()) {
                            Assignment pastTransition = new Assignment(timeShift(pt.getVar().getId(), time), transitionValue.get(pt), true);
                            assignments.add(pastTransition);
                        }
                    }
                    result.formula.add(new MVClause(assignments));
                }
            }
            
            
            // composition of actions
            // action -> all its transitions
            for (SasAction action : problem.getOperators()) {
                Assignment nonAction = new Assignment(timeShift(actionVariable.get(action), time), actionValue.get(action), false);
                for (Transition t : transitionActionIndex[action.getId()]) {
                    Assignment actionTransition = new Assignment(timeShift(t.getVar().getId(), time), transitionValue.get(t), true);
                    MVClause clause = new MVClause(2);
                    clause.clause[0] = nonAction;
                    clause.clause[1] = actionTransition;
                    result.formula.add(clause);
                }
            }
            // action's existence
            // transition -> at least one of its actions
            for (Transition t : transitions) {
                if (t.getType().equals(TransitionType.PREVAILING)) {
                    continue;
                }
                Assignment notTransition = new Assignment(timeShift(t.getVar().getId(), time), transitionValue.get(t), false);
                assignments.clear();
                assignments.add(notTransition);
                for (SasAction action : t.getSupportingActions()) {
                    assignments.add(new Assignment(timeShift(actionVariable.get(action), time), actionValue.get(action), true));
                }
                result.formula.add(new MVClause(assignments));
            }
            // non-interference of actions
            for (SasAction[] pair : interferingActionPairs) {
                SasAction a1 = pair[0];
                SasAction a2 = pair[1];
                MVClause clause = new MVClause(2);
                clause.clause[0] = new Assignment(timeShift(actionVariable.get(a1), time), actionValue.get(a1), false);
                clause.clause[1] = new Assignment(timeShift(actionVariable.get(a2), time), actionValue.get(a2), false);
                result.formula.add(clause);
            }
        }
        return result;
    }

}
