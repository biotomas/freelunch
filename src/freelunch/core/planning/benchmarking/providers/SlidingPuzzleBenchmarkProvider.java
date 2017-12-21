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
import freelunch.core.planning.problemGenerator.SlidingPuzzleGenerator;

public class SlidingPuzzleBenchmarkProvider implements BenchmarkProvider {
    
    private int width;
    private int widthTo;
    private int height;
    private int heightFrom;
    private int heightTo;
    private boolean last = false;
    private SlidingPuzzleGenerator generator = new SlidingPuzzleGenerator();
    

    public SlidingPuzzleBenchmarkProvider(int widthFrom, int widthTo, int heightFrom, int heightTo) {
        this.width = widthFrom;
        this.widthTo = widthTo;
        this.heightFrom = heightFrom;
        this.heightTo = heightTo;
        height = heightFrom;
    }

    @Override
    public SasProblem getNext() {
        if (last) {
            return null;
        }
        int shuffle = 10 * width * width * height;
        SasProblem problem = generator.generateSolvableProblem(width, height, shuffle).getSasProblem();
        String description = String.format("slidingPuzzle-%dx%d", width, height);
        problem.setDescription(description);
        
        height++;
        if (height == heightTo) {
            height = heightFrom;
            width++;
            if (width == widthTo) {
                last = true;
            }
        }
        return problem;
    }

}
