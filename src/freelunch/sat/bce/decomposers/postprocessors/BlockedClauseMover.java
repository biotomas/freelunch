package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.List;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.satLifter.Stopwatch;

public class BlockedClauseMover implements DecompositionPostprocessor {

	private int timelimit = 0;

	@Override
	public int moveToLarge(CnfSatFormula large, CnfSatFormula small) {
		Stopwatch watch = new Stopwatch();
		int moved = 0;
		ClauseIndex cindex = new ClauseIndex(large);
		List<int[]> newRest = new ArrayList<int[]>();

		for (int[] cl : small.clauses) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}

			if (cindex.isBlocked(cl)) {
				large.clauses.add(cl);
				cindex.addClause(cl);
				moved++;
			} else {
				newRest.add(cl);
			}
		}
		small.clauses = newRest;
		Logger.print(1, String.format("c Blocked clause mover moved %d clauses to the large set", moved));
		return moved;
	}

	@Override
	public void setTimeLimit(int seconds) {
		this.timelimit = seconds;
	}

}
