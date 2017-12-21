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
public class TrustyWalkAhead implements ForwardSearchSelectorFunction {

    public static class WalkAheadParameters {
        public int lookAheadLength = 7;
    };
    
    private SasToSatTranslator translator;
    private IncrementalSatSolver solver;
    private SasParallelPlan plan = null;
    private int planStep = 0;
    private int stepIndex = 0;
    private WalkAheadParameters params;
    
    public TrustyWalkAhead(SasProblem problem, WalkAheadParameters params) {
        this.params = params;
        solver = new Sparrow();
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

    public TrustyWalkAhead(SasProblem sasProb) {
        this(sasProb, new WalkAheadParameters());
    }

    @Override
    public SasAction select(int[] state, List<SasAction> candidates) throws NonexistentPlanException, TimeoutException {
        if (plan == null) {
            makeNewPlan(state);
        }
        
        while (true) {
            List<List<SasAction>> p = plan.getPlan();
            if (p.get(planStep).size() == stepIndex) {
                stepIndex = 0;
                planStep++;
                if (planStep == p.size()) {
                    makeNewPlan(state);
                }
            }
            SasAction action = p.get(planStep).get(stepIndex);
            stepIndex++;
            if (candidates.contains(action)) {
                return action;
            } else {
                makeNewPlan(state);
            }
        }
    }
    
    private void makeNewPlan(int[] state) throws TimeoutException {
        System.out.println("Making new plan");
        //FIXME rewrite this heuristic to be compatible with the new translator interface
        //translator.setInitialStateConstraints(solver, state);
        solver.isSatisfiable();
        plan = translator.decodePlan(solver.getModel(), params.lookAheadLength);
        planStep = 0;
        stepIndex = 0;
    }

    @Override
    public void noteRestart() {
        plan = null;
    }
}
