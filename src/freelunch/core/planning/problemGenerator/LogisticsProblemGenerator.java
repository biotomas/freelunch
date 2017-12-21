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
package freelunch.core.planning.problemGenerator;

import java.util.HashSet;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;
import freelunch.core.utilities.Pair;

public class LogisticsProblemGenerator {
    
    /**
     * Generate an example logistics planning problem.
     * @return the planning problem
     */
    public static SasProblemBuilder generateProblem(int trucks, int packages, int locations, int extraRoads) {

        SasProblemBuilder problem = new SasProblemBuilder();

        StateVariable truckLocation[] = new StateVariable[trucks];
        for (int i = 0; i < trucks; i++) {
            truckLocation[i] = problem.newVariable("t" + i, locations);
        }

        StateVariable[] packageLocation = new StateVariable[packages];
        for (int i = 0; i < packages; i++) {
            packageLocation[i] = problem.newVariable("p" + i, trucks+locations);
        }
        
        // move actions
        Set<Pair<Integer>> edges = new HashSet<Pair<Integer>>();
        for (int truck = 0; truck < trucks; truck++) {
            edges.clear();
            // generate a random connected graph
            // start by a cycle
            for (int loc = 0; loc < locations; loc++) {
                addMoveTruckAction(problem, truckLocation[truck], loc, (loc + 1) % locations);
                edges.add(new Pair<Integer>(loc, (loc+1) % locations));
            }
            // add extra shortcut edges
            for (int road = 0; road < extraRoads; road++) {
                int from = (3 * road) % locations;
                int to = (2 * road) % locations;
                if (from != to) {
                    Pair<Integer> edge = new Pair<Integer>(from, to);
                    if (edges.contains(edge)) {
                        continue;
                    }
                    addMoveTruckAction(problem, truckLocation[truck], from, to);
                    edges.add(edge);
                }
            }
        }

        // Load and unload package actions for each package, location and truck
        for (int pack = 0; pack < packages; pack++) {
            for (int location = 0; location < locations; location++) {
                for (int truck = 0; truck < trucks; truck++) {
                    // loads
                    addLoadPackageAction(problem, truckLocation[truck], packageLocation[pack], location, locations);
                    // unloads
                    addUnloadPackageAction(problem, truckLocation[truck], packageLocation[pack], location, locations);
                }
            }
        }

        // initial truck locations
        for (int truck = 0; truck < trucks; truck++) {
            problem.addInitialStateCondition(new Condition(truckLocation[truck], truck % locations));
        }

        // initial package locations
        for (int i = 0; i < packages; i++) {
            problem.addInitialStateCondition(new Condition(packageLocation[i], (3*i) % locations));
        }

        // goal conditions, goal package locations
        for (int i = 0; i < packages; i++) {
            problem.addGoalCondition(new Condition(packageLocation[i], (1 + 2*i) % locations));
        }

        return problem;
    }

    private static void addMoveTruckAction(SasProblemBuilder problem, StateVariable truckLocation, int from, int to) {
        SasAction op = problem.newAction(new StringActionInfo(String.format("MoveTruck-%s:%d->%d", truckLocation.getName(), from, to)));
        op.getPreconditions().add(new Condition(truckLocation, from));
        op.getEffects().add(new Condition(truckLocation, to));
        op.setCost(1);
    }

    private static void addLoadPackageAction(SasProblemBuilder problem, StateVariable truckLocation, StateVariable packageLocation, int location, int locations) {
        SasAction op = problem.newAction(new StringActionInfo(String.format("LoadPackage-%s-onTruck-%s-at-%d", packageLocation.getName(), truckLocation.getName(), location)));
        op.getPreconditions().add(new Condition(packageLocation, location));
        op.getPreconditions().add(new Condition(truckLocation, location));
        op.getEffects().add(new Condition(packageLocation, truckLocation.getId() + locations));
        op.setCost(10);
    }

    private static void addUnloadPackageAction(SasProblemBuilder problem, StateVariable truckLocation, StateVariable packageLocation, int location, int locations) {
        SasAction op = problem.newAction(new StringActionInfo(String.format("UnloadPackage-%s-onTruck-%s-at-%d", packageLocation.getName(), truckLocation.getName(), location)));
        op.getPreconditions().add(new Condition(packageLocation, truckLocation.getId() + locations));
        op.getPreconditions().add(new Condition(truckLocation, location));
        op.getEffects().add(new Condition(packageLocation, location));
        op.setCost(5);
    }
    


}
