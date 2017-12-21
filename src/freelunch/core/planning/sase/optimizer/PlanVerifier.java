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
package freelunch.core.planning.sase.optimizer;

import java.util.List;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.ConditionalEffect;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;


public class PlanVerifier {

    /**
     * Verifies a plan - checks if the given plan is a valid solution of the
     * given problem
     * 
     * @param problem
     * @param plan
     * @return true if the plan is valid, false otherwise
     */
    public static boolean verifyPlan(SasProblem problem, SasParallelPlan plan) {
        int[] state = new int[problem.getVariables().size()];
        // set up the initial state
        for (Condition c : problem.getInitialState()) {
            state[c.getVariable().getId()] = c.getValue();
        }
        int time = 0;
        for (List<SasAction> actions : plan.getPlan()) {
            for (SasAction op : actions) {
                //check preconditions
                for (Condition precond : op.getPreconditions()) {
                    int var = precond.getVariable().getId();
                    if (state[var] != precond.getValue()) {
                        System.out.println(String.format(
                                "Plan invalid: action (%s) at time %d has an unsatisfied precondition %s",
                                op.getActionInfo().getName(), time, precond.toString()));
                        return false;
                    }
                }
                
                //apply effects
                for (Condition eff : op.getEffects()) {
                    int var = eff.getVariable().getId();
                    state[var] = eff.getValue();
                }
                
                //check and apply conditional effects
                if (op.getConditionalEffects() != null) {
	                outer:
	                for (ConditionalEffect cef : op.getConditionalEffects()) {
	                	for (Condition ec : cef.getEffectConditions()) {
	                		if (state[ec.getVariable().getId()] != ec.getValue()) {
	                			continue outer;
	                		}
	                	}
	                	if (cef.getRequiredValue() != -1) {
	                		if (state[cef.getVar().getId()] != cef.getRequiredValue()) {
	                            System.out.println(String.format(
	                                    "Plan invalid: action (%s) at time %d has an unsatisfied conditional effect %s",
	                                    op.getActionInfo().getName(), time, cef.toString()));
	                            return false;                			
	                		}
	                	}
	                	state[cef.getVar().getId()] = cef.getNewValue();                	
	                }
                }
            }
            time++;
        }
        // check the goal
        for (Condition c : problem.getGoal()) {
            int var = c.getVariable().getId();
            if (state[var] != c.getValue()) {
                System.out.println(String.format(
                        "Plan invalid: goal condition %s is not satisfied",
                        c.toString()));
                return false;
            }
        }
        return true;
    }

}
