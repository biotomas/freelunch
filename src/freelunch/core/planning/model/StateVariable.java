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

public class StateVariable {

    private final int id;
    // domain size
    private final int domain;
    private String name = null;
    public String[] domainValues;

    /**
     * Construct a new variable
     * 
     * @param label
     *            string of the form varXX, where XX is the ID
     * @param domain
     *            size of the domain
     */
    public StateVariable(String label, int domain) {
    	this.name = label;
        this.id = Integer.parseInt(label.substring(3));
        this.domain = domain;
    }

    /**
     * Construct a new variable with the given id and domain size. The id must
     * be unique
     * 
     * @param id
     * @param domainSize
     */
    public StateVariable(int id, int domainSize) {
        this.id = id;
        this.domain = domainSize;
    }

    public StateVariable(String name, int id, int domainSize) {
        this.name = name;
        this.id = id;
        this.domain = domainSize;
    }

    public int getId() {
        return id;
    }

    public int getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%d(0-%d)", id, domain);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof StateVariable) && ((StateVariable) obj).getId() == id;
    }

    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }

}
