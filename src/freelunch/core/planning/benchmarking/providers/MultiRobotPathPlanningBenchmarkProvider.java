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
package freelunch.core.planning.benchmarking.providers;

import freelunch.core.planning.benchmarking.BenchmarkProvider;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.problemGenerator.MultiRobotPathPlanningGenerator;

public class MultiRobotPathPlanningBenchmarkProvider implements BenchmarkProvider {
    
    private int extraVertices;
    private int extraVerticesFrom;
    private int extraVerticesTo;
    private int robots;
    private int robotsTo;
    private boolean last = false;


    public MultiRobotPathPlanningBenchmarkProvider(int extraVerticesFrom, int extraVerticesTo, int robotsFrom, int robotsTo) {
        super();
        this.extraVerticesFrom = extraVerticesFrom;
        this.extraVerticesTo = extraVerticesTo;
        this.robots = robotsFrom;
        this.robotsTo = robotsTo;
        extraVertices = extraVerticesFrom;
    }


    @Override
    public SasProblem getNext() {
        if (last) {
            return null;
        }
        int vertices = robots + extraVertices;
        int edges = (vertices * vertices) / 2; 
        SasProblem problem = MultiRobotPathPlanningGenerator.generateProblem(vertices, edges, robots, 2013);
        String description = String.format("multibot-V=%d,E=%d,R=%d", vertices, edges, robots);
        problem.setDescription(description);
        
        extraVertices++;
        if (extraVertices == extraVerticesTo) {
            extraVertices = extraVerticesFrom;
            robots++;
            if (robots == robotsTo) {
                last = true;
            }
        }
        return problem;
    }

}
