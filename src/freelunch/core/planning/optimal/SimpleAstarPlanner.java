package freelunch.core.planning.optimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import freelunch.core.planning.AbstractSolver;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.PlanningUtils.HashableState;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.optimal.heuristics.StateGoalDistanceHeuristic;
import freelunch.core.utilities.Stopwatch;

public class SimpleAstarPlanner extends AbstractSolver {
	
	private BasicSettings settings;
	private StateGoalDistanceHeuristic heuristic;
	private Stats stats;
	
	public class Stats {
		public int maxOpen;
		public int closed;
		public String time;

		@Override
		public String toString() {
			return String.format("max open: %d closed: %d time %s", maxOpen, closed, time);
		}
	}
	
	public SimpleAstarPlanner(SasProblem problem, StateGoalDistanceHeuristic heuristic) {
		super(problem);
		settings = new BasicSettings();
		this.heuristic = heuristic;
	}
	
	private class AstarState extends HashableState implements Comparable<AstarState> {

		public int value;
		public int gvalue;
		public AstarState predeccessor;
		public SasAction action;

		public AstarState(int[] state) {
			super(state, false);
		}

		@Override
		public int compareTo(AstarState o) {
			return value - o.value;
		}
	}

	@Override
	public SasParallelPlan solve() throws TimeoutException,	NonexistentPlanException {
		Stopwatch watch = new Stopwatch();
		stats = new Stats();
		stats.maxOpen = 0;
		stats.closed = 0;
		
		Set<AstarState> closedList = new HashSet<>();
		PriorityQueue<AstarState> openList = new PriorityQueue<>();
		
		AstarState init = new AstarState(initialState);
		init.predeccessor = null;
		init.action = null;
		init.value = 0;
		init.gvalue = 0;
		
		openList.add(init);
		
		while (true) {
			if (watch.limitExceeded(settings.getTimelimit())) {
				stats.time = watch.elapsedFormatedSeconds();
				throw new TimeoutException();
			}
			
			if (openList.size() > stats.maxOpen) {
				stats.maxOpen = openList.size();
			}
			
			AstarState st = openList.poll();
			while (closedList.contains(st)) {
				st = openList.poll();
			}
			if (st == null) {
				break;
			}
			if (isGoalState(st.state)) {
				stats.time = watch.elapsedFormatedSeconds();
				return extractPlan(st);
			}
			closedList.add(st);
			stats.closed++;
			for (SasAction a : getApplicableActions(st.state)) {
				AstarState succ = new AstarState(applyAction(a, st.state));
				if (closedList.contains(succ)) {
					continue;
				}
				succ.predeccessor = st;
				succ.action = a;
				succ.gvalue = st.gvalue + a.getCost();
				succ.value = succ.gvalue + heuristic.getGoalDistance(succ.state);
				openList.add(succ);
			}
		}
		stats.time = watch.elapsedFormatedSeconds();
		return null;
	}
	
	private SasParallelPlan extractPlan(AstarState state) {
		List<SasAction> plan = new ArrayList<SasAction>();
		while (state.action != null) {
			plan.add(state.action);
			state = state.predeccessor;
		}
		Collections.reverse(plan);
		return PlanningUtils.listToPlan(plan);
	}

	@Override
	public BasicSettings getSettings() {
		return settings;
	}

	public Stats getStats() {
		return stats;
	}
}
