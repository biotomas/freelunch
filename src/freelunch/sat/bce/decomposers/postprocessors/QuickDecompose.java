package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class QuickDecompose implements DecompositionPostprocessor {

	private static final int ELIMINATION_LIMIT = 3;
	private BasicFormula large;
	private BasicFormula small;
	private List<int[]> garbage;
	private Stopwatch watch;
	private long timelimit = 0;

	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		watch = new Stopwatch();
		EagerBlockableClauseMover.sortClauses(large, small);
		this.large = large;
		this.small = small;
		garbage = new ArrayList<int[]>();

		int half = small.clauses.size() / 2;
		tryAdding(0, half);
		tryAdding(half + 1, small.clauses.size());
		
		Set<int[]> smallset = new HashSet<int[]>(small.clauses);
		smallset.removeAll(garbage);
		small.clauses = new ArrayList<int[]>(smallset);
		return 0;
	}

	private boolean tryAdding(int from, int to) {
		if (watch.timeLimitExceeded(timelimit)) {
			return false;
		}
		
		if (from >= to) {
			return true;
		}
		Logger.print(2, String.format("c trying to add clauses %d - %d", from, to));
		List<int[]> block = small.clauses.subList(from, to);
		Boolean added = EagerBlockableClauseMover.tryAdding(large, block, ELIMINATION_LIMIT);
		if (added == null) {
			Logger.print(2, String.format("c elimination time out (%d seconds)", ELIMINATION_LIMIT));
			return false;
		}
		if (added) {
			Logger.print(2, String.format("c moved %d clauses", to - from));
			garbage.addAll(block);
			return true;
		}
		int half = from + ((to - from) / 2);
		tryAdding(from, half);
		tryAdding(half + 1, to);
		return false;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}

}
