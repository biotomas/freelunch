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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.Transition;
import freelunch.core.planning.model.Transition.TransitionType;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.IntVector;
import freelunch.core.utilities.Pair;

/**
 * FIXME unfinished not working
 * How to do this (efficiently)?
 * Na kazdu change transition max 1 multichange akcia
 * zakazat dvojice akci co maju konflikt na 2 premennych (oba change)
 * => pre kazdy transition mutex na vsetky jej multichange akcie <------ toto asi staci
 *
 * @author Tomas Balyo
 * 2.7.2013
 */
public class TransitionExistStepTranslator extends TranslatorBase implements SasToSatTranslator {
    
    public class TransitionComparator implements Comparator<Transition> {
        @Override
        public int compare(Transition o1, Transition o2) {
            if (o1.getOldVal() == o2.getOldVal()) {
                return o1.getNewVal() - o2.getNewVal();
            } else {
                return o1.getOldVal() - o2.getOldVal();
            }
        }
    }
    
    protected int[] transitionRanks;
    
    protected void initializeTransitionRanks() {
        transitionRanks = new int[transitions.size()];
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            List<Transition> trans = transitionVariableIndex[varId];
            Collections.sort(trans, new TransitionComparator());
            int rank = 0;
            for (Transition t : trans) {
                transitionRanks[t.getId()] = rank;
                rank++;
            }
        }
    }
    
    protected boolean[] variableAllowsMultiTransition;
    
    protected void initializeMultiTransitionVariables() {
        variableAllowsMultiTransition = new boolean[problem.getVariables().size()];
        //Arrays.fill(variableAllowsMultiTransition, false);
        //*
        Arrays.fill(variableAllowsMultiTransition, true);
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            if (!variableAllowsMultiTransition[varId]) {
                continue;
            }
            for (SasAction a : actionVariableIndex[varId]) {
                for (int xvarId : getActionScope(a)) {
                    if (xvarId != varId) {
                        variableAllowsMultiTransition[xvarId] = false;
                    }
                }
            }
        }/**/
    }
    
    @SuppressWarnings({ "unchecked", "unlikely-arg-type" })
    protected void sortActionsByTransitions(SasParallelPlan plan, int[] model) {
        int time = 0;
        List<Transition>[] activeTransitions = new List[problem.getVariables().size()];
        for (int i = 0; i < activeTransitions.length; i++) {
            activeTransitions[i] = new ArrayList<Transition>();
        }
        
        for (List<SasAction> horizon : plan.getPlan()) {
            for (int i = 0; i < activeTransitions.length; i++) {
                activeTransitions[i].clear();
            }
            for (Transition t : transitions) {
                int var = transitionVariables.getVariable(t.getId(), time);
                if (model[var] > 0) {
                    activeTransitions[t.getVar().getId()].add(t);
                }
            }
            for (int i = 0; i < activeTransitions.length; i++) {
                Collections.sort(activeTransitions[i], new TransitionComparator());
            }
            List<SasAction> actions = new ArrayList<SasAction>(horizon);
            horizon.clear();
            
            while (actions.size() > 0) {
                SasAction selected = null;
                actions:
                for (SasAction a : actions) {
                    for (Transition t : transitionActionIndex[a.getId()]) {
                        int varId = t.getVar().getId();
                        if (activeTransitions[varId].get(0).getId() != t.getId()) {
                            continue actions;
                        }
                    }
                    selected = a;
                    break;
                }
                if (selected == null) {
                    for (int varId = 0; varId < activeTransitions.length; varId++) {
                        if (activeTransitions[varId].isEmpty()) {
                            continue;
                        }
                        Transition t = activeTransitions[varId].get(0); 
                        if (!t.getType().equals(TransitionType.PREVAILING)) {
                            continue;
                        }
                        boolean hasSupport = false;
                        for (SasAction a : actions) {
                            if (a.getPreconditions().contains(t)) {
                                hasSupport = true;
                                break;
                            }
                        }
                        if (!hasSupport) {
                            activeTransitions[varId].remove(0);
                        }
                    }
                    continue;
                }
                horizon.add(selected);
                actions.remove(selected);
                for (Transition t : transitionActionIndex[selected.getId()]) {
                    int varId = t.getVar().getId();
                    activeTransitions[varId].remove(0);
                }
            }
            time++;
        }
    }
    
    @Override
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        SasParallelPlan plan = super.decodePlan(model, makespan);
        plan.getPlan().remove(plan.getPlanLength() - 1);
        sortActionsByTransitions(plan, model);
        return plan;
    }
    
    public void printTransitionPlan(int[] model, int makespan) {
        for (int time = 0; time < makespan; time++) {
            System.out.println("Transitions time " + time);
            for (Transition t : transitions) {
                int var = transitionVariables.getVariable(t.getId(), time);
                if (model[var] > 0) {
                    System.out.print(t.toString() + " ");
                }
            }
            System.out.println();
        }
    }


    public TransitionExistStepTranslator(SasProblem problem) {
        super(problem);
        initializeActionVariables();
        initializeAssignmentVariables();
        initializeTransitionVariables();
        initializeTransitionRanks();
        //computeInterferingActionPairsExistTransition();
        computeInterferingActionPairsTransitionBased();
        initializeActionVariableIndex();
        initializeMultiTransitionVariables();
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_initialAssignementsHold(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        //?universal_atLeastOneTransitionForEachVariable(solver, time);
        universal_transitionsImplyPreconditonAssignmentOrConnectingTransition(solver, time);
        
        
        //FIXME treba constrainty, ze neexistuje predosli transition co mi zrusi precondition => budu max 3 trans. (z toho stredny je prevail)
        universal_transitionsDestroyingPreconditionsForbidden(solver, time);
        
        universal_atMostOneAssignmentPerVariable(solver, time);
        
        universal_mutexActionPairsNogood(solver, time);

        universal_actionsImplyTheirTransitions(solver, time);
        universal_transitionsMustBeSupportedByAnAction(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_transitionsImplyEffectAssignmentOrConnectingTransition(solver, time);
        transition_assignmetMustBeSupportedByPreviousTransition(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }
    
    protected void computeInterferingActionPairsExistTransition() {
        interferingActionPairs = new HashSet<Pair<SasAction>>();
        for (Transition t : transitions) {
            for (SasAction a1 : t.getSupportingActions()) {
                for (SasAction a2 : t.getSupportingActions()) {
                    if (a1.getId() >= a2.getId()) {
                        continue;
                    }
                    // check if A1 an A2 have another common variable which they change differently
                    for (Transition ta1 : transitionActionIndex[a1.getId()]) {
                        if (ta1.getId() == t.getId()) {
                            continue;
                        }
                        for (Transition ta2 : transitionActionIndex[a2.getId()]) {
                            if (ta2.getVar().getId() == ta1.getVar().getId() && ta2.getId() != ta1.getId()) {
                                interferingActionPairs.add(new Pair<SasAction>(a1, a2));
                            }
                        }
                    }
                }
            }
        }
    }
    
    protected void transition_assignmetMustBeSupportedByPreviousTransition(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            for (int val = 0; val < var.getDomain(); val++) {
                lits.clear();
                lits.add(-assignmentVariables.getVariable(assignmentIds[varId][val], time+1));
                for (Transition t : transitionVariableIndex[varId]) {
                    if (t.getNewVal() == val) {
                        lits.add(transitionVariables.getVariable(t.getId(), time));
                    }
                }
                solver.addNewClause(lits);
            }
        }
    }
    
    protected void universal_transitionsImplyPreconditonAssignmentOrConnectingTransition(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.MECHANICAL)) {
                continue;
            }
            
            int tl = -transitionVariables.getVariable(t.getId(), time);
            lits.clear();
            lits.add(tl);
            
            lits.add(assignmentVariables.getVariable(assignmentIds[t.getVar().getId()][t.getOldVal()], time));
            
            if (variableAllowsMultiTransition[t.getVar().getId()]) {
                for (Transition pt : transitionVariableIndex[t.getVar().getId()]) {
                    if (pt.getNewVal() == t.getOldVal() && transitionRanks[pt.getId()] < transitionRanks[t.getId()]) {
                        lits.add(transitionVariables.getVariable(pt.getId(), time));
                    }
                }
            }
            solver.addNewClause(lits);
        }
    }
    
    protected void universal_transitionsDestroyingPreconditionsForbidden(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.MECHANICAL)) {
                continue;
            }
            if (variableAllowsMultiTransition[t.getVar().getId()]) {
                int tl = -transitionVariables.getVariable(t.getId(), time);
                for (Transition pt : transitionVariableIndex[t.getVar().getId()]) {
                    if (pt.getNewVal() != t.getOldVal() && transitionRanks[pt.getId()] < transitionRanks[t.getId()]) {
                        lits.clear();
                        lits.add(tl);
                        lits.add(-transitionVariables.getVariable(pt.getId(), time));
                        solver.addNewClause(lits);
                    }
                }
            }
        }
    }
    
    protected void transition_transitionsImplyEffectAssignmentOrConnectingTransition(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            int tl = -transitionVariables.getVariable(t.getId(), time);
            lits.clear();
            lits.add(tl);
            
            lits.add(assignmentVariables.getVariable(assignmentIds[t.getVar().getId()][t.getNewVal()], time+1));
            
            if (variableAllowsMultiTransition[t.getVar().getId()]) {
                for (Transition pt : transitionVariableIndex[t.getVar().getId()]) {
                    if (pt.getOldVal() == t.getNewVal() && transitionRanks[pt.getId()] > transitionRanks[t.getId()]) {
                        lits.add(transitionVariables.getVariable(pt.getId(), time));
                    }
                }
            }
            solver.addNewClause(lits);
        }
    }
    


}
