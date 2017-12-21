package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.translator.CompactDirect;
import freelunch.core.satModelling.intModellers.IntVarGroupManager.IntVarGroup;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.satSolving.maxsat.MaxSatSolver;
import freelunch.core.satSolving.maxsat.Sat4JMaxsatSolver;
import freelunch.core.satSolving.maxsat.WeightedPartialMaxSatFormula;
import freelunch.core.satSolving.maxsat.WeightedPartialMaxSatFormula.WeightedClause;
import freelunch.core.utilities.IntVector;

public class PotentialPlanToMaxsatOptimizer {
	
	private class PPCompactDirect extends CompactDirect {
		
		private PotentialPlan pp;
		private IntVarGroup actionUsed;
		
	    @Override
	    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
	        universal_atMostOneAssignmentPerVariable(solver, time);
	        universal_actionImpliesItsEffects(solver, time);
	        //universal_mutexActionPairsNogood(solver, time);
	        universal_ppaConstraints(solver, time);
	    }
	    /**/
	    
	    private void universal_ppaConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
	    	// actions not in the pp step forbidden
	    	Set<SasAction> allowedActions = new HashSet<>(pp.getPlan().get(time));
	    	for (SasAction a : actions) {
	    		if (!allowedActions.contains(a)) {
	    			solver.addNewClause(IntVector.makeUnit(-actionVariables.getVariable(a.getId(), time)));
	    		}
	    	}
	    	// at most one of the actions in the pp step is allowed
	    	// an action triggers the action used indicator
	    	IntVector literals = new IntVector(allowedActions.size());
	    	IntVector trigger = new IntVector(2);
	    	for (SasAction a : allowedActions) {
	    		int act = actionVariables.getVariable(a.getId(), time);
	    		literals.add(act);
	    		// act => actionUsed
	    		trigger.clear();
	    		trigger.add(-act);
	    		trigger.add(actionUsed.getVariable(a.getId()));
	    		solver.addNewClause(trigger);
	    	}
	    	solver.addAtMostOneConstraint(literals);
	    }

		public PPCompactDirect(SasProblem problem, PotentialPlan pp) {
			super(problem);
			actionUsed = varManager.createNewVarGroup(1);
			actionUsed.setDimensionSize(0, actions.size());
			this.pp = pp;
		}
		
		public WeightedPartialMaxSatFormula makeFormula() {
			BasicSatFormula f = makeFormulaForMakespan(pp.getPlan().size() - 1);
			WeightedPartialMaxSatFormula result = new WeightedPartialMaxSatFormula(f.getVariables());
			result.getHardClauses().addAll(f.getClauses());
			
			for (SasAction a : actions) {
				int actu = actionUsed.getVariable(a.getId());
				result.getSoftClauses().add(new WeightedClause(a.getCost(), new int[] {-actu}));
			}
			return result;
		}
		
	}
	
	public WeightedPartialMaxSatFormula encodeToMaxSat(SasProblem problem, SasParallelPlan plan, PotentialPlan pplan) {
		updateProblem(problem, plan);
		PPCompactDirect translator = new PPCompactDirect(problem, pplan);
		return translator.makeFormula();
	}
	
	public SasParallelPlan optimizePlan(SasProblem problem, SasParallelPlan plan, PotentialPlan pplan) {
		updateProblem(problem, plan);
		PPCompactDirect translator = new PPCompactDirect(problem, pplan);
		WeightedPartialMaxSatFormula wpmax = translator.makeFormula();

		MaxSatSolver s4j = new Sat4JMaxsatSolver();
		int[] model = s4j.solvePartialWeightedMaxsat(wpmax);
		if (model == null) {
			return null;
		} else {
			return translator.decodePlan(model, pplan.getPlan().size()-1);
		}
	}
	
	private void updateProblem(SasProblem problem, SasParallelPlan plan) {
		Set<SasAction> actions = new HashSet<SasAction>();
		for (SasAction a : PlanningUtils.planToList(plan)) {
			actions.add(a);
		}
		problem.setOperators(new ArrayList<SasAction>(actions));
	}

}
