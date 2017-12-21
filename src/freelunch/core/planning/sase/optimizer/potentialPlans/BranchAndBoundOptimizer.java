package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.PlanningUtils.HashableState;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.utilities.Tuple;

public class BranchAndBoundOptimizer {
	
	private List<SasAction> actions;
	private BitSet actionUsed;
	private SasProblem problem;
	private Map<Tuple<BitSet, HashableState>, Integer> cache;
	private Stack<SasAction> plan;
	private int result;
	private int bestResult;
	private List<SasAction> bestPlan;
	
	public SasParallelPlan optimizePlan(SasProblem problem, SasParallelPlan orgPlan) {
		
		cache = new HashMap<Tuple<BitSet,HashableState>, Integer>();
		this.problem = problem;
		actions = PlanningUtils.planToList(orgPlan);
		actionUsed = new BitSet(actions.size());
		actionUsed.clear();
		bestPlan = null;
		result = 0;
		plan = new Stack<SasAction>();
		
		int[] state = PlanningUtils.getInitialState(problem);
		recursiveCompute(state, 0);
		
		return PlanningUtils.listToPlan(bestPlan);
	}
	
	private int recursiveCompute(int[] state, int level) {
		if (PlanningUtils.goalSatisfied(state, problem)) {
			if (bestPlan == null || plan.size() < bestResult) {
				bestPlan = new ArrayList<>(plan);
				bestResult = bestPlan.size();
			}
			return plan.size();
		}
		for (int i = 0; i < actions.size(); i++) {
			if (!actionUsed.get(i) && PlanningUtils.isApplicable(state, actions.get(i))) {
				
				actionUsed.set(i);
				plan.push(actions.get(i));
				result++;
				
				int[] nstate = Arrays.copyOf(state, state.length);
				PlanningUtils.applyAction(nstate, actions.get(i));
				
				Tuple<BitSet, HashableState> tup = new Tuple<BitSet, PlanningUtils.HashableState>(
						actionUsed, new HashableState(nstate, false));
				if (cache.containsKey(tup)) {
					//TODO unfinished
					//System.out.println("yeah!");
				} else {
					int res = recursiveCompute(nstate, level + 1);
					Tuple<BitSet, HashableState> tupc = new Tuple<BitSet, PlanningUtils.HashableState>(
							(BitSet) actionUsed.clone(), new HashableState(nstate, true));
					cache.put(tupc, res);
				}
				
				actionUsed.clear(i);
				plan.pop();
				result--;
			}
		}
		return result+257;
	}


}
