package freelunch.sat.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freelunch.sat.bce.utilities.BinaryHeap;

public class HittingSetSolver {
	
	private Map<Integer, List<Integer>> occurrenceMap = new HashMap<Integer, List<Integer>>();
	private List<Integer> hitsNeeded = new ArrayList<Integer>();
	private List<Collection<Integer>> setItems = new ArrayList<Collection<Integer>>();
	private Set<Integer> baseSet = new HashSet<Integer>(); 
	private int clauseId = 0;
	
	private void addToOccurence(int key, int value) {
		if (occurrenceMap.get(key) == null) {
			occurrenceMap.put(key, new ArrayList<Integer>());
		}
		occurrenceMap.get(key).add(value);
	}
	
	/**
	 * Add a set and the number of required hits
	 * @param set
	 * @param hitTimes
	 */
	public void addSet(Collection<Integer> set, int hitTimes) {
		hitsNeeded.add(hitTimes);
		setItems.add(set);
		for (int x : set) {
			addToOccurence(x, clauseId);
			baseSet.add(x);
		}
		clauseId++;
	}
	
	/**
	 * Add the positive numbers of this array as a set to hit size()-1 times
	 * @param cl
	 */
	public void addPositive(int[] cl) {
		List<Integer> items = new ArrayList<Integer>();
		for (int x : cl) {
			if (x > 0) {
				items.add(x);
			}
		}
		addSet(items, items.size()-1);
	}
	
	/**
	 * Add the negative numbers of this array as a set to hit size()-1 times
	 * @param cl
	 */
	public void addNegative(int[] cl) {
		List<Integer> items = new ArrayList<Integer>();
		for (int x : cl) {
			if (x < 0) {
				items.add(-x);
			}
		}
		addSet(items, items.size()-1);
	}
	
	private class BaseItem implements Comparable<BaseItem> {
		int id;
		int value;
		
		public BaseItem(int id, int value) {
			this.id = id;
			this.value = value;
		}

		@Override
		public int compareTo(BaseItem o) {
			return o.value - value;
		}
		
		@Override
		public int hashCode() {
			return id;
		}
		
		@Override
		public boolean equals(Object obj) {
			BaseItem o = (BaseItem) obj;
			return o.id == id; 
		}

		@Override
		public String toString() {
			return String.format("%d(%d)", id, value);
		}
	}
	
	public List<Integer> computeHittingSet() {
		List<Integer> hss = new ArrayList<Integer>();
		BinaryHeap<BaseItem> heap = new BinaryHeap<HittingSetSolver.BaseItem>();
		Map<Integer, BaseItem> baseItems = new HashMap<Integer, HittingSetSolver.BaseItem>();
		for (int x : baseSet) {
			BaseItem bi = new BaseItem(x, occurrenceMap.get(x).size()); 
			heap.add(bi);			
			baseItems.put(x, bi);
		}
		int uncoveredSets = setItems.size();
		while (uncoveredSets > 0) {
			BaseItem bi = heap.removeMinimum();
			int itemId = bi.id;
			hss.add(itemId);
			for (int setId : occurrenceMap.get(itemId)) {
				int hn = hitsNeeded.get(setId) - 1;
				hitsNeeded.set(setId, hn);
				if (hn == 0) {
					uncoveredSets--;
					for (int oItemId : setItems.get(setId)) {
						BaseItem obi = baseItems.get(oItemId);
						obi.value--;
						heap.update(obi);
					}
				}
			}
		}
		return hss;
	}
	
}
