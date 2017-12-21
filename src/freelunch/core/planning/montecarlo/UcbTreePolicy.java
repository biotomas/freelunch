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
 * Selects a node in the tree that has best average historical performance, but
 * disadvantages nodes that have been tried many times.
 * 
 * @author Vojtech Bardiovsky
 * 
 */
public class UcbTreePolicy implements TreePolicy {

    public static final double DEFAULT_C = 0.05;

    private final double C;

    /**
     * Provide the exploration/exploitaion coefficient.
     * 
     * @param C higher value means to prefer exploration.
     */
    public UcbTreePolicy(double C) {
        this.C = C;
    }

    /**
     * Default constructor for the UCB policy. Exploration/exploitation
     * coefficient is set to default.
     */
    public UcbTreePolicy() {
        this(DEFAULT_C);
    }

    @Override
    public int selectAction(int n, int[] numChosen, double[] rewards) {
        double max = Double.NEGATIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < rewards.length; i++) {
            double val = rewards[i] + 2 * C * Math.sqrt(2 * Math.log(n) / (numChosen[i] + 1));
            if (val > max) {
                max = val;
                index = i;
            }
        }
        return index;
    }

}
