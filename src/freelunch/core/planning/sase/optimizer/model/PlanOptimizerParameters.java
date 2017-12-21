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
package freelunch.core.planning.sase.optimizer.model;

/**
 * Parameters and settings for plan optimization.
 *
 * @author Tomas Balyo
 * Mar 16, 2012
 */
public class PlanOptimizerParameters {
	
	/**
	 * Algorithms for selection of sub-plans from a sup-optimal plan
	 * for optimization. Default is Turbo. 
	 *
	 * @author Tomas Balyo
	 * Mar 16, 2012
	 */
	public enum SelectionAlgorithm {
		randomFixedWindow,
		systematic,
		turbo,
		fullrandom,
		exponential,
		limitedrandom,
		timedwindows
	}

	/**
	 * File name of the improved plan to output
	 */
	private String intermediatePlanFileName = null;
	
	/**
	 * Increase window sizes until they take more time than this
	 * in seconds
	 */
	private int minWindowTime;

	private SelectionAlgorithm windowSelectionAlgorithm;
	/**
	 * Length of the sub-plan to optimize. Default value is 15.
	 */
	private int windowSize;
	/**
	 * Systematic selection shifts the window by this number of steps.
	 * Default value is 8.
	 */
	private int windowShift;
	/**
	 * Number of iterations of optimization. Default value is 10.
	 */
	private int iterations;
	/**
	 * Display additional information while optimizing
	 */
	private boolean verbose;
	/**
	 * Time limit for optimization of a single window. 0 means no limit. 
	 */
	private int solverTimeLimit;
	/**
	 * Time limit for the total optimization process. 0 mean no limit.
	 */
	private int totalTimeLimit;
	/**
	 * File where the final plan is saved.
	 */
	private String planFile;
	/**
	 * Growth of the window for exponential shifting. Default value is 1.5
	 */
	private float windowGrowth;
	/**
	 * Fixed point must be reached before the window size is increased
	 */
	private boolean fixedPointMode;
	/**
	 * do not optimize windows, just try one improvement
	 */
	private boolean oneTry;
	
	public PlanOptimizerParameters() {
		windowSelectionAlgorithm = SelectionAlgorithm.turbo;
		windowSize = 15;
		windowShift = 8;
		iterations = 10;
		setSolverTimeLimit(0);
		setTotalTimeLimit(Integer.MAX_VALUE);
		verbose = false;
		planFile = null;
		intermediatePlanFileName = null;
		windowGrowth = 1.5f;
		fixedPointMode = false;
		oneTry = false;
		minWindowTime = 2;
	}
	
	/**
	 * @return the windowSelectionAlgorithm
	 */
	public SelectionAlgorithm getWindowSelectionAlgorithm() {
		return windowSelectionAlgorithm;
	}
	/**
	 * @param windowSelectionAlgorithm the windowSelectionAlgorithm to set
	 */
	public void setWindowSelectionAlgorithm(
			SelectionAlgorithm windowSelectionAlgorithm) {
		this.windowSelectionAlgorithm = windowSelectionAlgorithm;
	}
	/**
	 * @return the windowSize
	 */
	public int getWindowSize() {
		return windowSize;
	}
	/**
	 * @param windowSize the windowSize to set
	 */
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	/**
	 * @return the windowShift
	 */
	public int getWindowShift() {
		return windowShift;
	}
	/**
	 * @param windowShift the windowShift to set
	 */
	public void setWindowShift(int windowShift) {
		this.windowShift = windowShift;
	}
	/**
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}
	/**
	 * @param iterations the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * @return the verbose
	 */
	public boolean isVerbose() {
		return verbose;
	}

	/**
	 * @param verbose the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public int getSolverTimeLimit() {
		return solverTimeLimit;
	}

	public void setSolverTimeLimit(int solverTimeLimit) {
		this.solverTimeLimit = solverTimeLimit;
	}

	public int getTotalTimeLimit() {
		return totalTimeLimit;
	}

	public void setTotalTimeLimit(int totalTimeLimit) {
		this.totalTimeLimit = totalTimeLimit;
	}

	public String getPlanFile() {
		return planFile;
	}

	public void setPlanFile(String planFile) {
		this.planFile = planFile;
	}

	public float getWindowGrowth() {
		return windowGrowth;
	}

	public void setWindowGrowth(float windowGrowth) {
		this.windowGrowth = windowGrowth;
	}

	public boolean isFixedPointMode() {
		return fixedPointMode;
	}

	public void setFixedPointMode(boolean fixedPointMode) {
		this.fixedPointMode = fixedPointMode;
	}

	public boolean isOneTry() {
		return oneTry;
	}

	public void setOneTry(boolean oneTry) {
		this.oneTry = oneTry;
	}

	/**
	 * @return the minWindowTime
	 */
	public int getMinWindowTime() {
		return minWindowTime;
	}

	/**
	 * @param minWindowTime the minWindowTime to set
	 */
	public void setMinWindowTime(int minWindowTime) {
		this.minWindowTime = minWindowTime;
	}

    /**
     * @return the intermediatePlanFileName
     */
    public String getIntermediatePlanFileName() {
        return intermediatePlanFileName;
    }

    /**
     * @param intermediatePlanFileName the intermediatePlanFileName to set
     */
    public void setIntermediatePlanFileName(String intermediatePlanFileName) {
        this.intermediatePlanFileName = intermediatePlanFileName;
    }

}
