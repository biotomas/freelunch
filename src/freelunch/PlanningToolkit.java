package freelunch;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.ActionEliminationOptimizer;
import freelunch.core.planning.sase.optimizer.PlanOptimizer;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.optimizer.RedundancyEliminator;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerParameters;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.planning.sase.sasToSat.translator.ReinforcedSaseTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SelectiveTranslator;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.maxsat.Sat4JMaxsatSolver;

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
