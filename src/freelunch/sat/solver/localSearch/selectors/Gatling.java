package freelunch.sat.solver.localSearch.selectors;

import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class Gatling implements LocalSearchSelector {
	
	private LocalSearchSelector[] selectors;
	private int nextSelector = 0;
	
	public Gatling() {
		selectors = new LocalSearchSelector[]{new ProbSat(), new WalkSat(), new Sparrow(), new BSeeker(), new ProbWalkSat()};
	}

	@Override
	public int selectLiteralToFlip(int[] candidates) {
		nextSelector++;
		if (nextSelector >= selectors.length) {
			nextSelector = 0;
		}
		return selectors[nextSelector].selectLiteralToFlip(candidates);
	}

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		for (LocalSearchSelector lss : selectors) {
			lss.prepareForFlipping(lsData);
		}
	}

}
