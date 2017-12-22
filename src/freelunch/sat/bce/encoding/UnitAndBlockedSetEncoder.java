package freelunch.sat.bce.encoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class UnitAndBlockedSetEncoder {

	/**
	 * Encode two blocked sets into a formula which consists
	 * of a single unit clause (first clause in the result) and
	 * a blocked set.
	 * @param l
	 * @param r
	 * @return
	 */
	public static BasicFormula encode(BasicFormula l, BasicFormula r) {
		BCEliminator elim = new IncrementalQueueBasedBCEliminator();
		List<int[]> stack = elim.eliminateBlockedClauses(l);
		if (stack.size() != l.clauses.size()) {
			throw new IllegalArgumentException("l is not blocked");
		}
		l.clauses = stack;
		BasicFormula result = new BasicFormula();
		result.variablesCount = l.variablesCount + 1;
		int newVar = l.variablesCount + 1;
		result.clauses = new ArrayList<int[]>();
		result.clauses.add(new int[]{newVar});
		for (int[] cl : r.clauses) {
			int[] ncl = Arrays.copyOf(cl, cl.length+1);
			ncl[cl.length] = ncl[0];
			ncl[0] = -newVar;
			result.clauses.add(ncl);
		}
		result.clauses.addAll(l.clauses);
		return result;
	}

}
