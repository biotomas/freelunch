package freelunch.sat.bce.solver;

import java.util.List;

public interface ChoiceMaker {
	
	/**
	 * Choose one the given candidates
	 * @param candidates
	 * @param assignment current partial truth assignment
	 * @return
	 */
	int chooseLiteral(List<Integer> candidates, int[] assignment);

}
