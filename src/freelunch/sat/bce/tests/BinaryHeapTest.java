package freelunch.sat.bce.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import freelunch.sat.bce.utilities.BinaryHeap;
import junit.framework.TestCase;

public class BinaryHeapTest extends TestCase {
	
	public class Word implements Comparable<Word> {
		
		private String word;
		public int length;
		
		public Word(String word) {
			this(word, word.length());
		}
		
		public Word(String word, int length) {
			this.word = word;
			this.length = length;
		}

		@Override
		public int compareTo(Word o) {
			return length - o.length;
		}
		
		@Override
		public int hashCode() {
			return word.hashCode();
		}
		
		@Override
		public String toString() {
			return length + ":" + word;
		}
		
	}
	
	public void testBinaryHeapConstruction() {
		List<Word> words = new ArrayList<BinaryHeapTest.Word>();
		words.add(new Word("hello"));
		words.add(new Word("jim"));
		words.add(new Word("my"));
		words.add(new Word("name"));
		words.add(new Word("is"));
		words.add(new Word("mortimer"));
		
		BinaryHeap<Word> wordsHeap = new BinaryHeap<BinaryHeapTest.Word>(words);
		
		wordsHeap.add(new Word("lol"));
		wordsHeap.add(new Word("a"));
		
		Word w = wordsHeap.removeMinimum();
		while (w != null) {
			System.out.println(w);
			w = wordsHeap.removeMinimum();
		}
	}
	
	public void testBinaryHeapUpdate() {
		Word w1 = new Word("monty", 32);
		Word w2 = new Word("john", 11);
		Word w3 = new Word("jim", 312);
		Word w4 = new Word("jack", 12);
		Word w5 = new Word("jil", 44);
		
		BinaryHeap<Word> words = new BinaryHeap<BinaryHeapTest.Word>();
		words.add(w1);
		words.add(w2);
		words.add(w3);
		words.add(w4);
		words.add(w5);
		
		System.out.println(words);
		
		w3.length = 1;
		words.update(w3);
		
		System.out.println(words);
		
		w5.length = 2;
		words.addOrUpdate(w5);
		
		System.out.println(words);
	}
	
	public void testRandom() {
		Random rnd = new Random(2013);
		BinaryHeap<Integer> ints = new BinaryHeap<Integer>();
		for (int i = 0; i < 100; i++) {
			ints.add(rnd.nextInt(1000));
		}
		Integer i = ints.removeMinimum();
		while (i != null) {
			System.out.print(i);
			System.out.print(", ");
			i = ints.removeMinimum();
		}
		
		List<Integer> intlist = new ArrayList<Integer>();
		for (int j = 0; j < 100; j++) {
			intlist.add(rnd.nextInt(1000));
		}
		
		ints = new BinaryHeap<Integer>(intlist);
		System.out.println("");
		i = ints.removeMinimum();
		while (i != null) {
			System.out.print(i);
			System.out.print(", ");
			i = ints.removeMinimum();
		}
		
	}
	
	public void testArraySet() {
		Set<int[]> s = new HashSet<int[]>();
		s.add(new int[] {1,2,3});
		s.add(new int[] {1,2,3});
		System.out.println(s);
	}

}
