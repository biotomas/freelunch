package freelunch.sat.bce.tests;

import java.util.ArrayList;
import java.util.BitSet;

import junit.framework.TestCase;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.encoding.DualRailEncoder;
import freelunch.sat.bce.encoding.ReconstructionEncoder;
import freelunch.sat.bce.utilities.LockedProvider;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import freelunch.sat.solver.Sat4JSolver;

public class EncodingTest extends TestCase {
	
	public void testReconstructionEncoding() {
		BasicFormula f = new BasicFormula();
		f.variablesCount = 3;
		f.clauses = new ArrayList<int[]>();
		f.clauses.add(new int[]{-1,2});
		f.clauses.add(new int[]{-1,3});
		f.clauses.add(new int[]{1,-2,-3});
		BasicFormula g = new BasicFormula();
		g.variablesCount = 3;
		g.clauses = new ArrayList<int[]>();
		g.clauses.add(new int[]{-2});
		
		BasicFormula enc = ReconstructionEncoder.encodeReconstruction(f, g, true, LockedProvider.lockNone(f.variablesCount));
		assertNotNull(enc);
		//System.out.println(enc);
	}
	
	public void testReencodingOnRandom() {
		final int tests = 100;
		final int vars = 150;
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(5000);
		Sat4JSolver solver = new Sat4JSolver();
		int satCount = 0;
		Stopwatch origTime = new Stopwatch();
		Stopwatch reencTime = new Stopwatch();
		//Logger.setVerbosity(1);
		for (int i = 0; i < tests; i++) {
			BasicFormula f = rfg.getRandomFormula(vars, 3, 0, 41*vars/10);
			origTime.unpause();
			boolean satOrig = solver.isSatisfiable(f);
			origTime.pause();
			
			PureDecomposer decomposer = new PureDecomposer();
			BasicFormula l = new BasicFormula();
			BasicFormula r = new BasicFormula();
			
			BasicFormula g = f;
			//BasicFormula g = DualRailEncoder.getDualRailEncoding(f);
			
			decomposer.decomposeFormula(g, l, r);
			System.out.println(l.clauses.size() + " " + r.clauses.size());
			//BasicFormula reencoded = ReconstructionEncoder.encodeReconstruction(l, r, true);
			
			//BitSet locked = LockedProvider.lockNone(l.variablesCount);
			BitSet locked = LockedProvider.lockBlits(r);
			System.out.println("locked " + locked.cardinality());
			BasicFormula reencoded = ReconstructionEncoder.encodeReconstruction(l, r, false, locked);
			//BasicFormula reencoded = ButterflyEncoder.encodeReconstruction(l, r, locked);
			//BasicFormula reencoded = SelectiveEncoder.encodeReconstruction(l, r);
			//BasicFormula reencoded = RibbonEncoder.encodeReconstruction(l, r);
			
			//reencoded = DualRailEncoder.getDualRailEncoding(reencoded);
			reencTime.unpause();
			boolean satReenc = solver.isSatisfiable(reencoded);
			reencTime.pause();
			
			//System.out.println(satOrig + " " + satReenc);
			assertEquals(satOrig, satReenc);
			if (satOrig) {
				satCount++;
			}
		}
		System.out.println(String.format("original %s reencoded %s", origTime.elapsedFormatedSeconds(), reencTime.elapsedFormatedSeconds()));
		System.out.println("SAT: " + satCount);
	}
	
	public void testDualRailEncoding() {
		BasicFormula f = new BasicFormula();
		f.variablesCount = 3;
		f.clauses = new ArrayList<int[]>();
		f.clauses.add(new int[]{-1,2});
		f.clauses.add(new int[]{-1,3});
		f.clauses.add(new int[]{1,-2,-3});

		BasicFormula drf = DualRailEncoder.getDualRailEncoding(f);
		assertEquals(f.variablesCount*2, drf.variablesCount);
		assertEquals(f.clauses.size() + f.variablesCount, drf.clauses.size());
		//System.out.println(f);
		//System.out.println(drf);
	}

}
