package freelunch.sat.solver.localSearch.selectors;

import java.util.Random;

import freelunch.sat.solver.localSearch.LSClause;
import freelunch.sat.solver.localSearch.SimpleClauseManager;
import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class BSeeker implements LocalSearchSelector {
	
	private static int UPDATE_LIMIT = 100;
	private int flips = 0;
	private SimpleClauseManager clManager;
	private Random random;

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		//UPDATE_LIMIT = lsData.clManager.getVariableCount();
		this.random = lsData.random;
		this.clManager = lsData.clManager;
	}

	@Override
	public int selectLiteralToFlip(int[] candidates) {
		flips++;
		if (flips > UPDATE_LIMIT) {
			flips = 0;
			for (LSClause cl : clManager.getClauseList()) {
				cl.decreaseBroken();
			}
		}
		int totalScore = 0;
		int[] values = new int[candidates.length];
		int i = 0;
		for (int lit : candidates) {
			int w = clManager.computeBreakScore(lit);
			int score = ((1024 >> w) << 10) + Math.max(0, 1024 - w);
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
