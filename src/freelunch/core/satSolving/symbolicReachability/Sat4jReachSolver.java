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
package freelunch.core.satSolving.symbolicReachability;

import java.util.ArrayList;
import java.util.Arrays;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.IntVector;


public class Sat4jReachSolver implements SymbolicReachabilitySolver {

    @Override
    public ArrayList<int[]> solve(SymbolicReachabilityProblem problem) {
        int size = 1;
        ArrayList<int[]> res = solveProblemForSize(problem, size);
        while (res == null) {
            size++;
            res = solveProblemForSize(problem, size);
        }
        return res;
    }
    
    private ArrayList<int[]> solveProblemForSize(SymbolicReachabilityProblem prob, int n) {
        BasicSatFormula f = prob.makeFormulaForMakespan(n);
        try {
            ArrayList<int[]> res = solveFormula(f, n);
            return res;
        } catch (SatContradictionException e) {
            return null;
        } catch (TimeoutException e) {
            return null;
        }
    }
    
    private ArrayList<int[]> solveFormula(BasicSatFormula f, int n) throws SatContradictionException, TimeoutException {
        IncrementalSatSolver solver = new Sat4JSolver();
        solver.setVariablesCount(f.getVariables());
        for (int[] cl : f.getClauses()) {
            solver.addNewClause(new IntVector(cl));
        }
        if (solver.isSatisfiable()) {
            int[] model = solver.getModel();
            ArrayList<int[]> res = new ArrayList<int[]>(n);
            int sig = f.getVariables() / n;
            for (int i = 0; i < n; i++) {
                int[] subModel = Arrays.copyOfRange(model, i*sig, 1 + (i+1)*sig);
                for (int j = 1; j <= sig; j++) {
                    int lit = subModel[j];
                    subModel[j] = lit > 0 ? lit - sig*i : lit + sig*i;
                }
                res.add(subModel);
            }
            return res;
        }
        return null;
    }
}
