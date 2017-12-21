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
package freelunch.core.satSolving;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;

public interface SatSolver {
    
    /**
     * Test if the formula is satisfiable
     * @return true if satisfiable, false if unsatisfiable, null if unknown
     */
    public Boolean isSatisfiable(BasicSatFormula formula) throws TimeoutException;
    
    /**
     * Get the time required to solve the formula in milliseconds.
     * @return time in milliseconds
     */
    public long getSolveTime();
    /**
     * Get the satisfying assignment of the formula if exists and is known.
     * The model format is array[var] > 0 if true, array[var] < 0 if false and array[var] = 0 otherwise 
     * @return formula model or null
     */
    public int[] getModel();

    /**
     * Set the solving timeout in seconds
     * @param seconds
     */
    public void setTimeout(int seconds);

}
