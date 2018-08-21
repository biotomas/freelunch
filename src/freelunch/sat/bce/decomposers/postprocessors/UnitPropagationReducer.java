package freelunch.sat.bce.decomposers.postprocessors;

import java.util.List;

import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.model.CnfSatFormula;

public class UnitPropagationReducer {

	public static int simplifyByUnitPropagation(CnfSatFormula large, CnfSatFormula small, boolean removeUnitClauses) {
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
