package freelunch.sat.bce.extractors;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import freelunch.sat.bce.solver.BlockedSetSolver;
import freelunch.sat.bce.solver.ChoiceMaker;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class FullAssignmentExtractor extends RandomExtractor {
	
	private static final int assignments = 512;
	private Random rnd = new Random(2013);
	
	public void extractBackBones(BasicFormula formula) {
		this.formula = formula;
		int[] assignment = new int[formula.variablesCount+1];
		makeRandomAssignment(assignment);
		
		BlockedSetSolver solver = new BlockedSetSolver();
		int[] result = solver.solveBlockedFormula(formula, new ChoiceMaker() {
			@Override
			public int chooseLiteral(List<Integer> candidates, int[] assignment) {
				throw new RuntimeException("no choosing allowed");
			}
		}, assignment);
		
		bones = Arrays.copyOf(result, result.length);
		stars = new int[result.length];
		for (int i = 1; i < result.length; i++) {
			stars[i] = result[i] == 0 ? 1 : 0;
			if (stars[i] > 0) starsCount++;
			if (result[i] != 0) bonesCount++;
		}
		int iterations = 1;
		
		while (iterations < assignments) {
			iterations++;
			makeRandomAssignment(assignment);
			result = solver.solveBlockedFormula(formula, new ChoiceMaker() {
				@Override
				public int chooseLiteral(List<Integer> candidates, int[] assignment) {
					throw new RuntimeException("no choosing allowed");
				}
			}, assignment);
			processSolution(result);
		}
		printResults(iterations);
	}
	
	private void makeRandomAssignment(int[] array) {
		for (int i = 1; i < array.length; i++) {
			boolean positive = rnd.nextBoolean();
			array[i] = positive ? i : -i;
		}
	}

}
