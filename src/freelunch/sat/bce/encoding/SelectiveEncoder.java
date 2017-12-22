package freelunch.sat.bce.encoding;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.bce.utilities.LockedProvider;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class SelectiveEncoder {
	
	public static BasicFormula encodeReconstruction(BasicFormula blockedSet, BasicFormula rest) {
		BCEliminator eliminator = new IncrementalQueueBasedBCEliminator();
		List<int[]> stackList = eliminator.eliminateBlockedClauses(blockedSet);
		if (stackList.size() != blockedSet.clauses.size()) {
			throw new RuntimeException("Input is not a blocked set");
		}
		if (!(stackList instanceof ArrayList<?>)) {
			stackList = new ArrayList<int[]>(stackList);
		}
		ArrayList<int[]> stack = (ArrayList<int[]>) stackList;
		
		// lock all the variables which appear in the rest
		BitSet locked = LockedProvider.lockPresentVars(rest);
		Logger.print(1, String.format("c locked %d of %d variables (%d%%)", locked.cardinality(), blockedSet.variablesCount, (100*locked.cardinality())/blockedSet.variablesCount));
		
		BasicFormula result = new BasicFormula();
		result.clauses = new ArrayList<int[]>();
		
		int lastVarId = blockedSet.variablesCount;
		int[] currentName = new int[lastVarId+1];
		int[] originalName = new int[blockedSet.variablesCount + blockedSet.clauses.size()];
		for (int i = 1; i <= blockedSet.variablesCount; i++) {
			currentName[i] = i;
			originalName[i] = i;
		}

		// encode the blocked set
		lastVarId = ReconstructionEncoder.encodeStack(stack, result, currentName, originalName, lastVarId, locked);
		result.variablesCount = lastVarId;
		// Work on the small set
		result.clauses.addAll(rest.clauses);
		return result;
	}


}
