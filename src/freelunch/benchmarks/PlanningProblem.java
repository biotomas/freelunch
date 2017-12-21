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
package freelunch.benchmarks;

import java.util.List;

import freelunch.core.planning.model.ActionInfo;


public interface PlanningProblem {

    /**
     * Apply the given action to the state given by this instance.
     * 
     * @param actions actions to apply.
     * @return object representing the new state of the problem.
     */
    public void applyActions(List<ActionInfo> actions);

    /**
     * Copies the current instance;
     * 
     * @return the copy of this instance.
     */
    public PlanningProblem copy();
}
