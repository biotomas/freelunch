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
package freelunch.benchmarks;

public interface PlanningProblemGenerator<T extends PlanningProblem> {

    /**
     * Is the set of generated problems static or not and it is dynamically
     * generated?
     */
    public boolean isStatic();

    /**
     * Defined if generator is dynamic.
     * 
     * @return next generated problem.
     */
    public T getNext();

    /**
     * Defined if generator is static.
     * 
     * @return all problems.
     */
    public T[] getProblems();

    /**
     * Defined if generator is static.
     * 
     * @return short meaningful names of all the problems.
     */
    public String[] getNames();

}
