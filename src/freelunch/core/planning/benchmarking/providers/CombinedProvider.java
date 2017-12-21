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
package freelunch.core.planning.benchmarking.providers;

import freelunch.core.planning.benchmarking.BenchmarkProvider;
import freelunch.core.planning.model.SasProblem;

public class CombinedProvider implements BenchmarkProvider {
    
    private BenchmarkProvider[] providers;
    private int next = 0;
    
    public CombinedProvider(BenchmarkProvider ... benchmarkProviders) {
        providers = benchmarkProviders;
    }

    @Override
    public SasProblem getNext() {
        
        while (next < providers.length) {
            SasProblem p = providers[next].getNext();
            if (p == null) {
                next++;
            } else {
                return p;
            }
        }
        return null;
    }

}
