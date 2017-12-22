package freelunch.sat.bce.eliminators;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Queue;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class IncrementalQueueBasedBCEliminator implements BCEliminator {
	
	private long timelimit = 0;

	@Override
	public ArrayList<int[]> eliminateBlockedClauses(BasicFormula formula) {
		Stopwatch watch = new Stopwatch();
		Queue<Integer> litsToCheck = new ArrayDeque<Integer>();
		BitSet inStack = new BitSet(2+2*formula.variablesCount);
		ArrayList<int[]> blockedClauses = new ArrayList<int[]>();
		ClauseIndex cindex = new ClauseIndex(formula);
		
		for (int[] cl : formula.clauses) {
			if (cindex.isBlocked(cl)) {
				for (int l : cl) {
					if (!inStack.get(ClauseIndex.encodeLiteral(-l))) {
						litsToCheck.offer(-l);
						inStack.set(ClauseIndex.encodeLiteral(-l));
					}
				}
				cindex.removeFromClauseIndex(cl);
				blockedClauses.add(cl);
			}
		}

		for (int i = 1; i <= formula.variablesCount; i++) {
			if (!inStack.get(ClauseIndex.encodeLiteral(i))) {
				litsToCheck.offer(i);
				inStack.set(ClauseIndex.encodeLiteral(i));
			}
			if (!inStack.get(ClauseIndex.encodeLiteral(-i))) {
				litsToCheck.offer(-i);
				inStack.set(ClauseIndex.encodeLiteral(-i));
			}
		}
		
		while (!litsToCheck.isEmpty()) {
			if (watch.timeLimitExceeded(timelimit)) {
				return null;
			}
			int lit = litsToCheck.poll();
			inStack.clear(ClauseIndex.encodeLiteral(lit));
			
			Iterator<int[]> iter = cindex.clauseIndex[ClauseIndex.encodeLiteral(lit)].iterator(); 
			while (iter.hasNext()) {
				int[] clause = iter.next();
				if (cindex.literalBlocksClause(lit, clause)) {
					cindex.makeBlockingLiteralFirst(clause, lit);
					blockedClauses.add(clause);
					for (int l : clause) {
						if (!inStack.get(ClauseIndex.encodeLiteral(-l))) {
							litsToCheck.offer(-l);
							inStack.set(ClauseIndex.encodeLiteral(-l));
						}
					}
					iter.remove();
					cindex.removeFromClauseIndex(clause);
				}
			}
		}
		return blockedClauses;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}
}
