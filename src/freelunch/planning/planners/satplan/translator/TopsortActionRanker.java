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
package freelunch.planning.planners.satplan.translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import freelunch.planning.model.SasAction;

public class TopsortActionRanker {
    
    private boolean[] visited;
    private int[] ranksById;
    private int lastRank;
    private long ignoredCycles;
    private ActionAssignmentTransitionIndices indices;
    
    /**
     * Return the ranks of the action by their IDs
     * @param actions
     * @return
     */
    public int[] getActionRanks(ActionAssignmentTransitionIndices indices, Collection<SasAction> actions) {
        this.indices = indices;
        ignoredCycles = 0;
        
        lastRank = 0;
        ranksById = new int[actions.size()];
        Arrays.fill(ranksById, -1);
        visited = new boolean[actions.size()];
        Arrays.fill(visited, false);
        for (SasAction a : actions) {
            if (!visited[a.getId()]) {
                visit(a);
            }
        }
        return ranksById;
    }
    
    private void visit(SasAction a) {
        if (visited[a.getId()]) {
            // cycle detected, but we ignore them
            ignoredCycles++;
            return;
        }
        
        visited[a.getId()] = true;
        // actions that have edge to "a" i.e. actions that support a precondition in "a"
        for (SasAction b : indices.getSupportingActions(a)) {
            visit(b);
        }
        
        ranksById[a.getId()] = lastRank;
        lastRank++;
    }
    
    /**
     * Return the ranks of the action by their IDs
     * @param actions
     * @return
     */
    public int[] getActionRanksNonRecursive(ActionAssignmentTransitionIndices indices, Collection<SasAction> actions) {
        ignoredCycles = 0;
        lastRank = 0;
        ranksById = new int[actions.size()];
        Arrays.fill(ranksById, -1);
        BitSet visited = new BitSet(actions.size());
        BitSet stacked = new BitSet(actions.size());
        visited.clear();
        stacked.clear();
        
        Stack<SasAction> actionsStack = new Stack<>();
        List<SasAction> preds = new ArrayList<>();
        
        for (SasAction action : actions) {
            if (!visited.get(action.getId())) {
                actionsStack.push(action);
                
                while (!actionsStack.empty()) {
                    SasAction a = actionsStack.pop();
                    visited.set(a.getId());
                    preds.clear();
                    for (SasAction b : indices.getSupportingActions(a)) {
                        if (visited.get(b.getId())) {
                            ignoredCycles++;
                        } else {
                            if (!stacked.get(b.getId())) {
                                preds.add(b);
                                stacked.set(b.getId());
                            }
                        }
                    }
                    if (preds.isEmpty()) {
                        if (ranksById[a.getId()] == -1) {
                            ranksById[a.getId()] = lastRank;
                            lastRank++;
                        }
                    } else {
                        actionsStack.push(a);
                        //Collections.reverse(preds);
                        for (SasAction b : preds) {
                            actionsStack.push(b);
                        }
                    }
                }
            }
        }
        return ranksById;
    }
    
    public int[] getActionRanksNonRecursiveOld(ActionAssignmentTransitionIndices indices, Collection<SasAction> actions) {
        ignoredCycles = 0;
        lastRank = 0;
        ranksById = new int[actions.size()];
        Arrays.fill(ranksById, -1);
        visited = new boolean[actions.size()];
        Arrays.fill(visited, false);
        Stack<SasAction> actionsStack = new Stack<>();
        List<SasAction> preds = new ArrayList<>();
        
        for (SasAction action : actions) {
            if (!visited[action.getId()]) {
                actionsStack.push(action);
                //visited[action.getId()] = true;
                
                while (!actionsStack.empty()) {
                    SasAction a = actionsStack.pop();
                    visited[a.getId()] = true;
                    preds.clear();
                    for (SasAction b : indices.getSupportingActions(a)) {
                        if (!visited[b.getId()]) {
                            //visited[b.getId()] = true;
                            preds.add(b);
                        } else {
                            ignoredCycles++;
                        }
                    }
                    if (preds.isEmpty()) {
                        if (ranksById[a.getId()] == -1) {
                            ranksById[a.getId()] = lastRank;
                            lastRank++;
                        }
                    } else {
                        actionsStack.push(a);
                        //Collections.reverse(preds);
                        for (SasAction b : preds) {
                            actionsStack.push(b);
                        }
                    }
                }
            }
        }
        return ranksById;
    }

    
    /**
     * Get the number of cycles ignored during topological sorting
     * @return
     */
    public long getIgnoredCycles() {
        return ignoredCycles;
    }
}
