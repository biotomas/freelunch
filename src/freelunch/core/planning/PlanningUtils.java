package freelunch.core.planning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;

public class PlanningUtils {
	
	public static class QuantumState {
		
		public Set<Integer>[] state;
		public BitSet anyValue;
		
		public QuantumState(Collection<Condition> conditions, int vars) {
			this(vars);
			anyValue.set(0, vars);
			for (Condition c : conditions) {
				int varId = c.getVariable().getId();
				state[varId].add(c.getValue());
				anyValue.clear(varId);
			}
		}
		
		@SuppressWarnings("unchecked")
		public QuantumState(int vars) {
			state = new Set[vars];
			for (int i = 0; i < vars; i++) {
				state[i] = new HashSet<Integer>();
			}
			anyValue = new BitSet(vars);
			anyValue.clear();
		}
		
		public boolean conditionsHold(Collection<Condition> conditions) {
			for (Condition c : conditions) {
				int varId = c.getVariable().getId();
				if (anyValue.get(varId)) {
					continue;
				}
				if (!state[varId].contains(c.getValue())) {
					return false;
				}
			}
			return true;
		}
		
		public boolean conditionsHoldAndUseful(Collection<Condition> conditions) {
			boolean usefull = false;
			for (Condition c : conditions) {
				int varId = c.getVariable().getId();
				if (!state[varId].contains(c.getValue())) {
					if (!anyValue.get(varId)) {
						return false;
					}
				} else {
					usefull = true;
				}
			}
			return usefull;
			
		}
		
		public void addConditions(Collection<Condition> conditions) {
			for (Condition c : conditions) {
				state[c.getVariable().getId()].add(c.getValue());
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < state.length; i++) {
				if (state[i].size() > 0) {
					sb.append("v"+i+"={"+state[i].toString()+"}, ");
				}
			}
			sb.append('\n');
			sb.append(anyValue.toString());					
			return sb.toString();
		}
	}
	
	public static class HashableState {
		
		public int[] state;
		
		public HashableState(int[] state, boolean copy) {
			if (copy) {
				this.state = Arrays.copyOf(state, state.length);
			} else {
				this.state = state;
			}
		}
		
		@Override
		public String toString() {
			return Arrays.toString(state);
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(state);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof HashableState) {
				return Arrays.equals(state, ((HashableState) obj).state);
			} else {
				return false;
			}
		}
	}

	public static List<SasAction> planToList(SasParallelPlan plan) {
	    List<SasAction> lplan = new ArrayList<>();
	    plan.linearize();
	    for (List<SasAction> segment : plan.getPlan()) {
	        lplan.add(segment.get(0));
	    }
	    return lplan;
	}

	public static void listToPlan(List<SasAction> list, SasParallelPlan plan) {
	    plan.getPlan().clear();
	    for (SasAction a : list) {
	        List<SasAction> seg = new ArrayList<>(1);
	        seg.add(a);
	        plan.getPlan().add(seg);
	    }
	}
	
	public static SasParallelPlan listToPlan(List<SasAction> list) {
		SasParallelPlan spp = new SasParallelPlan(new ArrayList<List<SasAction>>());
		listToPlan(list, spp);
		return spp;
	}

	public static int[] getInitialState(SasProblem problem) {
	    int[] state = new int[problem.getVariables().size()+1];
	    for (Condition c : problem.getInitialState()) {
	        state[c.getVariable().getId()] = c.getValue();
	    }
	    return state;
	}

	public static boolean goalSatisfied(int[] state, SasProblem problem) {
	    for (Condition g : problem.getGoal()) {
	        if (state[g.getVariable().getId()] != g.getValue()) {
	            return false;
	        }
	    }
	    return true;
	}

	public static void applyAction(int[] state, SasAction a) {
	    for (Condition e : a.getEffects()) {
	        state[e.getVariable().getId()] = e.getValue();            
	    }
	}

	public static boolean isApplicable(int[] state, SasAction a) {
	    for (Condition p : a.getPreconditions()) {
	        if (state[p.getVariable().getId()] != p.getValue()) {
	            return false;
	        }
	    }
	    return true;
	}

	public static void setPreconditionsOfAction(int[] state, SasAction a) {
	    for (Condition c : a.getPreconditions()) {
	        state[c.getVariable().getId()] = c.getValue();
	    }
	}

}
