package freelunch.sat.bce.decomposers.postprocessors;

import java.util.List;

import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class UnitPropagationReducer {

	public static int simplifyByUnitPropagation(BasicFormula large, BasicFormula small, boolean removeUnitClauses) {
		List<Integer> units = UnitPropagationSimplifier.simplifyByUnitPropagation(large, true);
		if (!removeUnitClauses) {
			// add back the unit clauses
			for (int lit : units) {
				large.clauses.add(new int[]{lit});
			}
		}
		units = UnitPropagationSimplifier.simplifyByUnitPropagation(small, true);
		if (!removeUnitClauses) {
			// add back the unit clauses
			for (int lit : units) {
				small.clauses.add(new int[]{lit});
			}
		}
		return 0;
	}

}
