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
package freelunch.core.utilities.tests;

import freelunch.core.utilities.IntVector;
import junit.framework.TestCase;

public class UtilitiesTest extends TestCase {

    public void testIntVector() {
        IntVector iv = new IntVector(5);

        for (int i = 0; i < 21; i++) {
            iv.add(i);
        }

        int[] arr = iv.getArrayCopy();

        assertEquals(21, arr.length);

        iv.clear();

        for (int i = 0; i < 7; i++) {
            iv.add(i*2+7);
        }

        int[] arr2 = iv.getArrayCopy();

        assertEquals(7, arr2.length);

        for (int i = 0; i < 7; i++) {
            assertEquals(i*2+7, arr2[i]);
            iv.add(i);
        }
    }
}
