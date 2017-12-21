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
package freelunch.core.satModelling.tests;

import freelunch.core.satModelling.intModellers.IntVarGroupManager;
import freelunch.core.satModelling.intModellers.IntVarGroupManager.IntVarGroup;
import junit.framework.TestCase;

public class IntVarGroupTest extends TestCase {

    public void testIntVarGroup() {
        IntVarGroupManager m = new IntVarGroupManager();
        IntVarGroup g1 = m.createNewVarGroup(1);
        IntVarGroup g2 = m.createNewVarGroup(2);
        IntVarGroup g3 = m.createNewVarGroup(3);
        g1.setDimensionSize(0, 10);
        g2.setDimensionSize(0, 5);
        g2.setDimensionSize(1, 10);
        g3.setDimensionSize(0, 5);
        g3.setDimensionSize(1, 6);
        g3.setDimensionSize(2, 7);

        System.out.println(g1.getVariable(5));
        System.out.println(g2.getVariable(2,2));
        System.out.println(g3.getVariable(2,2,2));
        System.out.println(m.getTotalVarsCount());
        g1.setDimensionSize(0, 20);
        System.out.println(g1.getVariable(5));
        System.out.println(g2.getVariable(2,2));
        System.out.println(g3.getVariable(2,2,2));
        System.out.println(m.getTotalVarsCount());
    }

}
