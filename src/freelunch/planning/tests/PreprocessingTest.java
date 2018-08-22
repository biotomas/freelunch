package freelunch.planning.tests;

import freelunch.planning.NonexistentPlanException;
import freelunch.planning.TimeoutException;
import freelunch.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.problemGenerator.SlidingPuzzleGenerator;
import freelunch.planning.sase.preprocessing.ReachabilityAnalysis;
import junit.framework.TestCase;

public class PreprocessingTest extends TestCase {
	
	public void testReachabilityAnalysis() {
		SlidingPuzzleGenerator spg = new SlidingPuzzleGenerator();
		for (int exp = 0; exp < 100; exp++) {
			SasProblem p = spg.generateRandomProblem(3, 3, exp);
			try {
				// corrupt the problem
				p.getInitialState().get(0).setValue(0);
				
				int rem = ReachabilityAnalysis.testGoalReachabilityAndRemoveUnreachableActions(p);
				System.out.println("removed actions: " + rem);
				
				MemoryEfficientForwardSearchSolver s = new MemoryEfficientForwardSearchSolver(p);
				SasParallelPlan plan = s.solve();
				System.out.println("plan size = " + plan.getPlanLength());
				
			} catch (NonexistentPlanException e) {
				System.out.println("Plan not existent");
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
