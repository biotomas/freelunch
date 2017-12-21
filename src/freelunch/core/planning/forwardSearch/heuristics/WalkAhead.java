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
package freelunch.core.planning.forwardSearch.heuristics;

import java.util.Arrays;
import java.util.List;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslator;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.satSolving.walksat.Sparrow;

@Deprecated
public class WalkAhead implements ForwardSearchSelectorFunction {

    public static class WalkAheadParameters {
        public int lookAheadLength = 7;
    };
    
    private SasToSatTranslator translator;
    private IncrementalSatSolver solver;
    private SasParallelPlan plan = null;
    private int planStep = 0;
    private WalkAheadParameters params;
    
    public WalkAhead(SasProblem problem, WalkAheadParameters params) {
        this.params = params;
        solver = new Sparrow();
        //solver = new Sat4JSolver();
        translator = new SaseTranslator(problem);
        int vars = translator.setMaxTimespan(params.lookAheadLength);
        solver.setVariablesCount(vars);
        try {
            for (int time = 0; time < params.lookAheadLength; time++) {
                translator.addUniversalStateConstraints(solver, time);
                translator.addTransitionConstraints(solver, time);
            }
            translator.addUniversalStateConstraints(solver, params.lookAheadLength);
            translator.setGoalStateConstraints(solver, params.lookAheadLength - 1);
        } catch (SatContradictionException e) {
            throw new RuntimeException(e);
        }
    }

    public WalkAhead(SasProblem sasProb) {
        this(sasProb, new WalkAheadParameters());
    }

    @Override
    public SasAction select(int[] state, List<SasAction> candidates) throws NonexistentPlanException {
        if (plan != null) {
            if (planStep == plan.getPlanLength()) {
                System.out.println(Arrays.toString(state));
            }
            return getActionFromThePlan(candidates);            
        }
        try {
            //FIXME rewrite this heuristic to be compatible with the new translator interface
            //translator.setInitialStateConstraints(solver, state);
            while (true) {
                Boolean sat = solver.isSatisfiable();
                SasParallelPlan tempPlan = translator.decodePlan(solver.getModel(), params.lookAheadLength);
                if (sat != null && sat) {
                    // a valid has been found, we will return it action by action
                    plan = tempPlan;
                    planStep = 0;
                    return getActionFromThePlan(candidates);
                }
                List<SasAction> firstStep = tempPlan.getPlan().get(0);
                for (SasAction action : firstStep) {
                    if (candidates.contains(action)) {
                        return action;
                    }
                }
            }
        } catch (TimeoutException e) {
            //TODO
            return null;
        }
    }
    
    private SasAction getActionFromThePlan(List<SasAction> candidates) {
        List<SasAction> step = plan.getPlan().get(planStep);
        SasAction action = step.get(0);
        assert candidates.contains(action);
        step.remove(action);
        if (step.isEmpty()) {
            planStep++;
        }
        return action;
    }

    @Override
    public void noteRestart() {
        plan = null;
    }

}
