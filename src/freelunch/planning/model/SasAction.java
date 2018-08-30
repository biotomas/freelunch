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
package freelunch.planning.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class SasAction {

    private final ActionInfo actionInfo;
    private List<Condition> preconditions;
    private List<Condition> effects;
	private List<ConditionalEffect> conditionalEffects;

    private int id;
    // the default cost of an action is 1
    private int cost = 1;

    public SasAction(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
        preconditions = new ArrayList<>();
        conditionalEffects = new ArrayList<>();
        effects = new ArrayList<>();        
    }

    public SasAction(String name) {
        this(new StringActionInfo(name));
    }

    public SasAction(SasAction other) {
    	this.actionInfo = other.actionInfo;
    	this.preconditions = new ArrayList<>(other.preconditions);
    	this.effects = new ArrayList<>(other.effects);
    	this.cost = other.cost;
    	this.conditionalEffects = new ArrayList<>(other.conditionalEffects);
	}
    
    public List<ConditionalEffect> getConditionalEffects() {
    	return conditionalEffects;
    }


    /**
     * @return the name
     */
    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    /**
     * @return the preconditions
     */
    public List<Condition> getPreconditions() {
        return preconditions;
    }

    /**
     * @return the effects
     */
    public List<Condition> getEffects() {
        return effects;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("\n%s: prec=%s eff=%s condeff=%s cost=%d",
                actionInfo.getName(),
                preconditions.toString(),
                effects.toString(),
                conditionalEffects.toString(),
                cost);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SasAction && this.id == ((SasAction) obj).id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        if (cost < 0) {
            throw new InvalidParameterException("Action cost must be non negative");
        }
        this.cost = cost;
    }

}
