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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanLoader;
import freelunch.core.planning.sase.optimizer.PlanOptimizer;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerParameters;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerStatistics;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerParameters.SelectionAlgorithm;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.utilities.Stopwatch;



public class OptimizerMain {
	
	private static void printUsage() {
		System.out.println("USAGE: java -jar PerPartes.jar PROBLEM.SAS PLAN.SOL [ parameters ]");
		System.out.println("  The parameters are the following:");
		System.out.println("  -v           verbose mode on");
		System.out.println("  -x           only improve windows by one");
		System.out.println("  -p STRING    filename for the final plan");
		System.out.println("  -m Method    the method of window moving. Possible values are: timedwindows, randomFixedWindow, systematic, turbo, fullrandom, limitedrandom, exponential");
		System.out.println("  -g FLOAT     window growth for the exponential method, default is 1.5");
		System.out.println("  -n INT       minimal window time in seconds, used by timedwindows method, default is 2");
		System.out.println("  -f           fixed point mode on");
		System.out.println("  -w INT       window size");
		System.out.println("  -s INT       window shift");
		System.out.println("  -i INT       number of iterations for the random methods");
		System.out.println("  -t INT       time limit for the entire program in seconds");
		System.out.println("  -o INT       time limit for the optimization of a window in seconds");		
		System.out.println();		
		System.out.println("The csv output contains the following information:");		
		System.out.println(" method, problemName, runtime, final-plan-timespan, final-plan-actions, initial-plan-timespan, initial-plan-actions, ");		
		System.out.println(" optimizations, improvements, optimization-timeouts, max-window-size, last-improvement-time, last-improvement-winsize");		
	}
	
	private static PlanOptimizerParameters argsParser(String[] args) {
		// skip the first 2 parameters which are the filenames
		PlanOptimizerParameters params = new PlanOptimizerParameters();
		for (int i = 2; i < args.length; i++) {
			String arg = args[i];
			char id = arg.charAt(1);
			switch (id) {
			case 'v':
				params.setVerbose(true);
				break;
			case 'x':
				params.setOneTry(true);
				break;
			case 'm':
				i++;
				params.setWindowSelectionAlgorithm(SelectionAlgorithm.valueOf(args[i]));
				break;
			case 'g':
				i++;
				float f = Float.parseFloat(args[i]);
				params.setWindowGrowth(f);
				break;
			case 'w':
				i++;
				int w = Integer.parseInt(args[i]);
				params.setWindowSize(w);
				break;
			case 'f':
				params.setFixedPointMode(true);
				break;
			case 's':
				i++;
				int s = Integer.parseInt(args[i]);
				params.setWindowShift(s);
				break;
			case 'p':
				i++;
				params.setPlanFile(args[i]);
				break;
			case 'i':
				i++;
				int it = Integer.parseInt(args[i]);
				params.setIterations(it);
				break;
			case 't':
				i++;
				int t = Integer.parseInt(args[i]);
				params.setTotalTimeLimit(t);
				break;
			case 'o':
				i++;
				int o = Integer.parseInt(args[i]);
				params.setSolverTimeLimit(o);
				break;
			default:
				System.out.println("Invalid parameter: " + args[i]);
				printUsage();
				return null;
			}
		}
		return params;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		if (args.length < 2) {
			printUsage();
			return;
		}		
		String problemFile = args[0];
		String planFile = args[1];
		
		PlanOptimizerParameters params = argsParser(args);
		if (params == null) {
			return;
		}
		

		try {
			SasProblem problem = SasIO.parse(problemFile);
			SasParallelPlan plan = PlanLoader.loadPlanFromFile(planFile, problem);
			PlanOptimizer optimizer = new PlanOptimizer();
			// time limit correction
			params.setTotalTimeLimit(params.getTotalTimeLimit() - watch.elapsedSeconds());			
			
			PlanOptimizerStatistics stats = optimizer.optimizePlan(problem, plan, params);
			stats.problemName = problemFile;
			stats.params = params;
			
			System.out.println(stats.getStatisticsCsv());

			if (params.getPlanFile() != null) {
				savePlanToFile(params, watch, stats, plan);
			}
		} catch (Exception e) {
			PlanOptimizerStatistics stats = new PlanOptimizerStatistics();
			stats.problemName = problemFile;
			stats.params = params;
			System.out.println(stats.getStatisticsCsv());
			System.out.println(e.getMessage());
			return;
		}
		
	}
	
	private static void savePlanToFile(PlanOptimizerParameters params, Stopwatch watch, PlanOptimizerStatistics stats, SasParallelPlan plan) {
		try {
			FileWriter fstream = new FileWriter(params.getPlanFile());
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("; The csv output contains the following information:\n");		
			out.write("; algorithm, problem-name, runtime, final-plan-timespan, final-plan-actions, initial-plan-timespan, initial-plan-actions, \n");		
			out.write("; optimizations, improvements, optimization-timeouts, max-window-size, last-improvement-time, last-improvement-winsize\n");		
			out.write("; " + stats.getStatisticsCsv());
			out.write("\n\n");
			out.write(plan.toString());
			out.close();
		} catch (IOException e) {
			System.out.println("Plan could not be written");
		}
	}

}
