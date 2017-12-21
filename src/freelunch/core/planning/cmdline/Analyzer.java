package freelunch.core.planning.cmdline;

import java.io.IOException;

import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.FormulaAnalyzer;

public class Analyzer {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("USAGE: java -jar fl.jar [val: validate] formula.cnf OR problem.sas");
            System.out.println("USAGE: java -jar fl.jar check formula.cnf solver.out");
            return;
        }
        if (args[0].equals("check")) {
        	BasicSatFormula f = BasicSatFormula.parseFromFile(args[1]);
        	int[] model = BasicSatFormula.parseSolutionFromFile(args[2], f.getVariables());
        	if (model == null) {
        		System.out.println("Formula not SAT.");
        		return;
        	}
        	if (f.validateSolution(model)) {
        		System.out.println("Solution validated.");
        	} else {
        		System.out.println("Solution INCORRECT!");
        	}
        	return;
        }
        if (args[0].equals("val")) {
        	BasicSatFormula.validateCnfFile(args[1]);
        	return;
        }
        String filename = args[0];
        if (filename.contains("cnf")) {
            BasicSatFormula f = BasicSatFormula.parseFromFile(filename);
            System.out.println(filename + ";" + FormulaAnalyzer.analyzeFormula(f).csv());
        } else {
            SasProblem prob;
            try {
                prob = SasIO.parse(filename);
                prob.compileConditionalActions();
                //Logger.print(0, "start");
                //SasProblemAnalyzer spa = new SasProblemAnalyzer(prob);
                //SasProblemProperties spp = spa.analyzeSasProblem();
                //System.out.println(spp);
                //Logger.print(0, "done");
            } catch (IOException e) {
                System.out.println("sas file cannot be opened");
            }
        }
    }
}
