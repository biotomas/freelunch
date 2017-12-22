package freelunch.sat.bce.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;
import freelunch.sat.bce.utilities.SparseTable;
import freelunch.sat.satLifter.Stopwatch;

public class UtilitiesTest extends TestCase {
	
	public void testSetArray() {
		Set<int[]> sa = new HashSet<int[]>();
		sa.add(new int[]{1,2,3});
		sa.add(new int[]{1,2,3});
		System.out.println(sa.size());		
	}
	
	public void testSparseTable() {
		
		float x = 0f;
		for (int i = 0; i < 200000; i++) 
			x += 0.125f;
		System.out.println(x);
		
		SparseTable st = new SparseTable(10);

		for (int i = 0; i < 10; i++)
			st.putAfterLabel(i, 2*i);

		st.remove(3);
		st.remove(4);
		st.remove(5);
		st.putAfterId(3, 6);
		st.putAfterId(4, 7);
		st.putAfterId(5, 8);
		st.remove(1);
		st.putAfterId(1, 4);
		st.remove(0);
		st.putAfterId(0, 1);
		st.remove(2);
		st.putAfterId(2, 7);
		
		assertTrue(st.getLabel(3) > st.getLabel(6));
		assertTrue(st.getLabel(4) > st.getLabel(7));
		assertTrue(st.getLabel(5) > st.getLabel(8));
		assertTrue(st.getLabel(1) > st.getLabel(4));
		assertTrue(st.getLabel(0) > st.getLabel(1));
		assertTrue(st.getLabel(2) > st.getLabel(7));

		//for (int i = 0; i < 10; i++)
		//	System.out.println(i + " " + st.getLabel(i));
	}
	
	public void testPerformaceSparseTable() {
		Stopwatch watch = new Stopwatch();
		watch.pause();
		Stopwatch shuffleTime = new Stopwatch();
		shuffleTime.pause();
		
		Random rnd = new Random(2013);
		int size = 1000000;
		int tests = 100;
		int nrsPerTest = 10000;
		
		SparseTable st = new SparseTable(size);
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			st.putAfterLabel(i, i);
			//st.addToEnd(i);
			numbers.add(i);
		}
		System.out.println("finished adding");

		for (int i = 0; i < tests; i++) {
			shuffleTime.unpause();
			Collections.shuffle(numbers, rnd);
			shuffleTime.pause();
			for (int j = 0; j < nrsPerTest; j++) {
				st.remove(numbers.get(j));
			}
			for (int j = 0; j < nrsPerTest; j++) {
				int x = numbers.get(j);
				int y = numbers.get(j+nrsPerTest);
				watch.unpause();
				st.putAfterId(x, y);
				watch.pause();
			}
			for (int j = 0; j < nrsPerTest; j++) {
				int x = numbers.get(j);
				int y = numbers.get(j+nrsPerTest);
				assertTrue(st.getLabel(x) > st.getLabel(y));
			}
		}
		
		System.out.println("Time: " + watch.elapsedFormatedSeconds());
		System.out.println("Shuffle time: " + shuffleTime.elapsedFormatedSeconds());
		
	}

}
