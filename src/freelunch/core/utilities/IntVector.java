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
package freelunch.core.utilities;

import java.util.Arrays;
import java.util.List;

public class IntVector {

    private int[] vector;
    private int last;

    /**
     * Create a new INT vector of the given initial size
     * @param initSize
     */
    public IntVector(int initSize) {
        vector = new int[initSize];
        last = 0;
    }
    
    public static IntVector makeUnit(int lit) {
    	IntVector iv = new IntVector(1);
    	iv.add(lit);
    	return iv;
    }
    
    public IntVector(int[] vector) {
        this.vector = vector;
        last = vector.length;
    }

    /**
     * Add x to the vector
     * @param x
     */
    public void add(int x) {
        if (last >= vector.length) {
            vector = Arrays.copyOf(vector, 2*last);
        }
        vector[last] = x;
        last++;
    }

    /**
     * Clear the array
     */
    public void clear() {
        last = 0;
    }

    /**
     * Returns a copy of the generated array, the vector can be
     * reused after calling a clear
     * @return
     */
    public int[] getArrayCopy() {
        return Arrays.copyOf(vector, last);
    }
    
    public static int[] listToArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        int i = 0;
        for (int e : list) {
            ret[i++] = e;
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(vector);
    }
    
}
