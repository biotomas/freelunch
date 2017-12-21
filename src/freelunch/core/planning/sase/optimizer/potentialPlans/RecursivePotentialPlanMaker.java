package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.blockDecomposition.BlockDecomposition;

public class RecursivePotentialPlanMaker implements PotentialPlanMaker {
	
	/**
	 * Approximate caching, faulty if two pairs of int[] and bitset have same hash values 
	 */
	private static class CacheKey {
		
		private int stateh;
		private int bitseth;
		private BitSet bs;
		private int[] st;
		
		public CacheKey(int[] state, BitSet bitset) {
			bs = (BitSet) bitset.clone();
			st = Arrays.copyOf(state, state.length);
			stateh = Arrays.hashCode(state);
			bitseth = bitset.hashCode();
		}
		
		@Override
		public int hashCode() {
			return stateh * bitseth;
		}
		
		@Override
		public boolean equals(Object obj) {
			CacheKey ck = (CacheKey) obj;
			if (ck.stateh == stateh && ck.bitseth == bitseth) {
				return bs.equals(ck.bs) && Arrays.equals(st, ck.st);
			} else {
				return false;
			}
		}
	}
	
	private PotentialPlan pp;
	private List<SasAction> actions;
	private BitSet actionUsed;
	private SasProblem problem;
	private Map<CacheKey, Boolean> cache;
	
	@Override
	public PotentialPlan makePotentialPlan(SasProblem problem, SasParallelPlan plan) {
		
		cache = new HashMap<CacheKey, Boolean>();
		this.problem = problem;
		pp = new PotentialPlan();
		actions = PlanningUtils.planToList(plan);
		actionUsed = new BitSet(actions.size());
		actionUsed.clear();
		
		int[] state = PlanningUtils.getInitialState(problem);
		recursiveCompute(state, 0);
		
		return pp;
	}
	
	private boolean recursiveCompute(int[] state, int level) {
		if (PlanningUtils.goalSatisfied(state, problem)) {
			return true;
		}
		boolean usedOne = false;
		for (int i = 0; i < actions.size(); i++) {
			if (!actionUsed.get(i) && PlanningUtils.isApplicable(state, actions.get(i))) {
				actionUsed.set(i);
				
				int[] nstate = Arrays.copyOf(state, state.length);
				PlanningUtils.applyAction(nstate, actions.get(i));

				CacheKey ck = new CacheKey(nstate, actionUsed);
				if (cache.containsKey(ck)) {
					if (cache.get(ck)) {
						while (level >= pp.getPlan().size()) {
							pp.getPlan().add(new HashSet<SasAction>());
						}
						pp.getPlan().get(level).add(actions.get(i));
						usedOne = true;
					}
				} else {
					if (recursiveCompute(nstate, level + 1)) {
						while (level >= pp.getPlan().size()) {
							pp.getPlan().add(new HashSet<SasAction>());
						}
						pp.getPlan().get(level).add(actions.get(i));
						usedOne = true;
						cache.put(ck, true);
					} else {
						cache.put(ck, false);
					}
				}
				actionUsed.clear(i);
			}
		}
		return usedOne;
	}

	@Override
	public PotentialPlan makePotentialPlan(SasProblem problem,
			SasParallelPlan plan, BlockDecomposition blockDecomposition) {
		throw new UnsupportedOperationException();
	}

}
