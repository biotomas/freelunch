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
package freelunch.core.satSolving.symbolicReachability;

import java.util.ArrayList;

public class SymbolicReachVerifier {
	
	public boolean solutionValid(SymbolicReachabilityProblem problem, ArrayList<int[]> solution) {
	    boolean valid;
		// verify the initial state
	    int[] assignment = solution.get(0);
	    for (int[] cl : problem.initialConditions.getClauses()) {
	        valid = clauseSat(cl, assignment);
	        if (valid == false) {
	            System.err.println("Verification failed for initial state conditions");
	            return false;
	        }
	    }
	    // verify the goal state
	    assignment = solution.get(solution.size() - 1);
	    for (int[] cl : problem.goalConditions.getClauses()) {
            valid = clauseSat(cl, assignment);
            if (valid == false) {
                System.err.println("Verification failed for goal state conditions");
                return false;
            }
	    }
	    // verify the universal state
	    for (int i = 0; i < solution.size(); i++) {
	        assignment = solution.get(i);
	        for (int[] cl : problem.universalConditions.getClauses()) {
	            valid = clauseSat(cl, assignment);
	            if (valid == false) {
	                System.err.println("Verification failed for universal state conditions");
	                return false;
	            }
	        }
	    }
		// verify the transitional state
	    for (int step = 0; step < solution.size() - 1; step++) {
	        int signature = problem.initialConditions.getVariables();
	        assignment = new int[2*signature+2];
	        for (int l = 0; l <= signature; l++) {
	            assignment[l] = solution.get(step)[l];
	        }
	        for (int l = signature+1; l <= 2*signature; l++) {
	            int lit = solution.get(step+1)[l - signature];
	            assignment[l] = lit > 0 ? lit + signature : lit - signature;
	        }
	        for (int[] cl : problem.transitionConditions.getClauses()) {
	            valid = clauseSat(cl, assignment);
                if (valid == false) {
                    System.err.println("Verification failed for transitional state conditions");
                    return false;
                }
	        }
	    }
		// everything was valid
		return true;
	}
	
	private boolean clauseSat(int[] cl, int[] assignment) {
	    for (int lit : cl) {
	        int var = Math.abs(lit);
	        if (lit == assignment[var]) {
	            return true;
	        }
	    }
        //System.err.println(Arrays.toString(cl));
        //System.err.println(Arrays.toString(assignment));
	    return false;
	}

}
