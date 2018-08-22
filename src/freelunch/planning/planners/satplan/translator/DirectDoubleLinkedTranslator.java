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
package freelunch.planning.planners.satplan.translator;

import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.sat.model.IncrementalSatSolver;
import freelunch.sat.model.SatContradictionException;

public class DirectDoubleLinkedTranslator extends TranslatorBase implements SasToSatTranslator {

    public DirectDoubleLinkedTranslator(SasProblem problem) {
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
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        SasParallelPlan p = super.decodePlan(model, makespan);
        p.getPlan().remove(p.getPlan().size() - 1);
        return p;
    }

}
