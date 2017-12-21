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
package freelunch.core.planning.sase.preprocessing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;


/**
 * Remove Impasse actions from the sas problem. Impasse actions are
 * such that set a state variable to a value X which is not a goal value
 * and there is no action that can modify the value of this variable 
 * from X to any other value.
 *
 * @author Tomas Balyo
 * Sep 29, 2012
 */
public class ImpasseRemover {
	
	/**
	 * Remove all the impasse actions from the problem.
	 * Returns the number of removed actions.
	 * @param problem
	 * @return the number of removed actions
	 * 
	 * TODO fixme: this method is incorrect, removes too many actions because of wrong evaluation
	 * of "valueCanChange", the old value does not have to be a precondition of another action, there
	 * could be *->x transition in some action, then the state is not an impass state.
	 */
	@Deprecated
	public static int removeImpasses(SasProblem problem) {
		// goal conditions by variable
		Map<StateVariable, Integer> goalMap = new HashMap<StateVariable, Integer>(problem.getGoal().size());
		// condition values by variables
		Map<StateVariable, Set<Integer>> variableValueIndex = new HashMap<StateVariable, Set<Integer>>(problem.getGoal().size());
		for (Condition c : problem.getGoal()) {
			goalMap.put(c.getVariable(), c.getValue());
			variableValueIndex.put(c.getVariable(), new HashSet<Integer>());
		}
		for (SasAction op : problem.getOperators()) {
			for (Condition c : op.getPreconditions()) {
				if (variableValueIndex.containsKey(c.getVariable())) {
					variableValueIndex.get(c.getVariable()).add(c.getValue());
				}
			}
		}
		LinkedList<SasAction> operators = new LinkedList<SasAction>(problem.getOperators());		
		Iterator<SasAction> opIt = operators.iterator();
		while (opIt.hasNext()) {
			SasAction op = opIt.next();
			for (Condition c : op.getEffects()) {
				if (variableValueIndex.containsKey(c.getVariable())) {
					boolean valueCanChange = variableValueIndex.get(c.getVariable()).contains(c.getValue());
					boolean isGoalValue = (goalMap.get(c.getVariable()) == c.getValue());
					if (!isGoalValue && !valueCanChange) {
						opIt.remove();
						//System.out.println(op);
						break;
					}
				}
			}
		}
		
		int removed = problem.getOperators().size() - operators.size();
		problem.setOperators(operators);
		return removed;
	}

}
