package freelunch.sat.analyzer;

import java.util.ArrayList;
import java.util.List;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class HornAnalyzer {
	
	/**
	 * First finds all non-Horn clauses, counts the number of positive
	 * literals p(C) in them, creates a bipartite graph of vars vs non-Horn clauses
	 * Hitting set like problem, but each clause must be hit p(C)-1 times
	 * @param f
	 * @return
	 */
	public static List<Integer> getHornBackDoorVariables(BasicFormula f) {
		List<int[]> notPHorn = new ArrayList<int[]>();
		List<int[]> notNHorn = new ArrayList<int[]>();
		for (int[] cl : f.clauses) {
			if (!isHornP(cl)) {
				notPHorn.add(cl);
			}
			if (!isHornN(cl)) {
				notNHorn.add(cl);
			}
		}
		Logger.print(1, String.format("c PHorn %d%%, NHorn %d%%",
				(f.clauses.size() - notPHorn.size())*100/f.clauses.size(),
				(f.clauses.size() - notNHorn.size())*100/f.clauses.size()));

		HittingSetSolver hss = new HittingSetSolver();
		if (notPHorn.size() < notNHorn.size()) {
			for (int[] cl : notPHorn) {
				hss.addPositive(cl);
			}
		} else {
			for (int[] cl : notNHorn) {
				hss.addNegative(cl);
			}
		}
		
		List<Integer> hs = hss.computeHittingSet();
		Logger.print(1, String.format("c bdsize: %d (%d%%)", hs.size(), hs.size()*100/f.variablesCount));
		return hs;
	}
	
	/**
	 * Is the clause horn -- has at most one positive literal
	 * @param cl
	 * @return
	 */
	public static boolean isHornP(int[] cl) {
		boolean pos = false;
		for (int l : cl) {
			if (l > 0) {
				if (pos) {
					return false;
				}
				pos = true;
			}
		}
		return true;
	}
	
	/**
	 * A clause that has exactly one positive literal
	 * @param cl
	 * @return
	 */
	public static boolean isBarelyHorn(int[] cl) {
		int pos = 0;
		for (int l : cl) {
			if (l > 0) {
				pos++;
				if (pos > 1) {
					return false;
				}
			}
		}
		return pos == 1;
	}
	
	/**
	 * A clause is almost Horn if it has exactly 2 positive literals
	 * @param cl
	 * @return true if cl is almost Horn
	 */
	public static boolean isAlmostHorn(int[] cl) {
		int pos = 0;
		for (int l : cl) {
			if (l > 0) {
				pos++;
				if (pos > 2) {
					return false;
				}
			}
		}
		return pos == 2;
	}

	/**
	 * Is the clause horn -- has at most one negative literal
	 * @param cl
	 * @return
	 */
	public static boolean isHornN(int[] cl) {
		boolean neg = false;
		for (int l : cl) {
			if (l < 0) {
				if (neg) {
					return false;
				}
				neg = true;
			}
		}
		return true;
	}

}
