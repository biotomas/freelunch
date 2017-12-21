package freelunch.core.satSolving.maxsat;

public interface MaxSatSolver {
    
	/**
	 * Solve the given partial maxsat formula, return null if unsatisfiable
	 * @param pmax
	 * @return null if unsat optimal model otherwise
	 */
    public int[] solvePartialMaxsat(PartialMaxSatFormula pmax);

	/**
	 * Solve the given weighted partial maxsat formula, return null if unsatisfiable
	 * @param pmax
	 * @return null if unsat optimal model otherwise
	 */
    public int[] solvePartialWeightedMaxsat(WeightedPartialMaxSatFormula wpmax);

}
