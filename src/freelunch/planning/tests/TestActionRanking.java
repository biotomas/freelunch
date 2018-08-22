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
package freelunch.planning.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.planning.benchmarking.problemGenerator.MultiRobotPathPlanningGenerator;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.TimeoutException;
import freelunch.planning.optimizer.PlanVerifier;
import freelunch.planning.planners.Planner;
import freelunch.planning.planners.satplan.iterative.IterativeSatBasedSolver;
import freelunch.planning.planners.satplan.translator.DirectExistStepTranslator;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.ExternalSatSolver;
import freelunch.sat.model.Sat4JSolver;
import freelunch.sat.model.SatSolver;
import junit.framework.TestCase;

public class TestActionRanking extends TestCase {
    
    public void testExternalSolver() {
        SatSolver solver = new ExternalSatSolver();
        
        List<int[]> clauses = new ArrayList<int[]>();
        clauses.add(new int[] {-1, 3});
        clauses.add(new int[] {-2});
        clauses.add(new int[] {1, 2});
        
        CnfSatFormula fla = new CnfSatFormula(5, clauses);
        
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
            Planner solver = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(problem));
            SasParallelPlan plan = solver.solve();
            boolean valid = PlanVerifier.verifyPlan(problem, plan);
            assertTrue(valid);
            totalLength += plan.getPlanLength();
            System.out.println(plan);
        }
        System.out.println(totalLength);
    }
}
