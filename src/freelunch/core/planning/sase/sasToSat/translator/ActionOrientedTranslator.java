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

import freelunch.core.planning.model.SasProblem;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;

/**
 * SAT translation using only action variables.
 *
 * @author Tomas Balyo
 * 21.4.2013
 */
public class ActionOrientedTranslator extends TranslatorBase implements SasToSatTranslator {
    
    public ActionOrientedTranslator(SasProblem problem) {
        super(problem);
        addNoopActions();
        initializeActionVariableIndex();
        initializeActionVariables();
        initializeActionOpposingActions();
        initializeGoalOpposingActions();
        computeInterferingActionPairsClassic();
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_inapplicableActionsForbidden(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_atLeastOneActionForEachVariable(solver, time);
        universal_mutexActionPairsNogood(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_actionDisablesPreviousOpposingActions(solver, time);
    }
    
    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_actionsOpposingGoalConditionsDisabled(solver, time);
    }

}
