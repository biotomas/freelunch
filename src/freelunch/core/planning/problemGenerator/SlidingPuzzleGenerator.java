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
package freelunch.core.planning.problemGenerator;

import java.util.Random;

import freelunch.core.planning.model.ActionInfo;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;
import freelunch.core.utilities.ArrayUtils;


public class SlidingPuzzleGenerator {
    
    public class SlidingPuzzleActionInfo implements ActionInfo {
        
        public int from;
        public int to;
        public int what;
        public String direction;
        
        public SlidingPuzzleActionInfo(int what, int from, int to, String direction) {
            this.from = from;
            this.to = to;
            this.what = what;
            this.direction = direction;
        }

        @Override
        public String getName() {
            return String.format("Move-%d-%s-from-%d-to-%d", what, direction, from, to);
        }
        
    }
    
    private StateVariable[] vars;
    private int width;
    private int height;
    private SasProblemBuilder problem;
    private int[] initialState;
    
    /**
     * Generate a sliding puzzle problem with the given parameters, may be unsolvable.
     * @param width of the board (at least 2)
     * @param height of the board (at least 2)
     * @param shuffles to be performed to generate the initial state from the goal state
     * @return planning problem
     */
    public SasProblem generateRandomProblem(int width, int height, long seed) {
    	// we take a satisfiable problem    	
    	SasProblem problem = generateSolvableProblem(width, height, 0).getSasProblem();
    	// and change its initial state to full random
    	int[] istate = new int[problem.getVariables().size()];
    	for (Condition c : problem.getInitialState()) {
    		istate[c.getVariable().getId()] = c.getValue();
    	}
    	ArrayUtils.shuffle(istate, seed);
    	for (Condition c : problem.getInitialState()) {
    		c.setValue(istate[c.getVariable().getId()]);
    	}

        return problem;
    }
    
    /**
     * Generate a satisfiable sliding puzzle problem with the given parameters.
     * @param width of the board (at least 2)
     * @param height of the board (at least 2)
     * @param shuffles to be performed to generate the initial state from the goal state
     * @return planning problem
     */
    public SasProblemBuilder generateSolvableProblem(int width, int height, int shuffles) {
        assert width >= 2;
        assert height >= 2;
        
        problem = new SasProblemBuilder();
        this.width = width;
        this.height = height;
        int size = width * height;
        // we have one variable for each board position
        vars = new StateVariable[size];
        for (int i = 0; i < size; i++) {
            // the values are 0 for gaps and 1,2,... for blocks
            vars[i] = problem.newVariable("pos-"+i, size);
        }
        
        // the actions are about moving numbered blocks
        for (int block = 1; block < size; block++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // left moves
                    if (x > 0) {
                        addMoveAction("left", block, linearize(x, y), linearize(x-1, y));
                    }
                    // right moves
                    if (x < width - 1) {
                        addMoveAction("right", block, linearize(x, y), linearize(x+1, y));
                    }
                    // up moves
                    if (y > 0) {
                        addMoveAction("up", block, linearize(x, y), linearize(x, y-1));
                    }
                    // down moves
                    if (y < height - 1) {
                        addMoveAction("down", block, linearize(x, y), linearize(x, y+1));
                    }
                }
            }
        }
        
        // the goal state is easy
        for (int position = 0; position < (size - 1); position++) {
            problem.addGoalCondition(new Condition(vars[position], position + 1));
        }
        
        // the initial state
        int[] state = getShuffledState(shuffles);
        for (int i = 0; i < size; i++) {
            problem.addInitialStateCondition(new Condition(vars[i], state[i]));
        }
        initialState = state;
        return problem;
    }
    
    private int[] getShuffledState(int shuffles) {
        Random rnd = new Random(width*height*shuffles);
        int[][] board = new int[width][height];
        // initial state
        int value = 1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (value < (width * height)) {
                    board[x][y] = value;
                    value++;
                } else {
                    board[x][y] = 0;
                }
            }
        }
        int gapx = width - 1;
        int gapy = height - 1;
        // shuffling
        while (shuffles > 0) {
            // 4 possible direction to move the gap
            int dir = rnd.nextInt(4);
            switch(dir) {
            case 0:
                // move gap left
                if (gapx > 0) {
                    board[gapx][gapy] = board[gapx-1][gapy];
                    gapx--;
                    board[gapx][gapy] = 0;
                    shuffles--;
                }
                break;
            case 1:
                // move gap right
                if (gapx < width - 1) {
                    board[gapx][gapy] = board[gapx+1][gapy];
                    gapx++;
                    board[gapx][gapy] = 0;
                    shuffles--;
                }
                break;
            case 2:
                // move gap up
                if (gapy > 0) {
                    board[gapx][gapy] = board[gapx][gapy-1];
                    gapy--;
                    board[gapx][gapy] = 0;
                    shuffles--;
                }
                break;
            case 3:
                // move gap down
                if (gapy < height - 1) {
                    board[gapx][gapy] = board[gapx][gapy+1];
                    gapy++;
                    board[gapx][gapy] = 0;
                    shuffles--;
                }
                break;
            }
        }
        int[] linearized = new int[width*height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                linearized[linearize(x, y)] = board[x][y];
            }
        }
        return linearized;
    }
    
    public int[] getInitialState() {
        return initialState;
    }
    
    private int linearize(int x, int y) {
        return x + (y*width);
    }
    
    private void addMoveAction(String direction, int what, int from, int to) {
        SasAction op = problem.newAction(new SlidingPuzzleActionInfo(what, from, to, direction));
        op.getPreconditions().add(new Condition(vars[from], what));
        op.getPreconditions().add(new Condition(vars[to], 0));
        op.getEffects().add(new Condition(vars[from], 0));
        op.getEffects().add(new Condition(vars[to], what));
    }
    

}
