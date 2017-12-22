package freelunch.sat.bce.eliminators;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Stack;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class SimplifiedArminsBCEliminator implements BCEliminator {
	
	private long timelimit = 0;
	
	@Override
	public ArrayList<int[]> eliminateBlockedClauses(BasicFormula formula) {
		Stopwatch watch = new Stopwatch();
		Stack<Integer> litsToCheck = new Stack<Integer>();
		BitSet notInStack = new BitSet(2+2*formula.variablesCount);
		
		for (int i = 1; i <= formula.variablesCount; i++) {
			litsToCheck.add(i);
			litsToCheck.add(-i);
		}
		
		ArrayList<int[]> blockedClauses = new ArrayList<int[]>();
		ClauseIndex cindex = new ClauseIndex(formula);
		
		while (!litsToCheck.isEmpty()) {
			if (watch.timeLimitExceeded(timelimit)) {
				return null;
			}
			int lit = litsToCheck.pop();
			notInStack.set(ClauseIndex.encodeLiteral(lit));
			
			Iterator<int[]> iter = cindex.clauseIndex[ClauseIndex.encodeLiteral(lit)].iterator(); 
			while (iter.hasNext()) {
				int[] clause = iter.next();
				if (cindex.literalBlocksClause(lit, clause)) {
					cindex.makeBlockingLiteralFirst(clause, lit);
					blockedClauses.add(clause);
					for (int l : clause) {
						if (notInStack.get(ClauseIndex.encodeLiteral(-l))) {
							litsToCheck.add(-l);
							notInStack.flip(ClauseIndex.encodeLiteral(-l));
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
