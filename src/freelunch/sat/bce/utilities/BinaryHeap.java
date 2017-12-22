package freelunch.sat.bce.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Binary heap supporting updates (decrease key and increase key)
 * @author balyo
 *
 * @param <Typename> Must implement equals and hashcode
 */
public class BinaryHeap<Typename extends Comparable<Typename>> {
	
	private ArrayList<Typename> array;
	private Map<Typename, Integer> positions;
	
	public BinaryHeap() {
		array = new ArrayList<Typename>(11);
		positions = new HashMap<Typename, Integer>();
	}
	
	public BinaryHeap(List<Typename> elements) {
		this();
		array.addAll(elements);
		int i = 0;
		for (Typename t : elements) {
			positions.put(t, i);
			i++;
		}
		heapify();
	}
	
	public void add(Typename element) {
		array.add(element);
		positions.put(element, array.size()-1);
		siftUp(array.size()-1);
	}
	
	public void addOrUpdate(Typename element) {
		if (positions.get(element) == null) {
			add(element);
		} else {
			update(element);
		}
	}
	
	public void update(Typename element) {
		Integer index = positions.get(element);
		if (index == null) {
			return;
		}
		if (index > 0 && array.get(index).compareTo(array.get(index/2)) < 0) {
			siftUp(index);
		} else {
			siftDown(index);
		}
	}
	
	public Typename peekMinimum() {
		if (array.isEmpty()) {
			return null;
		}
		return array.get(0);
	}
	
	public Typename removeMinimum() {
		if (array.isEmpty()) {
			return null;
		}
		Typename min = array.get(0);
		swap(0, array.size()-1);
		array.remove(array.size()-1);
		positions.remove(min);
		siftDown(0);
		return min;
	}
	
	private void heapify() {
		for (int i = array.size() / 2; i >= 0; i--) {
			siftDown(i);
		}
	}
	
	private void siftDown(int i) {
		int left = i*2;
		int right = i*2+1;
		if (left < array.size() && array.get(i).compareTo(array.get(left)) > 0) {
			swap(i, left);
			siftDown(left);
		}
		if (right < array.size() && array.get(i).compareTo(array.get(right)) > 0) {
			swap(i, right);
			siftDown(right);
		}
	}
	
	private void siftUp(int i) {
		if (i == 0)
			return;
		int parent = i / 2;
		if (array.get(i).compareTo(array.get(parent)) < 0) {
			swap(parent, i);
			siftUp(parent);
		}
	}
	
	private void swap(int i, int j) {
		Typename tmp = array.get(i);
		array.set(i, array.get(j));
		array.set(j, tmp);
		positions.remove(array.get(i));
		positions.remove(array.get(j));
		positions.put(array.get(i), i);
		positions.put(array.get(j), j);
	}
	
	@Override
	public String toString() {
		return array.toString();
	}
	

}
