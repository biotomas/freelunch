package freelunch.sat.bce.decomposers.postprocessors;

import java.security.InvalidParameterException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.bce.utilities.ClauseIdIndex;
import freelunch.sat.bce.utilities.SparseTable;
import freelunch.sat.bce.utilities.ClauseIdIndex.Clause;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class IncrementalMaximalMover implements DecompositionPostprocessor {
	
	private long timelimit = 0;

	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		Stopwatch watch = new Stopwatch();
		BCEliminator elim = new IncrementalQueueBasedBCEliminator();
		List<int[]> stack = elim.eliminateBlockedClauses(large);
		if (stack.size() != large.clauses.size()) {
			throw new InvalidParameterException("Large is not blocked");
		}
		int clausesCount = stack.size() + small.clauses.size();
		SparseTable st = new SparseTable(2 * clausesCount);
		ClauseIdIndex cindex = new ClauseIdIndex(large.variablesCount, st);
		List<Clause> clauses = new ArrayList<Clause>();

		int lastId = 0;
		for (int i = 0; i < stack.size(); i++) {
			Clause nc = new Clause(i, stack.get(i));
			cindex.addClause(nc);
			clauses.add(nc);
			st.putAfterLabel(i, 2*i);
			lastId = i;
		}
		for (int[] cl : small.clauses) {
			if (watch.timeLimitExceeded(timelimit)) {
				break;
			}
			Clause nc = new Clause(lastId++, cl);
			clauses.add(nc);
			
			// try to insert using a given literal
			for (int lit : nc.lits) {
				cindex.makeBlockingLiteralFirst(nc, lit);
				
				Queue<Clause> cls = new ArrayDeque<Clause>();
				cls.add(nc);
				
				while (!cls.isEmpty()) {
					Clause c = cls.poll();
					if (c == nc) {
						
					}
				}
				
				//Clause reason = cindex.lastWithConflict(lit, nc);
				//int label = st.getLabel(reason.id);
				//Set<Clause> remove = cindex.clausesWithBlitConflict(nc, label);
				
			}
		}
/*TODO FIXME
		
		while (!clausesToInsert.isEmpty()) {
			Clause c = clausesToInsert.poll();
			
			Clause lowestClause = null;
			int lowestLabel = Integer.MAX_VALUE;
			int bestLiteral = 0;
			
			for (int lit : c.lits) {
				Clause reason = cindex.lastWithConflict(lit, c);
				if (reason == null) {
					// c is blocked
					lowestClause = null;
					bestLiteral = lit;
					break;
				}
				int label = st.getLabel(reason.id);
				if (label < lowestLabel) {
					lowestLabel = label;
					lowestClause = reason;
					bestLiteral = lit;
				}
			}
			cindex.makeBlockingLiteralFirst(c, bestLiteral);
			if (lowestClause != null) {
				Set<Clause> remove = cindex.clausesWithBlitConflict(c, lowestLabel);
				for (Clause cr : remove) {
					st.remove(cr.id);
					removedClauses.add(cr);
				}
				st.putAfterLabel(c.id, lowestLabel);
			} else {
				st.addToEnd(c.id);
			}
		}
		System.out.println("Removed clauses: " + removedClauses.size());

		large.clauses.clear();
		small.clauses.clear();
		for (Clause c : clauses) {
			if (st.hasLabel(c.id)) {
				large.clauses.add(c.lits);
			} else {
				small.clauses.add(c.lits);
			}
		}
		*/
		return 0;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}

}
