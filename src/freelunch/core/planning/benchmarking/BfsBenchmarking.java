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
package freelunch.core.planning.benchmarking;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.SasProblemAnalyzer;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.benchmarking.PlannerBenchmarking.BenchmarkResultData;
import freelunch.core.planning.benchmarking.providers.SasFilesBenchmarkProvider;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver;
import freelunch.core.planning.forwardSearch.ForwardSearchSettings;
import freelunch.core.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.core.planning.forwardSearch.heuristics.SparrowHeuristic;
import freelunch.core.planning.forwardSearch.heuristics.StateVariablesValueGoalDistanceHeuristic;
import freelunch.core.planning.forwardSearch.heuristics.StatisticsRuledRestartHeuristic;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.utilities.Stopwatch;

public class BfsBenchmarking extends TestCase {
	
	private static final int TIMELIMIT = 5; // seconds
	
	public void testAnalyzeProblems() {
        BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles\\ipcbench");
        //bp = new LogisticsBenchmarkProvider(1, 3, 5, 10, 1, 2);
        SasProblem problem = bp.getNext();
        while (problem != null) {
            System.out.print(problem.getDescription());
            SasProblemAnalyzer spa = new SasProblemAnalyzer(problem);
            spa.analyzeSasProblem();
            problem = bp.getNext();
        }
	}
	
	public void testBestFirstSearchPlanner() {
		
		BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/ipcbench");
		// to test only some domains use
		// new SasFilesBenchmarkProvider("testfiles/ipcbench", new String[] {"parcprinter", "elevators", "transport"});
		
        int solved = 0;
        Stopwatch timer = new Stopwatch();
        
        SasProblem problem = bp.getNext();
        while (problem != null) {
            ForwardSearchSettings settings = new ForwardSearchSettings();
            settings.setHeuristic(new StateVariablesValueGoalDistanceHeuristic(problem, 100));
            settings.setTimelimit(TIMELIMIT);
            BasicForwardSearchSolver solver = new BasicForwardSearchSolver(problem, settings);
            System.out.println("solving " + problem.getDescription());
            try {
				SasParallelPlan plan = solver.solve();
				solved++;
				System.out.println("SOLVED, plan length: " + plan.getPlanLength());
			} catch (TimeoutException | NonexistentPlanException e) {
				System.out.println("TIME LIMIT EXCEEDED");
			}
            problem = bp.getNext();
        }
        System.out.println(String.format("SUMMARY: solved %d in %s seconds", solved, timer.elapsedFormatedSeconds()));
	}
	/**/
	public static final String[] EASY = new String[] {"pegsol", "openstack", "tidybot", "visitall", "transport", "elevators", "scanalyzer"};
	public static final String[] HARD = new String[] {"barman", "floor"};
	
	public void testEvaluateBfs1() {
		System.out.println("svvgd");
		evaluateSolver(new SolverMaker() {
			@Override
			public Solver makeSolver(SasProblem problem) {
	            ForwardSearchSettings settings = new ForwardSearchSettings();
	            settings.setHeuristic(new StateVariablesValueGoalDistanceHeuristic(problem, 100));
	            settings.setTimelimit(TIMELIMIT);
				return new MemoryEfficientForwardSearchSolver(problem, settings);
			}
		}, new SasFilesBenchmarkProvider("testfiles/ipcbench")); //,  new String[]{"printer"}
	}

	public void testEvaluateBfs2() {
		System.out.println("sparrow");
		evaluateSolver(new SolverMaker() {
			@Override
			public Solver makeSolver(SasProblem problem) {
	            ForwardSearchSettings settings = new ForwardSearchSettings();
	            settings.setHeuristic(new SparrowHeuristic(problem, 100));
	            settings.setTimelimit(TIMELIMIT);
				return new BasicForwardSearchSolver(problem, settings);
			}
		}, new SasFilesBenchmarkProvider("testfiles/ipcbench"));
	}
	
	public void testEvaluateBfs1r() {
		System.out.println("svvgd + restart");
		evaluateSolver(new SolverMaker() {
			@Override
			public Solver makeSolver(SasProblem problem) {
	            ForwardSearchSettings settings = new ForwardSearchSettings();
	            settings.setHeuristic(new StateVariablesValueGoalDistanceHeuristic(problem, 100));
	            settings.setRestartHeuristic(new StatisticsRuledRestartHeuristic());
	            settings.setTimelimit(TIMELIMIT);
				return new BasicForwardSearchSolver(problem, settings);
			}
		}, new SasFilesBenchmarkProvider("testfiles/ipcbench"));
	}

	public void testEvaluateBfs2r() {
		System.out.println("sparrow + restart");
		evaluateSolver(new SolverMaker() {
			@Override
			public Solver makeSolver(SasProblem problem) {
	            ForwardSearchSettings settings = new ForwardSearchSettings();
	            settings.setHeuristic(new SparrowHeuristic(problem, 100));
	            settings.setRestartHeuristic(new StatisticsRuledRestartHeuristic());
	            settings.setTimelimit(TIMELIMIT);
				return new BasicForwardSearchSolver(problem, settings);
			}
		}, new SasFilesBenchmarkProvider("testfiles/ipcbench"));
	}
	
	public interface SolverMaker {
		public Solver makeSolver(SasProblem problem);
	}
	
	public void evaluateSolver(SolverMaker smaker, BenchmarkProvider bp) {

		Map<String, BenchmarkResultData> results = new HashMap<String, BenchmarkResultData>();
        int solved = 0;
        int problems = 0;
        Stopwatch totalSolveTime = new Stopwatch();
        totalSolveTime.pause();
        
        SasProblem problem = bp.getNext();
        while (problem != null) {
        	problems++;
            Solver solver = smaker.makeSolver(problem); 
            String domName = problem.getDescription().split("-")[0];
            BenchmarkResultData data = results.get(domName);
            if (data == null) {
            	data = new BenchmarkResultData(domName);
            	results.put(domName, data);
            }
            data.problems++;
            System.out.print("solving " + problem.getDescription());
            try {
        		Stopwatch timer = new Stopwatch();
        		totalSolveTime.unpause();
				SasParallelPlan plan = solver.solve();
				totalSolveTime.pause();
				timer.pause();
                data.solvedTime += timer.elapsedNanoSeconds();
                data.solved++;
                data.totalMakespan += plan.getPlanLength();
                data.totalActions += plan.getTotalActions();
                solved++;
				System.out.println(String.format(" SOLVED, plan length %d, time %s",plan.getPlanLength(), timer.elapsedFormatedSeconds()));
				
				if (!PlanVerifier.verifyPlan(problem, plan)) {
					throw new RuntimeException("Invalid Plan");
				}
			} catch (TimeoutException e) {
				System.out.println(" TIME LIMIT EXCEEDED");
			} catch (NonexistentPlanException e) {
				System.out.println(" PLAN NOT EXISTENT");
				data.nonexistentPlan++;
			}
            totalSolveTime.unpause();
            problem = bp.getNext();
        }
        for (BenchmarkResultData brd : results.values()) {
            System.out.println(brd);
        }
        System.out.println(String.format("SUMMARY: solved %d/%d in %s", solved, problems, totalSolveTime.elapsedFormatedSeconds()));
	}

}
