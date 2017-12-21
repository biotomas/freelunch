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
import java.util.List;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.BasicSatFormulaGenerator;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;

public class DisertDirectTranslator extends TranslatorBase implements SasToSatTranslator {

    public DisertDirectTranslator(SasProblem problem) {
        super(problem);
        initializeActionVariables();
        initializeAssignmentVariables();
        initializeAssignmentSupportingActions();
        computeInterferingActionPairsMinimal();
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_initialAssignementsHold(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_atMostOneAssignmentPerVariable(solver, time);
        universal_actionRequiresItsPreconditions(solver, time);
        universal_mutexActionPairsNogood(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_assignmentsMustBeSupportedByActionsOrAssignments(solver, time);
        transition_actionsImplyEffects(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }
    
    @Override
    public BasicSatFormula makeFormulaForMakespan(int makespan) {
        BasicSatFormulaGenerator sgen = new BasicSatFormulaGenerator();
        actionVariables.setDimensionSize(1, makespan);
        assignmentVariables.setDimensionSize(1, makespan + 1);
        int vars = varManager.getTotalVarsCount();
        sgen.setVariablesCount(vars);
        try {
            addInitialStateConstraints(sgen);
            for (int time = 0; time < makespan; time++) {
                addUniversalStateConstraints(sgen, time);
                addTransitionConstraints(sgen, time);
            }
            universal_atMostOneAssignmentPerVariable(sgen, makespan);
            setGoalStateConstraints(sgen, makespan);
        } catch (SatContradictionException e) {
            return null;
        }
        BasicSatFormula formula = sgen.getFormula();
        return formula;
    }

    @Override
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        List<List<SasAction>> plan = new ArrayList<List<SasAction>>(makespan);
        List<SasAction> selectedActions = new ArrayList<SasAction>();
        for (int time = 0; time < makespan; time++) {
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

}
