package freelunch.sat.bce.decomposers;

import freelunch.sat.model.CnfSatFormula;

public interface FormulaDecomposer {
	
	public void decomposeFormula(CnfSatFormula input, CnfSatFormula largeBlocked, CnfSatFormula rest);

}
