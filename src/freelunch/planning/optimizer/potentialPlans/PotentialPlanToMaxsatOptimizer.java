package freelunch.planning.optimizer.potentialPlans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import freelunch.maxsat.MaxSatSolver;
import freelunch.maxsat.Sat4JMaxsatSolver;
import freelunch.maxsat.WeightedPartialMaxSatFormula;
import freelunch.maxsat.WeightedPartialMaxSatFormula.WeightedClause;
import freelunch.planning.PlanningUtils;
import freelunch.planning.model.SasAction;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.planners.satplan.translator.CompactDirect;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.IncrementalSatSolver;
import freelunch.sat.model.SatContradictionException;
import freelunch.sat.modelling.IntVarGroupManager.IntVarGroup;
import freelunch.utilities.IntVector;

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
	    			solver.addNewClause(new int[] {-actionVariables.getVariable(a.getId(), time)});
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
	    		solver.addNewClause(trigger.getArrayCopy());
	    	}
	    	solver.addAtMostOneConstraint(literals.getArrayCopy());
	    }

		public PPCompactDirect(SasProblem problem, PotentialPlan pp) {
			super(problem);
			actionUsed = varManager.createNewVarGroup(1);
			actionUsed.setDimensionSize(0, actions.size());
			this.pp = pp;
		}
		
		public WeightedPartialMaxSatFormula makeFormula() {
			CnfSatFormula f = makeFormulaForMakespan(pp.getPlan().size() - 1);
			WeightedPartialMaxSatFormula result = new WeightedPartialMaxSatFormula(f.variablesCount);
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
