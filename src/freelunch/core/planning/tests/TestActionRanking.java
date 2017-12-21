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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.problemGenerator.MultiRobotPathPlanningGenerator;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.ExternalSatSolver;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.SatSolver;

public class TestActionRanking extends TestCase {
    
    public void testExternalSolver() {
        SatSolver solver = new ExternalSatSolver();
        
        List<int[]> clauses = new ArrayList<int[]>();
        clauses.add(new int[] {-1, 3});
        clauses.add(new int[] {-2});
        clauses.add(new int[] {1, 2});
        
        BasicSatFormula fla = new BasicSatFormula(5, clauses);
        
        try {
            boolean sat = solver.isSatisfiable(fla);
            System.out.println(sat);
            System.out.println(Arrays.toString(solver.getModel()));
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void testProblem() throws TimeoutException, NonexistentPlanException {
        int totalLength = 0;
        for (int i = 0; i < 30; i++) {
            SasProblem problem = MultiRobotPathPlanningGenerator.generateProblem(6, 40, 4, 2013+i);
            Solver solver = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(problem));
            SasParallelPlan plan = solver.solve();
            boolean valid = PlanVerifier.verifyPlan(problem, plan);
            assertTrue(valid);
            totalLength += plan.getPlanLength();
            System.out.println(plan);
        }
        System.out.println(totalLength);
    }
}
