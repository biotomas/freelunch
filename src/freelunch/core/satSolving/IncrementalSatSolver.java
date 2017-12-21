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

import freelunch.core.planning.TimeoutException;
import freelunch.core.utilities.IntVector;

/**
 * Interface for incremental SAT solvers
 *
 * @author Tomas Balyo
 * Oct 13, 2012
 */
public interface IncrementalSatSolver extends SatSolver {
	
	/**
	 * Set the (new) number of variables in the SAT solver
	 * @param howMany number of variables in the solver
	 */
	public void setVariablesCount(int howMany);
	
	/**
	 * Test if the formula is satisfiable
	 * @return true if satisfiable, false if unsatisfiable, null if unknown
	 */
	public Boolean isSatisfiable() throws TimeoutException;
	
	/**
	 * Add a new permanent (non removable) clause to the formula.
	 * The IntVector object can be reused.
	 * @param literals the literals to add
	 * @throws SatContradictionException if the formula is unsatisfiable after adding the clause
	 */
	public void addNewClause(IntVector literals) throws SatContradictionException;
	
	/**
	 * Add a new removable clause to the formula
	 * The IntVector object can be reused.
	 * @param literals
	 * @return the clauseId used for removing the clause
	 * @throws SatContradictionException if the formula is unsatisfiable after adding the clause
	 */
	public int addRemovableClause(IntVector literals) throws SatContradictionException;
	
	/**
	 * Remove the specified clause from the clause database
	 * @param clauseId
	 */
	public void removeClause(int clauseId);
	
	/**
	 * Remove all removable clauses from the clause database
	 */
	public void clearRemovableClauses();
	
	/**
	 * Add a permanent constraint that at most one of the literals can be true
	 * The IntVector object can be reused.
	 * @param literals
	 * @throws SatContradictionException if the formula is unsatisfiable after adding the clause
	 */
	public void addAtMostOneConstraint(IntVector literals) throws SatContradictionException;

	/**
	 * Add a removable constraint that at most one of the literals can be true
	 * The IntVector object can be reused.
	 * @param literals
	 * @return the constraintId used for removing 
	 * @throws SatContradictionException if the formula is unsatisfiable after adding the clause
	 */
	public int addRemovableAtMostOneConstraint(IntVector literals) throws SatContradictionException;
	
	/**
	 * Remove the specified at-most-one constraint
	 * @param constraintId
	 */
	public void removeAtMostOneConstraint(int constraintId);

	/**
	 * Reset the solver to its initial state - remove all variables and clauses
	 */
	public void reset();
}
