package freelunch.sat.bce.decomposers;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public interface FormulaDecomposer {
	
	public void decomposeFormula(BasicFormula input, BasicFormula largeBlocked, BasicFormula rest);

}
