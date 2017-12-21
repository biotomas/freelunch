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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.NoopActionInfo;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.Transition;
import freelunch.core.planning.model.Transition.TransitionType;
import freelunch.core.planning.sase.sasToSat.TransitionGenerator;
import freelunch.core.satModelling.intModellers.IntVarGroupManager;
import freelunch.core.satModelling.intModellers.IntVarGroupManager.IntVarGroup;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.BasicSatFormulaGenerator;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.IntVector;
import freelunch.core.utilities.Pair;

public abstract class TranslatorBase extends ActionAssignmentTransitionIndices implements SasToSatTranslator {
    
    protected final IntVarGroupManager varManager;
    protected IntVarGroup assignmentVariables = null;
    protected IntVarGroup transitionVariables = null;
    protected IntVarGroup actionVariables;
    protected IntVarGroup chainVariables = null;
    protected IntVarGroup actionMutexHelperVariables = null;
    protected IntVarGroup varUnchangedVariables = null;
    protected IntVarGroup binaryActionVariables = null;
    

    protected Set<Pair<SasAction>> interferingActionPairs;
    protected Set<Pair<Condition>> mutexGroupPairs;
    protected List<List<Transition>> transitionMutexCliques;

    // removable goal condition IDs
    private final List<Integer> goalConditionIds = new ArrayList<Integer>();

    protected TranslatorBase(SasProblem problem) {
        varManager = new IntVarGroupManager();
        this.problem = problem;
        
        // initialize actions
        actions = new ArrayList<SasAction>();
        actions.addAll(problem.getOperators());
        int actionId = 0;
        for (SasAction a : actions) {
            a.setId(actionId);
            actionId++;
        }
    }
    
	public List<Integer> getActionVariables() {
		List<Integer> res = new ArrayList<Integer>();
        for (SasAction a : actions) {
        	res.add(actionVariables.getVariable(a.getId(), 0));
        }
		return res;
	}

    
    @Override
    public BasicSatFormula makeFormulaForMakespan(int makespan) {
        BasicSatFormulaGenerator sgen = new BasicSatFormulaGenerator();
        int vars = setMaxTimespan(makespan);
        sgen.setVariablesCount(vars);
        try {
            addInitialStateConstraints(sgen);
            for (int time = 0; time < makespan; time++) {
                addUniversalStateConstraints(sgen, time);
                addTransitionConstraints(sgen, time);
            }
            addUniversalStateConstraints(sgen, makespan);
            setGoalStateConstraints(sgen, makespan);
        } catch (SatContradictionException e) {
            return null;
        }
        BasicSatFormula formula = sgen.getFormula();
        return formula;
    }
    
    protected void addNoopActions() {
        int actionId = actions.size();
        for (StateVariable var : problem.getVariables()) {
            for (int value = 0; value < var.getDomain(); value++) {
                SasAction a = new SasAction(new NoopActionInfo(var, value));
                a.setPreconditions(new ArrayList<Condition>(1));
                a.getPreconditions().add(new Condition(var, value));
                a.setEffects(new ArrayList<Condition>());
                a.getEffects().add(new Condition(var, value));
                a.setId(actionId);
                actionId++;
                actions.add(a);
            }
        }
    }
    
    protected void initializeVarUnchangedVariables() {
        varUnchangedVariables = varManager.createNewVarGroup(2);
        varUnchangedVariables.setDimensionSize(0, problem.getVariables().size());
        varUnchangedVariables.setDimensionSize(1, 1);
    }
    
    protected void initializeBinaryActionVariables() {
        binaryActionVariables = varManager.createNewVarGroup(2);
        int log = 32 - Integer.numberOfLeadingZeros(actions.size() - 1);
        binaryActionVariables.setDimensionSize(0, log);
        binaryActionVariables.setDimensionSize(1, 1);
    }
    
    protected void transition_binaryActionsImplyEffects(IncrementalSatSolver solver, int time) throws SatContradictionException {
        int log = 32 - Integer.numberOfLeadingZeros(actions.size() - 1);
        int cap = 2 * Integer.highestOneBit(actions.size());
        int ind = 0;
        int count = 0;
        for (SasAction a : actions) {
            // first we get the binary code of the action
            int[] binCode = new int[log];
            Arrays.fill(binCode, -1);            
            if (count < cap - actions.size()) {
                for (int bit = 1; bit < log; bit++) {
                    int on = (ind & (1 << bit)) >> bit;
                    int val = 2*on - 1;
                    int lit = val * binaryActionVariables.getVariable(bit, time);
                    binCode[bit] = -lit;
                }
                ind += 2;
            } else {
                for (int bit = 0; bit < log; bit++) {
                    int on = (ind & (1 << bit)) >> bit;
                    int val = 2*on - 1;
                    int lit = val * binaryActionVariables.getVariable(bit, time);
                    binCode[bit] = -lit;
                }
                ind += 1;
            }
            count++;
            // shorter codes
            if (binCode[0] == -1) {
                binCode = Arrays.copyOfRange(binCode, 1, log);
            }
            // action implies its effects
            for (Condition c : a.getEffects()) {
                IntVector iv = new IntVector(binCode);
                iv.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time+1));
                solver.addNewClause(iv);
            }
        }
    }

    /**
     * variable assignment implies that the variable assignment was the the same in the previous state
     * or the variable value was changed
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_unchangedVarAssignmentsMustNotChange(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(3);
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            for (int val = 0; val < var.getDomain(); val++) {
                lits.clear();
                lits.add(-assignmentVariables.getVariable(assignmentIds[varId][val], time+1));
                lits.add(assignmentVariables.getVariable(assignmentIds[varId][val], time));
                lits.add(-varUnchangedVariables.getVariable(var.getId(), time));
                solver.addNewClause(lits);
            }
        }
    }
    
    protected void universal_binaryActionsImplyPreconditionsAndNoChanges(IncrementalSatSolver solver, int time) throws SatContradictionException {
        int log = 32 - Integer.numberOfLeadingZeros(actions.size() - 1);
        int cap = 2 * Integer.highestOneBit(actions.size());
        int ind = 0;
        int count = 0;
        for (SasAction a : actions) {
            // first we get the binary code of the action
            int[] binCode = new int[log];
            Arrays.fill(binCode, -1);            
            if (count < cap - actions.size()) {
                for (int bit = 1; bit < log; bit++) {
                    int on = (ind & (1 << bit)) >> bit;
                    int val = 2*on - 1;
                    int lit = val * binaryActionVariables.getVariable(bit, time);
                    binCode[bit] = -lit;
                }
                ind += 2;
            } else {
                for (int bit = 0; bit < log; bit++) {
                    int on = (ind & (1 << bit)) >> bit;
                    int val = 2*on - 1;
                    int lit = val * binaryActionVariables.getVariable(bit, time);
                    binCode[bit] = -lit;
                }
                ind += 1;
            }
            count++;
            // shorter codes
            if (binCode[0] == -1) {
                binCode = Arrays.copyOfRange(binCode, 1, log);
            }
            

            // action implies its preconditions
            for (Condition c : a.getPreconditions()) {
                IntVector iv = new IntVector(binCode);
                iv.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time));
                solver.addNewClause(iv);
            }
            // action implies no changes
            Collection<Integer> scope = getEffectsScope(a);
            for (StateVariable var : problem.getVariables()) {
                if (scope.contains(var.getId())) {
                    continue;
                }
                IntVector iv = new IntVector(binCode);
                iv.add(varUnchangedVariables.getVariable(var.getId(), time));
                solver.addNewClause(iv);
            }
            
            //solver.addNewClause(vec);
        }
    }
    
    protected void initializeActionMutexHelperVariables() {
        int helpers = 0;
        for (Set<SasAction> grp : actionVariableIndex) {
            if (grp.size() < 3) {
                continue;
            }
            int log = 32 - Integer.numberOfLeadingZeros(grp.size());
            helpers += log;
        }
        actionMutexHelperVariables = varManager.createNewVarGroup(2);
        actionMutexHelperVariables.setDimensionSize(0, helpers);
        actionMutexHelperVariables.setDimensionSize(1, 1);
    }
    
    protected void universal_actionMutexQuadratic(IncrementalSatSolver solver, int time) throws SatContradictionException {
        for (Set<SasAction> grp : actionVariableIndex) {
            if (grp.size() < 2) {
                continue;
            }
            IntVector vec = new IntVector(2);
            List<SasAction> acts = new ArrayList<>(grp);
            for (int i = 0; i < acts.size(); i++) {
                for (int j = i+1; j < acts.size(); j++) {
                    vec.clear();
                    vec.add(-actionVariables.getVariable(acts.get(i).getId(), time));
                    vec.add(-actionVariables.getVariable(acts.get(j).getId(), time));
                    solver.addNewClause(vec);
                }
            }
        }
    }
    
    /**
     * Binary encoding of action mutex. We use the binary encoding of the at-most-one constraint.
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_actionMutexBinaryEnc(IncrementalSatSolver solver, int time) throws SatContradictionException {
        int pos = 0;
        for (Set<SasAction> grp : actionVariableIndex) {
            if (grp.size() < 2) {
                continue;
            }
            if (grp.size() == 2) {
                IntVector vec = new IntVector(2);
                List<SasAction> acts = new ArrayList<>(grp);
                vec.add(-actionVariables.getVariable(acts.get(0).getId(), time));
                vec.add(-actionVariables.getVariable(acts.get(1).getId(), time));
                solver.addNewClause(vec);
                continue;
            }
            
            int log = 32 - Integer.numberOfLeadingZeros(grp.size());
            int cap = 2 * Integer.highestOneBit(grp.size());
            int ind = 0;
            int count = 0;
            IntVector vec = new IntVector(1+log);
            for (SasAction a : grp) {
                vec.clear();
                vec.add(actionVariables.getVariable(a.getId(), time));
                
                if (count + 1 < cap - grp.size()) {
                    for (int bit = 1; bit < log; bit++) {
                        int on = (ind & (1 << bit)) >> bit;
                        int val = 2*on - 1;
                        int lit = val * actionMutexHelperVariables.getVariable(pos+bit, time); 
                        vec.add(-lit);
                        solver.addNewClause(new IntVector(new int[] {-actionVariables.getVariable(a.getId(), time), lit}));
                    }
                    ind += 2;
                } else {
                    for (int bit = 0; bit < log; bit++) {
                        int on = (ind & (1 << bit)) >> bit;
                        int val = 2*on - 1;
                        int lit = val * actionMutexHelperVariables.getVariable(pos+bit, time); 
                        vec.add(-lit);
                        solver.addNewClause(new IntVector(new int[] {-actionVariables.getVariable(a.getId(), time), lit}));
                    }
                    ind += 1;
                }
                solver.addNewClause(vec);
                count++;
            }
            pos += log;
        }
    }

    protected void initializeActionVariables() {
        actionVariables = varManager.createNewVarGroup(2);
        actionVariables.setDimensionSize(0, actions.size());
        actionVariables.setDimensionSize(1, 1);
    }
    
    protected void initializeTransitionVariables() {
        TransitionGenerator tgen = new TransitionGenerator(problem, actions);
        transitions = tgen.getTransitionList();
        transitionVariableIndex = tgen.getTransitionsByVariables();
        transitionActionIndex = tgen.getTransitionByActions();
        
        transitionVariables = varManager.createNewVarGroup(2);
        transitionVariables.setDimensionSize(0, transitions.size());
        transitionVariables.setDimensionSize(1, 1);
    }
    
    
    
    protected void initializeAssignmentVariables() {
        if (assignmentIds == null) {
            initializeAssignmentIDs();
        }
        assignmentVariables = varManager.createNewVarGroup(2);
        assignmentVariables.setDimensionSize(0, totalAssignments);
        assignmentVariables.setDimensionSize(1, 1);
    }

    protected void initializeAssignmentIndex() {
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
     * Compute non-trivial (use different variables) MUTEX group pairs
     */
    protected void computeMutexGroupPairs() {
        mutexGroupPairs = new HashSet<>();
        if (problem.getMutexGroups() == null) {
            return;
        }
        for (List<Condition> mutexGroup : problem.getMutexGroups()) {
            for (int i1 = 0; i1 < mutexGroup.size(); i1++) {
                for (int i2 = i1+1; i2 < mutexGroup.size(); i2++) {
                    Condition c1 = mutexGroup.get(i1);
                    Condition c2 = mutexGroup.get(i2);
                    if (c1.getVariable().getId() != c2.getVariable().getId()) {
                        mutexGroupPairs.add(new Pair<Condition>(c1, c2));
                    }
                }
            }
        }
    }
    
    /**
     * Compute transition mutEx cliques as defined in the Plansig2011 paper
     */
    protected void computeTransitionMutexCliques() {
        transitionMutexCliques = new ArrayList<List<Transition>>(problem.getVariables().size());
        for (StateVariable var : problem.getVariables()) {
            transitionMutexCliques.add(transitionVariableIndex[var.getId()]);
        }
    }
    
    /**
     * Compute transition mutEx cliques as defined in the original SASE article
     */
    protected void computeTransitionMutexCliquesBySase() {
        transitionMutexCliques = new ArrayList<List<Transition>>(problem.getVariables().size());
        List<Transition> mechanical = new ArrayList<Transition>();
        List<Transition> normal = new ArrayList<Transition>();
        for (StateVariable var : problem.getVariables()) {
            mechanical.clear();
            normal.clear();
            for (Transition t : transitionVariableIndex[var.getId()]) {
                if (t.getType() == TransitionType.MECHANICAL) {
                    mechanical.add(t);
                } else {
                    normal.add(t);
                }
            }
            if (normal.size() > 1) {
                transitionMutexCliques.add(new ArrayList<Transition>(normal));
            }
            if (mechanical.size() > 1) {
                transitionMutexCliques.add(new ArrayList<Transition>(mechanical));
            }
            for (Transition m : mechanical) {
                for (Transition t : normal) {
                    if (m.getNewVal() != t.getNewVal()) {
                        transitionMutexCliques.add(Arrays.asList(m, t));
                    }
                }
            }
        }
    }



    /**
     * Two actions interfere if one destroys the precondition of the other
     */
    protected void computeInterferingActionPairsClassic() {
        interferingActionPairs = new HashSet<Pair<SasAction>>();
        for (SasAction a1 : actions) {
            for (SasAction a2 : actions) {
                if (a1.getId() >= a2.getId()) {
                    // save some time on symmetry
                    continue;
                }
                if (actionsInterfering(a1, a2)) {
                    interferingActionPairs.add(new Pair<SasAction>(a1, a2));
                }
            }
        }
    }
    
    /**
     * Two actions interfere if their preconditions and effects are compatible
     * and they share variables. 
     */
    protected void computeInterferingActionPairsMinimal() {
        interferingActionPairs = new HashSet<Pair<SasAction>>();

        for (List<SasAction> candidates : assignmentSupportingActions) {
            // a1 and a2 have at least one common effect
            for (SasAction a1 : candidates) {
                for (SasAction a2 : candidates) {
                    if (a1.getId() >= a2.getId()) {
                        // save some time on symmetry
                        continue;
                    }
                    
                    // check if the preconditions are compatible
                    if (!conditionsCompatible(a1.getPreconditions(), a2.getPreconditions())) {
                        continue;
                    }
                    // check if the effects are compatible
                    if (!conditionsCompatible(a1.getEffects(), a2.getEffects())) {
                        continue;
                    }
                    
                    // check if they share a variable
                    Collection<Integer> a2Scope = getActionScope(a2);
                    for (int v1 : getActionScope(a1)) {
                        if (a2Scope.contains(v1)) {
                            interferingActionPairs.add(new Pair<SasAction>(a1, a2));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Two sets of conditions are compatible if there is no pair on
     * the same variable but different values.
     * @param c1
     * @param c2
     * @return
     */
    private boolean conditionsCompatible(List<Condition> c1, List<Condition> c2) {
        for (Condition p1 : c1) {
            for (Condition p2 : c2) {
                if (p1.getVariable().getId() == p2.getVariable().getId()) {
                    if (p1.getValue() != p2.getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    protected void computeInterferingActionsAll() {
        interferingActionPairs = new HashSet<Pair<SasAction>>();
        for (SasAction a1 : actions) {
            for (SasAction a2 : actions) {
                if (a1.getId() < a2.getId()) {
                    // save some time on symmetry
                    interferingActionPairs.add(new Pair<SasAction>(a1, a2));
                }
            }
        }   	
    }
    
    //TODO remove transition dependency
    protected void computeInterferingActionPairsTransitionBased() {
        interferingActionPairs = new HashSet<Pair<SasAction>>();
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            List<Transition> transitions = transitionVariableIndex[varId];
            for (Transition transition : transitions) {
                if (transition.getType().equals(TransitionType.PREVAILING)) {
                    continue;
                }
                Set<SasAction> supports = transition.getSupportingActions();
                if (supports.size() > 1) {
                    for (SasAction a1 : supports) {
                        for (SasAction a2 : supports) {
                            if (a1.getId() < a2.getId()) {
                                interferingActionPairs.add(new Pair<SasAction>(a1, a2));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Two actions are interfering if one destroys a precondition of the other
     * @param a1
     * @param a2
     * @return
     */
    private boolean actionsInterfering(SasAction a1, SasAction a2) {
        for (Condition prec : a1.getPreconditions()) {
            for (Condition effect : a2.getEffects()) {
                if (effect.getVariable().getId() == prec.getVariable().getId()) {
                    if (effect.getValue() != prec.getValue()) {
                        return true;
                    }
                }
            }
        }
        for (Condition prec : a2.getPreconditions()) {
            for (Condition effect : a1.getEffects()) {
                if (effect.getVariable().getId() == prec.getVariable().getId()) {
                    if (effect.getValue() != prec.getValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Variable value assignments in the end of the first step must be supported by
     * an action in the first step or be in the initial state.
     * requires <i>assignmentVariables</i> and <i>assignmentSupportingActions</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void initial_assignmentsMustBeSupportedByActionsOrInitialState(IncrementalSatSolver solver) throws SatContradictionException {
        Set<Integer> initialAssignments = new HashSet<>(); 
        for (Condition c : problem.getInitialState()) {
            int varValId = assignmentIds[c.getVariable().getId()][c.getValue()];
            initialAssignments.add(varValId);
        }
        IntVector vec = new IntVector(10);
        for (int varValId = 0; varValId < assignmentSupportingActions.length; varValId++) {
            if (initialAssignments.contains(varValId)) {
                continue;
            }
            vec.clear();
            vec.add(-assignmentVariables.getVariable(varValId, 0));
            for (SasAction a : assignmentSupportingActions[varValId]) {
                vec.add(actionVariables.getVariable(a.getId(), 0));
            }
            solver.addNewClause(vec);
        }
    }
    
    /**
     * Add a unit clause for each action that is not applicable in the initial state.
     * @param solver
     * @throws SatContradictionException
     */
    protected void initial_inapplicableActionsForbidden(IncrementalSatSolver solver) throws SatContradictionException {
        int[] initialState = new int[problem.getVariables().size()];
        for (Condition c : problem.getInitialState()) {
            initialState[c.getVariable().getId()] = c.getValue();
        }
        for (SasAction a : actions) {
            for (Condition c : a.getPreconditions()) {
                int varId = c.getVariable().getId();
                int value = c.getValue();
                if (initialState[varId] != value) {
                    // action is not applicable
                    solver.addNewClause(new IntVector(new int[] {-actionVariables.getVariable(a.getId(), 0)}));
                    break;
                }
            }
        }
    }

    /**
     * Add a unit clause for each transition that is inapplicable in the initial state
     * requires <i>transitionVariables</i>
     * @param solver
     * @throws SatContradictionException
     */
    protected void initial_inapplicableTransitionForbidden(IncrementalSatSolver solver) throws SatContradictionException {
        IntVector vars = new IntVector(1);
        int[] istate = new int[problem.getVariables().size()];
        for (Condition c : problem.getInitialState()) {
            istate[c.getVariable().getId()] = c.getValue();
        }
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.MECHANICAL)) {
                continue;
            }
            if (istate[t.getVar().getId()] != t.getOldVal()) {
                vars.clear();
                vars.add(-transitionVariables.getVariable(t.getId(), 0));
                solver.addNewClause(vars);
            }
        }
    }
    
    /**
     * Add a clause for each state variable containing its allowed transitions from the initial state
     * requires <i>transitionVariables</i>
     * @param solver
     * @throws SatContradictionException
     */
    protected void initial_atLeastOneApplicableTransition(IncrementalSatSolver solver) throws SatContradictionException {
        IntVector vars = new IntVector(10);
        for (Condition cond : problem.getInitialState()) {
            int varId = cond.getVariable().getId();
            vars.clear();
            for (Transition t : transitionVariableIndex[varId]) {
                if (t.getType().equals(TransitionType.MECHANICAL) || t.getOldVal() == cond.getValue()) {
                    vars.add(transitionVariables.getVariable(t.getId(), 0));
                }
            }
            solver.addNewClause(vars);
        }
    }
    
    /**
     * Add a unit clause for each assignment that holds in the initial state.
     * requires <i>assignmentVariables</i>
     * @param solver
     * @throws SatContradictionException
     */
    protected void initial_initialAssignementsHold(IncrementalSatSolver solver) throws SatContradictionException {
        for (Condition c : problem.getInitialState()) {
            int varValId = assignmentIds[c.getVariable().getId()][c.getValue()];
            solver.addNewClause(new IntVector(new int[] {assignmentVariables.getVariable(varValId, 0)}));
        }
    }

    /**
     * At most one value per state variable is allowed.
     * requires <i>assignmentVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_atMostOneAssignmentPerVariable(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(10);
        for (StateVariable var : problem.getVariables()) {
            vec.clear();
            for (int d = 0; d < var.getDomain(); d++) {
                vec.add(assignmentVariables.getVariable(assignmentIds[var.getId()][d], time));
            }
            solver.addAtMostOneConstraint(vec);
        }
    }
    
    /**
     * At most one action per step is allowed.
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_atMostOneAction(IncrementalSatSolver solver, int time) throws SatContradictionException {
    	//TODO fixme
        IntVector vec = new IntVector(10);
        for (SasAction a : actions) {
        	vec.add(actionVariables.getVariable(a.getId(), time));
        }
        solver.addAtMostOneConstraint(vec);
    }
    
    protected void universal_transitionsImplyPreconditonAssignment(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.MECHANICAL)) {
                continue;
            }
            int tl = -transitionVariables.getVariable(t.getId(), time);
            lits.clear();
            lits.add(tl);
            lits.add(assignmentVariables.getVariable(assignmentIds[t.getVar().getId()][t.getOldVal()], time));
            solver.addNewClause(lits);
        }
    }

    protected void universal_transitionsImplyEffectAssignmentNow(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            int tl = -transitionVariables.getVariable(t.getId(), time);
            lits.clear();
            lits.add(tl);
            lits.add(assignmentVariables.getVariable(assignmentIds[t.getVar().getId()][t.getNewVal()], time));
            solver.addNewClause(lits);
        }
    }

    protected void transition_transitionsImplyPreconditonAssignmentInPrevTime(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.MECHANICAL)) {
                continue;
            }
            int tl = -transitionVariables.getVariable(t.getId(), time+1);
            lits.clear();
            lits.add(tl);
            lits.add(assignmentVariables.getVariable(assignmentIds[t.getVar().getId()][t.getOldVal()], time));
            solver.addNewClause(lits);
        }
    }

    protected void transition_transitionsImplyEffectAssignment(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            int tl = -transitionVariables.getVariable(t.getId(), time);
            lits.clear();
            lits.add(tl);
            lits.add(assignmentVariables.getVariable(assignmentIds[t.getVar().getId()][t.getNewVal()], time+1));
            solver.addNewClause(lits);
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

    protected void universal_assignmetMustBeSupportedTransition(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        
        for (StateVariable var : problem.getVariables()) {
            int varId = var.getId();
            for (int val = 0; val < var.getDomain(); val++) {
                lits.clear();
                lits.add(-assignmentVariables.getVariable(assignmentIds[varId][val], time));
                for (Transition t : transitionVariableIndex[varId]) {
                    if (t.getNewVal() == val) {
                        lits.add(transitionVariables.getVariable(t.getId(), time));
                    }
                }
                solver.addNewClause(lits);
            }
        }
    }

    /**
     * At least one action must occur for each state variable.
     * requires <i>actionVariablesIndex</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_atLeastOneActionForEachVariable(IncrementalSatSolver solver, int time) throws SatContradictionException {
        for (int var = 0; var < problem.getVariables().size(); var++) {
            Set<SasAction> varActions = actionVariableIndex[var];
            IntVector vec = new IntVector(varActions.size());
            for (SasAction a : varActions) {
                vec.add(actionVariables.getVariable(a.getId(), time));
            }
            solver.addNewClause(vec);
        }
    }
    
    /**
     * Each transition implies that at least one of its supporting actions must be true.
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_transitionsMustBeSupportedByAnAction(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.PREVAILING)) {
                continue;
            }
            int tl = -transitionVariables.getVariable(t.getId(), time);
            lits.clear();
            lits.add(tl);
            for (SasAction action : t.getSupportingActions()) {
                lits.add(actionVariables.getVariable(action.getId(), time));
            }
            solver.addNewClause(lits);
        }
    }

    /**
     * Each action implies its associated transitions
     * requires <i>transitionVariables</i> and
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_actionsImplyTheirTransitions(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(2);
        for (SasAction action : problem.getOperators()) {
            int l = -actionVariables.getVariable(action.getId(), time);
            for (Transition t : transitionActionIndex[action.getId()]) {
                int tv = transitionVariables.getVariable(t.getId(), time);
                lits.clear();
                lits.add(l);
                lits.add(tv);
                solver.addNewClause(lits);
            }
        }
    }
    
    /**
     * At least one transition must be true for each state variable
     * requires <i>transitionVariables</i> and
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_atLeastOneTransitionForEachVariable(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(10);
        for (StateVariable svar : problem.getVariables()) {
            lits.clear();
            for (Transition t : transitionVariableIndex[svar.getId()]) {
                lits.add(transitionVariables.getVariable(t.getId(), time));
            }
            solver.addNewClause(lits);
        }
    }
    
    /**
     * At most transition must be true from each transition mutEx clique
     * requires <i>transitionVariables</i> and <i>transitionMutexCliques</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_transitionMutex(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(10);
        for (List<Transition> clique : transitionMutexCliques) {
            lits.clear();
            for (Transition t : clique) {
                lits.add(transitionVariables.getVariable(t.getId(), time));
            }
            solver.addAtMostOneConstraint(lits);
        }
    }

    
    /**
     * Action implies its preconditions.
     * requires <i>assignmentVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_actionRequiresItsPreconditions(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPreconditions()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time));
                solver.addNewClause(vec);
            }
        }
    }
    
    /**
     * Action implies its effects.
     * requires <i>assignmentVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_actionImpliesItsEffects(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPrevailConditions()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time));
                solver.addNewClause(vec);
            }
            for (Condition c : a.getEffects()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time));
                solver.addNewClause(vec);
            }
        }

    }
    
    protected void universal_mutexGroups(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (Pair<Condition> mutexPair : mutexGroupPairs) {
            Condition c1 = mutexPair.first;
            Condition c2 = mutexPair.second;
            vec.clear();
            vec.add(-assignmentVariables.getVariable(assignmentIds[c1.getVariable().getId()][c1.getValue()], time));
            vec.add(-assignmentVariables.getVariable(assignmentIds[c2.getVariable().getId()][c2.getValue()], time));
            solver.addNewClause(vec);
        }
    }

    /**
     * Binary clauses forbidding pairwise mutEx actions
     * requires <i>interferingActionPairs</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void universal_mutexActionPairsNogood(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (Pair<SasAction> actionPair : interferingActionPairs) {
            vec.clear();
            vec.add(-actionVariables.getVariable(actionPair.first.getId(), time));
            vec.add(-actionVariables.getVariable(actionPair.second.getId(), time));
            solver.addNewClause(vec);
        }
    }
    
    /**
     * Variable value assignments in the next time window must be supported by
     * an action in the given time window. Requires NOOP actions.
     * requires <i>assignmentVariables</i> and <i>assignmentSupportingActions</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_assignmentsMustBeSupportedByActions(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(10);
        for (int varValId = 0; varValId < assignmentSupportingActions.length; varValId++) {
            vec.clear();
            vec.add(-assignmentVariables.getVariable(varValId, time+1));
            for (SasAction a : assignmentSupportingActions[varValId]) {
                vec.add(actionVariables.getVariable(a.getId(), time));
            }
            solver.addNewClause(vec);
        }
    }
    
    /**
     * Variable value assignments in the next time window must be supported by
     * an action or a current assignment in the given time window. Actions must imply
     * their effects for this to be sufficient. 
     * requires <i>assignmentVariables</i> and <i>assignmentSupportingActions</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_assignmentsMustBeSupportedByActionsOrAssignments(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(10);
        for (int varValId = 0; varValId < assignmentSupportingActions.length; varValId++) {
            vec.clear();
            vec.add(-assignmentVariables.getVariable(varValId, time+1));
            // assignment holds in the previous time
            vec.add(assignmentVariables.getVariable(varValId, time));
            // or assignment is set by an action
            for (SasAction a : assignmentSupportingActions[varValId]) {
                vec.add(actionVariables.getVariable(a.getId(), time));
            }
            solver.addNewClause(vec);
        }
    }
    
    protected void transition_assignmentsMustBeSupportedByActionsNowOrAssignmentsBefore(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(10);
        for (int varValId = 0; varValId < assignmentSupportingActions.length; varValId++) {
            vec.clear();
            vec.add(-assignmentVariables.getVariable(varValId, time+1));
            // assignment holds in the previous time
            vec.add(assignmentVariables.getVariable(varValId, time));
            // or assignment is set by an action now
            for (SasAction a : assignmentSupportingActions[varValId]) {
                vec.add(actionVariables.getVariable(a.getId(), time+1));
            }
            solver.addNewClause(vec);
        }
    }
    
    protected void transition_actionsImplyEffects(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPrevailConditions()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time+1));
                solver.addNewClause(vec);
            }
            for (Condition c : a.getEffects()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time+1));
                solver.addNewClause(vec);
            }
        }
    }
    
    /**
     * Each action at the following time forbids all actions in the given time
     * that destroy its precondition.
     * requires <i>actionOpposingActions</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_actionDisablesPreviousOpposingActions(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (SasAction opponent : actionOpposingActions[a.getId()]) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time + 1));
                vec.add(-actionVariables.getVariable(opponent.getId(), time));
                solver.addNewClause(vec);
            }
        }
    }
    
    /**
     * Each transition at the following time implies that at least compatible transition must
     * be true in the current time.
     * requires <i>transitionVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_transitionImpliesAtLeastOnePreviousCompatibleTransition(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(10);
        for (Transition t : transitions) {
            if (t.getType().equals(TransitionType.MECHANICAL)) {
                continue;
            }
            lits.clear();
            lits.add(-transitionVariables.getVariable(t.getId(), time+1));
            int varId = t.getVar().getId();
            for (Transition pt : transitionVariableIndex[varId]) {
                if (pt.getNewVal() == t.getOldVal()) {
                    lits.add(transitionVariables.getVariable(pt.getId(), time));
                }
            }
            solver.addNewClause(lits);
        }
    }
    
    /**
     * Each transition in the current time implies that at least compatible transition must
     * be true in the following time.
     * requires <i>transitionVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_transitionImpliesAtLeastOneFollowingCompatibleTransition(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(10);
        for (Transition t : transitions) {
            lits.clear();
            lits.add(-transitionVariables.getVariable(t.getId(), time));
            int varId = t.getVar().getId();
            for (Transition pt : transitionVariableIndex[varId]) {
                if (pt.getType().equals(TransitionType.MECHANICAL) || pt.getOldVal() == t.getNewVal()) {
                    lits.add(transitionVariables.getVariable(pt.getId(), time+1));
                }
            }
            solver.addNewClause(lits);
        }
    }
    
    /**
     * Action implies its preconditions.
     * requires <i>assignmentVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void transition_actionRequiresItsPreconditions(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(2);
        for (SasAction a : actions) {
            for (Condition c : a.getPreconditions()) {
                vec.clear();
                vec.add(-actionVariables.getVariable(a.getId(), time+1));
                vec.add(assignmentVariables.getVariable(assignmentIds[c.getVariable().getId()][c.getValue()], time));
                solver.addNewClause(vec);
            }
        }
    }

    
    /**
     * Unit clauses forbid actions that have effects opposing a goal condition.
     * requires <i>goalOpposingActions</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void goal_actionsOpposingGoalConditionsDisabled(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goalConditionIds.clear();
        // actions opposing a goal condition are forbidden
        for (SasAction action : goalOpposingActions) {
            IntVector vec = new IntVector(new int[] {-actionVariables.getVariable(action.getId(), time)});
            int constraintId = solver.addRemovableClause(vec);
            goalConditionIds.add(constraintId);
        }
    }
    
    /**
     * Add a unit clause for each assignment that holds in the goal state.
     * requires <i>assignmentVariables</i>
     * @param solver
     * @throws SatContradictionException
     */
    protected void goal_goalAssignementsHold(IncrementalSatSolver solver, int time) throws SatContradictionException {
        for (Condition c : problem.getGoal()) {
            int varValId = assignmentIds[c.getVariable().getId()][c.getValue()];
            solver.addNewClause(new IntVector(new int[] {assignmentVariables.getVariable(varValId, time)}));
        }
    }

    /**
     * Variable value assignments in the goal must be supported by an action
     * requires <i>assignmentVariables</i> and <i>assignmentSupportingActions</i>
     * and NOOP actions
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void goal_goalAssignmentMustBeSupportedByActions(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(10);
        for (Condition c : problem.getGoal()) {
            vec.clear();
            int varValId = assignmentIds[c.getVariable().getId()][c.getValue()];
            //vec.add(assignmentVariables.getVariable(varValId, time));
            for (SasAction a : assignmentSupportingActions[varValId]) {
                vec.add(actionVariables.getVariable(a.getId(), time));
            }
            int cid = solver.addRemovableClause(vec);
            goalConditionIds.add(cid);
        }
    }
    
    /**
     * Variable value assignments in the goal must be supported by an action
     * requires <i>assignmentVariables</i> and <i>assignmentSupportingActions</i>
     * Actions must imply their effects.
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void goal_goalAssignmentMustBeSupportedByActionsOrAssignments(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector vec = new IntVector(10);
        for (Condition c : problem.getGoal()) {
            vec.clear();
            int varValId = assignmentIds[c.getVariable().getId()][c.getValue()];
            vec.add(assignmentVariables.getVariable(varValId, time));
            for (SasAction a : assignmentSupportingActions[varValId]) {
                vec.add(actionVariables.getVariable(a.getId(), time));
            }
            int cid = solver.addRemovableClause(vec);
            goalConditionIds.add(cid);
        }
    }
    
    /**
     * For each goal variable at least one compatible transition must be true.
     * requires <i>transitionVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void goal_atLeastOneTransitionSupportingGoal(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(10);
        for (Condition cond : problem.getGoal()) {
            int varId = cond.getVariable().getId();
            lits.clear();
            for (Transition t : transitionVariableIndex[varId]) {
                if (t.getNewVal() == cond.getValue()) {
                    lits.add(transitionVariables.getVariable(t.getId(), time));
                }
            }
            int constrId = solver.addRemovableClause(lits);
            goalConditionIds.add(constrId);
        }
    }
    
    /**
     * Unit clauses that forbid each transition that destroy a goal condition.
     * requires <i>transitionVariables</i>
     * @param solver
     * @param time
     * @throws SatContradictionException
     */
    protected void goal_transitionsDestroyingGoalForbidden(IncrementalSatSolver solver, int time) throws SatContradictionException {
        IntVector lits = new IntVector(1);
        for (Condition cond : problem.getGoal()) {
            for (Transition t : transitionVariableIndex[cond.getVariable().getId()]) {
                if (t.getNewVal() != cond.getValue()) {
                    lits.clear();
                    lits.add(-transitionVariables.getVariable(t.getId(), time));
                    int constrId = solver.addRemovableClause(lits);
                    goalConditionIds.add(constrId);
                }
            }
        }
    }

    @Override
    public int setMaxTimespan(int time) {
        if (actionVariables != null) {
            actionVariables.setDimensionSize(1, time + 1);
        }
        if (assignmentVariables != null) {
            assignmentVariables.setDimensionSize(1, time + 1);
        }
        if (transitionVariables != null) {
            transitionVariables.setDimensionSize(1, time + 1);
        }
        if (chainVariables != null) {
            chainVariables.setDimensionSize(1, time + 1);
        }
        if (actionMutexHelperVariables != null) {
            actionMutexHelperVariables.setDimensionSize(1, time+1);
        }
        if (binaryActionVariables != null) {
            binaryActionVariables.setDimensionSize(1, time+1);
        }
        if (varUnchangedVariables != null) {
            varUnchangedVariables.setDimensionSize(1, time+1);
        }
        return varManager.getTotalVarsCount();
    }

    @Override
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        List<List<SasAction>> plan = new ArrayList<List<SasAction>>(makespan);
        List<SasAction> selectedActions = new ArrayList<SasAction>();
        for (int time = 0; time <= makespan; time++) {
            selectedActions.clear();
            for (SasAction action : problem.getOperators()) {
                int var = actionVariables.getVariable(action.getId(), time);
                if (model[var] > 0) {
                    selectedActions.add(action);
                }
            }
            plan.add(new ArrayList<SasAction>(selectedActions));
        }
        return new SasParallelPlan(plan);
    }

    @Override
    public SasParallelPlan decodePlan(List<int[]> model) {
        List<List<SasAction>> plan = new ArrayList<List<SasAction>>(model.size());
        List<SasAction> selectedActions = new ArrayList<SasAction>();
        for (int[] subModel : model) {
            selectedActions.clear();
            for (SasAction action : problem.getOperators()) {
                int var = actionVariables.getVariable(action.getId(), 0);
                if (subModel[var] > 0) {
                    selectedActions.add(action);
                }
            }
            plan.add(new ArrayList<SasAction>(selectedActions));
        }
        return new SasParallelPlan(plan);
    }

    @Override
    public void unsetLastGoalStateConstraints(IncrementalSatSolver solver) {
        for (int constrId : goalConditionIds) {
            solver.removeClause(constrId);
        }
        goalConditionIds.clear();
    }

}
