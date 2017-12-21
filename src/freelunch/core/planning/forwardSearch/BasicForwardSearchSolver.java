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
package freelunch.core.planning.forwardSearch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.AbstractSolver;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.forwardSearch.heuristics.StateVariablesValueGoalDistanceHeuristic;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.utilities.Stopwatch;


/**
 * A simple forward best-first-search planner. Does not create parallel plans.
 * Creates very long plans which should be shortened by some post-processing.
 * 
 * @author Tomas Balyo Oct 15, 2012
 */
public class BasicForwardSearchSolver extends AbstractSolver implements Solver {
    
    public class ForwardSearchStatistics {      
        public long backtracksSinceRestart = 0;
        public long iterationsSinceRestart = 0;
        public int maxDepth = 0;
        public int depth = 0;  
        public int restarts = 0;
        
        private long iterations = 0;
        
        public long getIterations() {
            return iterations + iterationsSinceRestart;
        }

        private void reset() {
            iterations += iterationsSinceRestart;
            backtracksSinceRestart = 0;
            iterationsSinceRestart = 0;
            depth = 0;
        }
    }

    protected ForwardSearchStatistics statistics;
    protected final ForwardSearchSettings settings;

    public ForwardSearchStatistics getStatistics() {
        return statistics;
    }

    public BasicForwardSearchSolver(SasProblem problem) {
        this(problem, new ForwardSearchSettings());
    }

    public BasicForwardSearchSolver(SasProblem problem, ForwardSearchSettings settings) {
        super(problem);
        this.settings = settings;
        if (settings.getHeuristic() == null) {
            settings.setHeuristic(new StateVariablesValueGoalDistanceHeuristic(problem, 2013));
        }
        statistics = new ForwardSearchStatistics();
    }
    

    @Override
    public SasParallelPlan solve() throws TimeoutException, NonexistentPlanException {
        ArrayDeque<int[]> states = new ArrayDeque<int[]>();
        ArrayDeque<SasAction> plan = new ArrayDeque<SasAction>();
        ArrayDeque<List<SasAction>> untriedActionsHeap = new ArrayDeque<List<SasAction>>();
        HashSet<String> visitedStates = new HashSet<String>();

        int[] state = Arrays.copyOf(initialState, initialState.length);

        states.add(state);
        visitedStates.add(encodeState(state));
        Set<SasAction> applicableActions = getApplicableActions(state);
        untriedActionsHeap.add(new ArrayList<SasAction>(applicableActions));

        Stopwatch watch = new Stopwatch();
        int timelimit = settings.getTimelimit();
        statistics.reset();
        
        while (!isGoalState(state)) {
            if (timelimit > 0 && watch.limitExceeded(timelimit)) {
                System.out.print(String.format(" iters = %d bts = %d ", statistics.iterationsSinceRestart, statistics.backtracksSinceRestart));
                throw new TimeoutException();
            }
            
            // restarting
            if (settings.getRestartHeuristic().shouldRestart(statistics)) {
                statistics.reset();
                statistics.restarts++;
                settings.getHeuristic().noteRestart();
                states.clear();
                plan.clear();
                untriedActionsHeap.clear();
                visitedStates.clear();
                
                state = Arrays.copyOf(initialState, initialState.length);
                states.add(state);
                visitedStates.add(encodeState(state));
                applicableActions = getApplicableActions(state);
                untriedActionsHeap.add(new ArrayList<SasAction>(applicableActions));
            }

            List<SasAction> untriedActions = untriedActionsHeap.getLast();

            if (untriedActions.isEmpty()) {
                if (plan.isEmpty()) {
                    System.out.print(String.format(" iters = %d bts = %d ", statistics.iterations, statistics.backtracksSinceRestart));
                    throw new NonexistentPlanException();
                }
                // backtracking
                statistics.backtracksSinceRestart++;
                statistics.depth--;
                untriedActionsHeap.removeLast();
                states.removeLast();
                state = states.getLast();
                plan.removeLast();
            } else {
                statistics.iterationsSinceRestart++;
                statistics.depth++;
                statistics.maxDepth = Math.max(statistics.maxDepth, statistics.depth);
                // select the best action
                SasAction best = settings.getHeuristic().select(state, untriedActions);
                // remove from the untried list
                untriedActions.remove(best);
                // use the action
                plan.add(best);
                
                // update the state and applicable actions
                int[] newState = applyAction(best, state);
                updateApplicableActions(applicableActions, state, newState);
                state = newState;
                
                states.add(state);
                visitedStates.add(encodeState(state));
                List<SasAction> newUntriedActions = new ArrayList<SasAction>();
                for (SasAction op : applicableActions) {
                    if (!visitedStates.contains(encodeState(applyAction(op, state)))) {
                        newUntriedActions.add(op);
                    }
                }
                untriedActionsHeap.addLast(newUntriedActions);
            }
        }

        // make the plan
        List<List<SasAction>> pplan = new ArrayList<List<SasAction>>();
        for (SasAction op : plan) {
            List<SasAction> step = new ArrayList<SasAction>(1);
            step.add(op);
            pplan.add(step);
        }
        System.out.print(String.format(" iters = %d bts = %d ", statistics.iterationsSinceRestart, statistics.backtracksSinceRestart));
        return new SasParallelPlan(pplan);
    }

    @Override
    public String toString() {
        return "Basic forward search";
    }

    @Override
    public BasicSettings getSettings() {
        return settings;
    }
}
