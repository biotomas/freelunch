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
package freelunch.benchmarks.slidingPuzzle;

import java.util.ArrayList;
import java.util.List;

import freelunch.benchmarks.PlanningProblemGenerator;



public class SlidingPuzzleGenerator implements PlanningProblemGenerator<SlidingPuzzleProblem> {
    
    private SlidingPuzzleProblem[] problems;
    private String[] names;
    
    public SlidingPuzzleGenerator() {
        List<SlidingPuzzleProblem> problems = new ArrayList<SlidingPuzzleProblem>();
        List<String> names = new ArrayList<String>();
        
        for (int width = 2; width < 6; width++) {
            for (int height = 2; height < 6; height++) {
                int hardness = width * height;
                problems.add(new SlidingPuzzleProblem(width, height, hardness));
                names.add(String.format("%d x %d, hardness: %d", width, height, hardness));
            }
        }
        this.problems = problems.toArray(new SlidingPuzzleProblem[problems.size()]);
        this.names = names.toArray(new String[names.size()]);
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public SlidingPuzzleProblem getNext() {
        return null;
    }

    @Override
    public SlidingPuzzleProblem[] getProblems() {
        return problems;
    }

    @Override
    public String[] getNames() {
        return names;
    }
    
}
