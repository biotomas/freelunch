package freelunch.sat.solver.preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.model.CnfSatFormula;

public class PreprocessorMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("USAGE: prep.jar in.cnf out.cnf timelimit");
			return;
		}
		System.out.println("c args: " + Arrays.toString(args));
		Logger.setVerbosity(3);
		CnfSatFormula f = CnfSatFormula.parseFromFile(args[0]);
		
		if (f == null) {
			System.out.println("c formula not found or too big.");
			return;
		}
		OneProvableEmpoweringClauses opec = new OneProvableEmpoweringClauses();
		long limit = Long.parseLong(args[2]);
		int oldCls = f.clauses.size();
		boolean res = opec.addOneProvableEmpoweringClauses(f, 4, limit);
		if (!res) {
			System.out.println("UNSAT, outputing fla with empty clause.");
			f = new CnfSatFormula();
			f.variablesCount = 1;
			f.clauses = new ArrayList<int[]>();
			f.clauses.add(new int[]{});
		}
		Logger.print(1, String.format("c nr. of clauses %d -> %d", oldCls, f.clauses.size()));
		try {
			f.printDimacsToFile(args[1]);
		} catch (IOException e) {
			System.err.println("Output cannot be written.");
		}
	}
}
