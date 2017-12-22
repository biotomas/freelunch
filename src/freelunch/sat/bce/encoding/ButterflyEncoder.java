package freelunch.sat.bce.encoding;

import java.util.ArrayList;
import java.util.BitSet;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ButterflyEncoder {
	
	public static BasicFormula encodeReconstruction(BasicFormula blockedSet, BasicFormula rest, BitSet locked) {
		BasicFormula result = new BasicFormula();
		// upper bound on variables
		result.variablesCount = blockedSet.variablesCount + blockedSet.clauses.size() + rest.clauses.size();
		result.clauses = new ArrayList<int[]>();
		
		Logger.print(1, "c starting BCE on large set");
		BCEliminator eliminator = new IncrementalQueueBasedBCEliminator();
		ArrayList<int[]> stack = eliminator.eliminateBlockedClauses(blockedSet);
		Logger.print(1, "c finished BCE on large set");

		if (stack.size() != blockedSet.clauses.size()) {
			throw new RuntimeException("Input is not a blocked set");
		}
		
		int lastVarId = blockedSet.variablesCount;
		int[] currentName = new int[lastVarId+1];
		int[] originalName = new int[1 + result.variablesCount];
		for (int i = 1; i <= blockedSet.variablesCount; i++) {
			currentName[i] = i;
			originalName[i] = i;
		}
		
		// encode the blocked set
		lastVarId = ReconstructionEncoder.encodeStack(stack, result, currentName, originalName, lastVarId, locked);
		// schmetterling on the big set
		for (int[] cl : result.clauses) {
			for (int i = 0; i < cl.length; i++) {
				int l = cl[i];
				int v = Math.abs(l);
				int origname = originalName[v];
				if (!locked.get(origname) && currentName[origname] == v) {
					cl[i] = l > 0 ? origname : -origname;
				}
			}
		}

		// Work on the small set
		Logger.print(1, "c starting BCE on small set");
		stack = eliminator.eliminateBlockedClauses(rest);
		Logger.print(1, "c finished BCE on small set");
		if (stack.size() != rest.clauses.size()) {
			Logger.print(1, "c The small set is not blocked");
			result.clauses.addAll(rest.clauses);
			return result;
		}

		for (int i = 1; i <= blockedSet.variablesCount; i++) {
			currentName[i] = i;
			originalName[i] = i;
		}
		
		// encode the rest
		lastVarId = ReconstructionEncoder.encodeStack(stack, result, currentName, originalName, lastVarId, locked);
		// schmetterling on the small set
		for (int[] cl : result.clauses) {
			for (int i = 0; i < cl.length; i++) {
				int l = cl[i];
				int v = Math.abs(l);
				int origname = originalName[v];
				if (!locked.get(origname) && currentName[origname] == v) {
					cl[i] = l > 0 ? origname : -origname;
				}
			}
		}
		result.variablesCount = lastVarId;
		return result;
	}
}
