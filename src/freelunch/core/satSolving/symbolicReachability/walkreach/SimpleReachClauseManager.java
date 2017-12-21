/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.satSolving.symbolicReachability.walkreach;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import freelunch.core.satSolving.symbolicReachability.walkreach.model.ReachClause;


public class SimpleReachClauseManager {
	
	private List<ReachClause>[] positiveOccurenceList;
	private List<ReachClause>[] negativeOccurenceList;
	private ArrayList<ReachClause> unsatClauses;
	private int unsatClauseTotalWeight = 0;
	private List<ReachClause> allClauses;
	private int[] assignment = null;
	private Random random;
	
	@SuppressWarnings("unchecked")
	public SimpleReachClauseManager(int variables, Random random) {
		this.random = random;
		unsatClauses = new ArrayList<ReachClause>();
		allClauses = new ArrayList<ReachClause>();
		positiveOccurenceList = new List[variables+1];
		negativeOccurenceList = new List[variables+1];
		for (int i = 1; i <= variables; i++) {
			positiveOccurenceList[i] = new ArrayList<ReachClause>();
			negativeOccurenceList[i] = new ArrayList<ReachClause>();
		}
	}

	public void addClause(ReachClause clause) {
		allClauses.add(clause);
		for (int literal : clause.getLiterals()) {
			if (literal > 0) {
				positiveOccurenceList[literal].add(clause);
			} else {
				negativeOccurenceList[-literal].add(clause);
			}
		}
	}

	/**
	 * Get a random unsatisfied clause. Each clause has equal probability
	 * to be selected.
	 * @return unsat clause or null if all clauses are satisfied
	 */
	public ReachClause getRandomUnsatClause() {
		if (unsatClauses.isEmpty()) {
			return null;
		}
		return unsatClauses.get(random.nextInt(unsatClauses.size()));
	}
	
	/**
	 * Get a random unsatisfied clause. Clauses with bigger weights have
	 * a bigger probability to be selected.
	 * @return unsat clause or null if are clauses are satisfied
	 */
	public ReachClause getWeightedRandomUnsatClause() {
        if (unsatClauses.isEmpty()) {
            return null;
        }
        int sum = 0;
        int goal = random.nextInt(unsatClauseTotalWeight);
        for (ReachClause clause : unsatClauses) {
            sum += clause.getWeight();
            if (sum <= goal) {
                return clause;
            }
        }
        // This should not happen
        return unsatClauses.get(0);
	}

	/**
	 * Sum of the weights of clauses that get satisfied by setting
	 * the literal to true.
	 * @param literal
	 * @return score
	 */
	public int computeMakeScore(int literal) {
		List<ReachClause>[] satClauseList = positiveOccurenceList;
		int var = literal;		
		if (literal < 0) {
			var = -literal;
			satClauseList = negativeOccurenceList;
		}
		int makeCount = 0;
		for (ReachClause clause : satClauseList[var]) {
			if (clause.getSatLiterals() == 0) {
				makeCount += clause.getWeight();
			}
		}
		return makeCount;
	}

	/**
	 * Sum of the weights of clauses that get unsatisfied by setting
	 * the literal to true
	 * @param literal
	 * @return score
	 */
	public int computeBreakScore(int literal) {
		List<ReachClause>[] unsatClauseList = negativeOccurenceList;
		int var = literal;
		if (literal < 0) {
			var = -literal;
			unsatClauseList = positiveOccurenceList;
		}
		int breakCount = 0;
		for (ReachClause clause : unsatClauseList[var]) {
			if (clause.getSatLiterals() == 1) {
				breakCount += clause.getWeight();
			}
		}
		return breakCount;
	}

	public void setAssignment(int[] assignment) {
		this.assignment = assignment;
		unsatClauses.clear();
		unsatClauseTotalWeight = 0;
		for (ReachClause clause : allClauses) {
			updateClauseStatus(clause);
		}
	}
	
	private void updateClauseStatus(ReachClause clause) {
		int satLits = 0;
		for (int lit : clause.getLiterals()) {
			int var = Math.abs(lit);
			if (assignment[var]*lit > 0) {
				satLits++;
			}
		}
		clause.setSatLiterals(satLits);
		if (satLits == 0) {
			putClauseToUnsatList(clause);
		}
	}

	public void makeLiteralTrue(int literal) {
		int var = literal;		
		List<ReachClause>[] satClauseList = positiveOccurenceList;
		List<ReachClause>[] unsatClauseList = negativeOccurenceList;
		
		if (literal < 0) {
			var = -literal;
			satClauseList = negativeOccurenceList;
			unsatClauseList = positiveOccurenceList;
		}
		
		if (assignment[var] * literal > 0) {
			// the value of this variable is not changed
			return;
		}
		
		assignment[var] = literal;
		
		for (ReachClause clause : satClauseList[var]) {
			int satLits = clause.getSatLiterals();
			clause.setSatLiterals(satLits+1);
			if (satLits == 0) {
				removeClauseFromUnsatList(clause);					
			}
		}
		
		for (ReachClause clause : unsatClauseList[var]) {
			int satLits = clause.getSatLiterals();
			clause.setSatLiterals(satLits-1);
			if (satLits == 1) {
				putClauseToUnsatList(clause);					
			}
		}
	}
	
	private void putClauseToUnsatList(ReachClause clause) {
		clause.setUnsatClauseIndex(unsatClauses.size());		
		unsatClauses.add(clause);
		unsatClauseTotalWeight += clause.getWeight();
	}
	
	private void removeClauseFromUnsatList(ReachClause clause) {
		int index = clause.getUnsatClauseIndex();
		int last = unsatClauses.size() - 1;
		ReachClause lastClause = unsatClauses.get(last);
		lastClause.setUnsatClauseIndex(index);
		unsatClauses.set(index, lastClause);
		unsatClauses.remove(last);
		unsatClauseTotalWeight -= clause.getWeight();
	}

	public List<ReachClause> getAllUnsatClauses() {
		return unsatClauses;
	}

	public List<ReachClause> getAllClauses() {
		return allClauses;
	}

}
