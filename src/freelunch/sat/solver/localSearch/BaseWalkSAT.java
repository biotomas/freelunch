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

import java.util.Random;

import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.solver.LocalSearchSatSolver;
import freelunch.sat.solver.localSearch.selectors.LocalSearchSelector;


public class BaseWalkSAT implements LocalSearchSatSolver {
	
	public class LocalSearchMetadata {
		public SimpleClauseManager clManager;
		public Random random;
		public int[] flipRates;
		public int totalFlips;
		public int totalRestarts;
	}
    
	protected LocalSearchMetadata lsData = new LocalSearchMetadata();
	protected LocalSearchSelector selector;
	
	protected int[] assignment;
	protected int[] bestAssignment;
	protected double[] distribution;
    protected long timeLimit;
    
    public BaseWalkSAT(LocalSearchSelector selector, long seed) {
    	this.selector = selector;
    	lsData.random = new Random(seed);
    }
	
	@Override
	public int getFlipsCount() {
		return lsData.totalFlips;
	}

	@Override
	public int getRestartsCount() {
		return lsData.totalRestarts;
	}

	
	public void setRandomAssignmentDistribution(double[] distribution) {
		this.distribution = distribution;
	}
	
	/**
	 * Generate a random assignment and fill it to 
	 * the prepared integer array.
	 * @param assignment
	 */
	protected void generateRandomAssignment(int[] assignment) {
		for (int i = 1; i < assignment.length; i++) {
			boolean val;
			if (distribution == null) {
				val = lsData.random.nextBoolean();
			} else {
				val = (lsData.random.nextDouble() <= distribution[i]);
			}
			assignment[i] = val ? i : -i;
		}
	}

	/**
	 * Decide if we should restart
	 * @param flipsSinceRestart
	 * @return true if restart is recommended
	 */
	protected static final int INITIAL_RESTART_INTERVAL = 100;
	protected int restartInterval;
	protected int outerRestartInterval;
	protected boolean shouldRestart(int flipsSinceRestart) {
		if (lsData.totalRestarts > outerRestartInterval) {
			restartInterval = INITIAL_RESTART_INTERVAL;
			outerRestartInterval += (outerRestartInterval >> 3)*9;
		}
		if (flipsSinceRestart > restartInterval) {
			// restartInterval *= 1.25
			restartInterval = (restartInterval >> 2)*5;
			return true;
		}
		return false;
	}
	
    public void setVariablesCount(int variables) {
        lsData.clManager.setVariablesCount(variables);
        assignment = new int[variables+1];
        lsData.flipRates = new int[variables+1];
    }
    

    @Override
    public void setTimeout(long nanoseconds) {
        this.timeLimit = nanoseconds;
    }
    
    @Override
    public Boolean isSatisfiable(BasicFormula formula) {
    	lsData.clManager = new SimpleClauseManager();
        setVariablesCount(formula.variablesCount);
        for (int[] c : formula.clauses) {
        	lsData.clManager.addClause(new LSClause(c));
        }
        return isSatisfiable();
    }

    public Boolean isSatisfiable() {
        Stopwatch watch = new Stopwatch();
        lsData.totalFlips = 0;
        lsData.totalRestarts = 0;
        restartInterval = INITIAL_RESTART_INTERVAL;
        outerRestartInterval = INITIAL_RESTART_INTERVAL >> 1;
        
        // Iterations (Restarts) cycle
        while (true) {
            generateRandomAssignment(assignment);
            lsData.clManager.setAssignment(assignment);
            selector.prepareForFlipping(lsData);
            int flipsSinceRestart = 0;
            
            // Flips cycle
            while (true) {
            	flipsSinceRestart++;
            	lsData.totalFlips++;
            	if (shouldRestart(flipsSinceRestart)) {
            		break;
            	}
                LSClause unsatCl = lsData.clManager.getUnsatClause();
                if (unsatCl == null) {
                    bestAssignment = assignment;
                    return true;
                }
                // checking time limit is somehow expensive
                if (lsData.totalFlips % 100000 == 0 && watch.timeLimitExceeded(timeLimit)) {
                    return null;
                }
                
                int literal = selector.selectLiteralToFlip(unsatCl.getLiterals());
                lsData.clManager.makeLiteralTrue(literal);
                lsData.flipRates[Math.abs(literal)]++;
            }
            lsData.totalRestarts++;
        }
    }

    @Override
    public int[] getModel() {
        return bestAssignment;
    }

}
