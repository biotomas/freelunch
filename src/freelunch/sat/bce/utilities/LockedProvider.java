package freelunch.sat.bce.utilities;

import java.util.BitSet;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class LockedProvider {
	
	public static BitSet flipAll(BitSet bs) {
		bs.flip(0, bs.size());
		return bs;
	}

	/**
	 * None of the variables will be locked
	 * @param variables count
	 * @return
	 */
	public static BitSet lockNone(int variables) {
		BitSet result = new BitSet(variables);
		result.clear();
		return result;
	}

	/**
	 * All variables present in the formula will be locked
	 * @param f
	 * @return
	 */
	public static BitSet lockPresentVars(BasicFormula f) {
		BitSet result = new BitSet(f.variablesCount+1);
		result.clear();
		for (int[] cl : f.clauses) {
			for (int lit : cl) {
				result.set(Math.abs(lit));
			}
		}
		return result;
	}
	
	public static BitSet lockCommonBlits(BasicFormula l, BasicFormula r) {
    	BitSet lvars = new BitSet(l.variablesCount+1);
    	BitSet rvars = new BitSet(l.variablesCount+1);
    	lvars.clear();
    	rvars.clear();
		for (int[] cl : l.clauses) {
			lvars.set(Math.abs(cl[0]));
		}
		for (int[] cl : r.clauses) {
			rvars.set(Math.abs(cl[0]));
		}
    	lvars.and(rvars);
    	return lvars;
	}
	
	/**
	 * All blocking literals in the formula will be locked
	 * Assuming that the input is a blocked set with blocking
	 * literals being the first each clause.
	 * @param f
	 * @return
	 */
	public static BitSet lockBlits(BasicFormula f) {
		BitSet result = new BitSet(f.variablesCount+1);
		result.clear();
		for (int[] cl : f.clauses) {
			result.set(Math.abs(cl[0]));
		}
		return result;
	}
	

}
