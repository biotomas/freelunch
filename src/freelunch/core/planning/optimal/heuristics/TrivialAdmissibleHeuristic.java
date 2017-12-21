package freelunch.core.planning.optimal.heuristics;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.model.SasProblem;

public class TrivialAdmissibleHeuristic implements StateGoalDistanceHeuristic {

	private SasProblem problem;
	
	public TrivialAdmissibleHeuristic(SasProblem problem) {
		this.problem = problem;
	}
	
	@Override
	public int getGoalDistance(int[] state) {
		return PlanningUtils.goalSatisfied(state, problem) ? 0 : 1;
	}

}
