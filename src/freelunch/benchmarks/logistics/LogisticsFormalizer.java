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

import freelunch.benchmarks.PlanningProblemFormalizer;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;

public class LogisticsFormalizer implements PlanningProblemFormalizer<Logistics> {

    @Override
    public SasProblemBuilder formalize(Logistics logistics) {
        // Initialize the planning problem
        SasProblemBuilder problem = new SasProblemBuilder();

        // ==========================
        // Create the state variables
        // ==========================

        // State variables of truck locations
        // domain size = number of locations
        StateVariable truckLocation[] = new StateVariable[logistics.numTrucks];
        for (int i = 0; i < logistics.numTrucks; i++) {
            truckLocation[i] = problem.newVariable("t" + i, logistics.numLocations);
        }

        // state variables of package locations
        // a package can be either at one of the locations
        // or on one of the trucks
        // domain size = number of location + number of trucks
        StateVariable[] packageLocation = new StateVariable[logistics.numPackages];
        for (int i = 0; i < logistics.numPackages; i++) {
            packageLocation[i] = problem.newVariable("p" + i, logistics.numLocations + logistics.numTrucks);
        }

        // ==============================
        // Define the actions (operators)
        // ==============================

        // Move truck actions for each truck
        for (int i = 0; i < logistics.numTrucks; i++) {
            for (int j = 0; j < logistics.numLocations; j++) {
                for (int k = 0; k < logistics.numLocations; k++) {
                    if (logistics.locationTruckReachability[j][k]) {
                        addMoveTruckAction(problem, truckLocation[i], j, k);
                    }
                }
            }
        }

        // Load and unload package actions for each package, location and truck
        for (int pack = 0; pack < logistics.numPackages; pack++) {
            for (int location = 0; location < logistics.numLocations; location++) {
                for (int truck = 0; truck < logistics.numTrucks; truck++) {
                    // loads
                    addLoadPackageAction(problem, truckLocation[truck], packageLocation[pack], location);
                    // unloads
                    addUnloadPackageAction(problem, truckLocation[truck], packageLocation[pack], location);
                }
            }
        }

        // ================================
        // Define the initial state
        // ================================

        // truck i starts at location initialTruckLocation[i]
        for (int i = 0; i < logistics.numTrucks; i++) {
            problem.addInitialStateCondition(new Condition(truckLocation[i], logistics.initialTruckLocations[i]));
        }

        // package i is at location initialPackageLocation[i]
        for (int i = 0; i < logistics.numPackages; i++) {
            problem.addInitialStateCondition(new Condition(packageLocation[i], logistics.initialPackageLocations[i]));
        }

        // =================================
        // Define the goal state
        // =================================

        // we do not care about the final location of the trucks
        // we define the desired locations of the packages
        // the desired location of package i is goalPackageLocation[i]
        for (int i = 0; i < logistics.numPackages; i++) {
            problem.addGoalCondition(new Condition(packageLocation[i], logistics.goalPackageLocations[i]));
        }
        return problem;
    }

    private void addMoveTruckAction(SasProblemBuilder problem, StateVariable truckLocation, int from, int to) {
        // Create a new operator named like MoveTruck-1:3->1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Move-%s:%d->%d", truckLocation.getName(), from, to)));
        // add the preconditions - the truck must be at the "from" location
        op.getPreconditions().add(new Condition(truckLocation, from));
        // add the effects - the truck is at the "to" location
        op.getEffects().add(new Condition(truckLocation, to));
    }

    private void addLoadPackageAction(SasProblemBuilder problem, StateVariable truckLocation, StateVariable packageLocation, int location) {
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-%s-on-%s-at-%d", packageLocation.getName(), truckLocation.getName(), location)));
        // the precondition - the package is at the correct location
        op.getPreconditions().add(new Condition(packageLocation, location));
        // the effect - the package location changes to the truck (locations 5,6,7 are trucks 0,1,2)
        op.getEffects().add(new Condition(packageLocation, truckLocation.getId() + 5));
        // the prevailing precondition - the truck must remain at the location
        op.getPreconditions().add(new Condition(truckLocation, location));
    }

    private void addUnloadPackageAction(SasProblemBuilder problem, StateVariable truckLocation, StateVariable packageLocation, int location) {
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-%s-from-%s-at-%d", packageLocation.getName(), truckLocation.getName(), location)));
        // the precondition - the package is on the correct truck (locations 5,6,7 are trucks 0,1,2)
        op.getPreconditions().add(new Condition(packageLocation, truckLocation.getId() + 5));
        // the effect - the package location changes to the location 
        op.getEffects().add(new Condition(packageLocation, location));
        // the prevailing precondition - the truck must remain at the location
        op.getPreconditions().add(new Condition(truckLocation, location));
    }

}
