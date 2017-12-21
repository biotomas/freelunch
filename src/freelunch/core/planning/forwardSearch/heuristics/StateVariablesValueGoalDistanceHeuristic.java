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

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;


/**
 * A simple heuristic counting the number of state variables that
 * have a value different from the value in the goal condition and
 * adding a random value to break the ties.
 *
 * @author Tomas Balyo
 * Oct 15, 2012
 */
public class StateVariablesValueGoalDistanceHeuristic implements ForwardSearchSelectorFunction {
	
	private int[] goal;
	private Random random;
	
	public StateVariablesValueGoalDistanceHeuristic(SasProblem problem, long seed) {
		random = new Random(seed);
		goal = new int[problem.getVariables().size()];
		Arrays.fill(goal, -1);
		for (Condition c : problem.getGoal()) {
			goal[c.getVariable().getId()] = c.getValue();
		}
	}

    private int evaluate(int[] state, SasAction action) {
        int score = 0;
        for (Condition c : action.getEffects()) {
            int varId = c.getVariable().getId();
            // the variable value is good, the action ruins it
            if (state[varId] == goal[varId] && c.getValue() != goal[varId]) {
                score--;
            }
            // the variable value is bad, the action fixes it
            if (state[varId] != goal[varId] && c.getValue() == goal[varId]) {
                score++;
            }
        }
        // break ties randomly
        return 10*score + random.nextInt(10);
    }
    
    @Override
    public SasAction select(int[] state, List<SasAction> actions) {
        SasAction bestAction = null;
        int bestvalue = Integer.MIN_VALUE;
        for (SasAction action : actions) {
            int value = evaluate(state, action);
            if (value >= bestvalue) {
                bestAction = action;
                bestvalue = value;
            }
        }
        return bestAction;
    }

    @Override
    public void noteRestart() {
        //nothing to do here.
    }

}
