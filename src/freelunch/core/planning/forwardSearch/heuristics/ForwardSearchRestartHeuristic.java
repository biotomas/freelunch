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
package freelunch.core.planning.forwardSearch.heuristics;

import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver.ForwardSearchStatistics;

/**
 * Interface for restarting heuristics. A restarting heuristic decides whether
 * the search should be restarted or continued.
 *
 * @author Tomas Balyo
 * 17.3.2013
 */
public interface ForwardSearchRestartHeuristic {
    
    public boolean shouldRestart(ForwardSearchStatistics statistics);

}
