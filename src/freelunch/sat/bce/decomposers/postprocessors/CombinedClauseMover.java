package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class CombinedClauseMover implements DecompositionPostprocessor {

	private long timelimit = 0;
	private static final float[] PARTS = new float[]{0.4f, 0.3f, 0.2f}; 


	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		Stopwatch watch = new Stopwatch();
		int remaining = small.clauses.size();
		for (float part : PARTS) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}
			List<int[]> blocks = new ArrayList<int[]>();
			List<int[]> blocksToRemove = new ArrayList<int[]>();
			int size = Math.round(part*small.clauses.size());
			if (size < 1) {
				break;
			}
			int from = 0;
			int to = from + size;
			
			while (to < small.clauses.size()) {
				blocks.add(new int[]{from, to});
				from = to;
				to = from + size;
			}
			for (int[] blockDesc : blocks) {
				if (watch.timeLimitExceeded(timelimit)) {
					break;
				}
				List<int[]> block = small.clauses.subList(blockDesc[0], blockDesc[1]);
				Logger.print(2, String.format("c trying to move %d clauses", block.size()));
				if (EagerBlockableClauseMover.tryAdding(large, block, 0)) {
					blocksToRemove.add(blockDesc);
					remaining -= block.size();
					Logger.print(2, String.format("c elimination successful, remaining %d clauses", remaining));
				}
			}
			Collections.reverse(blocksToRemove);
			for (int[] clauseDesc : blocksToRemove) {
				small.clauses.subList(clauseDesc[0], clauseDesc[1]).clear();
			}
		}
		return 0;
	}


	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}
}
