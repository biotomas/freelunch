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

public class PlanOptimizerStatistics {
	
	public PlanOptimizerParameters params;
	
	public String problemName;
	
	public int initialPlanTimespan;
	public int initialPlanActions;
	public int finalPlanTimespan;
	public int finalPlanActions;
	
	public String lastImprovementTime;
	public int lastImprovementWindowSize;
	public int maxWindow;
	
	public String solutionTime;

	public int optimizations;
	public int optimizationsTimeouted;
	public int improvements;
	
	public PlanOptimizerStatistics() {
		optimizations = 0;
		optimizationsTimeouted = 0;
		improvements = 0;
		lastImprovementTime = "";
		lastImprovementWindowSize = 0;
	}
	
	public String getStatisticsCsv() {
		return String.format("%s;%s;%s;%d;%d;%d;%d;%d;%d;%d;%d;%s;%d",
				params.getWindowSelectionAlgorithm() + (params.isFixedPointMode() ? "-fp" : ""),
				problemName, solutionTime, finalPlanTimespan, finalPlanActions, 
				initialPlanTimespan, initialPlanActions, optimizations, 
				improvements, optimizationsTimeouted, maxWindow, 
				lastImprovementTime, lastImprovementWindowSize);
	}
}
