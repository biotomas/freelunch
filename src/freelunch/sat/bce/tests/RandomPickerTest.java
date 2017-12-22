package freelunch.sat.bce.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

import junit.framework.TestCase;
import freelunch.sat.bce.utilities.WeightedRandomPicker;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class RandomPickerTest extends TestCase {
	
	public void testArrays() {
		BasicFormula bf1 = new BasicFormula();
		bf1.variablesCount = 4;
		bf1.clauses = new ArrayList<int[]>();
		bf1.clauses.add(new int[] {1,2,3});
		bf1.clauses.add(new int[] {2,3});
		bf1.clauses.add(new int[] {-3,4});
		
		BasicFormula bf2 = bf1.copy();

		bf2.printDimacs(System.out);

		bf1.clauses = new ArrayList<int[]>();

		bf2.printDimacs(System.out);
}
	
	public void testBitSet() {
		BitSet bs = new BitSet(100);
		bs.clear();
		System.out.println(bs.cardinality());
		bs.set(4);
		bs.set(3);
		bs.set(9);
		System.out.println(bs.cardinality());
		bs.flip(0, bs.length());
		System.out.println(bs.cardinality());
	}
	
	public void testPicker() {
		int[] freq = {0,0,0,0};
		WeightedRandomPicker wrp = new WeightedRandomPicker(4498);
		wrp.addChoice(0, 0.1f);
		wrp.addChoice(1, 0.2f);
		wrp.addChoice(2, 0.3f);
		wrp.addChoice(3, 0.4f);
		for (int i = 0; i < 10000; i++) {
			freq[wrp.pick()]++;
		}
		System.out.println(Arrays.toString(freq));
		wrp.clear();
		wrp.addChoice(0, 1f);
		wrp.addChoice(1, 2f);
		wrp.addChoice(2, 4f);
		wrp.addChoice(3, 8f);
		Arrays.fill(freq, 0);
		for (int i = 0; i < 10000; i++) {
			freq[wrp.pick()]++;
		}
		System.out.println(Arrays.toString(freq));
	}

}
