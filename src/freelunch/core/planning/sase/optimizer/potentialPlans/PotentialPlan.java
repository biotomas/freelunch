package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.model.SasAction;

public class PotentialPlan {
	
	private List<Set<SasAction>> plan;
	
	public PotentialPlan() {
		plan = new ArrayList<>();
	}

	public List<Set<SasAction>> getPlan() {
		return plan;
	}
	
	@Override
	public String toString() {
		int step = 0;
		StringBuilder sb = new StringBuilder();
		for (Set<SasAction> segment : plan) {
			sb.append(String.format("%d: [%d] ", step++, segment.size()));
			for (SasAction a : segment) {
				sb.append(String.format("(%s) ", a.getActionInfo().getName()));
			}
			sb.append('\n');
		}
		return sb.toString();
	}

}
