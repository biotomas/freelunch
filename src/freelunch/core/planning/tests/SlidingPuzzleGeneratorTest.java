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
package freelunch.core.planning.tests;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.problemGenerator.SlidingPuzzleGenerator;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import junit.framework.TestCase;

public class SlidingPuzzleGeneratorTest extends TestCase {
    
    public void testBasicExample() {
        SlidingPuzzleGenerator gen = new SlidingPuzzleGenerator();
        SasProblem problem = gen.generateSolvableProblem(2, 2, 1).getSasProblem();
        System.out.println("ACTIONS");
        System.out.println(problem.getOperators());
        System.out.println("total actions: " + problem.getOperators().size());
        System.out.println("INITIAL STATE");
        System.out.println(problem.getInitialState());
        System.out.println("GOAL");
        System.out.println(problem.getGoal());
    }
    
    public void testWithPlanner() throws NonexistentPlanException, TimeoutException {
        SlidingPuzzleGenerator gen = new SlidingPuzzleGenerator();
        SasProblem prob = gen.generateSolvableProblem(4, 4, 17).getSasProblem();
        IncrementalSolver solver = new IncrementalSolver(prob);
        SasParallelPlan plan = solver.solve();
        System.out.println("INITIAL STATE");
        System.out.println(prob.getInitialState());
        System.out.println(plan);
    }

}
