package freelunch.sat.bce.eliminators;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.satLifter.Stopwatch;

public class TrivialBCEliminator implements BCEliminator {
	
	public ClauseIndex cindex;
	protected int timelimit = 0;

	@Override
	public ArrayList<int[]> eliminateBlockedClauses(CnfSatFormula formula) {
		Stopwatch watch = new Stopwatch();
		cindex = new ClauseIndex(formula);
		LinkedList<int[]> clauses = new LinkedList<int[]>(formula.clauses);
		ArrayList<int[]> eliminatedClauses = new ArrayList<int[]>();
		boolean eliminated = true;
		while (eliminated && clauses.size() > 0) {
			if (watch.timeLimitExceeded(timelimit)) {
				return null;
			}
			Logger.print(2, "c trivial BCE round clauses:" + clauses.size());
			eliminated = false;
			ListIterator<int[]> iter = clauses.listIterator();
			while (iter.hasNext()) {
				int[] cl = iter.next();
				if (cindex.isBlocked(cl)) {
					iter.remove();
					cindex.removeFromClauseIndex(cl);
					eliminatedClauses.add(cl);
					eliminated = true;
				}
			}
		}
		return eliminatedClauses;
	}
	
	public static boolean checkBlockedSet(CnfSatFormula formula) {
		ClauseIndex cindex = new ClauseIndex(formula);
		for (int[] cl : formula.clauses) {
			if (!cindex.literalBlocksClause(cl[0], cl)) {
				return false;
			}
			cindex.removeFromClauseIndex(cl);
		}
		return true;
	}

	@Override
	public void setTimeLimit(int seconds) {
		this.timelimit = seconds;
	}
	
	
}
