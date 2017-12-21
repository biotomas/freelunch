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
package freelunch.core.planning.cmdline;

import java.io.IOException;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolverSettings;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.utilities.Stopwatch;


public class SasSolver {

    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
        ArgumentsParser ap = new ArgumentsParser();
        IncrementalSolverSettings settings = ap.parseArguments(args);
        if (settings == null) {
            return;
        }
        String problemName = settings.getProblem();
        try {
            SasProblem problem = null;

            Stopwatch parseTime = new Stopwatch();
            problem = SasIO.parse(problemName);
            parseTime.pause();

            Stopwatch transitionsTime = new Stopwatch();
            transitionsTime.pause();

            Stopwatch solveTime = new Stopwatch();
            SasToSatTranslator translator = new DirectExistStepTranslator(problem);
            IterativeSatBasedSolver solver = new IterativeSatBasedSolver(new Sat4JSolver(), translator);
            SasParallelPlan plan = solver.solve();
            solveTime.pause();
            
            // experimenter compatible output
            System.out.println("experimenter-results-head;makespan;actions;runtime;parseTime;transitionsTime;satTime;variables;operators");
            System.out.println(String.format("experimenter-results-data;%d;%d;%s;%s;%s;%s;%d;%d",
                    plan.getPlanLength(),
                    plan.getTotalActions(),
                    watch.elapsedFormatedSeconds(),
                    parseTime.elapsedFormatedSeconds(),
                    transitionsTime.elapsedFormatedSeconds(),
                    solveTime.elapsedFormatedSeconds(),
                    problem.getVariables().size(),
                    problem.getOperators().size()
                    ));

            if (settings.isCsvOutput()) {
                //"problem;result;plan_lenght;total_actions;total_runtime;parsing_time;transition_generation_time;
                //total_SAT_solver_time;problem_variables;problem_actions"
                System.out.println(String.format("%s;SOLVED;%d;%d;%s;%s;%s;%s;%d;%d",
                        problemName,
                        plan.getPlanLength(),
                        plan.getTotalActions(),
                        watch.elapsedFormatedSeconds(),
                        parseTime.elapsedFormatedSeconds(),
                        transitionsTime.elapsedFormatedSeconds(),
                        solveTime.elapsedFormatedSeconds(),
                        problem.getVariables().size(),
                        problem.getOperators().size()
                        ));
            } else {
                System.out.println(String.format("%s Solved: timespan %d time %s",
                        problemName, plan.getPlanLength(), watch.elapsedFormatedSeconds()));
                //verify plan
                boolean valid = PlanVerifier.verifyPlan(problem, plan);
                System.out.println(valid ? "Plan valid" : "Plan INVALID !!!");
            }
            if (settings.isPrintPlan()) {
                System.out.println("Plan:\n" + plan.toString());
            }
            if (settings.getPlanFile() != null) {
                plan.saveToFile(settings.getPlanFile());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (TimeoutException e) {
            if (settings.isCsvOutput()) {
                System.out.println(String.format("%s;TIMEOUT;;;%s;;;;;;",
                        problemName,
                        watch.elapsedFormatedSeconds()
                        ));

            } else {
                System.out.println(String.format("%s Timeout in %s seconds", problemName, watch.elapsedFormatedSeconds()));
            }
        } catch (NonexistentPlanException e) {
            if (settings.isCsvOutput()) {
                System.out.println(String.format("%s;CONTRADICTION;;;%s;;;;;;",
                        problemName,
                        watch.elapsedFormatedSeconds()
                        ));
            } else {
                System.out.println(problemName + " Contradiction");
            }
        } catch (OutOfMemoryError e) {
            if (settings.isCsvOutput()) {
                System.out.println(String.format("%s;OUT_OF_MEMORY;;;%s;;;;;;",
                        problemName,
                        watch.elapsedFormatedSeconds()
                        ));
            } else {
                System.out.println(problemName + " Out of memory");
            }
            e.printStackTrace();
        }
    }

}
