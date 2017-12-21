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

import java.util.List;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasAction;



/**
 * Heuristic function interface for forward search planners
 *
 * @author Tomas Balyo
 * Oct 15, 2012
 */
public interface ForwardSearchSelectorFunction {

    /**
     * Evaluate the given search space state and select which one of
     * the given actions should be applied in the next step.
     * @param state the current search space state
     * @param actions the set of actions to pick from
     * @return the recommended action 
     * @throws NonexistentPlanException 
     */
	public SasAction select(int[] state, List<SasAction> actions) throws NonexistentPlanException, TimeoutException;
	
	/**
	 * This method is called when the search is restarted. The search heuristic
	 * might need this information for its functioning.
	 */
	public void noteRestart();

}
