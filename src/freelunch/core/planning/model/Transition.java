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

import java.util.HashSet;
import java.util.Set;

public class Transition {

    public enum TransitionType {
        /**
         * x:f->g
         */
        STANDARD,
        /**
         * x:f->f
         */
        PREVAILING,
        /**
         * x:*->g
         */
        MECHANICAL
    }


    private StateVariable var;
    // -1 means no required value
    private int requiredValue;
    private int newValue;
    private TransitionType type;
    private int id;
    private Set<SasAction> supportingActions;

    public Transition(StateVariable var, int requiredValue, int newValue, int id) {
        this.var = var;
        this.requiredValue = requiredValue;
        this.newValue = newValue;
        this.id = id;
        supportingActions = new HashSet<SasAction>();
        // compute the transition type
        if (requiredValue == -1) {
            type = TransitionType.MECHANICAL;
        } else if (requiredValue == newValue) {
            type = TransitionType.PREVAILING;
        } else {
            type = TransitionType.STANDARD;
        }
    }

    /**
     * @return the var
     */
    public StateVariable getVar() {
        return var;
    }

    /**
     * @return the oldVal
     */
    public int getOldVal() {
        return requiredValue;
    }

    /**
     * @return the newVal
     */
    public int getNewVal() {
        return newValue;
    }

    /**
     * @return the type
     */
    public TransitionType getType() {
        return type;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the supportingActions
     */
    public Set<SasAction> getSupportingActions() {
        return supportingActions;
    }

    public String getDescriptor() {
        return String.format("%d:%d->%d", var.getId(), requiredValue, newValue);
    }

    @Override
    public String toString() {
        return getDescriptor();
    }

}
