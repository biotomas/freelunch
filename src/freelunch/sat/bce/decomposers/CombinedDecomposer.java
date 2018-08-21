package freelunch.sat.bce.decomposers;

import freelunch.sat.model.CnfSatFormula;

/**
 * Combines unit decomposition and pure decomposition.
 * If unit decomposition is sufficient then uses the result
 * otherwise runs pure decomposition.
 *
 */
public class CombinedDecomposer implements FormulaDecomposer {

	@Override
	public void decomposeFormula(CnfSatFormula input, CnfSatFormula largeBlocked,	CnfSatFormula rest) {
		UnitDecomposer dec = new UnitDecomposer();
		dec.exitIfNotUD = true;
		dec.decomposeFormula(input.shallowCopy(), largeBlocked, rest);
		if (dec.bceSolved) {
			return;
		}
		PureDecomposer pure = new PureDecomposer();
		pure.decomposeFormula(input, largeBlocked, rest);
	}

}
