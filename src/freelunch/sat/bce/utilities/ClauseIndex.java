package freelunch.sat.bce.utilities;

import java.util.HashSet;
import java.util.Set;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ClauseIndex {

	public Set<int[]>[] clauseIndex;

	public static int encodeLiteral(int lit) {
		return lit > 0 ? 2*lit : -2*lit - 1;
	}

	public static int decodeLiteral(int index) {
		return index % 2 == 0 ? index / 2 : -(index+1)/2; 
	}

	@SuppressWarnings("unchecked")
	public ClauseIndex(BasicFormula formula) {
		clauseIndex = new Set[2 + 2*formula.variablesCount];
		for (int i = 1; i < 2 + 2*formula.variablesCount; i++) {
			clauseIndex[i] = new HashSet<int[]>();
		}
		for (int[] clause : formula.clauses) {
			for (int lit : clause) {
				int index = encodeLiteral(lit);
				clauseIndex[index].add(clause);
			}
		}
	}
	
	public Set<int[]> getClausesWith(int lit) {
		int index = encodeLiteral(lit);
		return clauseIndex[index];
	}
	
	public void addClause(int[] clause) {
		for (int lit : clause) {
			int index = encodeLiteral(lit);
			clauseIndex[index].add(clause);
		}
	}
	
	public void removeFromClauseIndex(int[] clause) {
		for (int lit : clause) {
			int index = encodeLiteral(lit);
			clauseIndex[index].remove(clause);
		}
	}
	
	public boolean isBlocked(int[] clause) {
		for (int lit : clause) {
			if (literalBlocksClause(lit, clause)) {
				makeBlockingLiteralFirst(clause, lit);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return null if this clause is blocked or a reason
	 * why it is not blocked.
	 * @param clause
	 * @return
	 */
	public int[] reasonBlocked(int[] clause) {
		for (int lit : clause) {
			if (literalBlocksClause(lit, clause)) {
				makeBlockingLiteralFirst(clause, lit);
				return null;
			}
		}
		// find a reason
		int neg = -clause[0];
		int index = ClauseIndex.encodeLiteral(neg);
		for (int[] candidate : clauseIndex[index]) {
			if (!tautologicalResolvent(candidate, clause)) {
				return candidate;
			}
		}
		throw new RuntimeException("invalid state");
	}

	/**
	 * Given the clauses in this clause index are a blocked set
	 * check if the given clause can be added without violating
	 * the blocked property. 
	 * 
	 * This method assumes that the clauses in the index have 
	 * their blocking literal as the first literal
	 * @param clause
	 * @return true if clause can be added.
	 */
	public boolean addableToBlocked(int[] clause) {
		for (int lit : clause) {
			if (!literalCouldBlockClause(lit, clause)) {
				return false;
			}
		}
		//makeBlockingLiteralFirst(clause, lit);
		return true;
	}

	public boolean literalCouldBlockClause(int lit, int[] clause) {
		int neg = -lit;
		int index = ClauseIndex.encodeLiteral(neg);
		for (int[] candidate : clauseIndex[index]) {
			// check if the candidate has another blit
			if (candidate[0] != neg) {
				continue;
			}
			if (!tautologicalResolvent(candidate, clause)) {
				/*/ check if candidate can have another blocking literal - TOO EXPENSIVE
				for (int i = 1; i < candidate.length; i++) {
					if (literalBlocksClause(candidate[i], candidate)) {
						makeBlockingLiteralFirst(candidate, candidate[i]);
						continue;
					}
				}*/
				return false;
			}
		}
		return true;
	}
	
	public int getVariableScore(int var) {
		int pos = encodeLiteral(var);
		int neg = encodeLiteral(-var);
		return Math.min(clauseIndex[pos].size(), clauseIndex[neg].size());
	}
	
	public int getScore(int[] clause) {
		int score = Integer.MAX_VALUE;
		for (int lit : clause) {
			int litscore = 0;
			int neg = -lit;
			int index = ClauseIndex.encodeLiteral(neg);
			litscore = clauseIndex[index].size();
			/*
			for (int[] candidate : clauseIndex[index]) {
				if (!tautologicalResolvent(candidate, clause)) {
					// check if the candidate has another blit
					if (candidate[0] != neg) {
						litscore += 1;
					} else {
						litscore += 3;
					}
				}
			}
			*/
			score = Math.min(litscore, score);
		}
		return score;
	}
	
	public void makeBlockingLiteralFirst(int[] clause, int blit) {
		for (int i = 0; i < clause.length; i++) {
			if (clause[i] == blit) {
				int tmp = clause[0];
				clause[0] = blit;
				clause[i] = tmp;
				break;
			}
		}
	}

	public boolean literalBlocksClause(int lit, int[] clause) {
		int neg = -lit;
		int index = ClauseIndex.encodeLiteral(neg);
		for (int[] candidate : clauseIndex[index]) {
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
	
	public static int[] resolve(int[] c1, int[] c2) {
		Set<Integer> cls = new HashSet<Integer>();
		int pairs = 0;
		for (int l : c1) cls.add(l);
		for (int l : c2) cls.add(l);
		for (int l1 : c1) {
			for (int l2 : c2) {
				if (l1 == -l2) {
					cls.remove(l1);
					cls.remove(-l1);
					pairs++;
				}
			}
		}
		if (pairs != 1) {
			return null;
		}
		int[] res = new int[cls.size()];
		int i = 0;
		for (int x : cls) {
			res[i] = x;
			i++;
		}
		return res;
	}

	public static boolean tautologicalResolvent(int[] c1, int[] c2) {
		int pairs = 0;
		for (int l1 : c1) {
			for (int l2 : c2) {
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
