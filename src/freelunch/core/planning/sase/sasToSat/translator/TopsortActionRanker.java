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
package freelunch.core.planning.sase.sasToSat.translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import freelunch.core.planning.model.SasAction;

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
    
    public void visit(SasAction a) {
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
    
    public long evalueateRanking(final int[] ranking, ActionAssignmentTransitionIndices indices) {
        /* Sort actions by ranks
        List<SasAction> acts = new ArrayList<>(indices.actions);
        Collections.sort(acts, new Comparator<SasAction>() {
            @Override
            public int compare(SasAction o1, SasAction o2) {
                return ranking[o1.getId()] - ranking[o2.getId()];
            }
        });
        */
        
        long lowerSupps = 0;
        long higherSupps = 0;
        long lowerOpps = 0;
        long higherOpps = 0;
        //long inBetweenOpps = 0;
        int left = 0;
        int right = 0;
        int sleft = 0;
        int sright = 0;
        long supSqr = 0;
        long oppSqr = 0;
        long sqr;
        for (SasAction a : indices.actions) {
            int rankA = ranking[a.getId()];
            int l = 0;
            int r = 0;
            for (SasAction b : indices.actionOpposingActions[a.getId()]) {
                if (ranking[b.getId()] < rankA) {
                    lowerOpps++;
                    l++;
                } else {
                    higherOpps++;
                    r++;
                }
            }
            sqr = (l-r)*(l-r);
            if (l > r) {
                left++;
                oppSqr -= sqr;
            } else {
                right++;
                oppSqr += sqr;
            }
            
            l = 0;
            r = 0;
            for (SasAction b : indices.getSupportingActions(a)) {
                int rankB = ranking[b.getId()]; 
                if (rankB < rankA) {
                    lowerSupps++;
                    l++;
                } else {
                    higherSupps++;
                    r++;
                }
                /*
                for (SasAction c : indices.actionOpposingActions[a.getId()]) {
                    int rankC = ranking[c.getId()];
                    if (rankC > rankB && rankC < rankA) {
                        inBetweenOpps++;
                    }
                }/**/
            }
            sqr = (l-r)*(l-r);
            if (l > r) {
                sleft++;
                supSqr += sqr;
            } else {
                sright++;
                supSqr -= sqr;
            }
            
        }
        //System.out.println(String.format("inbetween opps: %d, %.2f, lower supps: %d, higher supps %d, lower opps: %d, higher opps %d", 
          //      inBetweenOpps, (float)inBetweenOpps/lowerSupps , lowerSupps, higherSupps, lowerOpps, higherOpps));
        System.out.println(String.format("lol s-sqr %10d, o-sqr %10d, supp diff %d, opp diff %d, supps: l=%d, r=%d, l-r=%d opps: l=%d, r=%d, l-r=%d", 
                supSqr, oppSqr, lowerSupps - higherSupps, higherOpps - lowerOpps, sleft, sright, sleft-sright, left, right, left-right));
        /*
        long assPrevOpps = 0;
        long assNextOpps = 0;
        long gold = 0;
        long x = 0;
        BitSet reg = new BitSet(indices.actions.size());
        for (StateVariable var : indices.problem.getVariables()) {
            for (int val = 0; val < var.getDomain(); val++) {
                int aid = indices.assignmentIds[var.getId()][val];
                reg.clear();
                for (SasAction a : indices.assignmentSupportingActions[aid]) {
                    for (SasAction b : indices.assignmentOpposingActions[aid]) {
                        int rankA = ranking[a.getId()];
                        int rankB = ranking[b.getId()];
                        if (rankB < rankA) {
                            reg.set(b.getId());
                            assPrevOpps++;
                        } else {
                            assNextOpps++;
                        }
                    }
                }
                gold += reg.cardinality();
                x += indices.assignmentOpposingActions[aid].size();
            }
        }
        System.out.println(String.format("gold %d, %d, ass prev opps: %d, ass next opps: %d", 
                gold, x, assPrevOpps, assNextOpps));
        /**/
        
        
        return 0;
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
