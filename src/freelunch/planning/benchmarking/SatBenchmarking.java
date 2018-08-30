package freelunch.planning.benchmarking;

import freelunch.planning.benchmarking.problemGenerator.CollectorProblemGenerator;
import freelunch.planning.benchmarking.providers.SasFilesBenchmarkProvider;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.TimeoutException;
import freelunch.planning.optimizer.PlanVerifier;
import freelunch.planning.planners.Planner;
import freelunch.planning.planners.optimal.SimpleAstarPlanner;
import freelunch.planning.planners.optimal.heuristics.TrivialAdmissibleHeuristic;
import freelunch.planning.planners.satplan.iterative.IterativeSatBasedSolver;
import freelunch.planning.planners.satplan.translator.DirectTranslator;
import freelunch.sat.model.Sat4JSolver;
import freelunch.sat.model.SatSolver;
import junit.framework.TestCase;

public class SatBenchmarking extends TestCase {
	
	public void testSatBasedSolver() {
		BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/benchmarks", new String[] {"nurikabe"});
		SasProblem problem = bp.getNext();
		SatSolver sats = new Sat4JSolver();
		while (problem != null) {
			System.out.print("solving " + problem.getDescription());
			Planner planner = new IterativeSatBasedSolver(sats, new DirectTranslator(problem));
			//planner.getSettings().setTimelimit(5);
			try {
				SasParallelPlan plan = planner.solve();
				if (PlanVerifier.verifyPlan(problem, plan)) {
					System.out.println("Found  valid plan :)");
					System.out.println(plan.toString());
				} else {
					System.out.println("Found INVALID PLAN !!!");
				}
			} catch (TimeoutException e) {
				System.out.println("Timeout :(");
			} catch (NonexistentPlanException e) {
				System.out.println("Ain't no none plan");
			}
			
			problem = bp.getNext();
		}
		
	}
	
	public void testSatOnCollector() throws TimeoutException, NonexistentPlanException {
		SasProblem problem = CollectorProblemGenerator.generateProblem(6, 10, 2);
		Planner planner = new SimpleAstarPlanner(problem, new TrivialAdmissibleHeuristic(problem));
		SasParallelPlan plan = planner.solve();
		System.out.println(plan.toString());
		if (PlanVerifier.verifyPlan(problem, plan)) {
			System.out.println("plan verified");
		}
	}

}
