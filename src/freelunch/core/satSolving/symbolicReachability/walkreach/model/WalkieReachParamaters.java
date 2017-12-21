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
package freelunch.core.satSolving.symbolicReachability.walkreach.model;

import freelunch.core.planning.model.BasicSettings;

public class WalkieReachParamaters extends BasicSettings {
    
    private int searchMakespan = 10; // at least 5
    private int searchCenter = searchMakespan / 2 ;
    private int weightIncrease = 1000;
    private float randomWalkProb = 0.04f;
    private int noImprovementFlips = 10000;
    private long seed = 2012;
    
    /**
     * @return the searchMakespan
     */
    public int getSearchMakespan() {
        return searchMakespan;
    }
    /**
     * @param searchMakespan the searchMakespan to set
     */
    public void setSearchMakespan(int searchMakespan) {
        this.searchMakespan = searchMakespan;
    }
    /**
     * @return the searchCenter
     */
    public int getSearchCenter() {
        return searchCenter;
    }
    /**
     * @param searchCenter the searchCenter to set
     */
    public void setSearchCenter(int searchCenter) {
        this.searchCenter = searchCenter;
    }
    /**
     * @return the weightIncrease
     */
    public int getWeightIncrease() {
        return weightIncrease;
    }
    /**
     * @param weightIncrease the weightIncrease to set
     */
    public void setWeightIncrease(int weightIncrease) {
        this.weightIncrease = weightIncrease;
    }
    /**
     * @return the randomWalkProb
     */
    public float getRandomWalkProb() {
        return randomWalkProb;
    }
    /**
     * @param randomWalkProb the randomWalkProb to set
     */
    public void setRandomWalkProb(float randomWalkProb) {
        this.randomWalkProb = randomWalkProb;
    }
    /**
     * @return the seed
     */
    public long getSeed() {
        return seed;
    }
    /**
     * @param seed the seed to set
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }
    /**
     * @return the noImprovementFlips
     */
    public int getNoImprovementFlips() {
        return noImprovementFlips;
    }
    /**
     * @param noImprovementFlips the noImprovementFlips to set
     */
    public void setNoImprovementFlips(int noImprovementFlips) {
        this.noImprovementFlips = noImprovementFlips;
    }
    
    
}
