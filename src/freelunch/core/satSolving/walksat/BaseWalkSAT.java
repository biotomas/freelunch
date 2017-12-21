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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.IntVector;
import freelunch.core.utilities.Stopwatch;


public abstract class BaseWalkSAT implements IncrementalSatSolver {
    
    private long satTime = 0;
    
    public long getSolveTime() {
        return satTime;
    }
    
    public static class BaseWalkSatSettings extends BasicSettings  {
        private int flipsToImprove = 100000;
        private int restartsToImprove = 10;
        private int seed = 2013;
        /**
         * @return the flipsToImprove
         */
        public int getFlipsToImprove() {
            return flipsToImprove;
        }
        /**
         * @param flipsToImprove the flipsToImprove to set
         */
        public void setFlipsToImprove(int flipsToImprove) {
            this.flipsToImprove = flipsToImprove;
        }
        /**
         * @return the restartsToImprove
         */
        public int getRestartsToImprove() {
            return restartsToImprove;
        }
        /**
         * @param restartsToImprove the restartsToImprove to set
         */
        public void setRestartsToImprove(int restartsToImprove) {
            this.restartsToImprove = restartsToImprove;
        }
        /**
         * @return the seed
         */
        public int getSeed() {
            return seed;
        }
        /**
         * @param seed the seed to set
         */
        public void setSeed(int seed) {
            this.seed = seed;
        }
    }
	
	protected Random random;
	protected SimpleClauseManager clManager;
	protected int variables;
	protected int[] assignment;
	protected int[] bestAssignment;
	protected int[] flipRates;
	protected int minUnsatClauses;
	protected int totalUnsatClauses;
	protected double[] distribution;
    private int timeLimit;
    private Map<Integer, LSClause> removableClauseMap;
    private Map<Integer, List<LSClause>> removableAtMostOnesMap;
    private int lastRemovableClauseId = 0;
    protected BaseWalkSatSettings settings;
	
	public int totalFlips = 0;
	
	public void setRandomAssignmentDistribution(double[] distribution) {
		this.distribution = distribution;
	}
	
	/**
	 * This constructor is useful for SatSolver interface usage
	 * @param seed
	 * @param flips
	 * @param restarts
	 */
	public BaseWalkSAT(BaseWalkSatSettings settings) {
	    this.settings = settings;
        random = new Random(settings.seed);
        removableClauseMap = new HashMap<Integer, LSClause>();
        removableAtMostOnesMap = new HashMap<Integer, List<LSClause>>();
        distribution = null;
        clManager = new SimpleClauseManager(random);
}
	
	/**
	 * General Constructor
	 * @param seed seed for the pseudo-random generator
	 * @param variables number of variables
	 */
	public BaseWalkSAT() {
	    this(new BaseWalkSatSettings());
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
				val = random.nextBoolean();
			} else {
				val = (random.nextDouble() <= distribution[i]);
			}
			assignment[i] = val ? i : -i;
		}
	}

	/**
	 * Select a literal to flip (make true) at the current step from the given
	 * list of literals. 
	 * @param candidates a list of literals
	 * @return a selected variable
	 */
	protected abstract int selectLiteralToFlip(int[] candidates);
	
	/**
	 * This method should be called before a series of flips, for
	 * example after restarting or when the number of variables change.
	 */
	protected abstract void prepareForFlipping();

    @Override
    public void setVariablesCount(int variables) {
        this.variables = variables;
        clManager.setVariablesCount(variables);
        assignment = new int[variables+1];
        flipRates = new int[variables+1];
    }
    

    @Override
    public void setTimeout(int seconds) {
        this.timeLimit = seconds;
    }
    
    @Override
    public Boolean isSatisfiable(BasicSatFormula formula) throws TimeoutException {
        reset();
        setVariablesCount(formula.getVariables());
        for (int[] c : formula.getClauses()) {
            clManager.addPermanentClause(new LSClause(c));
        }
        return isSatisfiable();
    }

    @Override
    public Boolean isSatisfiable() throws TimeoutException {
        Stopwatch watch = new Stopwatch();
        
        int iterationBestUnsatCount = Integer.MAX_VALUE;
        int iterationsSinceImprovement = 0;
        
        // Iterations (Restarts) cycle
        while (true) {
            generateRandomAssignment(assignment);
            clManager.setAssignment(assignment);
            Arrays.fill(flipRates, 0);
            prepareForFlipping();
            
            int minUnsat = Integer.MAX_VALUE;
            int flipsSinceImprovement = 0;
            int unsat = 0;
            
            // Flips cycle
            while (true) {
                LSClause unsatCl = clManager.getRandomUnsatClause();
                if (unsatCl == null) {
                    bestAssignment = assignment;
                    satTime = watch.elapsedMilliseconds();
                    return true;
                }
                if (watch.limitExceeded(timeLimit)) {
                    satTime = watch.elapsedMilliseconds();
                    return null;
                }
                unsat = clManager.getAllUnsatClauses().size();
                if (unsat < minUnsat) {
                    minUnsat = unsat;
                    flipsSinceImprovement = 0;
                } else {
                    flipsSinceImprovement++;
                    if (flipsSinceImprovement > settings.flipsToImprove) {
                        break;
                    }
                }
                
                int literal = selectLiteralToFlip(unsatCl.getLiterals());
                clManager.makeLiteralTrue(literal);
                flipRates[Math.abs(literal)]++;
            }

            // global best result
            if (minUnsat < iterationBestUnsatCount) {
                iterationsSinceImprovement = 0;
                iterationBestUnsatCount = minUnsat;
                bestAssignment = Arrays.copyOf(assignment, assignment.length);
            } else {
                iterationsSinceImprovement++;
                if (iterationsSinceImprovement > settings.restartsToImprove) {
                    break;
                }
            }
        }
        satTime = watch.elapsedMilliseconds();
        return null;
    }

    @Override
    public int[] getModel() {
        return bestAssignment;
    }

    @Override
    public void addNewClause(IntVector literals) throws SatContradictionException {
        clManager.addPermanentClause(new LSClause(literals.getArrayCopy()));
    }

    @Override
    public int addRemovableClause(IntVector literals) throws SatContradictionException {
        lastRemovableClauseId++;
        LSClause c = new LSClause(literals.getArrayCopy());
        clManager.addRemovableClause(c);
        removableClauseMap.put(lastRemovableClauseId, c);
        return lastRemovableClauseId;
    }
    
    @SuppressWarnings("unlikely-arg-type")
	@Override
    public void removeClause(int clauseId) {
        LSClause c = removableClauseMap.get(clauseId);
        clManager.removeClause(c);
        removableClauseMap.remove(c);
    }
    
    @Override
    public void clearRemovableClauses() {
        removableClauseMap.clear();
        removableAtMostOnesMap.clear();
        clManager.clearRemovableClauses();
    }

    @Override
    public void addAtMostOneConstraint(IntVector literals) throws SatContradictionException {
        int[] lits = literals.getArrayCopy();
        for (int i = 0; i < lits.length; i++) {
            for (int j = i+1; j < lits.length; j++) {
                clManager.addPermanentClause(new LSClause(new int[] {-lits[i], -lits[j]}));
            }
        }
    }

    @Override
    public int addRemovableAtMostOneConstraint(IntVector literals) throws SatContradictionException {
        lastRemovableClauseId++;
        int[] lits = literals.getArrayCopy();
        List<LSClause> cls = new ArrayList<LSClause>(lits.length * (lits.length - 1));
        for (int i = 0; i < lits.length; i++) {
            for (int j = i+1; j < lits.length; j++) {
                LSClause c = new LSClause(new int[] {-lits[i], -lits[j]});
                cls.add(c);
                clManager.addRemovableClause(c);
            }
        }
        removableAtMostOnesMap.put(lastRemovableClauseId, cls);
        return lastRemovableClauseId;
    }

    @Override
    public void removeAtMostOneConstraint(int constraintId) {
        for (LSClause c : removableAtMostOnesMap.get(constraintId)) {
            clManager.removeClause(c);
        }
        removableAtMostOnesMap.remove(constraintId);
    }
    
    @Override
    public void reset() {
        removableClauseMap.clear();
        removableAtMostOnesMap.clear();
        clManager = new SimpleClauseManager(random);
    }

}
