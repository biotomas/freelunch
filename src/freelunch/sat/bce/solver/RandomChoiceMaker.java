package freelunch.sat.bce.solver;

import java.util.List;
import java.util.Random;

public class RandomChoiceMaker implements ChoiceMaker {
	
	private Random random;
	
	public RandomChoiceMaker(long seed) {
		random = new Random(seed);
	}

	@Override
	public int chooseLiteral(List<Integer> candidates, int[] assignment) {
		return candidates.get(random.nextInt(candidates.size()));
	}

}
