package freelunch.planning;

import freelunch.maxsat.Sat4JMaxsatSolver;
import freelunch.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.sase.optimizer.ActionEliminationOptimizer;
import freelunch.planning.sase.optimizer.PlanOptimizer;
import freelunch.planning.sase.optimizer.PlanVerifier;
import freelunch.planning.sase.optimizer.RedundancyEliminator;
import freelunch.planning.sase.optimizer.model.PlanOptimizerParameters;
import freelunch.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.planning.sase.sasToSat.translator.ReinforcedSaseTranslator;
import freelunch.planning.sase.sasToSat.translator.SelectiveTranslator;
import freelunch.sat.model.Sat4JSolver;

/**
 * Convenient static methods for easy usage of the freelunch
 * library.
 */
public class PlanningToolkit {
	
	public enum PlanningAlgorithm {
		greedyForward,
		satParallel,
		satExist,
		satSelective
	}
	
	public enum PlanOptimizationAlgorithm {
		greedyActionElimination,
		maxsatElimination,
		segmentedOptimization
	}
	
	
	/**
	 * Find a plan using the default planning algorithm (satSelective) and verify it.
	 * @param problem
	 * @return a plan
	 * @throws TimeoutException
	 * @throws NonexistentPlanException
	 */
	public static SasParallelPlan findPlan(SasProblem problem) throws TimeoutException, NonexistentPlanException {
		return findPlan(problem, PlanningAlgorithm.satSelective);
	}
	
	/**
	 * Find a plan using the specified algorithm and verify it.
	 * @param problem
	 * @param algorithm
	 * @return a plan
	 * @throws TimeoutException
	 * @throws NonexistentPlanException
	 */
	public static SasParallelPlan findPlan(SasProblem problem, PlanningAlgorithm algorithm) throws TimeoutException, NonexistentPlanException {
		Solver planner = null;
		switch (algorithm) {
		case greedyForward:
			planner = new MemoryEfficientForwardSearchSolver(problem);
			break;
		case satExist:
			planner = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(problem));
			break;
		case satParallel:
			planner = new IterativeSatBasedSolver(new Sat4JSolver(), new ReinforcedSaseTranslator(problem));
			break;
		case satSelective:
			planner = new IterativeSatBasedSolver(new Sat4JSolver(), new SelectiveTranslator(problem));
		}
		
		SasParallelPlan plan = planner.solve();
		if (!PlanVerifier.verifyPlan(problem, plan)) {
			throw new RuntimeException("The found plan is invalid");
		}
		return plan;
	}
	
	/**
	 * Try to improve the plan by using the default optimization algorithm (maxsat elimination).
	 * @param problem
	 * @param plan
	 */
	public static void improvePlan(SasProblem problem, SasParallelPlan plan) {
		improvePlan(problem, plan, PlanOptimizationAlgorithm.maxsatElimination);
	}

	/**
	 * Try to improve the plan by using the specified algorithm.
	 * @param problem
	 * @param plan
	 * @param algorithm
	 */
	public static void improvePlan(SasProblem problem, SasParallelPlan plan, PlanOptimizationAlgorithm algorithm) {
		switch (algorithm) {
		case greedyActionElimination:
			ActionEliminationOptimizer opt = new ActionEliminationOptimizer();
			opt.greedyOptimizePlanCost(problem, plan);
			return;
		case maxsatElimination:
			RedundancyEliminator elm = new RedundancyEliminator();
			elm.optimizeWithMaxSat(problem, plan, new Sat4JMaxsatSolver());
			return;
		case segmentedOptimization:
			PlanOptimizer pop = new PlanOptimizer();
			PlanOptimizerParameters params = new PlanOptimizerParameters();
			pop.optimizePlan(problem, plan, params);
			return;
		}
	}
}
