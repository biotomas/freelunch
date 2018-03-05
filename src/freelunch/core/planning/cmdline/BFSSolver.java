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
package freelunch.core.planning.cmdline;

import java.io.IOException;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.ActionEliminationOptimizer;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.preprocessing.ReachabilityAnalysis;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.utilities.ParametersProcessor;
import freelunch.core.utilities.Stopwatch;


public class BFSSolver {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Stopwatch watch = new Stopwatch();
        String message = "Solved";
        int planSize = 0;
        if (args.length < 1) {
            System.out.println("USAGE: java -jar bfs.jar <problem.sas> [-p] [-pp] [plan-file]");
            return;
        }
        ParametersProcessor params = new ParametersProcessor(args);
        String filename = params.getParameter(0);
        String planFile = params.getParameter(1);
        try {
            SasProblem problem = SasIO.parse(filename);
            problem.compileConditionalActions();
            
            if (params.isSet("p")) {
            	int unreach = ReachabilityAnalysis.testGoalReachabilityAndRemoveUnreachableActions(problem);
                System.out.println(String.format("preprocessing removed %d unreachable actions.", unreach));
            }
            
            Solver solver = new MemoryEfficientForwardSearchSolver(problem);

            SasParallelPlan plan = solver.solve();
            planSize = plan.getPlanLength();
            boolean valid = PlanVerifier.verifyPlan(problem, plan);
            
            if (!valid) {
                System.out.println(filename + ";Invalid plan found");
                return;
            }
            if (params.isSet("pp")) {
            	ActionEliminationOptimizer aeopt = new ActionEliminationOptimizer();
            	int orgSize = plan.getPlanLength();
            	aeopt.optimizePlan(problem, plan);
            	int newSize = plan.getPlanLength();
            	System.out.println("Plan optimizer reduced the plan length from " + orgSize + " to " + newSize);
            }
            if (planFile != null) {
                plan.saveToFileIpcFormat(planFile);
            } else {
            	System.out.println();
            	System.out.println(plan.toString());            	
            }
            
        } catch (IOException e) {
            message = "File cannot be opened";
        } catch (TimeoutException e) {
            message = "Timeout Occurred";
        } catch (NonexistentPlanException e) {
            message = "Plan does not exist" + e.getMessage();
        } catch (OutOfMemoryError e) {
            message = "Out of memory";
        } catch (Throwable e) {
            System.out.println(filename + ";Unexpected error");
            e.printStackTrace(System.out);
            return;
        }
        System.out.println("header;file;result;planSize;time");
        System.out.println(String.format("data;%s;%s;%d;%s", filename, message, planSize, watch.elapsedFormatedSeconds()));
        
    }

}
