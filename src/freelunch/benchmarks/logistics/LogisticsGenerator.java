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
package freelunch.benchmarks.logistics;

import freelunch.benchmarks.PlanningProblemGenerator;

public class LogisticsGenerator implements PlanningProblemGenerator<Logistics> {

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public Logistics getNext() {
        return null;
    }

    @Override
    public Logistics[] getProblems() {
        return new Logistics[] { LOGISTICS_1 };
    }

    @Override
    public String[] getNames() {
        return new String[] { "Logistics 1" };
    }

    private final static Logistics LOGISTICS_1 = new Logistics(5, 10, 3,
            new int[] { 0, 1, 2, 3, 4, 0, 1, 2, 3, 4 },
            new int[] { 0, 4, 2 },
            new int[] { 2, 3, 4, 0, 1, 2, 3, 4, 0, 1 },
            generateReachabilityMatrix(
                    5,
                    new int[] { 0, 1 },
                    new int[] { 1, 0 },
                    new int[] { 1, 2 },
                    new int[] { 2, 3 },
                    new int[] { 3, 1 },
                    new int[] { 3, 4 },
                    new int[] { 4, 3 }
            )
            );

    private static boolean[][] generateReachabilityMatrix(int size, int[]... edges) {
        boolean[][] matrix = new boolean[size][size];
        for (int[] edge : edges) {
            matrix[edge[0]][edge[1]] = true;
        }
        return matrix;
    }
}
