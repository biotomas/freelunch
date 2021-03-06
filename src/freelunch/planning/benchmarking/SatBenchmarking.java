package freelunch.planning.benchmarking;

import java.io.IOException;

import freelunch.planning.benchmarking.problemGenerator.CollectorProblemGenerator;
import freelunch.planning.benchmarking.providers.SasFilesBenchmarkProvider;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasIO;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.TimeoutException;
import freelunch.planning.optimizer.PlanVerifier;
import freelunch.planning.planners.Planner;
import freelunch.planning.planners.satplan.iterative.IterativeSatBasedSolver;
import freelunch.planning.planners.satplan.translator.DisertDirectTranslator;
import freelunch.sat.model.Sat4JSolver;
import junit.framework.TestCase;

public class SatBenchmarking extends TestCase {
	
	public void testSatBasedSolver() {
		BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/benchmarks", new String[] {"settlers"});
		SasProblem problem = bp.getNext();
		while (problem != null) {
			System.out.print("solving " + problem.getDescription());
			Planner planner = new IterativeSatBasedSolver(new Sat4JSolver(), new DisertDirectTranslator(problem));
			//Planner planner;// = new SimpleAstarPlanner(problem, new SimpleGoalDistanceHeuristic(problem));
			//ForwardSearchSettings settings = new ForwardSearchSettings();
			//settings.setMaximumDepth(20);
			//planner = new MemoryEfficientForwardSearchSolver(problem, settings);
			//planner.getSettings().setTimelimit(10);
			planner.getSettings().setVerbose(true);
			try {
				SasParallelPlan plan = planner.solve();
				System.out.println(plan.toString());
				if (PlanVerifier.verifyPlan(problem, plan)) {
					System.out.println("Found  valid plan :)");
				} else {
					System.out.println("Found INVALID PLAN !!!");
				}
			} catch (TimeoutException e) {
				System.out.println("Timeout :(");
			} catch (NonexistentPlanException e) {
				System.out.println("Ain't no none plan");
			}
			break;
			//problem = bp.getNext();
		}
		
	}
	
	public void testSettlersPlans() throws IOException {
		for (int i = 1; i <= 4; i++) {
			SasProblem problem = SasIO.parse("testfiles/benchmarks/settlers-p0"+i+".sas");
			SasParallelPlan plan = SasParallelPlan.loadFromFile("testfiles/benchmarks/p0"+i+".pddl.plan", problem);
			if (PlanVerifier.verifyPlan(problem, plan)) {
				System.out.println("Plan is correct");
			} else {
				System.out.println("wrong!");
			}
		}
	}
	
	public void testSatOnCollector() throws TimeoutException, NonexistentPlanException {
		for (int i = 2; i < 100; i++) {
			SasProblem problem = CollectorProblemGenerator.generateProblem(7, 20, i);
			//Planner planner = new SimpleAstarPlanner(problem, new SimpleGoalDistanceHeuristic(problem));
			Planner planner = new IterativeSatBasedSolver(new Sat4JSolver(), new DisertDirectTranslator(problem));
			SasParallelPlan plan = planner.solve();
			System.out.println(plan.toString());
			if (PlanVerifier.verifyPlan(problem, plan)) {
				System.out.println("plan verified");
			} else {
				System.out.println("wrong");
				break;
			}
		}
	}

}
