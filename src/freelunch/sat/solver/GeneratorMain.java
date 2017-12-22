package freelunch.sat.solver;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;

public class GeneratorMain {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("USAGE: vars, seed");
			return;
		}
		int vars = Integer.parseInt(args[0]);
		int seed = Integer.parseInt(args[1]);

		RandomFormulaGenerator rfg = new RandomFormulaGenerator(vars + seed);
		BasicFormula bf;// = rfg.getRandomSat(vars, 0, cls);
		bf = rfg.getRandomFormula(vars, 3, 0, 41*vars/10);
		bf.printDimacs(System.out);		
	}

}
