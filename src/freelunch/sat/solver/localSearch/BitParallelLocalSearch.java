package freelunch.sat.solver.localSearch;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.solver.LocalSearchSatSolver;

public class BitParallelLocalSearch implements LocalSearchSatSolver {
	
	private int restarts = 0;
	private int flips = 0;
	private long timeout;

	@Override
	public Boolean isSatisfiable(BasicFormula formula) {
		return null;
	}

	@Override
	public int[] getModel() {
		// TODO Auto-generated method stub
		if (timeout > 0) {
		}
		return null;
	}

	@Override
	public void setTimeout(long nanoseconds) {
		timeout = nanoseconds;
	}

	@Override
	public int getFlipsCount() {
		return flips;
	}

	@Override
	public int getRestartsCount() {
		return restarts;
	}

}
