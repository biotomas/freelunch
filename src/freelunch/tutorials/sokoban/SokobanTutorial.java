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

package freelunch.tutorials.sokoban;

import freelunch.benchmarks.sokoban.SokobanFormalizer;
import freelunch.benchmarks.sokoban.SokobanGenerator;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver;
import freelunch.core.planning.forwardSearch.ForwardSearchSettings;
import freelunch.core.planning.forwardSearch.heuristics.StateVariablesValueGoalDistanceHeuristic;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;

public class SokobanTutorial {

    public static void main(String[] args) {
        SokobanTutorial sokobanTutorial = new SokobanTutorial();
        sokobanTutorial.runSokobanTutorial();
    }

    public void runSokobanTutorial() {
        SokobanFormalizer sokobanFormalizer = new SokobanFormalizer();

        // We generate the planning problem (see below)
        SasProblemBuilder problem = sokobanFormalizer.formalize(SokobanGenerator.SOKOBAN_CLASSIC_1);
        SasProblem sasProb = problem.getSasProblem();

        // We initialize the planner with the problem

        ForwardSearchSettings fss = new ForwardSearchSettings();
        fss.setHeuristic(new StateVariablesValueGoalDistanceHeuristic(sasProb, 1));
        fss.setTimelimit(600);
        Solver planner = new BasicForwardSearchSolver(sasProb, fss);

        // We solve the problem
        try {

            SasParallelPlan plan = planner.solve();

            // print the plan
            System.out.println(plan);

            // Optionally we can verify the plan
            boolean valid = PlanVerifier.verifyPlan(sasProb, plan);
            if (valid) {
                System.out.println("Plan is VALID");
            } else {
                System.out.println("Plan in INVALID");
            }

        } catch (TimeoutException e) {
            System.out.println("Timeout occured");
        } catch (NonexistentPlanException e) {
            System.out.println("The planning problem has no solution");
        }
    }
}
