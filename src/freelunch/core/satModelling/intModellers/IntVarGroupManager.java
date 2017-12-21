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
package freelunch.core.satModelling.intModellers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntVarGroupManager {

    public class IntVarGroup {

        /**
         * This recursive structure represents an array of
         * variables or an array of VariableVectors.
         */
        private class VariableVector {
            // only one of the arrays should be initialized
            private VariableVector[] subVectors = null;
            private int[] variables = null;
        }

        private int dimensions;
        private int[] dimensionSizes;
        /**
         * Indicates if the variable IDs need to be recomputed
         */
        private boolean dirty;
        private VariableVector variableVector;

        private IntVarGroup(int dimensions) {
            this.dimensions = dimensions;
            this.dimensionSizes = new int[dimensions];
            dirty = true;
        }

        public void setDimensionSize(int dimension, int newRange) {
            if (dimensionSizes[dimension] >= newRange) {
                return;
            }
            dimensionSizes[dimension] = newRange;
            dirty = true;
        }

        public int getVariable(int ... coordinates) {
            if (dirty) {
                recomputeIds();
            }
            VariableVector v = variableVector;
            int len = coordinates.length - 1;
            for (int i = 0; i < len; i++) {
                v = v.subVectors[coordinates[i]];
            }
            return v.variables[coordinates[len]];
        }

        private void recomputeIds() {
            variableVector = fixVariableVector(variableVector, 0);
            dirty = false;
        }

        private VariableVector fixVariableVector(VariableVector vec, int dimension) {
            if (vec == null) {
                vec = new VariableVector();
            }
            int dimensionSize = dimensionSizes[dimension];
            // variable vector
            if (dimension+1 < dimensions) {
                // if allocation needed
                if (vec.subVectors == null) {
                    vec.subVectors = new VariableVector[dimensionSize];
                }
                // if reallocation needed
                if (vec.subVectors.length < dimensionSize) {
                    vec.subVectors = Arrays.copyOf(vec.subVectors, dimensionSize*2);
                }
                // update subvectors
                for (int i = 0; i < dimensionSize; i++) {
                    vec.subVectors[i] = fixVariableVector(vec.subVectors[i], dimension + 1);
                }
            } else {
                // if allocation needed
                if (vec.variables == null) {
                    vec.variables = new int[dimensionSize];
                    Arrays.fill(vec.variables, 0);
                }
                // if reallocation needed
                if (vec.variables.length < dimensionSize) {
                    vec.variables = Arrays.copyOf(vec.variables, dimensionSize*2);
                }
                // add IDs if needed
                for (int i = dimensionSize-1; i >= 0; i--) {
                    if (vec.variables[i] != 0) {
                        break;
                    }
                    lastUsedId++;
                    vec.variables[i] = lastUsedId;
                }
            }
            return vec;
        }
    }

    private List<IntVarGroup> varGroups;
    private int lastUsedId;

    public IntVarGroupManager() {
        varGroups = new ArrayList<IntVarGroup>();
        lastUsedId = 0;
    }

    public IntVarGroup createNewVarGroup(int dimensions) {
        IntVarGroup ng = new IntVarGroup(dimensions);
        varGroups.add(ng);
        return ng;
    }

    public int getTotalVarsCount() {
        for (IntVarGroup vg : varGroups) {
            if (vg.dirty) {
                vg.recomputeIds();
            }
        }
        return lastUsedId;
    }

}
