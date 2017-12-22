package freelunch.sat.bce.decomposers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

/**
 * Decompose the formula in a way that the large and the small set
 * have a small number of common variables and the set sizes are similar.
 */
public class VarSplitDecomposer implements FormulaDecomposer {
	
	private class VariableOccurence implements Comparable<VariableOccurence> {
		private int variable;
		private int occurrence;
		
		public VariableOccurence(int variable, int occurrence) {
			this.variable = variable;
			this.occurrence = occurrence;
		}
		@Override
		public int compareTo(VariableOccurence o) {
			return this.occurrence - o.occurrence;
		}
		@Override
		public String toString() {
			return String.format("%d(%d)", variable, occurrence);
		}
	}

	@Override
	public void decomposeFormula(BasicFormula input, BasicFormula largeBlocked, BasicFormula rest) {
		
		largeBlocked.variablesCount = input.variablesCount;
		largeBlocked.clauses = new ArrayList<int[]>();
		rest.variablesCount = input.variablesCount;
		rest.clauses = new ArrayList<int[]>();
		
		// do unit propagation and remove SAT clauses and simplify clauses!
		List<Integer> units = UnitPropagationSimplifier.simplifyByUnitPropagation(input, true);
		// put the unit clauses into the large set
		for (int lit : units) {
			largeBlocked.clauses.add(new int[] {lit});
		}
		
		// variable already appears in the rest set?
		boolean[] varInRest = new boolean[input.variablesCount+1];
		Arrays.fill(varInRest, false);

		ClauseIndex cindex = new ClauseIndex(input);

		// sort the variable according to occurrence
		List<VariableOccurence> occurrences = new ArrayList<VarSplitDecomposer.VariableOccurence>();
		for (int var = 1; var <= input.variablesCount; var++) {
			int occurrence = Math.max(cindex.clauseIndex[ClauseIndex.encodeLiteral(var)].size(), cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)].size());
			occurrences.add(new VariableOccurence(var, occurrence));	
		}
		Collections.sort(occurrences);
		
		
		Set<int[]> large, small;
		List<int[]> trash = new ArrayList<int[]>();
		for (VariableOccurence vo : occurrences) {
			int var = vo.variable;
			int polarity;
			
			int posIncrease = 0;
			boolean[] tmpPosVarInRest = Arrays.copyOf(varInRest, varInRest.length);
			for (int[] cl : cindex.clauseIndex[ClauseIndex.encodeLiteral(var)]) {
				for (int lit : cl) {
					if (!tmpPosVarInRest[Math.abs(lit)]) {
						posIncrease++;
					}
					tmpPosVarInRest[Math.abs(lit)] = true;
				}
			}
			int negIncrease = 0;
			boolean[] tmpNegVarInRest = Arrays.copyOf(varInRest, varInRest.length);
			for (int[] cl : cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)]) {
				for (int lit : cl) {
					if (!tmpNegVarInRest[Math.abs(lit)]) {
						negIncrease++;
					}
					tmpNegVarInRest[Math.abs(lit)] = true;
				}
			}
			
			if (posIncrease > negIncrease) {
				large = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
				small = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
				varInRest = tmpPosVarInRest;
				polarity = 1;
			} else {
				large = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
				small = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
				varInRest = tmpNegVarInRest;
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
}
