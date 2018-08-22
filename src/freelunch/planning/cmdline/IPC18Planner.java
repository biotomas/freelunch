package freelunch.planning.cmdline;

import java.io.IOException;

import freelunch.planning.NonexistentPlanException;
import freelunch.planning.Solver;
import freelunch.planning.TimeoutException;
import freelunch.planning.cmdline.Translator.TranslationMethod;
import freelunch.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.sase.optimizer.ActionEliminationOptimizer;
import freelunch.planning.sase.optimizer.PlanVerifier;
import freelunch.planning.sase.preprocessing.ReachabilityAnalysis;
import freelunch.planning.sase.sasToSat.SasIO;
import freelunch.planning.sase.sasToSat.symbolicReachability.SymbolicReachabilityProblemGenerator;

public class IPC18Planner {

	public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("USAGE: freelunch.jar {bfs|srt|dec} <problem.sas> <plan.out> [tlimit|srt|srt-sol]");
            return;
        }
        String mode = args[0];
        String problemFile = args[1];
        String planFile = args[2];
        String srtFile = args[3];
        String srtSol = args[3];
        String bfsTimeLimit = args[3];
        
        SasProblem problem = null;
        SasProblem vanillaProblem;
        try {
			problem = SasIO.parse(problemFile);
			vanillaProblem = SasIO.parse(problemFile);
        	int unreach = ReachabilityAnalysis.testGoalReachabilityAndRemoveUnreachableActions(problem);
            System.out.println(String.format("preprocessing removed %d unreachable actions.", unreach));
		} catch (IOException e) {
			System.out.println("Could not load problem file");
			return;
		} catch (NonexistentPlanException e) {
			System.out.println("Plan does not exist");
			return;
		}

        if (mode.equals("bfs")) {
        	int seconds = Integer.parseInt(bfsTimeLimit);
        	System.out.println("running bfs for " + seconds + " seconds.");
            Solver solver = new MemoryEfficientForwardSearchSolver(problem);
            solver.getSettings().setTimelimit(seconds);
            SasParallelPlan plan;
			try {
				plan = solver.solve();
				System.out.println();
	            if (PlanVerifier.verifyPlan(vanillaProblem, plan)) {
	                System.out.println("Valid plan found :)");	
	                //System.out.println(plan.toString());
	            } else {
	                System.out.println("Invalid plan found");
	                return;
	            }
	        	ActionEliminationOptimizer aeopt = new ActionEliminationOptimizer();
	        	int orgSize = plan.getPlanLength();
	        	aeopt.optimizePlan(problem, plan);
	        	int newSize = plan.getPlanLength();
	        	System.out.println("Plan optimizer reduced the plan length from " + orgSize + " to " + newSize);
	            if (PlanVerifier.verifyPlan(vanillaProblem, plan)) {
	                System.out.println("Valid plan found :)");	            	
	            } else {
	                System.out.println("Invalid plan found");
	                return;
	            }
	            plan.saveToFileIpcFormat(planFile);
			} catch (TimeoutException e) {
				System.out.println("Time limit exceeded");
			} catch (NonexistentPlanException e) {
				System.out.println("Plan does not exist");
			} catch (IOException e) {
				System.out.println("Could not save plan");
			}

        } else if (mode.equals("srt")) {
        	System.out.println("generating srt file");
            problem.compileConditionalActions();
        	SymbolicReachabilityProblemGenerator gen = 
        			new SymbolicReachabilityProblemGenerator(problem, TranslationMethod.selective);
        	try {
				gen.getSRProblem().printToFile(srtFile);
			} catch (IOException e) {
				System.out.println("Could not save srt file");
			}
        } else if (mode.equals("dec")) {
        	SymbolicReachabilityProblemGenerator gen = 
        			new SymbolicReachabilityProblemGenerator(problem, TranslationMethod.selective);
        	try {
				SasParallelPlan plan = gen.decodePlanFromFile(srtSol);
				boolean valid = PlanVerifier.verifyPlan(vanillaProblem, plan);
				if (valid) {
					plan.saveToFileIpcFormat(planFile);
				} else {
					System.out.println("Plan Invalid");
				}
			} catch (IOException e) {
				System.out.println("Could not read solution file or save plan");
			}
        }
	}

}
