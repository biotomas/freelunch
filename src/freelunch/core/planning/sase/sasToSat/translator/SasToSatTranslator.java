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
package freelunch.core.planning.sase.sasToSat.translator;

import java.util.List;

import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;


/**
 * Interface for SAT encodings of the SAS planning problem for 
 * the purposes of incremental solver.
 *
 * @author Tomas Balyo
 * 31.10.2012
 */
public interface SasToSatTranslator {
    
    /**
     * Construct a CNF formula for the given makespan.
     * @param makespan
     * @return
     */
    public BasicSatFormula makeFormulaForMakespan(int makespan);
	
	/**
	 * Add constraints that enforce the initial state to hold.
	 * @param solver
	 */
	public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException;
	
	/**
	 * Add constraints that must hold in each individual encoded 
	 * step of the plan for the given time.
	 * @param solver
	 * @param time
	 */
	public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException;
	
	/**
	 * Add constraints that must hold for two consecutive encoded steps.
	 * Namely for steps time and time+1.
	 * @param solver
	 * @param time
	 */
	public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException;
	
	/**
	 * Add goal constraints for the given time.
	 * @param solver
	 * @param time
	 */
	public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException;
	
	/**
	 * Remove the last added goal constraints.
	 * @param solver
	 */
	public void unsetLastGoalStateConstraints(IncrementalSatSolver solver);
	
	/**
	 * Set the maximum allowed time returns the number of SAT variables
	 * @param time
	 * @return
	 */
	public int setMaxTimespan(int time);
	
	/**
	 * Extract the parallel plan from a given SAT model
	 * @param model
	 * @return
	 */
	public SasParallelPlan decodePlan(int[] model, int makespan);
	
	/**
	 * Extract the parallel plan from a Symbolic reachability model
	 * @param model
	 * @return
	 */
	public SasParallelPlan decodePlan(List<int[]> model);

	public List<Integer> getActionVariables();

}
