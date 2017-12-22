package freelunch.sat.bce.eliminators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ImprovedBCEliminator extends TrivialBCEliminator implements BCEliminator {
	
	protected Map<int[], List<int[]>> reasons;
	protected List<int[]> remainingClauses;
	
	public List<int[]> getRemainingClauses() {
		return remainingClauses;
	}
	

	@Override
	public ArrayList<int[]> eliminateBlockedClauses(BasicFormula formula) {
		cindex = new ClauseIndex(formula);
		HashSet<int[]> candidates = new HashSet<int[]>();
		
		LinkedList<int[]> clauses = new LinkedList<int[]>(formula.clauses);
		Set<int[]> eliminatedClauses = new HashSet<int[]>();
		reasons = new Hashtable<int[], List<int[]>>();

		ListIterator<int[]> iter = clauses.listIterator();
		while (iter.hasNext()) {
			int[] cl = iter.next();
			if (isBlocked(cl)) {
				iter.remove();
				cindex.removeFromClauseIndex(cl);
				eliminatedClauses.add(cl);
				if (reasons.containsKey(cl)) {
					candidates.addAll(reasons.get(cl));
				}
			}
		}
		while (!candidates.isEmpty()) {
			//Logger.print(2, String.format("c BCE: candidates: %d, eliminated: %d", candidates.size(), eliminatedClauses.size()));
			Iterator<int[]> iterh = candidates.iterator();
			int[] cl = null;
			while (iterh.hasNext()) {
				int[] candClause = iterh.next();
				iterh.remove();
				if (!eliminatedClauses.contains(candClause)) {
					cl = candClause;
					break;
				}
			}
			if (cl != null && isBlocked(cl)) {
				cindex.removeFromClauseIndex(cl);
				eliminatedClauses.add(cl);
				if (reasons.containsKey(cl)) {
					candidates.addAll(reasons.get(cl));
				}
			}
		}
		remainingClauses = clauses;
		return new ArrayList<int[]>(eliminatedClauses);
	}
	
	protected boolean isBlocked(int[] clause) {
		for (int lit : clause) {
			if (literalBlocksClause(lit, clause)) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean literalBlocksClause(int lit, int[] clause) {
		int neg = -lit;
		int index = ClauseIndex.encodeLiteral(neg);
		for (int[] candidate : cindex.clauseIndex[index]) {
			if (!ClauseIndex.tautologicalResolvent(candidate, clause)) {
				if (!reasons.containsKey(candidate)) {
					reasons.put(candidate, new ArrayList<int[]>());
				}
				reasons.get(candidate).add(clause);
				return false;
			}
		}
		return true;
	}


}
