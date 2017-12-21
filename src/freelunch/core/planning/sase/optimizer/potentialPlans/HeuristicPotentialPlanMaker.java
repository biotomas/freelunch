package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.PlanningUtils.QuantumState;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.blockDecomposition.BlockDecomposition;

public class HeuristicPotentialPlanMaker implements PotentialPlanMaker {

	@Override
	public PotentialPlan makePotentialPlan(SasProblem problem, SasParallelPlan plan) {
		
		PotentialPlan pp = new PotentialPlan();
		List<SasAction> actions = PlanningUtils.planToList(plan);
		
		for (int i = 0; i < actions.size(); i++) {
			pp.getPlan().add(new HashSet<SasAction>());
		}
		
		QuantumState qstart = new QuantumState(problem.getInitialState(), problem.getVariables().size());
		
		for (int i = 0; i < actions.size(); i++) {
			for (int j = 0; j < actions.size(); j++) {
				SasAction a = actions.get(j);
				if (qstart.conditionsHold(a.getPreconditions())) {
					pp.getPlan().get(i).add(a);
				}
			}
			for (SasAction a : pp.getPlan().get(i)) {
				qstart.addConditions(a.getEffects());
			}
		}
		
		QuantumState qend = new QuantumState(problem.getGoal(), problem.getVariables().size());

		for (int i = actions.size()-1; i >= 0; i--) {
			Iterator<SasAction> iter = pp.getPlan().get(i).iterator();
			while (iter.hasNext()) {
				SasAction a = iter.next();
				if (!qend.conditionsHoldAndUseful(a.getEffects())) {
					iter.remove();
				}
			}
			for (SasAction a : pp.getPlan().get(i)) {
				qend.addConditions(a.getPreconditions());
			}
		}
		return pp;
	}

	@Override
	public PotentialPlan makePotentialPlan(SasProblem problem,
			SasParallelPlan plan, BlockDecomposition blockDecomposition) {
		throw new UnsupportedOperationException();
	}

}
