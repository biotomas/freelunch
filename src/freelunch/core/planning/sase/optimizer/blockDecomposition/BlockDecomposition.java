package freelunch.core.planning.sase.optimizer.blockDecomposition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;


public class BlockDecomposition {
	
	/**
	 * Block invariants:
	 * - either action or subBlocks is null
	 * - a block with null parent is a top-level block 
	 */
	public class ActionBlock {
		public int id;
		public SasAction action;
		public List<ActionBlock> subBlocks;
		public ActionBlock parentBlock;
		public Set<ActionBlock> preconditionBlocks;
		
		@Override
		public String toString() {
			return "id=" + id;
		}
		
		@Override
		public int hashCode() {
			return id;
		}
		
		@Override
		public boolean equals(Object obj) {
			ActionBlock o = (ActionBlock) obj;
			return o.id == id;
		}
	}

	enum LoadState {
		none,
		plan, 
		orderings, 
		blocks, 
		blockHierarchy
	};
	
	private List<ActionBlock> topLevelBlocks;
	private int actions;
	
	public BlockDecomposition(SasProblem problem, String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        
        Map<String, SasAction> nameActionMap = new HashMap<String, SasAction>();
        for (SasAction a : problem.getOperators()) {
        	nameActionMap.put(a.getActionInfo().getName(), a);
        }
        //add special actions INIT and GOAL
        nameActionMap.put("INIT", new SasAction("INIT"));
        nameActionMap.put("GOAL", new SasAction("GOAL"));
        
        List<SasAction> plan = new ArrayList<SasAction>();
        Map<Integer, List<Integer>> actionOrderdings = new HashMap<>();
        Map<Integer, ActionBlock> blocksById = new HashMap<>();
        Map<Integer, Integer> actionIdToBlockId = new HashMap<>();
        
        LoadState state = LoadState.none;
        while (line != null) {
        	if (line.contains("input_plan")) {
        		state = LoadState.plan;
            	line = reader.readLine();
        	}
        	if (line.contains("necessary_orderings_among_steps")) {
        		state = LoadState.orderings;
            	line = reader.readLine();
        	}
        	if (line.contains("basic_blocks")) {
        		state = LoadState.blocks;
            	line = reader.readLine();
        	}
        	if (line.contains("basic_block_hierarchy")) {
        		state = LoadState.blockHierarchy;
            	line = reader.readLine();
        	}
        	
        	if (line.isEmpty()) {
        		line = reader.readLine();
        		continue;
        	}
        	
        	switch (state) {
        	case plan:
        		if (line.contains(" INIT")) {
        			plan.add(nameActionMap.get("INIT"));
        			break;
        		}
        		if (line.contains(" GOAL")) {
        			plan.add(nameActionMap.get("GOAL"));
        			break;
        		}
        		String name = extractActionName(line);
        		SasAction a = nameActionMap.get(name);
        		if (a == null) {
        			throw new RuntimeException("Action not found in the problem specification.");
        		}
        		plan.add(a);
        		break;
        	case orderings:
        		String[] linep = line.split(" ");
        		int head = Integer.parseInt(linep[0]);
        		actionOrderdings.put(head, new ArrayList<Integer>());
        		assert (linep[1].equals(":"));
        		for (int i = 2; i < linep.length; i++) {
        			actionOrderdings.get(head).add(Integer.parseInt(linep[i]));
        		}
        		break;
        	case blocks:
        		linep = line.split(" ");
        		int blockId = Integer.parseInt(linep[0].substring(2, linep[0].length()-1));
        		ActionBlock ab = new ActionBlock();
        		ab.id = blockId;
        		blocksById.put(blockId, ab);
        		assert (linep[1].equals("{"));
        		if (linep.length > 5) {
        			// composite block
        			ab.action = null;
        			ab.subBlocks = new ArrayList<>();
        		} else {
        			int actionId = Integer.parseInt(linep[2]);
        			ab.action = plan.get(actionId);
        			ab.subBlocks = null;
        			actionIdToBlockId.put(actionId, blockId);
            		assert (linep[3].equals("}"));
        		}
        		break;
        	case blockHierarchy:
        		linep = line.split(" ");
        		blockId = Integer.parseInt(linep[0].substring(2, linep[0].length()-1));
        		ab = blocksById.get(blockId);
        		for (int i = 2; i < linep.length - 2; i++) {
        			int subBlockId = Integer.parseInt(linep[i]);
        			blocksById.get(blockId).subBlocks.add(blocksById.get(subBlockId));
        			blocksById.get(subBlockId).parentBlock = blocksById.get(blockId);
        		}
        		break;
			case none:
				break;
			default:
				break;
        	}
        	line = reader.readLine();
        }
        fr.close();
        
        topLevelBlocks = new ArrayList<ActionBlock>();
        for (ActionBlock ab : blocksById.values()) {
        	if (ab.parentBlock == null) {
        		topLevelBlocks.add(ab);
        	}
        }
        
        for (int head : actionOrderdings.keySet()) {
        	List<Integer> successors = actionOrderdings.get(head);
        	for (int s : successors) {
        		ActionBlock pred = blocksById.get(actionIdToBlockId.get(head));
        		ActionBlock succ = blocksById.get(actionIdToBlockId.get(s));
        		setBlockRelation(succ, pred);
        	}
        }
        actions = (plan.size() - 2);
	}
	
	private void setBlockRelation(ActionBlock ab1, ActionBlock ab2) {
		if (ab1.parentBlock != null && ab2.parentBlock != null) {
			// find first common parent
			ActionBlock parent = findFirstCommonParent(ab1, ab2);
			if (parent == null) {
				ab1 = findTopLevel(ab1);
				ab2 = findTopLevel(ab2);
			} else {
				while (ab1.parentBlock != parent) {
					ab1 = ab1.parentBlock;
				}
				while (ab2.parentBlock != parent) {
					ab2 = ab2.parentBlock;
				}
			}
		} else if (ab1.parentBlock == null) {
			ab2 = findTopLevel(ab2);
		} else if (ab2.parentBlock == null) {
			ab1 = findTopLevel(ab1);
		}
		if (ab1.preconditionBlocks == null) {
			ab1.preconditionBlocks = new HashSet<>();
		}
		ab1.preconditionBlocks.add(ab2);
	}
	
	private ActionBlock findTopLevel(ActionBlock ab) {
		while (ab.parentBlock != null) {
			ab = ab.parentBlock;
		}
		return ab;
	}
	
	private ActionBlock findFirstCommonParent(ActionBlock ab1, ActionBlock ab2) {
		Set<ActionBlock> parents = new HashSet<>();
		while (ab1 != null) {
			parents.add(ab1);
			ab1 = ab1.parentBlock;
		}
		while (ab2 != null) {
			if (parents.contains(ab2)) {
				return ab2;
			}
			ab2 = ab2.parentBlock;
		}
		return null;
	}
	
    private String extractActionName(String line) {
        int start = line.indexOf('(');
        int end = line.indexOf(')');
        if (start != -1 && end != -1) {
            return line.substring(1 + start, end).toLowerCase();
        } else {
            return null;
        }
    }


	public List<ActionBlock> getTopLevelBlocks() {
		return topLevelBlocks;
	}

	public int getActions() {
		return actions;
	}

}
