package freelunch.sat.solver.localSearch.selectors;

import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;

public class Revolver implements LocalSearchSelector {

	private LocalSearchSelector[] selectors;
	LocalSearchSelector selector;
	private int nextSelector = 0;
	
	public void printStatus() {
		System.out.println("c last algorithm: " + selector.getClass().getName());
	}
	
	public Revolver() {
		selectors = new LocalSearchSelector[]{new ProbSat(), new Sparrow(), new WalkSat(), new BSeeker(), new ProbWalkSat(), new Gatling()};
	}

	@Override
	public int selectLiteralToFlip(int[] candidates) {
		return selector.selectLiteralToFlip(candidates);
	}

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		nextSelector++;
		if (nextSelector >= selectors.length) {
			nextSelector = 0;
		}
		selector = selectors[nextSelector];
		selector.prepareForFlipping(lsData);
	}

}
