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

public class StatisticsRuledRestartHeuristic implements ForwardSearchRestartHeuristic {
    
    private int iterationsLimit = 100;
    private int backtracksLimit = 100;
    private int depthLimit = 100;
    private float increaseRate = 1.5f;
    
    @Override
    public boolean shouldRestart(ForwardSearchStatistics statistics) {
        if (statistics.depth > depthLimit) {
            depthLimit = (int) (increaseRate * depthLimit);
            return true;
        }
        if (statistics.iterationsSinceRestart > iterationsLimit) {
            iterationsLimit = (int) (increaseRate * iterationsLimit);
            return true;
        }
        if (statistics.backtracksSinceRestart > backtracksLimit) {
            backtracksLimit = (int) (increaseRate * backtracksLimit);
            return true;
        }
        return false;
    }
    
    

}
