package freelunch.sat.solver;

import freelunch.sat.model.SatSolver;

public interface LocalSearchSatSolver extends SatSolver {
	
	/**
	 * Get the number of flips done
	 * @return
	 */
	public int getFlipsCount();
	
	/**
	 * Get the number of restarts done
	 * @return
	 */
	public int getRestartsCount();

}
