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


public class LiftedStateVariable {
	
	private String name;
	private PlanningObjectClass parameterClass;
	private PlanningObjectClass valueClass;
	
	public LiftedStateVariable(String name, PlanningObjectClass parameterClass, PlanningObjectClass valueClass) {
		this.name = name;
		this.parameterClass = parameterClass;
		this.valueClass = valueClass;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the parameterClass
	 */
	public PlanningObjectClass getParameterClass() {
		return parameterClass;
	}

	/**
	 * @return the valueClass
	 */
	public PlanningObjectClass getValueClass() {
		return valueClass;
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s]:%s", name, parameterClass, valueClass);
	}

}
