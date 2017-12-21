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

import java.util.List;

import freelunch.benchmarks.PlanningProblem;
import freelunch.core.planning.model.ActionInfo;



public class Logistics implements PlanningProblem {

    int numLocations;
    int numPackages;
    int numTrucks;
    int[] initialPackageLocations;
    int[] initialTruckLocations;
    int[] goalPackageLocations;
    boolean[][] locationTruckReachability;

    public Logistics(int numLocations, int numPackages, int numTrucks, int[] initialPackageLocations, int[] initialTruckLocations, int[] goalPackageLocations, boolean[][] locationTruckReachability) {
        this.numLocations = numLocations;
        this.numPackages = numPackages;
        this.numTrucks = numTrucks;
        this.initialPackageLocations = initialPackageLocations;
        this.initialTruckLocations = initialTruckLocations;
        this.goalPackageLocations = goalPackageLocations;
        this.locationTruckReachability = locationTruckReachability;
    }

    @Override
    public void applyActions(List<ActionInfo> actions) {
        // TODO implement method to enable visualization
    }

    @Override
    public PlanningProblem copy() {
        // TODO implement method to enable visualization
        return null;
    }

}
