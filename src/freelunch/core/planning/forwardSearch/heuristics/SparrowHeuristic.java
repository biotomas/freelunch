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
package freelunch.core.planning.forwardSearch.heuristics;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver.ForwardSearchStatistics;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;

/**
 * A simple heuristic counting the number of state variables that
 * have a value different from the value in the goal condition and
 * adding extra score to actions that change state variables not changed
 * recently. Inspired by the Sparrow heuristic. 
 * @see StateVariablesValueGoalDistanceHeuristic 
 *
 * This is also a restart heuristic. Restarting is recommended if the
 * age of all available actions effect is smaller than a constant.
 * @author Tomas Balyo
 * Feb 28, 2013
 */
public class SparrowHeuristic implements ForwardSearchSelectorFunction, ForwardSearchRestartHeuristic {
	
	private int[] goal;
	private Random random;
	private long[][] ages;
	private long time = 1;
	private float avgScore;
	
	public SparrowHeuristic(SasProblem problem, long seed) {
		random = new Random(seed);
		goal = new int[problem.getVariables().size()];
		Arrays.fill(goal, -1);
        ages = new long[problem.getVariables().size()][];
        for (StateVariable var : problem.getVariables()) {
            ages[var.getId()] = new long[var.getDomain()];
            Arrays.fill(ages[var.getId()], 0);
        }
		for (Condition c : problem.getGoal()) {
			goal[c.getVariable().getId()] = c.getValue();
		}
	}

    private float evaluate(int[] state, SasAction action) {
        int score = 0;
        long age = 0;
        for (Condition c : action.getEffects()) {
            // basic score for a condition
            score += 1;
            
            int varId = c.getVariable().getId();
            // the variable value is good, the action ruins it
            if (state[varId] == goal[varId] && c.getValue() != goal[varId]) {
                // the basic score is lost
                score -= 1;
            }
            // the variable value is bad, the action fixes it
            if (state[varId] != goal[varId] && c.getValue() == goal[varId]) {
                // extra score gain
                score += 1;
            }
            // the variable value is different to the current state
            if (state[varId] != c.getValue()) {
                long valueAge = time - ages[varId][c.getValue()];
                age += valueAge;
            }
        }
        // normalized score 0-1
        float scoreFactor = (float)score / (float)2*action.getEffects().size();
        avgScore += scoreFactor;
        // normalized age 0-1
        float ageFactor = (float)age / (float)time;
        return (scoreFactor) * (ageFactor);
    }
    
    @Override
    public SasAction select(int[] state, List<SasAction> actions) {
        SasAction bestAction = null;
        float[] values = new float[actions.size()];
        float valuesSum = 0f;
        int i = 0;
        avgScore = 0;
        for (SasAction action : actions) {
            values[i] = evaluate(state, action);
            valuesSum += values[i];
            i++;
        }
        avgScore = avgScore / actions.size();
        float randValue = random.nextFloat() * valuesSum;
        i = 0;
        for (SasAction action : actions) {
            float d = values[i];
            i++;
            if (d >= randValue) {
                bestAction = action;
                break;
            }
            randValue -= d;
        }
        if (bestAction == null) {
        	bestAction = actions.get(0);
        }
        for (Condition c : bestAction.getEffects()) {
            int varId = c.getVariable().getId();
            if (state[varId] != c.getValue()) {
                ages[varId][c.getValue()] = time;
            }
        }
        time++;
        return bestAction;
    }

    @Override
    public void noteRestart() {
        // Maybe also divide the ages by two?
        // clear ages
        //for (int i = 0; i < ages.length; i++)
        //    Arrays.fill(ages[i], 0);
    }


    private int sampleSize = 1000;
    private double totalScore = 0;
    private double lastTotalScore = -1;
    private long iter = 0;

    @Override
    public boolean shouldRestart(ForwardSearchStatistics statistics) {
        totalScore += avgScore;
        iter++;
        if (iter % sampleSize == 0) {
            System.out.println(totalScore);
            if (totalScore < lastTotalScore) {
                lastTotalScore = totalScore;
                totalScore = 0;
                return true;
            }
            System.out.print("n");
            lastTotalScore = totalScore;
            totalScore = 0;
        }
        return false;
    }

}
