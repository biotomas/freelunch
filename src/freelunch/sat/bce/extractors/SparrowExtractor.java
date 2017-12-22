package freelunch.sat.bce.extractors;

import java.util.Arrays;
import java.util.List;

import freelunch.sat.bce.solver.BlockedSetSolver;
import freelunch.sat.bce.solver.ChoiceMaker;
import freelunch.sat.bce.solver.RandomChoiceMaker;
import freelunch.sat.bce.utilities.WeightedRandomPicker;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class SparrowExtractor extends RandomExtractor {
	public void extractBackBones(BasicFormula formula) {
		this.formula = formula;
		BlockedSetSolver solver = new BlockedSetSolver();
		int[] result = solver.solveBlockedFormula(formula, new RandomChoiceMaker(2013));
		bones = Arrays.copyOf(result, result.length);
		stars = new int[result.length];
		for (int i = 1; i < result.length; i++) {
			stars[i] = result[i] == 0 ? 1 : 0;
			if (stars[i] > 0) starsCount++;
			if (result[i] != 0) bonesCount++;
		}
		int oldStars = starsCount;
		int oldBones = bonesCount;
		int iterations = 1;
		final WeightedRandomPicker picker = new WeightedRandomPicker(2013);
		
		while (true) {
			iterations++;
			result = solver.solveBlockedFormula(formula, new ChoiceMaker() {
				@Override
				public int chooseLiteral(List<Integer> candidates, int[] assignment) {
					picker.clear();
					for (int lit : candidates) {
						int var = Math.abs(lit);
						float boneBonus = 0f;
						if (bones[var] == 0) {
							boneBonus += 1;
						}
						if (bones[var] == -lit) {
							boneBonus += 3;
						}
						//float weight = (float) (1/Math.log(3d + stars[var]) + boneBonus);
						picker.addChoice(lit, boneBonus);
					}
					return picker.pick();
				}
			});
			oldBones = bonesCount;
			oldStars = starsCount;
			processSolution(result);
			if (oldBones == bonesCount && oldStars == starsCount) {
				break;
			}
		}		
		printResults(iterations);
	}

}
