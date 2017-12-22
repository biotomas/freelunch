package freelunch.sat.solver.localSearch.selectors;

import java.util.Random;

import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class RandomWalk implements LocalSearchSelector {

	private Random random;

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		this.random = lsData.random;
	}

	@Override
	public int selectLiteralToFlip(int[] candidates) {
		return candidates[random.nextInt(candidates.length)];
	}

}
