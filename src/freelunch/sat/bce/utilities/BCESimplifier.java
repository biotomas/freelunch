package freelunch.sat.bce.utilities;

import freelunch.sat.bce.eliminators.ImprovedBCEliminator;
import freelunch.sat.model.CnfSatFormula;

public class BCESimplifier {
	
	/**
	 * Remove all blocked clauses from the formula
	 * @param f
	 */
	public static void simplify(CnfSatFormula f) {
		ImprovedBCEliminator bcel = new ImprovedBCEliminator();
		int elimCount = bcel.eliminateBlockedClauses(f).size();
		Logger.print(1, String.format("c BCE simplifier removed %d (%d%%) clauses", 
				elimCount, (elimCount*100)/f.clauses.size()));
		f.clauses = bcel.getRemainingClauses();
	}

}
