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

import freelunch.core.planning.model.BasicSettings;

public class MctsSettings extends BasicSettings {

    protected int numIterations = 1000;
    protected int rolloutLength = 30;
    protected int numRollouts = 5;
    /**
     * How many actions do we select at once after rollouts are finished.
     */
    protected int numActionsAtOnce = 1;

    protected TreePolicy treePolicy = new UcbTreePolicy(UcbTreePolicy.DEFAULT_C);
    protected NormalizedHeuristicFunction heuristic;

    public MctsSettings() {
        super();
    }

    public MctsSettings(BasicSettings settings) {
        super(settings);
    }

    public int getNumIterations() {
        return numIterations;
    }

    public void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public int getRolloutLength() {
        return rolloutLength;
    }

    public void setRolloutLength(int rolloutLength) {
        this.rolloutLength = rolloutLength;
    }

    public TreePolicy getTreePolicy() {
        return treePolicy;
    }

    public void setTreePolicy(TreePolicy treePolicy) {
        this.treePolicy = treePolicy;
    }

    public NormalizedHeuristicFunction getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(NormalizedHeuristicFunction heuristic) {
        this.heuristic = heuristic;
    }

    public int getNumRollouts() {
        return numRollouts;
    }

    public void setNumRollouts(int numRollouts) {
        this.numRollouts = numRollouts;
    }

    public int getNumActionsAtOnce() {
        return numActionsAtOnce;
    }

    public void setNumActionsAtOnce(int numActionsAtOnce) {
        this.numActionsAtOnce = numActionsAtOnce;
    }

}
