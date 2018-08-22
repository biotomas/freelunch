package freelunch.planning.optimizer.potentialPlans;

import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.optimizer.blockDecomposition.BlockDecomposition;

public interface PotentialPlanMaker {
	
	/**
	 * Make a potential plan from a given plan.
	 * @param problem
	 * @param plan
	 * @return
	 */
	public PotentialPlan makePotentialPlan(SasProblem problem, SasParallelPlan plan);

	/**
	 * Make a potential plan from a given plan and block decomposition.
	 * @param problem
	 * @param plan
	 * @param blockDecomposition
	 * @return
	 */
	public PotentialPlan makePotentialPlan(SasProblem problem, SasParallelPlan plan,
			BlockDecomposition blockDecomposition);

}
