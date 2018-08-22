package freelunch.planning.optimal.heuristics;

import freelunch.planning.PlanningUtils;
import freelunch.planning.model.SasProblem;

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
