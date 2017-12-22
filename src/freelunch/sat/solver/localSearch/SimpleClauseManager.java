/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.sat.solver.localSearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SimpleClauseManager {
	
	private ArrayList<List<LSClause>> positiveOccurenceList;
	private ArrayList<List<LSClause>> negativeOccurenceList;
	private int variables = 1; //variables are indexed from 1
	private Queue<LSClause> unsatClauses;
	private List<LSClause> clausesList;
	private int[] assignment = null;
	
	public SimpleClauseManager() {
		unsatClauses = new ArrayDeque<LSClause>();
		clausesList = new ArrayList<LSClause>();
		positiveOccurenceList = new ArrayList<List<LSClause>>();
		// dummy variable 0
		positiveOccurenceList.add(new ArrayList<LSClause>());
		negativeOccurenceList = new ArrayList<List<LSClause>>();
        // dummy variable 0
        negativeOccurenceList.add(new ArrayList<LSClause>());
	}
	
	public List<LSClause> getClauseList() {
		return clausesList;
	}
	
	public void setVariablesCount(int howMany) {
        for (int i = variables; i <= howMany; i++) {
            positiveOccurenceList.add(new ArrayList<LSClause>());
            negativeOccurenceList.add(new ArrayList<LSClause>());
        }
        variables = howMany;
	}
	
	public int getVariableCount() {
		return variables;
	}

	public void addClause(LSClause clause) {
		clausesList.add(clause);
		registerClause(clause);
	}
	
	/**
	 * Register the clause in the occurrence lists
	 * @param clause
	 */
	private void registerClause(LSClause clause) {
        for (int literal : clause.getLiterals()) {
            if (literal > 0) {
                positiveOccurenceList.get(literal).add(clause);
            } else {
                negativeOccurenceList.get(-literal).add(clause);
            }
        }
	}
	
	/**
	 * Return an UNSAT clause or null if all clauses are SAT
	 * @return
	 */
	public LSClause getUnsatClause() {
		while (!unsatClauses.isEmpty()) {
			LSClause cl = unsatClauses.poll();
			cl.inqueue = false;
			if (cl.getSatLiterals() == 0) {
				return cl;				
			}
		}
		return null;
	}
	
	public List<LSClause> getAllUnsatClauses() {
		List<LSClause> cls = new ArrayList<LSClause>();
		LSClause c = getUnsatClause();
		while (c != null) {
			cls.add(c);
			c = getUnsatClause();
		}
		return cls;
	}
	
	private void putClauseToUnsatList(LSClause clause) {
		if (clause.inqueue) {
			return;
		}
		clause.inqueue = true;
		unsatClauses.add(clause);
	}
	
	public int computeMakecount(int literal) {
		ArrayList<List<LSClause>> satClauseList = positiveOccurenceList;
		int var = literal;		
		if (literal < 0) {
			var = -literal;
			satClauseList = negativeOccurenceList;
		}
		int makeCount = 0;
		for (LSClause clause : satClauseList.get(var)) {
			if (clause.getSatLiterals() == 0) {
				makeCount++;
			}
		}
		return makeCount;
	}

	public int computeBreakCount(int literal) {
		ArrayList<List<LSClause>> unsatClauseList = negativeOccurenceList;
		int var = literal;
		if (literal < 0) {
			var = -literal;
			unsatClauseList = positiveOccurenceList;
		}
		int breakCount = 0;
		for (LSClause clause : unsatClauseList.get(var)) {
			if (clause.getSatLiterals() == 1) {
				breakCount++;
			}
		}
		return breakCount;
	}

	public int computeBreakScore(int literal) {
		ArrayList<List<LSClause>> unsatClauseList = negativeOccurenceList;
		int var = literal;
		if (literal < 0) {
			var = -literal;
			unsatClauseList = positiveOccurenceList;
		}
		int breakScore = 0;
		for (LSClause clause : unsatClauseList.get(var)) {
			if (clause.getSatLiterals() == 1) {
				breakScore += clause.getBrokenCount();
			}
		}
		return breakScore;
	}

	public void setAssignment(int[] assignment) {
		this.assignment = assignment;
		unsatClauses.clear();
		for (LSClause clause : clausesList) {
			clause.inqueue = false;
			updateClauseStatus(clause);
		}
	}
	
	private void updateClauseStatus(LSClause clause) {
		int satLits = 0;
		for (int lit : clause.getLiterals()) {
			int var = Math.abs(lit);
			if (assignment[var] == lit) {
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
		ArrayList<List<LSClause>> satClauseList = positiveOccurenceList;
		ArrayList<List<LSClause>> unsatClauseList = negativeOccurenceList;
		
		if (literal < 0) {
			var = -literal;
			satClauseList = negativeOccurenceList;
			unsatClauseList = positiveOccurenceList;
		}
		
		if (assignment[var] == literal) {
			// the value of this variable is not changed
			return;
		}
		
		assignment[var] = literal;
		
		for (LSClause clause : satClauseList.get(var)) {
			int satLits = clause.getSatLiterals();
			clause.setSatLiterals(satLits+1);
			// clauses that become SAT will be recognized
			// when choosing an UNSAT clause
		}
		
		for (LSClause clause : unsatClauseList.get(var)) {
			int satLits = clause.getSatLiterals();
			clause.setSatLiterals(satLits-1);
			if (satLits == 1) {
				putClauseToUnsatList(clause);					
			}
		}
	}
	
}
