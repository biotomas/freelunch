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

import java.util.Arrays;

/**
 * Local Search Clause
 *
 * @author Tomas Balyo
 * Jul 8, 2012
 */
public class ReachClause {
    
    private int weight;    
	private int[] literals;
	private int unsatClauseIndex;
	private int satLiterals;
	
	public ReachClause(int[] literals, int weight) {
		this.literals = literals;
        this.weight = weight;
		unsatClauseIndex = -42;
		satLiterals = 0;
	}
	
	public int[] getLiterals() {
		return literals;
	}

	/**
	 * @return the unsatClauseIndex
	 */
	public int getUnsatClauseIndex() {
		return unsatClauseIndex;
	}

	/**
	 * @param unsatClauseIndex the unsatClauseIndex to set
	 */
	public void setUnsatClauseIndex(int unsatClauseIndex) {
		this.unsatClauseIndex = unsatClauseIndex;
	}

	/**
	 * @return the satLiterals
	 */
	public int getSatLiterals() {
		return satLiterals;
	}

	/**
	 * @param satLiterals the satLiterals to set
	 */
	public void setSatLiterals(int satLiterals) {
		this.satLiterals = satLiterals;
	}
	
	@Override
	public String toString() {
	    return String.format("w=%d lits=%s", weight, Arrays.toString(literals));
	}

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
