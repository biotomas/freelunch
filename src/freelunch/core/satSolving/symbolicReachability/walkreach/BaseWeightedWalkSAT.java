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

import java.util.Arrays;
import java.util.Random;

import freelunch.core.satSolving.symbolicReachability.walkreach.model.ReachClause;


public abstract class BaseWeightedWalkSAT {
	
	protected Random random;
	protected SimpleReachClauseManager clManager;
	protected int[] assignment;
	protected int[] flipRates;
	
	public int totalFlips = 0;
	
	/**
	 * General Constructor
	 * @param seed seed for the pseudo-random generator
	 * @param variables number of variables
	 */
	public BaseWeightedWalkSAT(long seed) {
		random = new Random(seed);
	}
	
	/**
	 * Generate a random assignment and fill it to 
	 * the prepared integer array.
	 * @param assignment
	 */
	protected void generateRandomAssignment(int[] assignment) {
		for (int i = 1; i < assignment.length; i++) {
			assignment[i] = random.nextBoolean() ? i : -i;
		}
	}

	public void addClause(ReachClause clause) {
		clManager.addClause(clause);
	}

	public void initializeEmptyFormula(int variables) {
		clManager = new SimpleReachClauseManager(variables, random);
		assignment = new int[variables+1];
		flipRates = new int[variables+1];
	}
	
	/**
	 * Select a literal to flip (make true) at the current step from the given
	 * list of literals. 
	 * @param candidates a list of literals
	 * @return a selected variable
	 */
	protected abstract int selectLiteralToFlip(int[] candidates);

	/**
	 * Local search for a satisfying solution until a long period of
	 * no improvement occurs. The length of this period is specified
	 * by the parameter in the number of flips.
	 * @param noImprovementFlips
	 * @return
	 */
	public boolean searchForSolution(int noImprovementFlips, boolean firstPhase) {
	    if (firstPhase) {
            generateRandomAssignment(assignment);
	    }
        clManager.setAssignment(assignment);
		Arrays.fill(flipRates, 0);
		
		int bestScore = Integer.MAX_VALUE;
		int bestUnsatCl = Integer.MAX_VALUE;
		int flipsSinceLastImprovement = 0;
		
		while (true) {
			// find an unsatisfied clause
			ReachClause unsatCl = clManager.getWeightedRandomUnsatClause();
			// if no such clause we are done
			if (unsatCl == null) {
				return true;
			}
			
			int unsatCount = clManager.getAllUnsatClauses().size();
			
			if (unsatCount < bestUnsatCl) {
			    bestUnsatCl = unsatCount;
			    flipsSinceLastImprovement = 0;
			} else {
			    flipsSinceLastImprovement++;
			}
			
			if (flipsSinceLastImprovement >= noImprovementFlips) {
			    int unsatWeights = 0;
			    for (ReachClause rc : clManager.getAllUnsatClauses()) {
			        unsatWeights += rc.getWeight();
			    }
			    if (unsatWeights < bestScore) {
			        bestScore = unsatWeights;
			        flipsSinceLastImprovement = 0;
			    } else {
			        break;
			    }
			}
			
			// select a variable to flip
			int literal = selectLiteralToFlip(unsatCl.getLiterals());
			// flip the variable
			if (literal != 0) {
	            clManager.makeLiteralTrue(literal);
	            flipRates[Math.abs(literal)]++;
			}
			totalFlips++;
		}
		
		return false;
	}
	
	public int[] getCurrentAssignment() {
		return assignment;
	}

}
