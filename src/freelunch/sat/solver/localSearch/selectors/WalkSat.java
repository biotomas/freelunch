package freelunch.sat.solver.localSearch.selectors;

import java.util.Random;

import freelunch.sat.solver.localSearch.SimpleClauseManager;
import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class WalkSat implements LocalSearchSelector {
    
	private float randomWalkProb = 0.01f;
	private SimpleClauseManager clManager;
	private Random random;
	private int[] flipRates;

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		this.random = lsData.random;
		this.clManager = lsData.clManager;
		this.flipRates = lsData.flipRates;

		//Arrays.fill(flipRates, 0);
        for (int i = 0; i < flipRates.length; i++) flipRates[i] = 1 + (flipRates[i] >> 1);
	}

	@Override
	public int selectLiteralToFlip(int[] candidates) {
		int bestLit = 0;
		int bestScore = Integer.MIN_VALUE;
		int worstLit = 0;
		int worstScore = Integer.MAX_VALUE;
		
		for (int lit : candidates) {
			int var = Math.abs(lit);
			int score = clManager.computeMakecount(lit) - clManager.computeBreakCount(lit) - flipRates[var];
			//int score = 0 - clManager.computeBreakCount(lit) - flipRates[var];
			if (score > bestScore) {
				bestScore = score;
				bestLit = lit;
			}
			if (score < worstScore) {
				worstScore = score;
				worstLit = lit;
			}
		}
		
		int lit = bestLit;
		if (random.nextFloat() <= randomWalkProb || lit == 0) {
			lit = worstLit;
		}
		return lit;
	}

}
