package freelunch.sat.bce.decomposers;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

/**
 * Combines unit decomposition and pure decomposition.
 * If unit decomposition is sufficient then uses the result
 * otherwise runs pure decomposition.
 *
 */
public class CombinedDecomposer implements FormulaDecomposer {

	@Override
	public void decomposeFormula(BasicFormula input, BasicFormula largeBlocked,	BasicFormula rest) {
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
