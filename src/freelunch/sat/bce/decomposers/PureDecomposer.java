package freelunch.sat.bce.decomposers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class PureDecomposer implements FormulaDecomposer {
	
	@Override
	public void decomposeFormula(BasicFormula input, BasicFormula largeBlocked, BasicFormula rest) {
		
		largeBlocked.variablesCount = input.variablesCount;
		largeBlocked.clauses = new ArrayList<int[]>();
		rest.variablesCount = input.variablesCount;
		rest.clauses = new ArrayList<int[]>();
		
		// do unit propagation and remove SAT clauses and simplify clauses!
		List<Integer> units = UnitPropagationSimplifier.simplifyByUnitPropagation(input, true);
		if (units == null) {
			Logger.print(1, "c formula UNSAT by unit propagation");
			System.out.println("s UNSATISFIABLE");
			return;
		}
		// put the unit clauses back into the formula
		for (int lit : units) {
			input.clauses.add(new int[] {lit});
		}
		pureDecomposeCore(input, largeBlocked, rest);
	}
	
	public static void pureDecomposeCore(BasicFormula input, BasicFormula largeBlocked, BasicFormula rest) {
		ClauseIndex cindex = new ClauseIndex(input);
		Set<int[]> large, small; 
		List<int[]> trash = new ArrayList<int[]>();
		List<Integer> vars = new ArrayList<Integer>(input.variablesCount);
		for (int var = 1; var <= input.variablesCount; var++) {
			vars.add(var);
		}
		sortVariables(cindex, vars);
		for (int var : vars) {
			int polarity;
			if (cindex.clauseIndex[ClauseIndex.encodeLiteral(var)].size() > 
				cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)].size()) {
				large = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
				small = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
				polarity = 1;
			} else {
				large = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
				small = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
				polarity = -1;
			}
			for (int[] cl : large) {
				cindex.makeBlockingLiteralFirst(cl, polarity*var);
				largeBlocked.clauses.add(cl);
			}
			for (int[] cl : small) {
				cindex.makeBlockingLiteralFirst(cl, -polarity*var);
				rest.clauses.add(cl);
			}
			trash.clear();
			trash.addAll(small);
			trash.addAll(large);
			large.clear();
			small.clear();
			for (int[] cl : trash) {
				cindex.removeFromClauseIndex(cl);
			}
		}
	}
	
	public static void sortVariables(final ClauseIndex cindex, final List<Integer> vars) {
		Collections.sort(vars, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return cindex.getVariableScore(o1) - cindex.getVariableScore(o2);
			}
		});
	}
}
