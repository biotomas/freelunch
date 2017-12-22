package freelunch.sat.bce.decomposers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.SimplifiedArminsBCEliminator;
import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class UnitDecomposer implements FormulaDecomposer {
	
	public boolean bceSolved = false;
	public boolean exitIfNotUD = false;
	
	@Override
	public void decomposeFormula(BasicFormula input, BasicFormula largeBlocked, BasicFormula rest) {
		bceSolved = false;
		largeBlocked.variablesCount = input.variablesCount;
		largeBlocked.clauses = new ArrayList<int[]>();
		rest.variablesCount = input.variablesCount;
		rest.clauses = new ArrayList<int[]>();
		BasicFormula tmpFormula = new BasicFormula();
		tmpFormula.variablesCount = input.variablesCount;
		tmpFormula.clauses = new ArrayList<int[]>();
		List<Integer> units = new ArrayList<Integer>();

		// select non-unit clauses
		for (int[] cl : input.clauses) {
			if (cl.length > 1) {
				tmpFormula.clauses.add(cl);
			} else {
				units.add(cl[0]);
			}
		}
		// put the unit clauses into the Rest set
		for (int lit : units) {
			rest.clauses.add(new int[] {lit});
		}
		// try to eliminate the entire Large set
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		List<int[]> cls = elim.eliminateBlockedClauses(tmpFormula);
		if (cls.size() == tmpFormula.clauses.size()) {
			Logger.print(1, "c Unit decomposition sufficient (before UP)");
			largeBlocked.clauses = cls;
			bceSolved = true;
			return;
		}

		// do unit propagation and remove SAT clauses, don't simplify clauses!
		units = UnitPropagationSimplifier.simplifyByUnitPropagation(input, false);
		if (units == null) {
			Logger.print(1, "c formula UNSAT by unit propagation");
			System.out.println("s UNSATISFIABLE");
			return;
		}

		// put the unit clauses into the Rest set
		rest.clauses.clear();
		for (int lit : units) {
			rest.clauses.add(new int[] {lit});
		}
		tmpFormula.clauses.clear();
		// put the non unit clauses into the Temporary set
		for (int[] cl : input.clauses) {
			if (cl.length > 1) {
				tmpFormula.clauses.add(cl);
			}
		}
		// try to eliminate the entire Large set
		cls = elim.eliminateBlockedClauses(tmpFormula);
		if (cls.size() == tmpFormula.clauses.size()) {
			Logger.print(1, "c Unit decomposition sufficient (after UP)");
			largeBlocked.clauses = cls;
			bceSolved = true;
			return;
		}
		
		if (exitIfNotUD) {
			return;
		}
		
		// do a modified pure decomposition (all units go to small)
		ClauseIndex cindex = new ClauseIndex(tmpFormula);
		int[] unitsValues = new int[input.variablesCount+1];
		Arrays.fill(unitsValues, 0);
		for (int[] cl : rest.clauses) {
			// cl are unit clauses
			int lit = cl[0];
			unitsValues[Math.abs(lit)] = lit;
		}

		Set<int[]> large, small;
		List<int[]> trash = new ArrayList<int[]>();
		
		for (int var = 1; var <= input.variablesCount; var++) {
			int polarity;
			
			if (unitsValues[var] != 0) {
				if (unitsValues[var] > 0) {
					large = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
					small = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
					polarity = -1;
				} else {
					large = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
					small = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
					polarity = 1;
				}
			} else {
				if (cindex.clauseIndex[ClauseIndex.encodeLiteral(var)].size() > cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)].size()) {
					large = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
					small = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
					polarity = 1;
				} else {
					large = cindex.clauseIndex[ClauseIndex.encodeLiteral(-var)];
					small = cindex.clauseIndex[ClauseIndex.encodeLiteral(var)];
					polarity = -1;
				}
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
		}/**/
	}
}
