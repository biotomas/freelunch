package freelunch.sat.bce.encoding;

import java.util.ArrayList;
import java.util.BitSet;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.bce.utilities.LockedProvider;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ReconstructionEncoder {
	
	public static BasicFormula encodeReconstruction(BasicFormula blockedSet, BasicFormula rest, boolean closeCycle, BitSet locked) {
		BasicFormula result = new BasicFormula();
		result.variablesCount = blockedSet.variablesCount + blockedSet.clauses.size() ;
		result.clauses = new ArrayList<int[]>();
		
		BCEliminator eliminator = new IncrementalQueueBasedBCEliminator();
		ArrayList<int[]> stack = eliminator.eliminateBlockedClauses(blockedSet);

		if (stack.size() != blockedSet.clauses.size()) {
			throw new RuntimeException("Input is not a blocked set");
		}
		
		int lastVarId = blockedSet.variablesCount;
		int[] currentName = new int[lastVarId+1];
		int[] originalName = new int[1 + result.variablesCount];
		for (int i = 1; i <= lastVarId; i++) {
			currentName[i] = i;
			originalName[i] = i;
		}
		
		// encode the blocked set
		lastVarId = encodeStack(stack, result, currentName, originalName, lastVarId, locked);
		result.variablesCount = lastVarId;
		
		// encode the rest
		if (closeCycle) {
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
			result.clauses.addAll(rest.clauses);			
		} else { 
			for (int[] clause : rest.clauses) {
				int[] nclause = new int[clause.length];
				for (int i = 0; i < clause.length; i++) {
					int l = clause[i];
					int v = Math.abs(l);
					nclause[i] = l > 0 ? currentName[v] : -currentName[v];
				}
				result.clauses.add(nclause);
			}
		}
		return result;
	}
	
	protected static int encodeStack(ArrayList<int[]> stack, BasicFormula outFormula, int[] currentName, int[] originalName, int lastVarId) {
		return encodeStack(stack, outFormula, currentName, originalName, lastVarId, LockedProvider.lockNone(lastVarId));
	}
	
	protected static int encodeStack(ArrayList<int[]> stack, BasicFormula outFormula, int[] currentName, int[] originalName, int lastVarId, BitSet locked) {
		int lastBlit = 0;
		for (int i = stack.size() - 1; i >= 0; i--) {
			int[] clause = stack.get(i);
			
			//debug code
			//System.out.println(Arrays.toString(clause));
			int[] nclause = new int[clause.length];
			int blit = clause[0];
			int bvar = Math.abs(blit);
			int bval = blit > 0 ? 1 : -1;
			int previousName = currentName[bvar];
			// Armins suggestion - consecutive blocking literals
			boolean newVersion = (lastBlit != blit) && !locked.get(bvar); 
			if (newVersion) {
				lastVarId++;
				currentName[bvar] = lastVarId;
				originalName[lastVarId] = bvar;
			}
			for (int j = 0; j < clause.length; j++) {
				int lit = clause[j];
				int val = lit > 0 ? 1 : -1;
				int var = Math.abs(lit);
				nclause[j] = val * currentName[var];
				// if a not blocking literal is true then the blocking literal
				// must have the same value as previously.
				if (j > 0 && newVersion) {
					outFormula.clauses.add(new int[] {-val * currentName[var], - bval * currentName[bvar], bval * previousName});
				}
			}
			outFormula.clauses.add(nclause);
			if (newVersion) {
				outFormula.clauses.add(new int[] {- bval * previousName, bval * currentName[bvar]});
			}
			lastBlit = blit;
		}
		return lastVarId;
	}
	
}
