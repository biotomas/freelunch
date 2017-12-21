package freelunch.core.planning.cmdline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import freelunch.core.planning.PlanningUtils;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.ActionEliminationOptimizer;
import freelunch.core.planning.sase.optimizer.PlanLoader;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.optimizer.RedundancyEliminator;
import freelunch.core.planning.sase.optimizer.potentialPlans.HeuristicPotentialPlanMaker;
import freelunch.core.planning.sase.optimizer.potentialPlans.PotentialPlan;
import freelunch.core.planning.sase.optimizer.potentialPlans.PotentialPlanMaker;
import freelunch.core.planning.sase.optimizer.potentialPlans.RecursivePotentialPlanMaker;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.utilities.Stopwatch;

public class ReducerMain {
    
    private enum Mode {
        ae,
        gae,
        sat,
        aegaesat,
        pmax,
        wpmax,
        eval,
        rpp,
        hpp,
        sas
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("USAGE: java -jar fl.jar (ae | gae | sat | aegaesat | pmax | wpmax | eval | rpp | hpp | sas) problem.sas plan.sol (maxsat.wcnf | maxsat.out)");
            System.out.println(" where: ae = action elimination, gae = greedy action elimination, sat = sat reduction, " +
            		"pmax = partial maxsat reduction, wpmax = " +
            		"weighted partial maxsat reduction, eval = evaluate maxsat result");
            return;
        }
        System.out.println("Starting reducer with params " + Arrays.toString(args));
        ActionEliminationOptimizer actElim = new ActionEliminationOptimizer();
        RedundancyEliminator elim = new RedundancyEliminator();
        
        Mode mode = Mode.valueOf(args[0]);
        
        try {
            SasProblem problem = SasIO.parse(args[1]);
            SasParallelPlan plan = PlanLoader.loadPlanFromFile(args[2], problem);
            if (!PlanVerifier.verifyPlan(problem, plan)) {
                System.out.println(args[1] + ": input plan invalid");
                return;
            }

            int oldPlanCost = plan.getPlanCost();
            int oldPlanLength = plan.getPlanLength();
            System.out.println("plan loaded, has cost " + oldPlanCost);
            int newPlanCost = -1;
            int newPlanLength = -1;
            // plan is loaded and verified
            
            Stopwatch timer = new Stopwatch();
            switch (mode) {
            case ae:
                actElim.optimizePlan(problem, plan);
                newPlanCost = plan.getPlanCost();
                newPlanLength = plan.getPlanLength();
                break;
            case gae:
                actElim.greedyOptimizePlanCost(problem, plan);
                newPlanCost = plan.getPlanCost();                
                newPlanLength = plan.getPlanLength();
                break;
            case sat:
                elim.optimizePlanIncremental(problem, plan);
                newPlanCost = plan.getPlanCost();
                newPlanLength = plan.getPlanLength();
                break;
            case aegaesat:
                SasParallelPlan p = plan.copy();
                actElim.optimizePlan(problem, p);
                int aelen = p.getPlanLength();
                elim.optimizePlanIncremental(problem, p);
                int aelen2 = p.getPlanLength();
                
                p = plan.copy();
                actElim.greedyOptimizePlanCost(problem, p);
                int gaelen = p.getPlanLength();
                elim.optimizePlanIncremental(problem, p);
                int gaelen2 = p.getPlanLength();
                String s1 = aelen == aelen2 ? "pfae" : "no";
                String s2 = gaelen == gaelen2 ? "pfgae" : "no";
                System.out.println(String.format("data;%s;%s;%s;%s", args[0], args[1], s1, s2));
                return;
            case pmax:
                elim.encodeToPMaxSat(problem, plan).saveToDimacsFile(args[3]);
                System.out.println("Partial max sat formula saved to " + args[3]);
                return;
            case wpmax:
                elim.encodeToWeightedPMaxSat(problem, plan).saveToDimacsFile(args[3]);
                System.out.println("Weighted partial max sat formula saved to " + args[3]);
                return;
            case eval:
                elim.updatePlanUsingMaxSatSolution(problem, plan, args[3]);
                newPlanCost = plan.getPlanCost();
                newPlanLength = plan.getPlanLength();
                break;
            case rpp:
            case hpp:
            	PotentialPlanMaker ppm = mode == Mode.rpp ? new RecursivePotentialPlanMaker() : new HeuristicPotentialPlanMaker();
            	System.out.println("plan length is " + plan.getTotalActions());
            	Stopwatch pptime = new Stopwatch();
            	PotentialPlan pp = ppm.makePotentialPlan(problem, plan);
            	System.out.println(pp.toString());
            	System.out.println(String.format("time=%s", pptime.elapsedFormatedSeconds()));
            	return;
            case sas:
            	Set<SasAction> actions = new HashSet<>();
            	for (SasAction a : PlanningUtils.planToList(plan)) {
            		actions.add(a);
            	}
            	int actionCount = problem.getOperators().size();
            	problem.setOperators(new ArrayList<>(actions));
            	SasIO.saveToFile(problem, args[3]);
                System.out.println(String.format("Reduced SAS problem saved to %s, actions %d->%d", 
                		args[3], actionCount, actions.size()));
            	return;
            }
            
            if (!PlanVerifier.verifyPlan(problem, plan)) {
                System.out.println(args[1] + ": reduced plan invalid");
                return;
            }
            
            System.out.println("header;algorithm;filename;orig-cost;new-cost;org-length;new-length;time");
            System.out.println(String.format("data;%s;%s;%d;%d;%d;%d;%s", args[0], args[1], oldPlanCost, newPlanCost, 
                    oldPlanLength, newPlanLength, timer.elapsedFormatedSeconds()));
            
        } catch (IOException e) {
            System.out.println("Problem or plan file cannot be opened.");
            return;
        } catch (TimeoutException e) {
            System.out.println("solver timeout");
            return;
        }

    }

}
