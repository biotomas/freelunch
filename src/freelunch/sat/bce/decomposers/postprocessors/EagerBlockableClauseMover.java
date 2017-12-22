package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.bce.utilities.ClauseIdIndex;
import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.ClauseIdIndex.Clause;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class EagerBlockableClauseMover implements DecompositionPostprocessor {
	
	private int rounds = 4;
	private long timelimit = 0;

	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		Stopwatch watch = new Stopwatch();
		
		//sort the clauses in the small set
		sortClauses(large, small);
		
		boolean success = true;
		int round = 0;
		while (success) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}
			success = false;
			for (int blockNum = 0; blockNum+1 < rounds; blockNum++) {
				
				int blockSize = small.clauses.size() / rounds;
				int from = blockNum*blockSize;
				int to = Math.min(from + blockSize, small.clauses.size());
				List<int[]> block = small.clauses.subList(from, to);
				if (block.size() < 1) {
					return 0;
				}
	
				Logger.print(2, String.format("c starting elimination in block %d of round %d, block size is %d", blockNum, round, blockSize));
				if (tryAdding(large, block, 0)) {
					Logger.print(2, String.format("c elimination in block %d successful", blockNum));
					block.clear();
					success = true;
				} else {
					Logger.print(2, String.format("c elimination in block %d NOT successful", blockNum));
				}
			}
			round++;
		}
		return 0;
	}
	
	public static Boolean tryAdding(BasicFormula large, List<int[]> clauses, int timelimit) {
		BCEliminator elim = new IncrementalQueueBasedBCEliminator();
		elim.setTimeLimit(timelimit);
		BasicFormula tmp = new BasicFormula();
		tmp.variablesCount = large.variablesCount;
		tmp.clauses = new ArrayList<int[]>();
		tmp.clauses.addAll(large.clauses);
		tmp.clauses.addAll(clauses);
		List<int[]> cls = elim.eliminateBlockedClauses(tmp);
		if (cls == null) {
			// time limit exceeded
			return null;
		}
		/* TODO use this
		if (cls.size() > large.clauses.size()) {
			System.out.println(String.format("could replace %d in large by %d", large.clauses.size(), cls.size()));
		}
		*/
		if (cls.size() == tmp.clauses.size()) {
			large.clauses = cls;
			return true;
		}
		return false;
	}

	/**
	 * Sort the clauses in the small according to resolution
	 * connections to the large set.
	 * @param large
	 * @param small
	 */
	public static void sortClauses(BasicFormula large, BasicFormula small) {
		ClauseIndex cindex = new ClauseIndex(large);
		final Map<int[], Integer> clauseScores = new HashMap<int[], Integer>();
		for (int[] cl : small.clauses) {
			clauseScores.put(cl, cindex.getScore(cl));			
		}
		Collections.sort(small.clauses, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int score1 = clauseScores.get(o1);
				int score2 = clauseScores.get(o2);
				return score1 - score2;
			}
		});
	}
	
	public static void sortClausesByFirstBlitConflict(BasicFormula large, BasicFormula small) {
		ClauseIdIndex cindex = new ClauseIdIndex(large.variablesCount, null);
		int id = 0;
		for (int[] cl : large.clauses) {
			Clause nc = new Clause(id++, cl);
			cindex.addClause(nc);
		}
		final Map<int[], Integer> clauseScores = new HashMap<int[], Integer>();
		for (int[] cl : small.clauses) {
			clauseScores.put(cl, cindex.getScoreByFirstBlitConflict(new Clause(id++, cl)));			
		}
		Collections.sort(small.clauses, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int score1 = clauseScores.get(o1);
				int score2 = clauseScores.get(o2);
				return score2 - score1;
			}
		});
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}
}
