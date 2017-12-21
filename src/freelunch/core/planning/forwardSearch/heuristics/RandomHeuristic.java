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
package freelunch.core.planning.forwardSearch.heuristics;

import java.util.List;
import java.util.Random;

import freelunch.core.planning.model.SasAction;


/**
 * A fully random heuristic which return one of the
 * available actions
 *
 * @author Tomas Balyo
 * 4.11.2012
 */
public class RandomHeuristic implements ForwardSearchSelectorFunction {
    
    private Random random;
    
    public RandomHeuristic() {
        this(100);
    }
    
    public RandomHeuristic(long seed) {
        random = new Random(seed);
    }

    @Override
    public SasAction select(int[] state, List<SasAction> actions) {
        int i = random.nextInt(actions.size());
        return actions.get(i);
    }

    @Override
    public void noteRestart() {
        //nothing to do here.
    }

}
