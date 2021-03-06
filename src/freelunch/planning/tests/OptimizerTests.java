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
package freelunch.planning.tests;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import freelunch.maxsat.PartialMaxSatFormula;
import freelunch.maxsat.Sat4JMaxsatSolver;
import freelunch.planning.benchmarking.BenchmarkProvider;
import freelunch.planning.benchmarking.providers.LogisticsBenchmarkProvider;
import freelunch.planning.benchmarking.providers.SasFilesBenchmarkProvider;
import freelunch.planning.model.Condition;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasIO;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.TimeoutException;
import freelunch.planning.optimizer.ActionEliminationOptimizer;
import freelunch.planning.optimizer.PlanLoader;
import freelunch.planning.optimizer.PlanOptimizer;
import freelunch.planning.optimizer.PlanVerifier;
import freelunch.planning.optimizer.RedundancyEliminator;
import freelunch.planning.optimizer.model.PlanOptimizerParameters;
import freelunch.planning.optimizer.model.SubPlanStructure;
import freelunch.planning.optimizer.model.PlanOptimizerParameters.SelectionAlgorithm;
import freelunch.planning.planners.Planner;
import freelunch.planning.planners.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.planning.planners.satplan.iterative.IterativeSatBasedSolver;
import freelunch.planning.planners.satplan.translator.DirectExistStepTranslator;
import freelunch.sat.model.Sat4JSolver;
import freelunch.utilities.Stopwatch;

public class OptimizerTests extends TestCase {
	
	public void testMaxSatOptimizer() throws IOException, TimeoutException, NonexistentPlanException {
		LogisticsBenchmarkProvider lbp = new LogisticsBenchmarkProvider(3, 3, 5, 10, 5, 10);
		SasProblem problem = lbp.getNext();
        Planner planner = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(problem));
        SasParallelPlan plan = planner.solve();
        System.out.println(plan.getPlanCost());
        
        RedundancyEliminator elim = new RedundancyEliminator();
        elim.optimizeWithMaxSat(problem, plan, new Sat4JMaxsatSolver());
        
        //System.out.println(plan);
        System.out.println(plan.getPlanCost());
        assertTrue(PlanVerifier.verifyPlan(problem, plan));
	}
    
    public void testMaxSatEncoding() throws IOException, TimeoutException, NonexistentPlanException {
        SasProblem problem = SasIO.parse("testfiles/ipcbench/visitall-problem12.sas");
        Planner planner = new MemoryEfficientForwardSearchSolver(problem);
        SasParallelPlan plan = planner.solve();
        RedundancyEliminator elim = new RedundancyEliminator();
        PartialMaxSatFormula pmaxf = elim.encodeToPMaxSat(problem, plan);
        pmaxf.saveToDimacsFile("lol.wcnf");
    }
    
    public void testRedundancyEliminator() {
        Stopwatch optTime = new Stopwatch();
        optTime.pause();
        //BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/ipcbench", new String[] {"visitall", "elevators", "transport"});
        BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/ipcbench", new String[] {"visitall"});
        SasProblem problem = bp.getNext();
        ActionEliminationOptimizer optimizer1 = new ActionEliminationOptimizer();
        //RedundancyEliminator optimizer2 = new RedundancyEliminator();
        while (problem != null) {
            Planner planner = new MemoryEfficientForwardSearchSolver(problem);
            planner.getSettings().setTimelimit(20);
            try {
                SasParallelPlan plan  = planner.solve();
                if (PlanVerifier.verifyPlan(problem, plan) == false) {
                    System.out.println("invalid initial plan");
                }
                System.out.println(problem.getDescription());
                
                optTime.unpause();
                int oldPlan = plan.getPlanLength();
                optimizer1.optimizePlanFixPoint(problem, plan);
                //optimizer1.greedyOptimizePlanCost(problem, plan);
                int afterOpt1 = plan.getPlanLength();
                //optimizer2.optimizePlanIncremental(problem, plan);
                //optimizer2.optimizePlan(problem, plan);
                optTime.pause();
                
                if (PlanVerifier.verifyPlan(problem, plan) == false) {
                    System.out.println("invalid optimized plan");
                }
                int newPlan = plan.getPlanLength();
                System.out.println(String.format("%d -> %d -> %d (diff: %d,%d)", oldPlan, afterOpt1, newPlan, oldPlan - afterOpt1, afterOpt1 - newPlan));
            } catch (TimeoutException e) {
                // ignore
            } catch (NonexistentPlanException e) {
                // ignore
            }
            problem = bp.getNext();
        }
        System.out.println("time = " + optTime.elapsedFormatedSeconds());
    }
    
    
    public void testLightweightOptimizer() {
        BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/ipcbench", new String[] {"visitall", "elevators", "transport"});
        SasProblem problem = bp.getNext();
        ActionEliminationOptimizer optimizer = new ActionEliminationOptimizer();
        while (problem != null) {
            Planner planner = new MemoryEfficientForwardSearchSolver(problem);
            planner.getSettings().setTimelimit(20);
            try {
                SasParallelPlan plan  = planner.solve();
                if (PlanVerifier.verifyPlan(problem, plan) == false) {
                    System.out.println("invalid initial plan");
                }
                System.out.println(problem.getDescription());
                int oldPlan = plan.getPlanLength();
                optimizer.optimizePlan(problem, plan);
                if (PlanVerifier.verifyPlan(problem, plan) == false) {
                    System.out.println("invalid optimized plan");
                }
                int newPlan = plan.getPlanLength();
                System.out.println(String.format("%d -> %d (diff: %d)", oldPlan, newPlan, oldPlan - newPlan));
            } catch (TimeoutException e) {
                // ignore
            } catch (NonexistentPlanException e) {
                // ignore
            }
            problem = bp.getNext();
        }
    }
	
	public void testPlanLoader() throws IOException {
		SasProblem problem = SasIO.parse("testfiles/output.sas");
		SasParallelPlan plan = PlanLoader.loadPlanFromFile("testfiles/speedplan_1.SOL", problem);
		
		assertEquals(38, plan.getPlanLength());
		assertEquals(39, plan.getTotalActions());
		
		System.out.println(plan);
	}
	
	public void testPlanVerifier() throws IOException {
		SasProblem problem = SasIO.parse("testfiles/output.sas");
		SasParallelPlan plan = PlanLoader.loadPlanFromFile("testfiles/speedplan_1.SOL", problem);
		SasParallelPlan plan2 = PlanLoader.loadPlanFromFile("testfiles/optimal.sol", problem);
		boolean valid = PlanVerifier.verifyPlan(problem, plan);
		assertEquals(true, valid);
		valid = PlanVerifier.verifyPlan(problem, plan2);
		assertEquals(true, valid);
	}
	
	public void testSubPlan() throws IOException {
		SasProblem problem = SasIO.parse("testfiles/output.sas");
		SasParallelPlan plan = PlanLoader.loadPlanFromFile("testfiles/speedplan_1.SOL", problem);
		
		SubPlanStructure sps = new SubPlanStructure(plan, problem);
		
		List<Condition> x = sps.getInitialStateForTime(4);
		x = sps.getGoalStateForTime(4);
		System.out.println(x);
	}
	
	public void testPlanOptimizer() throws IOException {
		SasProblem problem = SasIO.parse("testfiles/output.sas");
		SasParallelPlan plan = PlanLoader.loadPlanFromFile("testfiles/speedplan_1.SOL", problem);
		PlanOptimizerParameters params = new PlanOptimizerParameters();
		//params.setVerbose(true);
		params.setWindowSelectionAlgorithm(SelectionAlgorithm.turbo);
		params.setTotalTimeLimit(20);
		//params.setIterations(30);
		//params.setWindowSize(12);
		params.setWindowShift(1);
		//params.setSolverTimeLimit(5);
		
		PlanOptimizer optimizer = new PlanOptimizer();
		optimizer.optimizePlan(problem, plan, params);
	}

}
