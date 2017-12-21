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
package freelunch.core.planning.forwardSearch;

import freelunch.core.planning.forwardSearch.heuristics.ForwardSearchSelectorFunction;
import freelunch.core.planning.forwardSearch.heuristics.ForwardSearchRestartHeuristic;
import freelunch.core.planning.forwardSearch.heuristics.NeverRestartHeuristic;
import freelunch.core.planning.forwardSearch.heuristics.RandomHeuristic;
import freelunch.core.planning.model.BasicSettings;

/**
 * Settings for forward search algorithms
 * 
 * @author Tomas Balyo 4.11.2012
 */
public class ForwardSearchSettings extends BasicSettings {

    private ForwardSearchSelectorFunction heuristic = new RandomHeuristic();
    private ForwardSearchRestartHeuristic restartHeuristic = new NeverRestartHeuristic();

    public ForwardSearchSettings() {
        super();
    }

    public ForwardSearchSettings(BasicSettings settings) {
        super(settings);
    }

    /**
     * Default heuristic is Random
     * 
     * @return the heuristic
     */
    public ForwardSearchSelectorFunction getHeuristic() {
        return heuristic;
    }

    /**
     * Default heuristic is Random
     * 
     * @param heuristic the heuristic to set
     */
    public void setHeuristic(ForwardSearchSelectorFunction heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Default heuristic is statistics ruled restarts
     * 
     * @return the restartHeuristic
     */
    public ForwardSearchRestartHeuristic getRestartHeuristic() {
        return restartHeuristic;
    }

    /**
     * @param restartHeuristic the restartHeuristic to set
     */
    public void setRestartHeuristic(ForwardSearchRestartHeuristic restartHeuristic) {
        this.restartHeuristic = restartHeuristic;
    }

}
