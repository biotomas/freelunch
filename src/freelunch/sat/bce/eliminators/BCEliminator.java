package freelunch.sat.bce.eliminators;

import java.util.ArrayList;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public interface BCEliminator {
	
	/**
	 * Remove blocked clauses from the formula and
	 * return the stack of blocked clauses.
	 * The first literal in each returned clause is the blocking literal
	 * @param formula
	 * @return
	 */
	public ArrayList<int[]> eliminateBlockedClauses(BasicFormula formula);
	
	/**
	 * Set the time limit for elimination
	 * @param seconds
	 */
	public void setTimeLimit(long nanoseconds);

}
