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
import freelunch.core.planning.problemGenerator.LogisticsProblemGenerator;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;

public class LogisticsBenchmarkProvider implements BenchmarkProvider {

    private int trucksTo, trucks;
    private int locationsFrom, locationsTo, locations;
    private int packsFrom, packsTo, packs;
    private boolean last = false;
    
    public LogisticsBenchmarkProvider(int trucksFrom, int trucksTo, int locationsFrom, int locationsTo, int packsFrom, int packsTo) {
        this.trucks = trucksFrom;
        this.locations = this.locationsFrom = locationsFrom;
        this.packs = this.packsFrom = packsFrom;
        this.packsTo = packsTo;
        this.locationsTo = locationsTo;
        this.trucksTo = trucksTo;
    }
    
    @Override
    public SasProblem getNext() {
        if (last) {
            return null;
        }
        SasProblemBuilder problem = LogisticsProblemGenerator.generateProblem(trucks, packs, locations, 2*locations);
        SasProblem sasProb = problem.getSasProblem();
        String description = String.format("logistics-T=%d,L=%d,P=%d", trucks, locations, packs);
        sasProb.setDescription(description);
        
        packs++;
        if (packs == packsTo) {
            packs = packsFrom;
            locations++;
            if (locations == locationsTo) {
                locations = locationsFrom;
                trucks++;
                if (trucks == trucksTo) {
                    last = true;
                }
            }
        }
        return sasProb;
    }

}
