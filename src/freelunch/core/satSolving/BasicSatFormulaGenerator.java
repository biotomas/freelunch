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
package freelunch.core.satSolving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.utilities.IntVector;


/**
 * A fake solver used only to generate a SAT formula through
 * the {@link IncrementalSatSolver} interface. It can not actually
 * solve a formula, it only records the clauses and variables.
 *
 * @author Tomas Balyo
 * 31.10.2012
 */
public class BasicSatFormulaGenerator implements IncrementalSatSolver {
	
	private int variables;
	private List<int[]> clauses;
	private Map<Integer, int[]> removableClauses;
	private int removableClauseId;
	
	public BasicSatFormulaGenerator() {
		variables = 0;
		removableClauseId = 0;
		clauses = new ArrayList<int[]>();
		removableClauses = new HashMap<Integer, int[]>();
	}

	@Override
	public void setVariablesCount(int howMany) {
		this.variables = howMany;
	}

	@Override
	public void setTimeout(int seconds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Boolean isSatisfiable() throws TimeoutException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[] getModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addNewClause(IntVector literals) throws SatContradictionException {
		clauses.add(literals.getArrayCopy());
	}

	@Override
	public int addRemovableClause(IntVector literals) throws SatContradictionException {
		removableClauseId++;
		removableClauses.put(removableClauseId, literals.getArrayCopy());
		return removableClauseId;
	}

	@Override
	public void removeClause(int clauseId) {
		removableClauses.remove(clauseId);
	}

	@Override
	public void addAtMostOneConstraint(IntVector literals) throws SatContradictionException {
		int[] lits = literals.getArrayCopy();
		for (int i = 0; i < lits.length; i++) {
			for (int j = i+1; j < lits.length; j++) {
				clauses.add(new int[] {-lits[i], -lits[j]});
			}
		}
	}

	@Override
	public int addRemovableAtMostOneConstraint(IntVector literals) throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAtMostOneConstraint(int constraintId) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Get the current formula.
	 * @return
	 */
	public BasicSatFormula getFormula() {
		List<int[]> fclauses = new ArrayList<int[]>(clauses);
		fclauses.addAll(removableClauses.values());
		return new BasicSatFormula(variables, fclauses);
	}
	
	/**
	 * Clear the formula i.e. remove all clauses and variables
	 */
	public void clear() {
		clauses.clear();
		removableClauses.clear();
		variables = 0;
	}

    @Override
    public void clearRemovableClauses() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        clear();
    }

    @Override
    public Boolean isSatisfiable(BasicSatFormula formula) throws TimeoutException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String toString() {
        return getFormula().toString();
    }

    @Override
    public long getSolveTime() {
        throw new UnsupportedOperationException();
    }

}
