package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class BlockableClauseMover implements DecompositionPostprocessor {
	
	private long timelimit = 0;

	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		Stopwatch watch = new Stopwatch();

		// sort the clauses, short clauses first
		Collections.sort(small.clauses, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1.length - o2.length;
			}
		});/**/
		List<int[]> units = new ArrayList<int[]>();
		
		int moved = 0;
		ClauseIndex cindex = new ClauseIndex(large);
		List<int[]> newRest = new ArrayList<int[]>();
		for (int[] cl : small.clauses) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}
			// save units for later
			if (cl.length == 1) {
				units.add(cl);
				continue;
			}
			if (cindex.isBlocked(cl) || cindex.addableToBlocked(cl)) {
				large.clauses.add(cl);
				cindex.addClause(cl);
				moved++;
			} else {
				newRest.add(cl);
			}
		}
		//System.out.println("moved " + moved + " long clauses");
		// now come the units
		for (int[] cl : units) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}
			if (cindex.isBlocked(cl) || cindex.addableToBlocked(cl)) {
				large.clauses.add(cl);
				cindex.addClause(cl);
				moved++;
			} else {
				newRest.add(cl);
			}
		}
		small.clauses = newRest;
		Logger.print(1, String.format("c Blockable clause mover moved %d clauses to the large set", moved));
		return moved;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}

}
