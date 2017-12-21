package freelunch.core.planning.sase.optimizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.satSolving.SatSolver;
import freelunch.core.satSolving.maxsat.MaxSatSolver;
import freelunch.core.satSolving.maxsat.PartialMaxSatFormula;
import freelunch.core.satSolving.maxsat.WeightedPartialMaxSatFormula;
import freelunch.core.satSolving.maxsat.WeightedPartialMaxSatFormula.WeightedClause;
import freelunch.core.utilities.IntVector;

public class RedundancyEliminator {
	
	public void optimizeWithMaxSat(SasProblem problem, SasParallelPlan plan, MaxSatSolver solver) {
		WeightedPartialMaxSatFormula f = encodeToWeightedPMaxSat(problem, plan);
		int[] model = solver.solvePartialWeightedMaxsat(f);
		updatePlanUsingMaxSatSolution(problem, plan, model);
	}
    
    public PartialMaxSatFormula encodeToPMaxSat(SasProblem problem, SasParallelPlan plan) {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        BasicSatFormula planf = encodePlan(problem, lplan);
        PartialMaxSatFormula pmaxf = new PartialMaxSatFormula(planf.getVariables());
        // plan clauses are the hard clauses
        pmaxf.getHardClauses().addAll(planf.getClauses());

        // soft clauses disable actions
        for (int act = 1; act <= lplan.size(); act++) {
            pmaxf.getSoftClauses().add(new int[] {-act});
        }
        
        return pmaxf;
    }

    public WeightedPartialMaxSatFormula encodeToWeightedPMaxSat(SasProblem problem, SasParallelPlan plan) {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        BasicSatFormula planf = encodePlan(problem, lplan);
        WeightedPartialMaxSatFormula wpmaxf = new WeightedPartialMaxSatFormula(planf.getVariables());
        // plan clauses are the hard clauses
        wpmaxf.getHardClauses().addAll(planf.getClauses());

        // soft clauses disable actions
        for (int act = 1; act <= lplan.size(); act++) {
            SasAction a = lplan.get(act - 1);
            if (a.getCost() > 0) {
                WeightedClause wc = new WeightedClause(a.getCost(), new int[]{-act});
                wpmaxf.getSoftClauses().add(wc);
            }
        }
        return wpmaxf;
    }
    
    public void updatePlanUsingMaxSatSolution(SasProblem problem, SasParallelPlan plan, String maxResultFile) throws IOException {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        BasicSatFormula planf = encodePlan(problem, lplan);
        
        FileReader fr = new FileReader(maxResultFile);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("v")) {
                String[] parts = line.split(" ");
                int[] model = new int[planf.getVariables()+1];
                for (int i = 1; i < parts.length; i++) {
                    int lit = Integer.parseInt(parts[i]);
                    model[Math.abs(lit)] = lit;
                }
                updatePlanUsingMaxSatSolution(problem, plan, model);
            }
            line = reader.readLine();
        }
        fr.close();
    }

    public void updatePlanUsingMaxSatSolution(SasProblem problem, SasParallelPlan plan, int[] model) {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        List<SasAction> nplan = new ArrayList<SasAction>();
        for (int act = 1; act <= lplan.size(); act++) {
            if (model[act] > 0) {
                nplan.add(lplan.get(act-1));
            }
        }
        PlanningUtils.listToPlan(nplan, plan);
    }

    /**
     * Removes all redundant actions from the given plan using SAT.
     * Consider using {@link ActionEliminationOptimizer} before this method.
     * The plan should be linearized.
     * @param problem
     * @param plan
     * @throws TimeoutException 
     */
    public void optimizePlan(SasProblem problem, SasParallelPlan plan) throws TimeoutException {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        SatSolver solver = new Sat4JSolver();

        while (true) {
            BasicSatFormula f = encodeRedundancy(problem, lplan);
            if (solver.isSatisfiable(f)) {
                lplan = improvePlan(lplan, solver.getModel());
            } else {
                break;
            }
        }
        PlanningUtils.listToPlan(lplan, plan);
    }
    
    
    public void optimizePlanIncremental(SasProblem problem, SasParallelPlan plan) throws TimeoutException {
        List<SasAction> lplan = PlanningUtils.planToList(plan);
        IncrementalSatSolver solver = new Sat4JSolver();
        BasicSatFormula f = encodeRedundancy(problem, lplan);
        solver.setVariablesCount(f.getVariables());
        try {
            for (int[] c : f.getClauses()) {
                solver.addNewClause(new IntVector(c));
            }
        } catch (SatContradictionException e) {
            // f is unsatisfiable
            return;
        }

        int[] model = null;
        while (solver.isSatisfiable()) {
            model = solver.getModel();
            try {
                IntVector newRedundClause = new IntVector(5);
                for (int i = 1; i <= lplan.size(); i++) {
                    if (model[i] > 0) {
                        newRedundClause.add(-i);
                    } else {
                        solver.addNewClause(new IntVector(new int[] {-i}));
                    }
                }
                solver.addNewClause(newRedundClause);
            } catch (SatContradictionException e) {
                break;
            }
        }
        if (model != null) {
            lplan = improvePlan(lplan, model);
        }
        PlanningUtils.listToPlan(lplan, plan);
    }

    private List<SasAction> improvePlan(List<SasAction> plan, int[] model) {
        List<SasAction> newPlan = new ArrayList<>();
        for (int i = 1; i <= plan.size(); i++) {
            if (model[i] > 0) {
                newPlan.add(plan.get(i-1));
            }
        }
        return newPlan;
    }

    @Deprecated
    @SuppressWarnings("unused")
    //TODO FIXME unfinished
    private BasicSatFormula encodeRedundancyChainy(SasProblem problem, List<SasAction> plan) {
        // the first n variables represent the actions
        variables = 2 + plan.size();
        List<int[]> clauses = new ArrayList<int[]>();
        int[] initState = new int[problem.getVariables().size()];
        for (Condition i : problem.getInitialState()) {
            initState[i.getVariable().getId()] = i.getValue();
        }
        
        // encode the chains
        for (StateVariable var : problem.getVariables()) {
            for (int value = 0; value < var.getDomain(); value++) {
                // encode the chain for var != value
                //TODO FIXME unfinished
                Condition c = new Condition(var, value);
                Iterator<Integer> opponents = opponentActionsBetween(plan, c, 0, plan.size()).iterator();
                Iterator<Integer> friends = friendlyActionsBefore(plan, c, plan.size()).iterator();
                Iterator<Integer> requirers = requiringActions(plan, c).iterator();
                int opponent = 0, friend = 0, requirer = 0;
                if (opponents.hasNext()) {
                    opponent = opponents.next();
                }
                if (friends.hasNext()) {
                    friend = friends.next();
                }
                if (requirers.hasNext()) {
                    requirer = requirers.next();
                } else {
                    continue;
                }
                
            }
        }
        
        int actionId = 1;
        for (SasAction a : plan) {
            for (Condition c : a.getPreconditions()) {
                clauses.addAll(getConditionClauses(true, actionId, c, initState, plan));
            }
            actionId++;
        }
        
        BasicSatFormula fla = new BasicSatFormula(variables, clauses);
        return fla;
        
    }
    
    
    private BasicSatFormula encodeRedundancy(SasProblem problem, List<SasAction> plan) {
        BasicSatFormula fla = encodePlan(problem, plan);
        // at least one action should not be used
        int[] cardClause = new int[plan.size()];
        for (int i = 1; i <= plan.size(); i++) {
            cardClause[i-1] = -i;
        }
        fla.getClauses().add(cardClause);
        return fla;
    }
    
    private int variables;
    private BasicSatFormula encodePlan(SasProblem problem, List<SasAction> plan) {
        // the first n variables represent the actions
        variables = 1 + plan.size();
        List<int[]> clauses = new ArrayList<int[]>();
        int[] initState = new int[problem.getVariables().size()];
        for (Condition i : problem.getInitialState()) {
            initState[i.getVariable().getId()] = i.getValue();
        }
        
        // encode the plan
        int actionId = 1;
        for (SasAction a : plan) {
            for (Condition c : a.getPreconditions()) {
                clauses.addAll(getConditionClauses(true, actionId, c, initState, plan));
            }
            actionId++;
        }
        // encode goal conditions
        for (Condition c : problem.getGoal()) {
            clauses.addAll(getConditionClauses(false, plan.size() + 1, c, initState, plan));            
        }
        
        BasicSatFormula fla = new BasicSatFormula(variables, clauses);
        return fla;
    }
    
    private List<int[]> getConditionClauses(boolean action, int actionId, Condition c, int[] initState, List<SasAction> plan) {
        // condition -> (initial \/ (friend1 /\ not(enemy1,1) /\ not(enemy1,2) ...) \/ (friend2 /\ ...) \/ ... )
        List<int[]> cls = new ArrayList<>();
        List<Integer> options = new ArrayList<>();
        if (initState[c.getVariable().getId()] == c.getValue()) {
            List<Integer> opps = opponentActionsBetween(plan, c, 0, actionId - 1);
            if (opps.isEmpty()) {
                // condition holds since the initial state and no action can break it
                // no clauses are needed.
                return cls;
            }
            int h = variables++;
            options.add(h);
            // h -> no opponents
            for (int opp : opps) {
                cls.add(new int[] {-h, -opp});
            }
        } 
        for (int friend : friendlyActionsBefore(plan, c, actionId - 1)) {
            List<Integer> opps = opponentActionsBetween(plan, c, friend, actionId - 1);
            if (opps.isEmpty()) {
                options.add(friend);
            } else {
                int h = variables++;
                options.add(h);
                cls.add(new int[] {-h, friend});
                for (int opp : opps) {
                    cls.add(new int[] {-h, -opp});
                }
            }
        }
        if (action) {
            // action conditions
            options.add(-actionId);
        }
        cls.add(IntVector.listToArray(options));
        return cls;
    }
    
    /**
     * Get the orders of actions that make the given condition satisfied
     * and their order is smaller than the given "to" value in the plan.
     * @param plan
     * @param before
     * @param c
     * @return
     */
    private List<Integer> friendlyActionsBefore(List<SasAction> plan, Condition c, int to) {
        List<Integer> vals = new ArrayList<>();
        for (int i = 0; i < to; i++) {
            SasAction a = plan.get(i);
            for (Condition e : a.getEffects()) {
                if (e.getVariable().getId() == c.getVariable().getId() && e.getValue() == c.getValue()) {
                    vals.add(i+1);
                    break;
                }
            }
        }
        return vals;
    }

    /**
     * Get the orders of actions that require the given condition 
     * (they have it as a precondition).
     * @param plan
     * @param c
     * @return
     */
    private List<Integer> requiringActions(List<SasAction> plan, Condition c) {
        List<Integer> vals = new ArrayList<>();
        for (int i = 0; i < plan.size(); i++) {
            SasAction a = plan.get(i);
            for (Condition e : a.getPreconditions()) {
                if (e.getVariable().getId() == c.getVariable().getId() && e.getValue() == c.getValue()) {
                    vals.add(i+1);
                    break;
                }
            }
        }
        return vals;
    }
    
    /**
     * Get the orders of actions that destroy the given condition and their
     * order is between "from" and "to" in the plan.
     * @param plan
     * @param c
     * @param from
     * @param to
     * @return
     */
    private List<Integer> opponentActionsBetween(List<SasAction> plan, Condition c, int from, int to) {
        List<Integer> vals = new ArrayList<>();
        for (int i = from; i < to; i++) {
            SasAction a = plan.get(i);
            for (Condition e : a.getEffects()) {
                if (e.getVariable().getId() == c.getVariable().getId() && e.getValue() != c.getValue()) {
                    vals.add(i+1);
                    break;
                }
            }
        }
        return vals;
    }


}
