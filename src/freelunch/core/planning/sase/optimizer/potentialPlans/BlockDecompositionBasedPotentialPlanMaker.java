package freelunch.core.planning.sase.optimizer.potentialPlans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.blockDecomposition.BlockDecomposition;
import freelunch.core.planning.sase.optimizer.blockDecomposition.BlockDecomposition.ActionBlock;

public class BlockDecompositionBasedPotentialPlanMaker implements PotentialPlanMaker {

	@Override
	public PotentialPlan makePotentialPlan(SasProblem problem, SasParallelPlan plan) {
		throw new UnsupportedOperationException();
	}
	
	PotentialPlan pp;

	@Override
	public PotentialPlan makePotentialPlan(SasProblem problem,
			SasParallelPlan plan, BlockDecomposition blockDecomposition) {

		pp = new PotentialPlan();
		for (int i = 0; i < blockDecomposition.getActions(); i++) {
			pp.getPlan().add(new HashSet<SasAction>());
		}
		getPotentialPlan(0, blockDecomposition.getTopLevelBlocks());
		
		return pp;
	}
	
	private void getPotentialPlan(int position, List<ActionBlock> blocks) {
		List<List<ActionBlock>> allOrderings = getAllOrderings(blocks);
		for (List<ActionBlock> ordering : allOrderings) {
			int myPosition = position;
			for (ActionBlock ab : ordering) {
				// simple block = 1 action block
				if (ab.action != null) {
					pp.getPlan().get(myPosition).add(ab.action);
					myPosition++;
				} else {
					getPotentialPlan(myPosition, ab.subBlocks);
					myPosition += countActions(ab);
				}
			}
		}
	}
	
	private int countActions(ActionBlock block) {
		if (block.action != null) {
			return 1;
		} else {
			int actions = 0;
			for (ActionBlock ab : block.subBlocks) {
				actions += countActions(ab);
			}
			return actions;
		}
	}
	
	
	private Set<ActionBlock> usedBlocks;
	private List<List<ActionBlock>> orderings;
	private Stack<ActionBlock> blockStack;
	private List<ActionBlock> blocks;
	
	private Map<String, List<List<ActionBlock>>> orderingCache;
	
	private List<List<ActionBlock>> getAllOrderings(List<ActionBlock> blocks) {
		
		// caching
		if (orderingCache == null) {
			orderingCache = new HashMap<String, List<List<ActionBlock>>>();
		}
		String key = makeKey(blocks);
		if (orderingCache.containsKey(key)) {
			return orderingCache.get(key);
		}
		
		this.blocks = blocks;
		usedBlocks = new HashSet<>();
		orderings = new ArrayList<List<ActionBlock>>();
		blockStack = new Stack<>();
		
		for (ActionBlock ab : blocks) {
			tryBlock(ab);
		}
		
		// caching
		orderingCache.put(key, orderings);
		
		return orderings;
	}
	
	private String makeKey(List<ActionBlock> blocks) {
		List<Integer> ids = new ArrayList<>();
		for (ActionBlock ab : blocks) {
			ids.add(ab.id);
		}
		Collections.sort(ids);
		return ids.toString();
	}
	
	//TODO caching?
	private void tryBlock(ActionBlock block) {
		if (!usedBlocks.contains(block)) {
			if (block.preconditionBlocks != null) {
				for (ActionBlock ab : block.preconditionBlocks) {
					if (!usedBlocks.contains(ab)) {
						return;
					}
				}
			}
			blockStack.push(block);
			usedBlocks.add(block);
			if (usedBlocks.size() == blocks.size()) {
				orderings.add(new ArrayList<>(blockStack));
			} else {
				for (ActionBlock ab : blocks) {
					tryBlock(ab);
				}
			}
			usedBlocks.remove(block);
			blockStack.pop();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
