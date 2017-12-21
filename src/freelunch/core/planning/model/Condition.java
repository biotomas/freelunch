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

/**
 * Represents a variable value (from its domain) pair
 */
public class Condition {

    private StateVariable var;
    private int value;

    public static final int ARBITRARY = -1;

    public Condition(StateVariable var, int value) {
        this.var = var;
        this.value = value;
    }

    public Condition(StateVariable var, int[] values) {
        // TODO add a condition where variable can have more than one value
    }

    public Condition(Condition other) {
        this.var = other.var;
        this.value = other.value;
    }

    public StateVariable getVariable() {
        return var;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == ARBITRARY) {
            return "";
        }
        return String.format("var%d = %d", var.getId(), value);
    }
    
    @Override
    public boolean equals(Object obj) {
        Condition other = (Condition) obj;
        return (var.getId() == other.var.getId()) && (value == other.value);
    }
    
    @Override
    public int hashCode() {
        return (var.getId() << 16) + value;
    }
}
