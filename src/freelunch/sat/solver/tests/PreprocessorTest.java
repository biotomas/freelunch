package freelunch.sat.solver.tests;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import freelunch.sat.solver.Sat4JSolver;
import freelunch.sat.solver.preprocessing.LookAheadPreprocessor;
import freelunch.sat.solver.preprocessing.OneProvableEmpoweringClauses;
import junit.framework.TestCase;

public class PreprocessorTest extends TestCase {
	
	public void testOneProvableEmpClause() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2014);
		OneProvableEmpoweringClauses opec = new OneProvableEmpoweringClauses();
		Sat4JSolver solver = new Sat4JSolver();
		
		int vars = 70;
		int cls = (4*vars) + (vars/4);
		
		for (int i = 0; i < 40; i++) {
			BasicFormula f = rfg.getRandomFormula(vars, 0, cls);
			boolean sres = solver.isSatisfiable(f);
			boolean res = true;
			opec.addOneProvableEmpoweringClauses(f, 3, 60);
			boolean sres2 = solver.isSatisfiable(f);
			
			assertEquals(sres, sres2);
			if (res == false) {
				assertFalse(sres);
			}
		}
	}
	
	public void testRandom3Sat() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2014);
		LookAheadPreprocessor fce = new LookAheadPreprocessor();
		Sat4JSolver solver = new Sat4JSolver();
		
		int vars = 100;
		int cls = (4*vars) + (vars/4);
		
		for (int i = 0; i < 200; i++) {
			BasicFormula f = rfg.getRandomFormula(vars, 0, cls);
			boolean sres = solver.isSatisfiable(f);
			if (sres) System.out.print("SAT   "); else System.out.print("UNSAT ");
			
			
			boolean res = fce.preprocess(f);
			if (res) System.out.println("??"); else System.out.println("UNSAT");

			//FormulaAnalyzer.analyzeFormula(f).print();
			//System.out.println("----------");
		}
	}
}
