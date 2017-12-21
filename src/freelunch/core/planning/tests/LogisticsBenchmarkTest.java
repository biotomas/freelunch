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
package freelunch.core.planning.tests;

import java.io.IOException;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.SasProblemAnalyzer;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.benchmarking.BenchmarkProvider;
import freelunch.core.planning.benchmarking.providers.LogisticsBenchmarkProvider;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver.ForwardSearchStatistics;
import freelunch.core.planning.forwardSearch.ForwardSearchSettings;
import freelunch.core.planning.forwardSearch.heuristics.NeverRestartHeuristic;
import freelunch.core.planning.forwardSearch.heuristics.SparrowHeuristic;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.optimal.SimpleAstarPlanner;
import freelunch.core.planning.optimal.heuristics.SimpleGoalDistanceHeuristic;
import freelunch.core.planning.optimal.heuristics.TrivialAdmissibleHeuristic;
import freelunch.core.planning.problemGenerator.LogisticsProblemGenerator;
import freelunch.core.planning.sase.optimizer.PlanOptimizer;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerParameters;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolverSettings;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.DirectDoubleLinkedTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectTranslator;
import freelunch.core.planning.sase.sasToSat.translator.DirectTranslatorSingleAction;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslatorSettings;
import freelunch.core.planning.sase.sasToSat.translator.SelectiveTranslator;
import freelunch.core.planning.sase.sasToSat.translator.TransitionExistStepTranslator;
import freelunch.core.satSolving.ExternalSatSolver;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.SatSolver;
import freelunch.core.satSolving.walksat.Sparrow;
import freelunch.core.utilities.Stopwatch;
import junit.framework.TestCase;

public class LogisticsBenchmarkTest extends TestCase {
    
    public static BenchmarkProvider getEasySet() {
        return new LogisticsBenchmarkProvider(3, 6, 3, 6, 4, 6);
    }
    
    public static BenchmarkProvider getHardSet() {
        return new LogisticsBenchmarkProvider(3, 6, 5, 10, 10, 15);
    }
    
    public static int[] hardSetMakespans = new int[] {6,6,6,6,6,7,7,7,7,7,9,9,10,10,10,9,9,9,9,9,9,9,9,9,9,5,5,5,5,5,6,6,6,6,6,7,7,8,8,9,8,8,8,8,9,8,8,8,8,8,4,4,4,4,4,5,5,5,5,5,6,6,7,7,7,7,7,7,7,7,7,7,8,8,8};

	
    public void testOptimalOnEasy() throws TimeoutException, NonexistentPlanException {
	    BenchmarkProvider bp = new LogisticsBenchmarkProvider(1, 1, 3, 3, 1, 1);
	    SasProblem problem = bp.getNext();
    	//SimpleAstarPlanner sap = new SimpleAstarPlanner(problem, new SimpleGoalDistanceHeuristic(problem));
    	SimpleAstarPlanner sap = new SimpleAstarPlanner(problem, new TrivialAdmissibleHeuristic(problem));
    	SasParallelPlan p = sap.solve();
    	assertTrue(PlanVerifier.verifyPlan(problem, p));
    }

    public void testOptimalOnEasySet() throws TimeoutException, NonexistentPlanException {
	    BenchmarkProvider bp = new LogisticsBenchmarkProvider(3, 6, 3, 6, 4, 5);
	    SasProblem problem = bp.getNext();
	    while (problem != null) {
	    	SimpleAstarPlanner sap = new SimpleAstarPlanner(problem, new SimpleGoalDistanceHeuristic(problem));
	    	SasParallelPlan p = sap.solve();
	    	assertTrue(PlanVerifier.verifyPlan(problem, p));
	    	System.out.println(problem.getDescription() + " " + sap.getStats());
	    	System.out.println(String.format("plan len: %d, plan cost %d", p.getPlanLength(), p.getPlanCost()));
	    	problem = bp.getNext();
	    }
    }
    
    public void testIncrementalSolverTimeLimit() {
		SasProblem problem = LogisticsProblemGenerator.generateProblem(20, 100, 30, 30).getSasProblem();
		Stopwatch timer = new Stopwatch();
    	IncrementalSolverSettings settings = new IncrementalSolverSettings();
    	settings.setTimelimit(5);
		IncrementalSolver planner = new IncrementalSolver(problem, settings);
		solveProblem(planner, problem, false, false);
		System.out.println(timer.elapsedFormatedSeconds());
	}
	
	public void testEasyLogistics() {
	    BenchmarkProvider bp = new LogisticsBenchmarkProvider(1, 1, 3, 3, 1, 1);
	    SasProblem problem = bp.getNext();
        IterativeSatBasedSolver s = new IterativeSatBasedSolver(new Sat4JSolver(), new SelectiveTranslator(problem));
        s.getSettings().setTimelimit(10);
        
        try {
            SasParallelPlan p = s.solve();
            System.out.println(p);
        } catch (TimeoutException e) {
            System.out.println("timeout at " + s.getLastMakepan());
        } catch (NonexistentPlanException e) {
        }

	}
	
    public void testEasyLogisticsSeries() {
        BenchmarkProvider pp = getEasySet();
        SasProblem sasProb = pp.getNext();
        Stopwatch time = new Stopwatch();
        while (sasProb != null) {
            //IterativeSatBasedSolver s = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(sasProb,1));
            IterativeSatBasedSolver s = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectTranslatorSingleAction(sasProb));
            s.getSettings().setVerbose(true);
            solveProblem(s, sasProb, false, true);
            sasProb = pp.getNext();
        }
        System.out.println(time.elapsedFormatedSeconds());
    }

	
	public void testSasAnalyzer() throws IOException {
        SasProblem problem = SasIO.parse("testfiles/ipcbench/barman-pfile06-021.sas");
        SasProblemAnalyzer spa = new SasProblemAnalyzer(problem);
        System.out.println(spa.analyzeSasProblem().toString());
	}
	
	public void testLogisticsProblemOnMCTS() {
	    /*
	    ProblemProvider pp = getEasySet();
	    SasProblem sasProb = pp.getNext();
	    while (sasProb != null) {
            MctsSettings settings = new MctsSettings();
            settings.setNumIterations(50);
            settings.setRolloutLength(5);
            settings.setHeuristic(new StateVariablesEqualityHeuristic(sasProb));
            solveProblem(new MctsSolver(sasProb, settings), sasProb, false);
	        sasProb = pp.getNext();
	    }
	    */
	}
	
    public void testLogisticsProblemOnBFSS() {
        BenchmarkProvider pp = new LogisticsBenchmarkProvider(15, 17, 20, 25, 30, 35);
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            ForwardSearchSettings fss = new ForwardSearchSettings();
            SparrowHeuristic sh = new SparrowHeuristic(sasProb, 2012);
            fss.setHeuristic(sh);
            fss.setRestartHeuristic(new NeverRestartHeuristic());
            fss.setTimelimit(10);
            BasicForwardSearchSolver s = new BasicForwardSearchSolver(sasProb, fss); 
            solveProblem(s, sasProb, false, false);
            ForwardSearchStatistics statistics = s.getStatistics();
            System.out.println(String.format("%d;%d;%d;%d;%d", statistics.getIterations(), statistics.backtracksSinceRestart, statistics.maxDepth, statistics.depth, statistics.restarts));
            sasProb = pp.getNext();
        }
    }
    
    public void testLogisticsProblemOnSparrowSASE() {
        BenchmarkProvider pp = getEasySet();
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            IncrementalSolverSettings settings = new IncrementalSolverSettings();
            solveProblem(new IncrementalSolver(sasProb, new Sparrow(), settings), sasProb, false, false);
            sasProb = pp.getNext();
        }
    }
	
    public void testLogisticsProblemOnSASE() {
        BenchmarkProvider pp = getHardSet();
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            IncrementalSolverSettings settings = new IncrementalSolverSettings();
            settings.setTimelimit(10);
            solveProblem(new IncrementalSolver(sasProb, settings), sasProb, false, false);
            sasProb = pp.getNext();
        }
        // TB result: 0 timeout, total time 70s
    }

    public void testExternalSolver() {
        BenchmarkProvider pp = getEasySet();
        
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            Solver s = new IterativeSatBasedSolver(new ExternalSatSolver(), new SaseTranslator(sasProb));
            s.getSettings().setTimelimit(10);
            solveProblem(s, sasProb, false, false);
            sasProb = pp.getNext();
        }
    }
    
    public void testLogisticsProblemOnIterativeSASE() {
        BenchmarkProvider pp = getHardSet();
        
        SaseTranslatorSettings settings = new SaseTranslatorSettings();
        //settings.setUseOriginalGoalEncoding(true);
        //settings.setUseOriginalInitialStateEncoding(true);
        int i = 0;
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            Solver s = new IterativeSatBasedSolver(new Sat4JSolver(), new SaseTranslator(sasProb, settings));
            s.getSettings().setTimelimit(10);
            int makespan = solveProblem(s, sasProb, false, false);
            assertEquals(hardSetMakespans[i], makespan);
            i++;
            sasProb = pp.getNext();
        }
        // TB result: 0 timeout, total time 83s
    }
    
    public void testLogisticsProblemOnDirectExistSASE() {
        SasProblemBuilder problem = LogisticsProblemGenerator.generateProblem(1, 1, 3, 0);
        SasProblem sasProb = problem.getSasProblem();
        BenchmarkProvider pp = getHardSet();
        //SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            Solver s = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(sasProb));
            s.getSettings().setTimelimit(10);
            //s.getSettings().setVerbose(true);
            solveProblem(s, sasProb, false, true);
            sasProb = pp.getNext();
        }
    }

    public void testLogisticsProblemOnTransitionExistStep() {
        SatSolver solver = new Sat4JSolver();
        BenchmarkProvider pp = getHardSet();
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            TransitionExistStepTranslator tr = new TransitionExistStepTranslator(sasProb);
            Solver s = new IterativeSatBasedSolver(solver, tr);
            s.getSettings().setTimelimit(10);
            //s.getSettings().setVerbose(true);
            solveProblem(s, sasProb, false, true);
            sasProb = pp.getNext();
        }
    }

    
    public void testLogisticsProblemOnIterativeDirect() {
        SatSolver solver = new Sat4JSolver();
        BenchmarkProvider pp = getHardSet();
        SasProblem sasProb = pp.getNext();
        int i = 0;
        while (sasProb != null) {
            //Solver s = new IterativeSatBasedSolver(solver, new DirectDoubleLinkedTranslator(sasProb));
            Solver s = new IterativeSatBasedSolver(solver, new DirectTranslator(sasProb));
            s.getSettings().setTimelimit(10);
            int makespan = solveProblem(s, sasProb, false, false);
            // check makespan length
            assertEquals(hardSetMakespans[i], makespan);
            i++;
            sasProb = pp.getNext();
        }
        // TB result: 0 timeout, total time 85s 93s
    }
    
    public void testWoodLinear() throws IOException, TimeoutException, NonexistentPlanException {
        SasProblem prob = SasIO.parse("testfiles/wood.sas");
        SasToSatTranslator translator = new DirectDoubleLinkedTranslator(prob);
        IterativeSatBasedSolver solver = new IterativeSatBasedSolver(new Sat4JSolver(), translator);
        SasParallelPlan plan = solver.solve();
        
        //workaround test
        //plan.getPlan().get(4).clear();
        
        boolean valid = PlanVerifier.verifyPlan(prob, plan);
        
        System.out.println(plan);
        if (!valid) {
            System.out.println("BAD PLAN");
        }

    }
    
    public void testLogisticsSASEDownwards() {
        BenchmarkProvider pp = getEasySet();
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            solveProblemDownward(sasProb, 10, 8);
            sasProb = pp.getNext();
        }
    }

    private void solveProblemDownward(SasProblem problem, int timeLimit, int startTime) {
    	IncrementalSolverSettings settings = new IncrementalSolverSettings();
    	settings.setTimelimit(timeLimit);
    	IncrementalSolver planner = new IncrementalSolver(problem, settings);
        Stopwatch watch = new Stopwatch();
        SasParallelPlan plan = planner.downwardSolve(startTime, false);
        
        boolean valid = PlanVerifier.verifyPlan(problem, plan);
        assertEquals(valid, true);
        
        System.out.println(String.format("Plan length %d time %s", plan.getPlanLength(), watch.elapsedFormatedSeconds()));
    }
    
    public static int solveProblem(Solver planner, SasProblem problem, boolean improve, boolean printPlan) {
        System.out.print("Solving " + problem.getDescription() + " ");
        Stopwatch watch = new Stopwatch();
        try {
            SasParallelPlan plan = planner.solve();
            if (printPlan) {
                System.out.println("\n" + plan);
            }
            boolean valid = PlanVerifier.verifyPlan(problem, plan);
            assertEquals(valid, true);
            
            if (improve) {
            	PlanOptimizer optimizer = new PlanOptimizer();
            	optimizer.optimizePlan(problem, plan, new PlanOptimizerParameters());
                System.out.println(String.format("Plan length after optimalization %d time %s", plan.getPlanLength(), watch.elapsedFormatedSeconds()));
            } else {
                System.out.println(String.format("Plan length %d time %s", plan.getPlanLength(), watch.elapsedFormatedSeconds()));
            }
            return plan.getPlanLength(); 
        } catch (TimeoutException e) {
            System.out.println("Timeout occured");
        } catch (NonexistentPlanException e) {
            System.out.println("The planning problem has no solution");
        }
        return -1;
    }

}
