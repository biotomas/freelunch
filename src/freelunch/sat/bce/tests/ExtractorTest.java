package freelunch.sat.bce.tests;

import junit.framework.TestCase;
import freelunch.sat.bce.decomposers.FormulaDecomposer;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.SimplifiedArminsBCEliminator;
import freelunch.sat.bce.extractors.FullAssignmentExtractor;
import freelunch.sat.bce.extractors.GreedyExtractor;
import freelunch.sat.bce.extractors.RandomExtractor;
import freelunch.sat.bce.extractors.SparrowExtractor;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;

public class ExtractorTest extends TestCase {
	
	public void testRandomExtractor() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(10);
		BasicFormula f = rfg.getRandomFormula(10, 10, 20);
		
		FormulaDecomposer decomposer = new PureDecomposer();
		BasicFormula l = new BasicFormula();
		BasicFormula r = new BasicFormula();
		decomposer.decomposeFormula(f, l, r);
		
		l.clauses.add(new int[] {-1, -2});
		l.clauses.add(new int[] {1, 2});
		
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		f.clauses = elim.eliminateBlockedClauses(l);
		
		RandomExtractor e = new RandomExtractor();
		e.extractBackBones(f);
		
	}
	
	public void testBackBoneExtractor() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(10);
		BasicFormula f = rfg.getRandomFormula(10000, 10000, 20000);
		//BasicFormula f = rfg.getRandomSat(50, 50, 100);
		
		FormulaDecomposer decomposer = new PureDecomposer();
		BasicFormula l = new BasicFormula();
		BasicFormula r = new BasicFormula();
		decomposer.decomposeFormula(f, l, r);
		
		l.clauses.add(new int[] {-1, -2});
		l.clauses.add(new int[] {1, 2});
		
		// order the clauses of l properly
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		l.clauses = elim.eliminateBlockedClauses(l);
		System.out.println("greedy");
		GreedyExtractor extractor = new GreedyExtractor();
		extractor.extractBackBones(l);
		System.out.println("random");
		RandomExtractor e2 = new RandomExtractor();
		e2.extractBackBones(l);
		System.out.println("sparrow");
		SparrowExtractor e3 = new SparrowExtractor();
		e3.extractBackBones(l);
		System.out.println("full");
		FullAssignmentExtractor e4 = new FullAssignmentExtractor();
		e4.extractBackBones(l);
	}

}
