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
package freelunch.core.planning.montecarlo;

import java.util.Arrays;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasProblem;


public class StateVariablesEqualityHeuristic implements NormalizedHeuristicFunction {

    private final int[] goal;

    public StateVariablesEqualityHeuristic(SasProblem problem) {
        goal = new int[problem.getVariables().size()];
        Arrays.fill(goal, -1);
        for (Condition c : problem.getGoal()) {
            goal[c.getVariable().getId()] = c.getValue();
        }
    }

    @Override
    public double evaluate(int[] state) {
        int diffs = 0;
        int pertinent = 0;
        for (int i = 0; i < state.length; i++) {
            if (goal[i] >= 0) {
                pertinent++;
                if (goal[i] != state[i]) {
                    diffs++;
                }
            }
        }
        return 1 - (double) diffs / pertinent;
    }

}
