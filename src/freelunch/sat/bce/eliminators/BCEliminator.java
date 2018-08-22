package freelunch.sat.bce.eliminators;

import java.util.ArrayList;

import freelunch.sat.model.CnfSatFormula;

public interface BCEliminator {
	
	/**
	 * Remove blocked clauses from the formula and
	 * return the stack of blocked clauses.
	 * The first literal in each returned clause is the blocking literal
	 * @param formula
	 * @return
	 */
	public ArrayList<int[]> eliminateBlockedClauses(CnfSatFormula formula);
	
	/**
	 * Set the time limit for elimination
	 * @param seconds
	 */
	public void setTimeLimit(int seconds);

}
