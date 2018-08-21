package freelunch.sat.bce.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.sat.bce.decomposers.FormulaDecomposer;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.SimplifiedArminsBCEliminator;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import junit.framework.TestCase;

public class CounterExample extends TestCase {
	
	public void testCe() {
		CnfSatFormula f = CnfSatFormula.parseFromFile("counterex.cnf");
		tryFormula(f);
	}
	
	public void testFindCounterExample() {
		
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(23);
		
		for (int vars = 5; vars < 40; vars+=1)
			for (int bin = 0; bin < 20; bin+=1)
				for (int tri = bin+1; tri < 15; tri+=1) {
					for (int i = 0; i < 10; i++) {
						FormulaDecomposer dec = new PureDecomposer();
						CnfSatFormula f = rfg.getRandomFormula(vars, bin, tri);
						CnfSatFormula l = new CnfSatFormula();
						CnfSatFormula r = new CnfSatFormula();
						dec.decomposeFormula(f, l, r);
						assertTrue(tryFormula(l));
						System.out.println(String.format("var %d bin %d tri %d nr. %d OK", vars, bin, tri, i));
					}
				}
	}
	
	public boolean tryFormula(CnfSatFormula f) {
		if (!isBlocked(f)) {
			System.out.println("Error, input formula not blocked");
			return false;
		}
		for (int var = 1; var <= f.variablesCount; var++) {
			CnfSatFormula f1 = simplify(f, var);
			boolean b1 = isBlocked(f1);
			CnfSatFormula f2 = simplify(f, -var);
			boolean b2 = isBlocked(f2);
			
			System.out.println(var + ": " + b1 + ", " + b2);
			if (b1 || b2) {
				//System.out.println("lemma holds");
			} else {
				System.err.println("COUNTEREXAMPLE");
				//System.out.println(f);
				return false;
			}
		}
		return true;
	}
	
	public boolean isBlocked(CnfSatFormula f) {
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		List<int[]> clauses = elim.eliminateBlockedClauses(f);
		System.out.println("==================");
		for (int[] cl : clauses) {
			System.out.println(Arrays.toString(cl));
		}
		System.out.println("==================");
		//System.out.println(clauses + " vs " + f.clauses.size());
		return clauses.size() == f.clauses.size();
	}
	
	public CnfSatFormula simplify(CnfSatFormula f, int l) {
		CnfSatFormula sf = new CnfSatFormula();
		sf.variablesCount = f.variablesCount;
		sf.clauses = new ArrayList<int[]>();

		clausesCycle:
		for (int[] cl : f.clauses) {
			for (int i = 0; i < cl.length; i++) {
				int lit = cl[i];
				if (lit == l) {
					continue clausesCycle;
				}
				if (lit == -l) {
					int[] ncl = Arrays.copyOf(cl, cl.length-1);
					if (i < ncl.length) {
						ncl[i] = cl[cl.length-1];
					}
					sf.clauses.add(ncl);
					continue clausesCycle;
				}
			}
			sf.clauses.add(cl);
		}
		return sf;
	}

}
