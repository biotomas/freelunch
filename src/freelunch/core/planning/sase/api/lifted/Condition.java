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
package freelunch.core.planning.sase.api.lifted;

public class Condition {
	
	private LiftedStateVariable variable;
	private PlanningObject parameter;
	private PlanningObject value;
	
	public Condition(LiftedStateVariable variable, PlanningObject parameter, PlanningObject value) {
		this.variable = variable;
		this.parameter = parameter;
		this.value = value;
		//TODO check types
	}

	/**
	 * @return the variable
	 */
	public LiftedStateVariable getVariable() {
		return variable;
	}

	/**
	 * @return the value
	 */
	public PlanningObject getValue() {
		return value;
	}

	/**
	 * @return the parameter
	 */
	public PlanningObject getParameter() {
		return parameter;
	}

}
