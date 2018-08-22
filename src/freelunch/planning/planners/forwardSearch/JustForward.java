package freelunch.planning.planners.forwardSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import freelunch.planning.model.BasicSettings;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasAction;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.TimeoutException;
import freelunch.planning.planners.AbstractPlanner;
import freelunch.planning.planners.Planner;
import freelunch.utilities.Stopwatch;

public class JustForward extends AbstractPlanner implements Planner {

    protected final ForwardSearchSettings settings;

	public JustForward(SasProblem problem, ForwardSearchSettings settings) {
		super(problem);
		this.settings = settings;
	}

	@Override
	public SasParallelPlan solve() throws TimeoutException,	NonexistentPlanException {
		Stopwatch time = new Stopwatch();
		List<SasAction> plan = new ArrayList<SasAction>();
		int iterations = 0;
		
        int[] state = Arrays.copyOf(initialState, initialState.length);
        Set<SasAction> applicableActions = getApplicableActions(state);

        while (!isGoalState(state)) {
        	iterations++;
        	if (time.limitExceeded(settings.getTimelimit())) {
        		System.out.print(String.format(" iters = %d ", iterations));
        		throw new TimeoutException();
        	}
        	if (applicableActions.isEmpty()) {
        		// restart
        		plan.clear();
        		state = Arrays.copyOf(initialState, initialState.length);
        		applicableActions = getApplicableActions(state);
        	}
            SasAction best = settings.getHeuristic().select(state, new ArrayList<SasAction>(applicableActions));
            plan.add(best);
            int[] change = applyActionInPlace(best, state);
            updateApplicableActionsChanges(applicableActions, state, change);
        }
		
		List<List<SasAction>> pplan = new ArrayList<List<SasAction>>();
        for (SasAction op : plan) {
            List<SasAction> step = new ArrayList<SasAction>(1);
            step.add(op);
            pplan.add(step);
        }
		System.out.print(String.format(" iters = %d ", iterations));
        return new SasParallelPlan(pplan);
	}

	@Override
	public BasicSettings getSettings() {
		return settings;
	}

}
