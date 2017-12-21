package freelunch.core.planning.cmdline;

import java.io.IOException;
import java.util.Arrays;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.cmdline.Translator.TranslationMethod;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.satSolving.ExternalSatSolver;
import freelunch.core.utilities.ArrayUtils;
import freelunch.core.utilities.Stopwatch;

public class ExternalSolverPlanner {	
  
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("USAGE: java -jar fl.jar <problem.sas> <method> <sat-timelimit> [-p print plan] [-r N ranking type]");
            System.out.println("Methods: " + Arrays.toString(TranslationMethod.values()));
            return;
        }
        String problemName = args[0];
        TranslationMethod method = TranslationMethod.valueOf(args[1]);
        int timeLimit = Integer.parseInt(args[2]);
        boolean printPlan = false;
        if (ArrayUtils.find(args, "-p") >= 0) {
            printPlan = true;
        }
        int rid = ArrayUtils.find(args, "-r");
        // default ranking is 5
        int ranking = 5;
        if (rid >= 0) {
            ranking = Integer.parseInt(args[rid+1]);
        }

        SasProblem problem = null;
        
        System.out.print("Running ExternalSolverPlanner with params: " + Arrays.toString(args) + " ");

        try {
            problem = SasIO.parse(problemName);
            problem.setActionIDs();

            SasToSatTranslator translator = Translator.makeTranslator(problem, method, ranking);
            
            Stopwatch solveTime = new Stopwatch();
            IterativeSatBasedSolver solver = new IterativeSatBasedSolver(new ExternalSatSolver(), translator);
            solver.getSettings().setSatLimit(timeLimit);
            if (printPlan) {
                solver.getSettings().setVerbose(true);
            }
            SasParallelPlan plan = solver.solve();
            
            System.out.println(String.format("SOLVED in %s with %d steps sat-time %.2f", solveTime.elapsedFormatedSeconds(), plan.getPlanLength(),
                    ((float)solver.getStats().satTime)/1000f));
            
            if (printPlan) {
                System.out.println(plan);
            }
            
            boolean valid = PlanVerifier.verifyPlan(problem, plan);
            if (!valid) {
                System.out.println("!!!!!! INVALID PLAN !!!!!!");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("TIMEOUT");
        } catch (NonexistentPlanException e) {
            System.out.println("Plan does not exist");
        }

    }

}
