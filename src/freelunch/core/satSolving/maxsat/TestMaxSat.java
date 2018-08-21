package freelunch.core.satSolving.maxsat;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satSolving.RandomFormulaGenerator;
import freelunch.core.satSolving.maxsat.WeightedPartialMaxSatFormula.WeightedClause;
import freelunch.core.satSolving.solvers.Sat4JSolver;
import freelunch.core.satSolving.solvers.SatSolver;
import freelunch.sat.model.CnfSatFormula;
import junit.framework.TestCase;

public class TestMaxSat extends TestCase {
	
	public void testSat4jMaxSatRandom() throws TimeoutException {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(10);
		SatSolver s = new Sat4JSolver();
		MaxSatSolver m = new Sat4JMaxsatSolver();
		for (int i = 0; i < 50; i++) {
			CnfSatFormula f = rfg.getRandomSat(50);
			boolean sat = s.isSatisfiable(f);
			
			WeightedPartialMaxSatFormula wf = new WeightedPartialMaxSatFormula(f.variablesCount);
			wf.getHardClauses().addAll(f.getClauses());
			wf.getSoftClauses().add(new WeightedClause(1, new int[] {1}));
			
			int[] model = m.solvePartialWeightedMaxsat(wf);
			
			
			if (sat && model == null) {
				System.out.println("error, sat -> unsat");
			}
			if (!sat && model != null) {
				System.out.println("error unsat -> sat");
			}
			if (model != null && f.validateSolution(model) == false) {
				System.out.println("error invalid model");
			}
		}
	}
	
	public void testSat4jMaxSat() {
		WeightedPartialMaxSatFormula f = new WeightedPartialMaxSatFormula(3);
		f.getHardClauses().add(new int[] {1,2});
		f.getSoftClauses().add(new WeightedClause(100, new int[] {-1,-2}));
		f.getSoftClauses().add(new WeightedClause(150, new int[] {1,-2}));
		f.getSoftClauses().add(new WeightedClause(20, new int[] {-1,2}));
		
		MaxSatSolver s = new Sat4JMaxsatSolver();
		int[] model = s.solvePartialWeightedMaxsat(f);
		
		assertEquals(1, model[1]);
		assertEquals(-2, model[2]);
	}

	public void testSat4jMaxSatUNSAT() {
		WeightedPartialMaxSatFormula f = new WeightedPartialMaxSatFormula(3);
		f.getHardClauses().add(new int[] {1,2});
		f.getHardClauses().add(new int[] {-1,-2});
		f.getHardClauses().add(new int[] {1,-2});
		f.getHardClauses().add(new int[] {-1,2});
		
		MaxSatSolver s = new Sat4JMaxsatSolver();
		int[] model = s.solvePartialWeightedMaxsat(f);

		assertNull(model);
	}

}
