package freelunch.sat.bce.tests;

import java.util.ArrayList;

import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import junit.framework.TestCase;

public class UnitPropagationSimplifierTest extends TestCase {
	
	public void testSimplifier() {
		BasicFormula f = new BasicFormula();
		f.clauses = new ArrayList<int[]>();
		f.clauses.add(new int[] {1, 2, -3});
		f.clauses.add(new int[] {-1, -3, 5});
		f.clauses.add(new int[] {5, 2, -4, -3});
		f.clauses.add(new int[] {5, 1, -3});
		f.clauses.add(new int[] {4, -3});
		f.clauses.add(new int[] {3});

		f.variablesCount = 5;
		System.out.println(f);
		
		UnitPropagationSimplifier.simplifyByUnitPropagation(f, true);
		
		System.out.println(f);

	}

}
