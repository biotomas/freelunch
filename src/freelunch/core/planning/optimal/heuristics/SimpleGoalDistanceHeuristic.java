package freelunch.core.planning.optimal.heuristics;

import java.util.BitSet;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;

/**
 * A simple admissible heuristic for planning. It computes the maximum number
 * of goal related effects any action has and then multiplies it by the
 * number of goal variables not set to a proper value and the minimal action cost.
 */
public class SimpleGoalDistanceHeuristic implements StateGoalDistanceHeuristic {
	
	private SasProblem problem;
	private int maxEffects = 0;
	private int minActionCost = Integer.MAX_VALUE;
	
	public SimpleGoalDistanceHeuristic(SasProblem problem) {
		this.problem = problem;
		// variables that appear in goal conditions
		BitSet goalVars = new BitSet(problem.getVariables().size());
		goalVars.clear();
		for (Condition g : problem.getGoal()) {
			goalVars.set(g.getVariable().getId());
		}
		for (SasAction a : problem.getOperators()) {
			// number of effects related to goal variables
			int effs = 0;
			for (Condition e : a.getEffects()) {
				if (goalVars.get(e.getVariable().getId())) {
					effs++;
				}
			}
			if (effs > maxEffects) {
				maxEffects = effs;
			}
			if (effs > 0 && a.getCost() < minActionCost) {
				minActionCost = a.getCost();
			}
		}
		if (minActionCost < 1) {
			minActionCost = 1;
		}
	}

	@Override
	public int getGoalDistance(int[] state) {
		int differences = 0;
	    for (Condition g : problem.getGoal()) {
	        if (state[g.getVariable().getId()] != g.getValue()) {
	            differences++;
	        }
	    }
		return (differences*minActionCost) / maxEffects;
	}

}
