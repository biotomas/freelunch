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

/**
 * Selects a node in the tree that has best average historical performance.
 * 
 * @author Vojtech Bardiovsky
 * 
 */
public class SelectBestTreePolicy implements TreePolicy {

    @Override
    public int selectAction(int n, int[] numChosen, double[] rewards) {
        double max = Double.NEGATIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < rewards.length; i++) {
            if (rewards[i] / numChosen[i] > max) {
                max = rewards[i] / numChosen[i];
                index = i;
            }
        }
        return index;
    }

}
