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
package freelunch.core.planning.problemGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;
import freelunch.core.utilities.Pair;

/**
 * This planning domain describes the problem of moving multiple robots
 * on an oriented graph. At most one robot can be at any given vertex. 
 *
 * @author Tomas Balyo
 * 2.7.2013
 */
public class MultiRobotPathPlanningGenerator {

    /**
     * A random graph with multiple robots moving around
     * At most one robot can be at any given location
     * @param vertices must be more than robots
     * @param edges
     * @param robots must be less than vertices
     * @return planning problem
     */
    public static SasProblem generateProblem(int vertices, int edges, int robots, long seed) {
        if (robots >= vertices) {
            throw new IllegalArgumentException("There must be more vertices than robots.");
        }
        SasProblemBuilder pb = new SasProblemBuilder();
        StateVariable[] vertexTaken = new StateVariable[vertices];
        StateVariable[] robotLocation = new StateVariable[robots];
        for (int i = 0; i < robots; i++) {
            robotLocation[i] = pb.newVariable("var"+i, vertices);
            pb.addInitialStateCondition(new Condition(robotLocation[i], i));
            pb.addGoalCondition(new Condition(robotLocation[i], (i + 1) % vertices));
        }
        for (int i = 0; i < vertices; i++) {
            vertexTaken[i] = pb.newVariable("taken"+i, 2);
            pb.addInitialStateCondition(new Condition(vertexTaken[i], i < robots ? 1 : 0));
        }
        
        Random rnd = new Random(seed);
        Set<Pair<Integer>> edgeSet = new HashSet<Pair<Integer>>();
        // actions
        for (int edge = 0; edge < edges; edge++) {
            
            int start = rnd.nextInt(vertices);
            int end = rnd.nextInt(vertices);
            while (end == start) {
                end = rnd.nextInt(vertices);
            }
            
            
            Pair<Integer> e = new Pair<Integer>(start, end);
            if (edgeSet.contains(e)) {
                continue;
            }
            edgeSet.add(e);
            
            // create a move action
            for (int robot = 0; robot < robots; robot++) {
                SasAction move = pb.newAction(new StringActionInfo(String.format("move %d from %d to %d", robot, start, end)));
                move.getPreconditions().add(new Condition(robotLocation[robot], start));
                move.getPreconditions().add(new Condition(vertexTaken[end], 0));
                move.getEffects().add(new Condition(robotLocation[robot], end));
                move.getEffects().add(new Condition(vertexTaken[end], 1));
                move.getEffects().add(new Condition(vertexTaken[start], 0));
            }
        }
        return pb.getSasProblem();
    }

}
