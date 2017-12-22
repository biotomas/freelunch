package freelunch.sat.bce.utilities;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class SparseTable {
	
	private static final int EMPTY = -1;
	
	private int[] table;
	private int[] labels;
	private int last = 0;
	
	public SparseTable(int maxLabel) {
		table = new int[maxLabel * 2];
		labels = new int[maxLabel];
		Arrays.fill(table, EMPTY);
		Arrays.fill(labels, EMPTY);
	}
	
	public boolean hasLabel(int id) {
		return labels[id] != EMPTY;
	}
	
	public int getLabel(int id) {
		if (labels[id] == EMPTY) {
			throw new InvalidParameterException("No such element in the Sparse Table");
		}
		return labels[id];
	}
	
	/**
	 * Add a new element 
	 * @param id
	 */
	public void addToEnd(int id) {
		if (last + 1 < table.length) {
			putAfterLabel(id, last + 1);
		} else {
			putAfterId(id, table.length - 1);
		}
	}
	
	public void putAfterId(int id, int otherId) {
		int label = getLabel(otherId);
		putAfterLabel(id, label);
	}

	public void putAfterLabel(int id, int label) {
		if (table[label] == EMPTY) {
			table[label] = id;
			labels[id] = label;
			if (label > last) last = label;
		} else if (label + 1 < table.length && table[label+1] == EMPTY) {
			table[label+1] = id;
			labels[id] = label+1;
			if (label > last) last = label;
		} else {
			for (int size = 1; size < table.length; size*=2) {
				int[] neigh = findNeiqhbourhood(label, size);
				if (insert(id, label, neigh[0], neigh[1])) {
					break;
				}
			}
		}
	}

	/**
	 * Remove the element with the given ID from the Sparse Table
	 * @param id
	 */
	public void remove(int id) {
		table[labels[id]] = EMPTY;
		labels[id] = EMPTY;
	}
	
	private boolean insert(int id, int label, int from, int to) {
		int emptySlots = 0;
		int[] tmp = new int[1 + to - from];
		int j = 0;
		for (int i = from; i < to; i++) {
			if (table[i] == EMPTY) {
				emptySlots++;
			} else {
				tmp[j++] = table[i];
			}
			if (i == label) {
				tmp[j++] = id;
			}
		}
		if (emptySlots < 2 + (to - from)/10) { 
			return false;
		} else {
			int gapStep = 1 + ((to - from) / (emptySlots - 1));
			int items = to - from - emptySlots + 1;
			//System.out.println("Rearranging items " + items);
			int i;
			j = 0;
			for (i = from; i < to; i++) {
				if (i % gapStep != 0 && j < items) { 
					int who = tmp[j++];
					labels[who] = i;
					table[i] = who;
				} else {
					table[i] = EMPTY;
				}
			}
			if (j != items) {
				throw new RuntimeException("sparse table wrong");
			}
			if (to >= last) {
				last = i;
			}
			return true;
		}
	}
	
	private int[] findNeiqhbourhood(int center, int size) {
		int from = center - size;
		int to = center + size;
		
		if (to > table.length) {
			to = table.length;
		}
		if (from < 0) {
			from = 0;
		}
		return new int[] {from, to};
	}
	
	 

}
