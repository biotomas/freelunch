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
package freelunch.core.planning.model;

import java.util.List;


public class SasProblem {

    private List<Condition> initialState;
    private List<Condition> goal;
    private List<StateVariable> variables;
    private List<SasAction> operators;
    private List<List<Condition>> mutexGroups;
    private String description;
    
    public void setActionIDs() {
        int actionId = 0;
        for (SasAction op : operators) {
            op.setId(actionId);
            actionId++;
        }
    }
    
    /**
     * Copy constructor
     * @param other
     */
    public SasProblem(SasProblem other) {
		this.initialState = other.initialState;
		this.goal = other.goal;
		this.variables = other.variables;
		this.operators = other.operators;
		this.mutexGroups = other.mutexGroups;
	}

	public SasProblem() {
    }

    /**
     * @param initialState the initialState to set
     */
    public void setInitialState(List<Condition> initialState) {
        this.initialState = initialState;
    }

    /**
     * @param goal the goal to set
     */
    public void setGoal(List<Condition> goal) {
        this.goal = goal;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(List<StateVariable> variables) {
        this.variables = variables;
    }

    /**
     * @param operators the operators to set
     */
    public void setOperators(List<SasAction> operators) {
        this.operators = operators;
    }

    /**
     * @return the initialState
     */
    public List<Condition> getInitialState() {
        return initialState;
    }
    /**
     * @return the goal
     */
    public List<Condition> getGoal() {
        return goal;
    }
    /**
     * @return the variables
     */
    public List<StateVariable> getVariables() {
        return variables;
    }

    /**
     * @return the operators
     */
    public List<SasAction> getOperators() {
        return operators;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the mutexGroups
     */
    public List<List<Condition>> getMutexGroups() {
        return mutexGroups;
    }

    /**
     * @param mutexGroups the mutexGroups to set
     */
    public void setMutexGroups(List<List<Condition>> mutexGroups) {
        this.mutexGroups = mutexGroups;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof SasProblem) {
        	SasProblem o = (SasProblem) obj;
        	if (!variables.equals(o.variables)) {
        		return false;
        	}
        	if (!initialState.equals(o.initialState)) {
        		return false;
        	}
        	if (!goal.equals(o.goal)) {
        		return false;
        	}
        	if (!operators.equals(o.operators)) {
        		return false;
        	}
    		return true;
    	} else {
    		return false;
    	}
    }

}
