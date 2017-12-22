package freelunch.sat.bce.tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import freelunch.sat.bce.eliminators.ArminsBCEliminator;
import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.ImprovedBCEliminator;
import freelunch.sat.bce.eliminators.SimplifiedArminsBCEliminator;
import freelunch.sat.bce.eliminators.TrivialBCEliminator;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;

public class EliminatorTests extends TestCase {
	
	public void testFile() {
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		BasicFormula f = DimacsParser.parseFromFile("test.bcnf");
		//BasicFormula f = DimacsParser.parseFromFile("result.cnf");

		System.out.println(f.clauses.size());
		UnitPropagationSimplifier.simplifyByUnitPropagation(f, true);
		System.out.println(f.clauses.size());
		int elimsize = elim.eliminateBlockedClauses(f).size();
		assertEquals(f.clauses.size(), elimsize);
	}
	
	public void testTrivialEliminator() {
		BCEliminator elim = new TrivialBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(10, 10, 10);
		
		System.out.println(f);
		printClauses(elim.eliminateBlockedClauses(f));
	}
	
	public void testArminsEliminator() {
		BCEliminator elim = new ArminsBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(10, 10, 10);
		
		//System.out.println(f);
		printClauses(elim.eliminateBlockedClauses(f));
	}
	
	public void testSimplifiedArminsEliminator() {
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(10, 10, 10);
		
		//System.out.println(f);
		printClauses(elim.eliminateBlockedClauses(f));
	}
	
	public void testImprovedEliminator() {
		BCEliminator elim = new ImprovedBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(10, 10, 10);
		
		//System.out.println(f);
		printClauses(elim.eliminateBlockedClauses(f));
	}
	
	private void printClauses(List<int[]> clauses) {
		for (int[] clause : clauses) {
			System.out.print(Arrays.toString(clause) + " ");
		}
		System.out.println();
	}
	
	public void testTrivialEliminatorSpeed() {
		BCEliminator elim = new TrivialBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(1000000, 1000000, 4000000);
		Stopwatch watch = new Stopwatch();
		int eliminated = elim.eliminateBlockedClauses(f).size();
		System.out.println(watch.elapsedFormatedSeconds());
		assertEquals(12998, eliminated);
		//Tautology tests: 20945897
		//15.670
	}
	
	public void testImprovedEliminatorSpeed() {
		BCEliminator elim = new ImprovedBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(1000000, 1000000, 4000000);
		Stopwatch watch = new Stopwatch();
		int eliminated = elim.eliminateBlockedClauses(f).size();
		System.out.println(watch.elapsedFormatedSeconds());
		assertEquals(12998, eliminated);
		//Tautology tests: 13983420
		//14.112
	}
	/*
	public void testArminEliminatorSpeed() {
		BCEliminator elim = new ArminsBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomSat(1000000, 1000000, 4000000);
		Stopwatch watch = new Stopwatch();
		int eliminated = elim.eliminateBlockedClauses(f).size();
		System.out.println(watch.elapsedFormatedSeconds());
		assertEquals(12998, eliminated);
		//Tautology tests: 14060631
		//139.783
	}
	/**/
	
	public void testSimplifiedArminEliminatorSpeed() {
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(1000000, 1000000, 4000000);
		Stopwatch watch = new Stopwatch();
		int eliminated = elim.eliminateBlockedClauses(f).size();
		System.out.println(watch.elapsedFormatedSeconds());
		assertEquals(12998, eliminated);
		//Tautology tests: 14062319
		//6.274
	}	

}
