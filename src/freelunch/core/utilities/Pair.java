/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.core.utilities;

/**
 * A simple template class representing a tuple of something
 *
 * @author Tomas Balyo
 * 27.4.2013
 */
public class Pair<Typename> {
    
    public Typename first;
    public Typename second;
    
    public Pair(Typename first, Typename second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public String toString() {
        return String.format("[%s,%s]", first.toString(), second.toString());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?>) {
            Pair<?> other = (Pair<?>) obj;
            return other.first.equals(first) && other.second.equals(second);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (first.hashCode() << 16) + second.hashCode();
    }

}
