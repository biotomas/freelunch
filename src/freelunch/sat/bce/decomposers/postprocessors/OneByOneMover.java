package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class OneByOneMover implements DecompositionPostprocessor {
	
	private long timelimit = 0;

	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		Stopwatch watch = new Stopwatch();
		EagerBlockableClauseMover.sortClauses(large, small);
		Collections.reverse(small.clauses);
		List<int[]> newSmall = new ArrayList<int[]>();
		for (int[] cl : small.clauses) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}
			List<int[]> clauses = new ArrayList<int[]>();
			clauses.add(cl);
			if (EagerBlockableClauseMover.tryAdding(large, clauses, 0)) {
				large.clauses.add(cl);
			} else {
				newSmall.add(cl);
			}
		}
		small.clauses = newSmall;
		return 0;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}

}
