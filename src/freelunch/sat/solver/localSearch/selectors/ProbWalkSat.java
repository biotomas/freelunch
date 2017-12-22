package freelunch.sat.solver.localSearch.selectors;

import java.util.Random;

import freelunch.sat.solver.localSearch.LSClause;
import freelunch.sat.solver.localSearch.SimpleClauseManager;
import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class ProbWalkSat implements LocalSearchSelector {

	private static int UPDATE_LIMIT = 100;
	private SimpleClauseManager clManager;
	private Random random;
	private int[] flipRates;
	private long flips = 0;

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		this.random = lsData.random;
		this.clManager = lsData.clManager;
		this.flipRates = lsData.flipRates;
	}


	@Override
	public int selectLiteralToFlip(int[] candidates) {
		flips++;
		if (flips > UPDATE_LIMIT) {
			flips = 0;
	        for (int i = 0; i < flipRates.length; i++) {
	        	flipRates[i] = (flipRates[i] >> 1);
	        }
			for (LSClause cl : clManager.getClauseList()) {
				cl.decreaseBroken();
			}
		}

		int[] values = new int[candidates.length];
		int i = 0;
		int min = 0;
		for (int lit : candidates) {
			int w = 0 - clManager.computeBreakScore(lit) - flipRates[Math.abs(lit)];
			values[i++] = w;
			if (w < min) {
				min = w;
			}
		}
		int totalScore = 0;
		for (i = 0; i < values.length; i++) {
			int w = Math.min(100, (values[i] - min) + 1);
			values[i] = w * w * w;
			//int w = -values[i]; 
			//values[i] = ((1024 >> w) << 10) + Math.max(0, 1024 - w);
			
			totalScore += values[i];
		}
		//System.out.println(Arrays.toString(values));
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
