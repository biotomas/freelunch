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

public class PlanningObjectClass {
	
	private PlanningObjectClass parentClass;
	private String name;
	
	public PlanningObjectClass(String name, PlanningObjectClass parentClass) {
		this.parentClass = parentClass;
		this.name = name;
	}
	
	public boolean isSuperClassOf(PlanningObjectClass otherClass) {
		PlanningObjectClass c = otherClass;
		while (c != null) {
			if (c == this) {
				return true;
			}
			c = c.parentClass;
		}
		return false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
