package freelunch.planning.preprocessing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.planning.model.Condition;
import freelunch.planning.model.ConditionalEffect;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasAction;
import freelunch.planning.model.SasProblem;

public class ReachabilityAnalysis {
	
	/**
	 * Check if all goal conditions are reachable under a delete relaxation
	 * and remove non-reachable actions, return the number of removed actions
	 * or throw an exception if a goal is unreachable.
	 * @param problem
	 * @return
	 * @throws NonexistentPlanException
	 */
	public static int testGoalReachabilityAndRemoveUnreachableActions(SasProblem problem)
			throws NonexistentPlanException {
		Set<Condition> reachableConds = new HashSet<Condition>(problem.getInitialState());
		int rcsize = 0;
		while (reachableConds.size() > rcsize) {
			rcsize = reachableConds.size();
			for (SasAction a : problem.getOperators()) {
				if (reachableConds.containsAll(a.getPreconditions())) {
					reachableConds.addAll(a.getEffects());
				}
				for (ConditionalEffect ce : a.getConditionalEffects()) {
					if (reachableConds.containsAll(ce.getEffectConditions())) {
						reachableConds.add(new Condition(ce.getVar(), ce.getNewValue()));
					}
				}
			}
		}
		
		// check the goal conditions
		if (!reachableConds.containsAll(problem.getGoal())) {
			throw new NonexistentPlanException("Goal unreachable under delete relaxation.");
		}
		
		List<SasAction> reachableActions = new ArrayList<SasAction>();
		for (SasAction a : problem.getOperators()) {
			if (reachableConds.containsAll(a.getPreconditions())) {
				reachableActions.add(a);
			}
		}
		
		int removed = problem.getOperators().size() - reachableActions.size();
		problem.setOperators(reachableActions);
		return removed;
	}
	

}
