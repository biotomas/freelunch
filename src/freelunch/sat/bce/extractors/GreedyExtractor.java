package freelunch.sat.bce.extractors;

import java.util.Arrays;
import java.util.List;

import freelunch.sat.bce.solver.BlockedSetSolver;
import freelunch.sat.bce.solver.ChoiceMaker;
import freelunch.sat.bce.solver.RandomChoiceMaker;
import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.bce.utilities.WeightedRandomPicker;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class GreedyExtractor extends RandomExtractor {


	/**
	 * Extract the potential backbones and not backbones.
	 * The input formula must be a blocked set properly ordered
	 * with the first literal of each clause being the blocking literal.
	 * @param formula
	 */
	public void extractBackBones(BasicFormula formula) {
		this.formula = formula;
		BlockedSetSolver solver = new BlockedSetSolver();
		int[] result = solver.solveBlockedFormula(formula, new RandomChoiceMaker(2013));
		FormulaAnalyzer.solutionOk(formula, result);
		bones = Arrays.copyOf(result, result.length);
		stars = new int[result.length];
		for (int i = 1; i < result.length; i++) {
			stars[i] = result[i] == 0 ? 1 : 0;
			if (stars[i] > 0) starsCount++;
			if (result[i] != 0) bonesCount++;
		}
		processSolution(result);
		int oldStars = starsCount;
		int oldBones = bonesCount;
		int oldEquivalences = equivalences.size();
		int iterations = 1;
		
		final WeightedRandomPicker picker = new WeightedRandomPicker(2013);
		// Phase 1 - crush bones
		while (true) {
			iterations++;
			result = solver.solveBlockedFormula(formula, new ChoiceMaker() {
				@Override
				public int chooseLiteral(List<Integer> candidates, int[] assignment) {
					picker.clear();
					for (int lit : candidates) {
						int var = Math.abs(lit);
						float boneBonus = 0f;
						if (bones[var] == 0) boneBonus += 1;
						if (bones[var] == -lit) boneBonus += 3;
						picker.addChoice(lit, boneBonus);
					}
					return picker.pick();
				}
			});
			oldBones = bonesCount;
			oldStars = starsCount;
			oldEquivalences = equivalences.size();
			processSolution(result);
			if (oldBones == bonesCount && oldStars == starsCount && oldEquivalences == equivalences.size()) {
				break;
			}
		}		
		// Phase 2 - mine stars
		while (true) {
			iterations++;
			result = solver.solveBlockedFormula(formula, new ChoiceMaker() {
				@Override
				public int chooseLiteral(List<Integer> candidates, int[] assignment) {
					int selected = candidates.get(0);
					int maxstars = stars[Math.abs(selected)];
					for (int lit : candidates) {
						int var = Math.abs(lit);
						if (stars[var] > maxstars) {
							selected = lit;
							maxstars = stars[var];
						}
					}
					return selected;
				}
			});
			oldBones = bonesCount;
			oldStars = starsCount;
			oldEquivalences = equivalences.size();
			processSolution(result);
			if (oldBones == bonesCount && oldStars == starsCount && oldEquivalences == equivalences.size()) {
				break;
			}
		}
		// Phase 3 - break equivalences
		while (true) {
			iterations++;
			result = solver.solveBlockedFormula(formula, new ChoiceMaker() {
				@Override
				public int chooseLiteral(List<Integer> candidates, int[] assignment) {
					picker.clear();
					for (int lit : candidates) {
						float boneBonus = 0f;
						int var = Math.abs(lit);
						if (equivalenceIndex[var] == null) {
							boneBonus += 0.1f;
						} else {
							for (int eqvar : equivalenceIndex[var]) {
								if (assignment[eqvar] * lit < 0) {
									boneBonus += 1f;
								}
							}
						}
						picker.addChoice(lit, boneBonus);
					}
					return picker.pick();
				}
			});
			oldBones = bonesCount;
			oldStars = starsCount;
			oldEquivalences = equivalences.size();
			processSolution(result);
			if (oldBones == bonesCount && oldStars == starsCount && oldEquivalences == equivalences.size()) {
				break;
			}
		}
		
		
		/**/
		printResults(iterations);
	}
}
