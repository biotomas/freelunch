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

import java.util.List;

import freelunch.benchmarks.PlanningProblem;
import freelunch.core.planning.model.ActionInfo;
import freelunch.core.planning.problemGenerator.SlidingPuzzleGenerator;
import freelunch.core.planning.problemGenerator.SlidingPuzzleGenerator.SlidingPuzzleActionInfo;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;



public class SlidingPuzzleProblem implements PlanningProblem {
    
    private int width;
    private int height;
    private int hardness;
    private int[] board;
    private SasProblemBuilder problem;
    
    public SlidingPuzzleProblem(int width, int height, int hardness) {
        this.width = width;
        this.height = height;
        this.hardness = hardness;

        SlidingPuzzleGenerator gen = new SlidingPuzzleGenerator();
        problem = gen.generateSolvableProblem(width, height, hardness);
        board = gen.getInitialState();
    }
    
    public SasProblemBuilder getProblem() {
        return problem;
    }

    @Override
    public void applyActions(List<ActionInfo> actions) {
        assert actions.size() == 1;
        SlidingPuzzleActionInfo ainfo = (SlidingPuzzleActionInfo) actions.get(0);
        assert board[ainfo.to] == 0;
        board[ainfo.to] = ainfo.what;
        assert board[ainfo.from] == ainfo.what;
        board[ainfo.from] = 0;
    }

    @Override
    public PlanningProblem copy() {
        return new SlidingPuzzleProblem(width, height, hardness);
    }
    
    public int[] getBoard() {
        return board;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    

}
