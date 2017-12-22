package freelunch.sat.solver.localSearch.selectors;

import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public interface LocalSearchSelector {

	/**
	 * Select a literal to flip (make true) at the current step from the given
	 * list of literals. 
	 * @param candidates a list of literals
	 * @return a selected variable
	 */
	public int selectLiteralToFlip(int[] candidates);
	

	/**
	 * This method should be called before a series of flips, for
	 * example in the beginning of search of after restarting.
	 * @param lsData
	 */
	public void prepareForFlipping(LocalSearchMetadata lsData);

}
