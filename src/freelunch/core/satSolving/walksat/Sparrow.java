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
package freelunch.core.satSolving.walksat;

import java.util.Arrays;


public class Sparrow extends BaseWalkSAT {

    public static class SparrowSettings extends BaseWalkSatSettings {
        private double scoreBaseExp = 4; // c1
        private double ageExponent = 4;  // c2
        private double ageDivisor = 1e5; // c3
        /**
         * @return the scoreBaseExp
         */
        public double getScoreBaseExp() {
            return scoreBaseExp;
        }
        /**
         * @param scoreBaseExp the scoreBaseExp to set
         */
        public void setScoreBaseExp(double scoreBaseExp) {
            this.scoreBaseExp = scoreBaseExp;
        }
        /**
         * @return the ageExponent
         */
        public double getAgeExponent() {
            return ageExponent;
        }
        /**
         * @param ageExponent the ageExponent to set
         */
        public void setAgeExponent(double ageExponent) {
            this.ageExponent = ageExponent;
        }
        /**
         * @return the ageDivisor
         */
        public double getAgeDivisor() {
            return ageDivisor;
        }
        /**
         * @param ageDivisor the ageDivisor to set
         */
        public void setAgeDivisor(double ageDivisor) {
            this.ageDivisor = ageDivisor;
        }
    }
    
	
	private int time = 0;
	private int[] ages = null;
	
	private SparrowSettings settings;
	
	public Sparrow(SparrowSettings settings) {
        super(settings);
	    this.settings = settings;
	}
	
	public Sparrow() {
	    this(new SparrowSettings());
	}

    @Override
    protected void prepareForFlipping() {
        if (ages == null || ages.length != variables) {
            ages = new int[variables+1];
        }
        Arrays.fill(ages, 0);
        time = 0;
    }
	
	@Override
	protected int selectLiteralToFlip(int[] candidates) {
	    time++;
	    double[] values = new double[candidates.length];
	    double totalValue = 0;
	    int i = 0;
	    
		for (int lit : candidates) {
		    int var = Math.abs(lit);
		    int score = 0;
		    //score += clManager.computeMakecount(lit); 
		    score -= clManager.computeBreakCount(lit);
		    double age = ages[var] - time;
		    
		    double ps = Math.pow(settings.scoreBaseExp, score);
		    double pa = Math.pow((age / settings.ageDivisor), settings.ageExponent) + 1;
		    
		    values[i] = ps * pa;
		    i++;
		    totalValue += ps * pa;
		}
		double rand = random.nextDouble();
		for (int k = 0; k < i; k++) {
		    double d = values[k] / totalValue;
		    if (d > rand) {
		        return candidates[k];
		    } else {
		        rand -= d;
		    }
		}	
		return candidates[candidates.length - 1];
	}

}
