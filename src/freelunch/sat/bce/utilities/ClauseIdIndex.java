package freelunch.sat.bce.utilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClauseIdIndex {
	
	public static class Clause {
		public int[] lits;
		public int id;

		public Clause(int id, int[] lits) {
			this.id = id;
			this.lits = lits;
		}
		
		@Override
		public int hashCode() {
			return id;
		}
		
		@Override
		public String toString() {
			return String.format("%s(%d)", Arrays.toString(lits), id);
		}
	}

	private Set<Clause>[] clauseIndex;
	private SparseTable st;

	public static int encodeLiteral(int lit) {
		return lit > 0 ? 2*lit : -2*lit - 1;
	}

	public static int decodeLiteral(int index) {
		return index % 2 == 0 ? index / 2 : -(index+1)/2; 
	}

	@SuppressWarnings("unchecked")
	public ClauseIdIndex(int variables, SparseTable st) {
		this.st = st;
		clauseIndex = new Set[2 + 2*variables];
		for (int i = 1; i < 2 + 2*variables; i++) {
			clauseIndex[i] = new HashSet<Clause>();
		}
	}
	
	public void addClause(Clause clause) {
		for (int lit : clause.lits) {
			int index = encodeLiteral(lit);
			clauseIndex[index].add(clause);
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public void removeFromClauseIndex(int[] clause) {
		for (int lit : clause) {
			int index = encodeLiteral(lit);
			clauseIndex[index].remove(clause);
		}
	}
	
	/**
	 * Get all the clauses that have a blocking literal that is
	 * a negation of a literal in c and their label is lower than
	 * max label
	 * @param c
	 * @return
	 */
	public Set<Clause> clausesWithBlitConflict(Clause c, int maxLabel) {
		Set<Clause> result = new HashSet<Clause>();
		for (int lit : c.lits) {
			int neg = -lit;
			int index = ClauseIdIndex.encodeLiteral(neg);
			for (Clause candidate : clauseIndex[index]) {
				if (st.getLabel(candidate.id) <= maxLabel && candidate.lits[0] == neg && !tautologicalResolvent(c, candidate)) {
					result.add(candidate);
				}
			}
		}
		return result;
	}
	
	public int getScoreByFirstBlitConflict(Clause clause) {
		int score = 0;
		for (int lit : clause.lits) {
			Clause first = firstWithBlitConflictId(lit, clause);
			if (first != null) {
				score += first.id;
			}
		}
		return score;
	}
	
	/**
	 * Get the first clause (smallest id) in the stack that 
	 * has a blocking literal conflict with the given clause.
	 * @param lit
	 * @return null if no such clause
	 */
	public Clause firstWithBlitConflictId(int lit, Clause clause) {
		int neg = -lit;
		int index = ClauseIdIndex.encodeLiteral(neg);
		int minId = Integer.MAX_VALUE;
		Clause result = null;
		
		for (Clause candidate : clauseIndex[index]) {
			if (candidate.lits[0] != neg || candidate.id > minId) {
				continue;
			}
			if (!tautologicalResolvent(candidate, clause)) {
				minId = candidate.id;
				result = candidate;
			}
		}
		return result;
	}

	/**
	 * Get the first clause (smallest label) in the stack that 
	 * has a blocking literal conflict with the given clause.
	 * @param lit
	 * @return null if no such clause
	 */
	public Clause firstWithBlitConflict(int lit, Clause clause) {
		int neg = -lit;
		int index = ClauseIdIndex.encodeLiteral(neg);
		int minlabel = Integer.MAX_VALUE;
		Clause result = null;
		
		for (Clause candidate : clauseIndex[index]) {
			if (candidate.lits[0] != neg || st.getLabel(candidate.id) > minlabel) {
				continue;
			}
			if (!tautologicalResolvent(candidate, clause)) {
				minlabel = st.getLabel(candidate.id);
				result = candidate;
			}
		}
		return result;
	}
	
	public void makeBlockingLiteralFirst(Clause clause, int blit) {
		for (int i = 0; i < clause.lits.length; i++) {
			if (clause.lits[i] == blit) {
				int tmp = clause.lits[0];
				clause.lits[0] = blit;
				clause.lits[i] = tmp;
				break;
			}
		}
	}

	public boolean literalBlocksClause(int lit, Clause clause) {
		int neg = -lit;
		int index = ClauseIdIndex.encodeLiteral(neg);
		for (Clause candidate : clauseIndex[index]) {
			if (!tautologicalResolvent(candidate, clause)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean tautology(int[] cl) {
		for (int i = 0; i < cl.length; i++) {
			for (int j = i+1; j < cl.length; j++) {
				if (cl[i] == -cl[j]) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean tautologicalResolvent(Clause c1, Clause c2) {
		int pairs = 0;
		for (int l1 : c1.lits) {
			for (int l2 : c2.lits) {
				if (l1 == -l2) {
					pairs++;
				}
				if (pairs > 1) {
					return true;
				}
			}
		}
		return false;
	}	

}
