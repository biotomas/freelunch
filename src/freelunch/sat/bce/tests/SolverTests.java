package freelunch.sat.bce.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import freelunch.sat.bce.decomposers.FormulaDecomposer;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.SimplifiedArminsBCEliminator;
import freelunch.sat.bce.solver.BlockedSetSolver;
import freelunch.sat.bce.solver.ChoiceMaker;
import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;

public class SolverTests extends TestCase {
	
	public void testSolver() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(10);
		BasicFormula f = rfg.getRandomFormula(100000, 100000, 400000);
		//BasicFormula f = rfg.getRandomSat(10, 5, 10);
		
		FormulaDecomposer decomposer = new PureDecomposer();
		BasicFormula l = new BasicFormula();
		BasicFormula r = new BasicFormula();
		decomposer.decomposeFormula(f, l, r);
		// order the clauses of l properly
		l = DimacsParser.parseFromFile("result.cnf");
		
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		l.clauses = elim.eliminateBlockedClauses(l);
		
		System.out.println(String.format("large: %d (%d%%), small %d", l.clauses.size(), (100*l.clauses.size()/f.clauses.size()), r.clauses.size()));

		
		BlockedSetSolver solver = new BlockedSetSolver();
		final int[] choices = new int[30];
		final Random rnd = new Random();
		Arrays.fill(choices, 0);
		int[] assignment = solver.solveBlockedFormula(l, new ChoiceMaker() {
			@Override
			public int chooseLiteral(List<Integer> candidates, int[] assignment) {
				choices[0]++;
				choices[candidates.size()]++;
				return candidates.get(rnd.nextInt(candidates.size()));
			}
		});
		System.out.println(Arrays.toString(choices));

		//System.out.println(Arrays.toString(assignment));
		int zeros = countZeros(assignment);
		System.out.println(String.format("unset: %d of %d (%d%%)", zeros, l.variablesCount, (100*zeros)/l.variablesCount));
		assertTrue(FormulaAnalyzer.solutionOk(l, assignment));
	}
	
	private int countZeros(int[] assignment) {
		int zeros = 0;
		for (int i : assignment) {
			if (i == 0) {
				zeros++;
			}
		}
		return zeros;
	}

}
