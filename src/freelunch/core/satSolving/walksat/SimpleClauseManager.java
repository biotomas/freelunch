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
package freelunch.core.satSolving.walksat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleClauseManager {
	
	private ArrayList<List<LSClause>> positiveOccurenceList;
	private ArrayList<List<LSClause>> negativeOccurenceList;
	private int variables = 1; //variables are indexed from 1
	private ArrayList<LSClause> unsatClauses;
	private List<LSClause> permanentClauses;
	private List<LSClause> removableClauses;
	private int[] assignment = null;
	private Random random;
	
	public SimpleClauseManager(Random random) {
		this.random = random;
		unsatClauses = new ArrayList<LSClause>();
		permanentClauses = new ArrayList<LSClause>();
		removableClauses = new ArrayList<LSClause>();
		positiveOccurenceList = new ArrayList<List<LSClause>>();
		// dummy variable 0
		positiveOccurenceList.add(new ArrayList<LSClause>());
		negativeOccurenceList = new ArrayList<List<LSClause>>();
        // dummy variable 0
        negativeOccurenceList.add(new ArrayList<LSClause>());
	}
	
	public void setVariablesCount(int howMany) {
        for (int i = variables; i <= howMany; i++) {
            positiveOccurenceList.add(new ArrayList<LSClause>());
            negativeOccurenceList.add(new ArrayList<LSClause>());
        }
	}

	public void addPermanentClause(LSClause clause) {
		permanentClauses.add(clause);
		registerClause(clause);
	}
	
	public void addRemovableClause(LSClause clause) {
	    removableClauses.add(clause);
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
	 * Unregister the clause in the occurrence lists
	 * @param clause
	 */
	private void unregisterClause(LSClause clause) {
        for (int literal : clause.getLiterals()) {
            if (literal > 0) {
                positiveOccurenceList.get(literal).remove(clause);
            } else {
                negativeOccurenceList.get(-literal).remove(clause);
            }
        }
	}
	
	public void removeClause(LSClause clause) {
	    assert removableClauses.remove(clause);
	    unsatClauses.remove(clause);
	    unregisterClause(clause);
	}
	
	public void clearRemovableClauses() {
	    for (LSClause clause : removableClauses) {
	        unregisterClause(clause);
	    }
	    unsatClauses.removeAll(removableClauses);
	    unsatClauses.clear();
	}

	public LSClause getRandomUnsatClause() {
		if (unsatClauses.isEmpty()) {
			return null;
		}
		return unsatClauses.get(random.nextInt(unsatClauses.size()));
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

	public void setAssignment(int[] assignment) {
		this.assignment = assignment;
		unsatClauses.clear();
		for (LSClause clause : permanentClauses) {
			updateClauseStatus(clause);
		}
		for (LSClause clause : removableClauses) {
		    updateClauseStatus(clause);
		}
	}
	
	private void updateClauseStatus(LSClause clause) {
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
		ArrayList<List<LSClause>> satClauseList = positiveOccurenceList;
		ArrayList<List<LSClause>> unsatClauseList = negativeOccurenceList;
		
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
		
		for (LSClause clause : satClauseList.get(var)) {
			int satLits = clause.getSatLiterals();
			clause.setSatLiterals(satLits+1);
			if (satLits == 0) {
				removeClauseFromUnsatList(clause);					
			}
		}
		
		for (LSClause clause : unsatClauseList.get(var)) {
			int satLits = clause.getSatLiterals();
			clause.setSatLiterals(satLits-1);
			if (satLits == 1) {
				putClauseToUnsatList(clause);					
			}
		}
	}
	
	private void putClauseToUnsatList(LSClause clause) {
		clause.setUnsatClauseIndex(unsatClauses.size());		
		unsatClauses.add(clause);
	}
	
	private void removeClauseFromUnsatList(LSClause clause) {
		int index = clause.getUnsatClauseIndex();
		int last = unsatClauses.size() - 1;
		LSClause lastClause = unsatClauses.get(last);
		lastClause.setUnsatClauseIndex(index);
		unsatClauses.set(index, lastClause);
		unsatClauses.remove(last);
	}

	public List<LSClause> getAllUnsatClauses() {
		return unsatClauses;
	}

}
