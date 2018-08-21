package freelunch.core.planning.cmdline;

import java.io.IOException;

import freelunch.core.planning.SasProblemAnalyzer;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.FormulaAnalyzer;

public class Analyzer {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("USAGE: java -jar fl.jar [val: validate] formula.cnf OR problem.sas");
            System.out.println("USAGE: java -jar fl.jar check formula.cnf solver.out");
            return;
        }
        if (args[0].equals("check")) {
        	CnfSatFormula f = CnfSatFormula.parseFromFile(args[1]);
        	int[] model = CnfSatFormula.parseSolutionFromFile(args[2], f.variablesCount);
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
        	CnfSatFormula.validateCnfFile(args[1]);
        	return;
        }
        String filename = args[0];
        if (filename.contains("cnf")) {
        	CnfSatFormula f = CnfSatFormula.parseFromFile(filename);
            System.out.println(filename + ";" + FormulaAnalyzer.analyzeFormula(f).csv());
        } else {
            SasProblem prob;
            try {
                prob = SasIO.parse(filename);
                //Logger.print(0, "start");
                SasProblemAnalyzer spa = new SasProblemAnalyzer(prob);
                System.out.println(spa.analyzeSasProblem());
                //Logger.print(0, "done");
                System.out.println("----------------------");
                prob.compileConditionalActions();
                spa = new SasProblemAnalyzer(prob);
                System.out.println(spa.analyzeSasProblem());
            } catch (IOException e) {
                System.out.println("sas file cannot be opened");
            }
        }
    }
}
