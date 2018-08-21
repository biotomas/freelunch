package freelunch.sat.bce.decomposers.postprocessors;

import freelunch.sat.model.CnfSatFormula;

public interface DecompositionPostprocessor {
	
	/**
	 * Move clauses from the small set to the large while both remain blocked
	 * @param large
	 * @param small
	 * @return the number of moved clauses
	 */
	public int moveToLarge(CnfSatFormula large, CnfSatFormula small);
	
	/**
	 * Set the time limit for post-processing
	 * @param nanoseconds
	 */
	public void setTimeLimit(long nanoseconds);

}
