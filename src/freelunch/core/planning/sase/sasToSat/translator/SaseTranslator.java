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
package freelunch.core.planning.sase.sasToSat.translator;

import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.Transition;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslatorSettings.TransitionProgressionType;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;


public class SaseTranslator extends TranslatorBase implements SasToSatTranslator {
	
    private final SaseTranslatorSettings settings;
    
    public SaseTranslator(SasProblem problem) {
    	this(problem, new SaseTranslatorSettings());
    }

	public SaseTranslator(SasProblem problem, SaseTranslatorSettings settings) {
	    super(problem);
		this.settings = settings;
		initializeActionVariables();
		initializeTransitionVariables();
		
        if (settings.isUseSaseTransMutex()) {
            computeTransitionMutexCliquesBySase();
        } else {
            computeTransitionMutexCliques();
        }
        initializeAssignmentIndex();
        initializeAssignmentSupportingActions();
        computeInterferingActionPairsMinimal();
	}
	
	@Override
	public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        if (settings.isUseOriginalInitialStateEncoding()) {
            initial_atLeastOneApplicableTransition(solver);
        } else {
            initial_inapplicableTransitionForbidden(solver);
        }
	}

	@Override
	public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        // 4. transition mutex
        universal_transitionMutex(solver, time);
        // 5. existence of transitions
        universal_atLeastOneTransitionForEachVariable(solver, time);
        // 6. composition of actions
        universal_actionsImplyTheirTransitions(solver, time);
        // 7. action's existence
        universal_transitionsMustBeSupportedByAnAction(solver, time);
        // 8. non-interference of actions
        universal_mutexActionPairsNogood(solver, time);
	}
	
	@Override
	public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        TransitionProgressionType transType = settings.getTransitionProgressionType();
        if (transType == TransitionProgressionType.Both || transType == TransitionProgressionType.Forward) {
            //3A. backwards transition progression
            // REDUNDANT WITH 3B
            transition_transitionImpliesAtLeastOnePreviousCompatibleTransition(solver, time);
        }
        if (transType == TransitionProgressionType.Both || transType == TransitionProgressionType.Backward) {
            //3B. backwards transition progression
            // REDUNDANT WITH 3A
            transition_transitionImpliesAtLeastOneFollowingCompatibleTransition(solver, time);
        }
	}

	@Override
	public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        if (settings.isUseOriginalGoalEncoding()) {
            goal_atLeastOneTransitionSupportingGoal(solver, time);
        } else {
            goal_transitionsDestroyingGoalForbidden(solver, time);
        }
	}

	/**
	 * Print the plan showing the active transitions.
	 * Useful for debugging purposes.
	 * @param model
	 * @param makespan
	 */
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

}
