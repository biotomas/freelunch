package freelunch.core.planning.cmdline;

import java.io.IOException;

import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.forwardSearch.MemoryEfficientForwardSearchSolver;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.ActionEliminationOptimizer;
import freelunch.core.planning.sase.optimizer.PlanOptimizer;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerParameters;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.satSolving.ExternalSatSolver;

public class IPC14Planner {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("USAGE freelunch.jar <problem.sas> <planfile> [i : improve]");
            return;
        }
        String problemFile = args[0];
        String planFile = args[1];
        boolean improve = false;
        if (args.length > 2 && args[2].equals("i")) {
            improve = true;
        }
        SasProblem problem = SasIO.parse(problemFile);
        SasParallelPlan plan = null;
        
        // first try blind forward for 10 seconds
        Solver planner = new MemoryEfficientForwardSearchSolver(problem);
        planner.getSettings().setTimelimit(10);
        try {
            plan = planner.solve();
            System.out.println("forward search success");
        } catch (TimeoutException e) {
            System.out.println("forward search timeout");
        } catch (Throwable e) {
            System.out.println("forward search unexpected error: " + e.getMessage());
        }
        
        if (plan == null) {
            // SAT search
            SasToSatTranslator translator = new DirectExistStepTranslator(problem);
            Solver solver = new IterativeSatBasedSolver(new ExternalSatSolver(), translator);
            //Solver solver = new IterativeSatBasedSolver(new Sat4JSolver(), translator);
            try {
                plan = solver.solve();
                System.out.println("sat search success");
                // linearize the plan
                plan.linearize();
            } catch (Throwable e) {
                System.out.println("sat search unexpected error: " + e.getMessage());
            }
        }
        
        if (plan == null) {
            //failure
            return;
        }
        
        //verify the plan
        if (!PlanVerifier.verifyPlan(problem, plan)) {
            System.out.println("invalid plan");
            return;
        }
        
        // save the plan
        plan.saveToFileIpcFormat(planFile);
        System.out.println("The initial plan was saved");
        
        // improve plans
        if (improve) {
            System.out.println("Trying to improve the plan of size " + plan.getPlanLength());
            ActionEliminationOptimizer lopt = new ActionEliminationOptimizer();
            lopt.optimizePlan(problem, plan);
            System.out.println("Lightweight opt result: " + plan.getPlanLength());
            if (!PlanVerifier.verifyPlan(problem, plan)) {
                System.out.println("invalid plan after lightweight opt");
                return;
            }
            
            PlanOptimizer optimizer = new PlanOptimizer();
            PlanOptimizerParameters pop = new PlanOptimizerParameters();
            pop.setVerbose(true);
            pop.setIntermediatePlanFileName(planFile);
            pop.setFixedPointMode(true);
            optimizer.optimizePlan(problem, plan, pop);
            if (!PlanVerifier.verifyPlan(problem, plan)) {
                System.out.println("invalid plan after SAT opt");
                return;
            }
        }
    }
}
