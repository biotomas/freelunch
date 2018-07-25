package freelunch.core.satModelling.tests;

import freelunch.core.satModelling.modelObjects.PseudoBooleanFormula;
import freelunch.core.satModelling.modelObjects.PseudoBooleanFormula.PseudoBooleanEquality;
import junit.framework.TestCase;

public class PseudoBooleanTest extends TestCase {
	
	public void testPseudoBooleanFormula() {
		PseudoBooleanFormula pbf = new PseudoBooleanFormula(10);
		pbf.addEquality(PseudoBooleanEquality.makeFromAtMostOneConstraint(new int[]{1, 2, 3, 4}));
		pbf.addEquality(PseudoBooleanEquality.makeFromAtMostOneConstraint(new int[]{-5, -6, -7, -8}));
		pbf.addEquality(PseudoBooleanEquality.makeFromAtMostOneConstraint(new int[]{1, 2, -7, -8}));
		pbf.addEquality(PseudoBooleanEquality.makeFromClause(new int[] {1,2,3}));
		pbf.addEquality(PseudoBooleanEquality.makeFromClause(new int[] {1,2,3,4}));
		pbf.addEquality(PseudoBooleanEquality.makeFromClause(new int[] {-1,-2,-3}));
		pbf.addEquality(PseudoBooleanEquality.makeFromClause(new int[] {1,-2,3}));
		System.out.println(pbf.toString());
	}

}
