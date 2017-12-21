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
package freelunch.core.planning.sase.optimizer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;



/**
 * Create a SUBPLAN structure. We assume that the given plan is valid
 *
 * @author Tomas Balyo
 * Mar 12, 2012
 */
public class SubPlanStructure {
	
	private Map<Integer, List<Condition>> starts;
	private Map<Integer, List<Condition>> ends;
	
	/**
	 * Create the subplan structure
	 * FIXME depends on the correct ordering of conditions in the initial state
	 * @param plan a valid plan of the problem
	 * @param problem the problem
	 */
	public SubPlanStructure(SasParallelPlan plan, SasProblem problem) {
		starts = new HashMap<Integer, List<Condition>>();
		List<Condition> state = cloneState(problem.getInitialState()); 
		starts.put(0, state);
		
		int time = 1;
		for (List<SasAction> actions : plan.getPlan()) {
			state = cloneState(state);
			//apply the effects, plan validity is assumed
			for (SasAction op : actions) {
				for (Condition eff : op.getEffects()) {
					int var = eff.getVariable().getId();
					state.get(var).setValue(eff.getValue());
				}
			}
			starts.put(time, state);
			time++;
		}
		
		time = plan.getPlanLength();
		ends = new HashMap<Integer, List<Condition>>();
		// clear the state
		for (Condition c : state)
			c.setValue(Condition.ARBITRARY);
		// load the goal state
		for (Condition c : problem.getGoal()) {
			int var = c.getVariable().getId();
			state.get(var).setValue(c.getValue());
		}
		ends.put(time, state);
		time--;
		for (; time >= 0; time--) {
			state = cloneState(state);
			// set the preconditions, plan validity is assumed
			List<SasAction> actions = plan.getPlan().get(time);
			for (SasAction op : actions) {
				for (Condition effect : op.getEffects()) {
					int var = effect.getVariable().getId();
					state.get(var).setValue(Condition.ARBITRARY);
				}
				for (Condition prec : op.getPreconditions()) {
					int var = prec.getVariable().getId();
					state.get(var).setValue(prec.getValue());
				}
			}
			ends.put(time, state);
		}
		// filter out ARBITRARY conditions
		for (List<Condition> conditions : ends.values()) {
			List<Condition> toDelete = new ArrayList<Condition>();
			for (Condition c : conditions) {
				if (c.getValue() == Condition.ARBITRARY) {
					toDelete.add(c);
				}
			}
			conditions.removeAll(toDelete);
		}
	}
	
	private List<Condition> cloneState(List<Condition> state) {
		List<Condition> result = new ArrayList<Condition>(state.size());
		for (Condition c : state) {
			result.add(new Condition(c));
		}
		return result;
	}
	
	public List<Condition> getInitialStateForTime(int time) {
		return starts.get(time);
	}
	
	public List<Condition> getGoalStateForTime(int time) {
		return ends.get(time);
	}

}
