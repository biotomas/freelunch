package freelunch.sat.bce.encoding;

import java.util.ArrayList;
import java.util.Arrays;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class RibbonEncoder {

	public static BasicFormula encodeReconstruction(BasicFormula blockedSet, BasicFormula rest) {
		BasicFormula result = new BasicFormula();
		result.variablesCount = blockedSet.variablesCount + blockedSet.clauses.size() + rest.clauses.size();
		result.clauses = new ArrayList<int[]>();
		
		BCEliminator eliminator = new IncrementalQueueBasedBCEliminator();
		ArrayList<int[]> stack = eliminator.eliminateBlockedClauses(blockedSet);
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
		lastVarId = ReconstructionEncoder.encodeStack(stack, result, currentName, originalName, lastVarId);
		
		stack = eliminator.eliminateBlockedClauses(rest);
		if (stack.size() != rest.clauses.size()) {
			throw new RuntimeException("Input is not a blocked set");
		}

		int[] lastNameInLargeSet = Arrays.copyOf(currentName, currentName.length);
		for (int i = 1; i <= blockedSet.variablesCount; i++) {
			currentName[i] = i;
			originalName[i] = i;
		}
		
		// encode the rest
		BasicFormula restClauses = new BasicFormula();
		restClauses.clauses = new ArrayList<int[]>();
		lastVarId = ReconstructionEncoder.encodeStack(stack, restClauses, currentName, originalName, lastVarId);

		// unify the ends on the big and small set
		for (int[] cl : restClauses.clauses) {
			for (int i = 0; i < cl.length; i++) {
				int l = cl[i];
				int v = Math.abs(l);
				int origname = originalName[v];
				if (currentName[origname] == v) {
					cl[i] = l > 0 ? lastNameInLargeSet[origname] : -lastNameInLargeSet[origname];
				}
			}
		}
		result.clauses.addAll(restClauses.clauses);
		return result;
	}

}
