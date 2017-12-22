package freelunch.sat.solver.localSearch.selectors;

import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class FlipFirst implements LocalSearchSelector {
    
	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
	}

	@Override
	public int selectLiteralToFlip(int[] candidates) {
		return candidates[0];
	}

}
