package freelunch.core.planning.optimal.heuristics;

public interface StateGoalDistanceHeuristic {
	
	/**
	 * Estimate the distance to a goal state from a given state. Must return 0
	 * if the given state is a goal state and a positive number otherwise.
	 * @param state
	 * @return
	 */
	public int getGoalDistance(int[] state);

}
