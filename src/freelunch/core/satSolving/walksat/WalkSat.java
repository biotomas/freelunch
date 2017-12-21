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


public class WalkSat extends BaseWalkSAT {
    
	private float randomWalkProb = 0.01f;
	
	public WalkSat(BaseWalkSatSettings settings) {
	    super(settings);
	}

	public WalkSat() {
	    super();
	}
	
	@Override
	protected int selectLiteralToFlip(int[] candidates) {
		int bestLit = 0;
		int bestScore = Integer.MIN_VALUE;
		int worstLit = 0;
		int worstScore = Integer.MAX_VALUE;
		
		for (int lit : candidates) {
			int var = Math.abs(lit);
			int score = clManager.computeMakecount(lit) - clManager.computeBreakCount(lit) - flipRates[var];
			if (score > bestScore) {
				bestScore = score;
				bestLit = lit;
			}
			if (score < worstScore) {
				worstScore = score;
				worstLit = lit;
			}
		}
		
		int lit = bestLit;
		if (random.nextFloat() <= randomWalkProb || lit == 0) {
			lit = worstLit;
		}
		return lit;
	}

    @Override
    protected void prepareForFlipping() {
    }

}
