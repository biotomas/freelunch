package freelunch.sat.bce.eliminators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.utilities.BinaryHeap;
import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ArminsBCEliminator extends TrivialBCEliminator implements BCEliminator {
	
	class LiteralOccurrence implements Comparable<LiteralOccurrence> {
		private int literal;
		public int occurrences;
		
		public LiteralOccurrence(int literal) {
			this.literal = literal;
			occurrences = 0;
		}
		
		@Override
		public int compareTo(LiteralOccurrence o) {
			return this.occurrences - o.occurrences;
		}
		
		@Override
		public int hashCode() {
			return literal;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LiteralOccurrence) {
				return ((LiteralOccurrence)obj).literal == literal;
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return String.format("%d:%d", literal, occurrences);
		}
	}

	private BinaryHeap<LiteralOccurrence> occurrenceHeap;
	private LiteralOccurrence[] literals;
	
	protected void updateOccurrence(int literal) {
		LiteralOccurrence lo = literals[ClauseIndex.encodeLiteral(literal)];
		lo.occurrences--;
		occurrenceHeap.update(lo);
	}

	@Override
	public ArrayList<int[]> eliminateBlockedClauses(BasicFormula formula) {
		Stopwatch watch = new Stopwatch();
		literals = new LiteralOccurrence[2+2*formula.variablesCount];
		List<LiteralOccurrence> occurrenceList = new ArrayList<LiteralOccurrence>();
		for (int i = 1; i < literals.length; i++) {
			literals[i] = new LiteralOccurrence(ClauseIndex.decodeLiteral(i));
			occurrenceList.add(literals[i]);
		}
		// count the occurrences
		for (int[] clause : formula.clauses) {
			for (int lit : clause) {
				literals[ClauseIndex.encodeLiteral(lit)].occurrences++;
			}
		}
		// initialize the occurrence heap
		occurrenceHeap = new BinaryHeap<LiteralOccurrence>(occurrenceList);
		
		
		ArrayList<int[]> blockedClauses = new ArrayList<int[]>();
		cindex = new ClauseIndex(formula);
		Set<int[]> clauseSet = new HashSet<int[]>(formula.clauses);
		
		LiteralOccurrence lo = occurrenceHeap.removeMinimum();
		while (lo != null) {
			if (watch.timeLimitExceeded(timelimit)) {
				return null;
			}
			int lit = lo.literal;
			Iterator<int[]> iter = cindex.clauseIndex[ClauseIndex.encodeLiteral(lit)].iterator(); 
			while (iter.hasNext()) {
				int[] clause = iter.next();
				if (cindex.literalBlocksClause(lit, clause)) {
					cindex.makeBlockingLiteralFirst(clause, lit);
					blockedClauses.add(clause);
					for (int l : clause) {
						updateOccurrence(l);
						occurrenceHeap.addOrUpdate(literals[ClauseIndex.encodeLiteral(-l)]);
					}
					clauseSet.remove(clause);
					iter.remove();
					cindex.removeFromClauseIndex(clause);
				}
			}
			lo = occurrenceHeap.removeMinimum();
		}
		//formula.clauses = new ArrayList<int[]>(clauseSet);
		return blockedClauses;
	}

}
