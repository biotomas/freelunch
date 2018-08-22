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
package freelunch.planning.sase.api.lifted;


public class LiftedCondition {

	
	private LiftedStateVariable variable;
	private NamedParameter variableIndex;
	private NamedParameter valueIndex;

	public LiftedCondition(LiftedStateVariable variable, NamedParameter variableIndex, NamedParameter valueIndex) {
		this.variable = variable;
		this.variableIndex = variableIndex;
		this.valueIndex = valueIndex;
	}

	/**
	 * @return the variable
	 */
	public LiftedStateVariable getVariable() {
		return variable;
	}

	/**
	 * @return the variableIndex
	 */
	public NamedParameter getVariableIndex() {
		return variableIndex;
	}

	/**
	 * @return the valueIndex
	 */
	public NamedParameter getValueIndex() {
		return valueIndex;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s", variable, variableIndex, valueIndex);
	}

}
