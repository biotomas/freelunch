package freelunch.sat.bce.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedRandomPicker {
	
	private Random random;
	private List<Float> weights;
	private List<Integer> ids;
	private float totalWeight;
	
	public WeightedRandomPicker(long seed) {
		random = new Random(seed);
		weights = new ArrayList<Float>();
		ids = new ArrayList<Integer>();
		clear();
	}
	
	public void addChoice(int id, float weight) {
		ids.add(id);
		weights.add(weight);
		totalWeight += weight;		
	}
	
	public void clear() {
		weights.clear();
		ids.clear();
		totalWeight = 0f;
	}
	
	public int pick() {
		float limit = random.nextFloat()*totalWeight;
		for (int i = 0; i < ids.size(); i++) {
			if (weights.get(i) >= limit) {
				return ids.get(i);				
			}
			limit -= weights.get(i);
		}
		return ids.get(ids.size()-1);
	}

}
