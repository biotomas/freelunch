package freelunch.sat.bce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import freelunch.sat.bce.decomposers.FormulaDecomposer;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.decomposers.UnitDecomposer;
import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.bce.utilities.LockedProvider;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class BCAnalyzerMain {
	
	private static boolean finished = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (finished) {
					Logger.print(1, "c Analysis finished successfully");
				} else {
					Logger.print(1, "c Analysis terminated");
				}
			}
		});
		Logger.setVerbosity(1);
		if (args.length < 1) {
			System.out.println("Usage: java -jar analyzer.jar <formula.cnf>");
			return;
		}
		BasicFormula inputf = DimacsParser.parseFromFile(args[0]);
		if (inputf == null) {
			System.err.println("Input file not found / cannot be opened");
			return;
		}
		Logger.print(1, String.format("c Input parsed, contains %d variables and %d clauses", inputf.variablesCount, inputf.clauses.size()));
		if (inputf.clauses.size() + inputf.variablesCount >= 15*1000*1000) {
			Logger.print(1, "c Input too big, ending analysis");
			finished = true;
			return;
		}

		// Try all decomposers and all lockers
		List<FormulaDecomposer> decomposers = new ArrayList<FormulaDecomposer>();
		decomposers.add(new UnitDecomposer());
		decomposers.add(new PureDecomposer());
		//decomposers.add(new VarSplitDecomposer());
		
		for (FormulaDecomposer decomposer : decomposers) {
			BasicFormula mf = new BasicFormula();
			mf.variablesCount = inputf.variablesCount;
			mf.clauses = new ArrayList<int[]>();
			for (int[] cl : inputf.clauses) {
				mf.clauses.add(Arrays.copyOf(cl, cl.length));
			}
			
			BasicFormula large = new BasicFormula();
			BasicFormula small = new BasicFormula();
			Logger.print(1, String.format("c Starting %s on the input formula", decomposer.getClass().getName()));
			decomposer.decomposeFormula(mf, large, small);
			Logger.print(1, String.format("c %s result: large: %d (%d%%), small %d", decomposer.getClass().getName(), 
					large.clauses.size(), (100*large.clauses.size()/mf.clauses.size()), small.clauses.size()));
			
			Logger.print(1, FormulaAnalyzer.compareFormulas(large, small));
			
			BitSet locked;
			locked = LockedProvider.lockPresentVars(small);
			Logger.print(1, String.format("c locked all vars in the small set: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
			locked = LockedProvider.lockBlits(small);
			Logger.print(1, String.format("c locked all blocking vars in the small set: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));

			locked = LockedProvider.lockPresentVars(large);
			Logger.print(1, String.format("c locked all vars in the large set: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
			locked = LockedProvider.lockBlits(large);
			Logger.print(1, String.format("c locked all blocking vars in the large set: %d of %d (%d%%)", locked.cardinality(), large.variablesCount, (100*locked.cardinality())/large.variablesCount));
			
			/*/ test the sets - and sort the blocked clauses
			SimplifiedArminsBCEliminator elim = new SimplifiedArminsBCEliminator();
			ArrayList<int[]> stack = elim.eliminateBlockedClauses(large);
			if (large.clauses.size() != elim.eliminateBlockedClauses(large).size()) {
				System.err.println("Large set is not blocked");
				return;
			}
			large.clauses = stack;
			stack = elim.eliminateBlockedClauses(small);
			if (small.clauses.size() != elim.eliminateBlockedClauses(small).size()) {
				System.err.println("Small set is not blocked");
				return;
			}
			small.clauses = stack;
			
			GreedyExtractor ge = new GreedyExtractor();
			System.out.print("c large;");
			ge.extractBackBones(large);
			System.out.print("c small;");
			ge.extractBackBones(small);
			/**/
			Logger.print(1, "c =======================================================");
		}
		finished = true;
	}

}
