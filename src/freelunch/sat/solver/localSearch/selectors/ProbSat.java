package freelunch.sat.solver.localSearch.selectors;

import java.util.Random;

import freelunch.sat.solver.localSearch.SimpleClauseManager;
import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class ProbSat implements LocalSearchSelector {

	private SimpleClauseManager clManager;
	private Random random;

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		this.random = lsData.random;
		this.clManager = lsData.clManager;
	}


	@Override
	public int selectLiteralToFlip(int[] candidates) {
		int totalScore = 0;
		int[] values = new int[candidates.length];
		int i = 0;
		for (int lit : candidates) {
			int breakVal = clManager.computeBreakCount(lit);
			int score = ((1024 >> breakVal) << 10) + Math.max(0, 1024 - breakVal);
			values[i++] = score;
			totalScore += score;			
		}
		if (totalScore == 0) {
			return candidates[0];
		}
		int cutoff = random.nextInt(totalScore);
		i = 0;
		for (int lit : candidates) {
			if (values[i] >= cutoff) {
				return lit;
			}
			cutoff -= values[i];
			i++;
		}
		return 0;
	}

}
