package freelunch.sat.satLifter.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Propagator {

    private class Watch {

        int blit; // the other watched literal
        int[] clause; // NULL for binary clauses

        public Watch(int l, int[] c) {
            blit = l;
            clause = c;
        }
        
        @Override
        public String toString() {
            return blit + ":" + Arrays.toString(clause);
        }
    };

    // the truth values of variables, positive = true, negative = false, 0 = N/A
    public int[] varValues;
    private List<Watch>[] positiveLits;
    private List<Watch>[] negativeLits;
    private HashSet<Integer> candidateLiterals;
    private Stack<List<Integer>> assignmentStack;

    private boolean litTrue(int lit) {
        return varValues[Math.abs(lit)] == lit;
    }

    private boolean litFalse(int lit) {
        return varValues[Math.abs(lit)] == -lit;
    }

    @SuppressWarnings("unchecked")
    public Propagator(int variables) {
        positiveLits = new List[variables + 1];
        negativeLits = new List[variables + 1];
        for (int i = 1; i <= variables; i++) {
            positiveLits[i] = new LinkedList<Propagator.Watch>();
            negativeLits[i] = new LinkedList<Propagator.Watch>();
        }
        candidateLiterals = new HashSet<Integer>();
        varValues = new int[variables + 1];
        assignmentStack = new Stack<List<Integer>>();
        restartPropagator();
    }

    public void addClause(int[] clause) {
        int l1 = clause[0];
        int l2 = clause[1];
        boolean binary = (clause.length == 2);
        if (l1 > 0) {
            positiveLits[l1].add(new Watch(l2, binary ? null : clause));
        } else {
            negativeLits[-l1].add(new Watch(l2, binary ? null : clause));
        }
        if (l2 > 0) {
            positiveLits[l2].add(new Watch(l1, binary ? null : clause));
        } else {
            negativeLits[-l2].add(new Watch(l1, binary ? null : clause));
        }
    }
    
    /**
     * Get literals that are in currently binary clauses
     * @return
     */
    public HashSet<Integer> getCandidateLiterals() {
        return candidateLiterals;
    }
   
    /**
     * Clear the variable assignments
     */
    public void restartPropagator() {
        Arrays.fill(varValues, 0);
        candidateLiterals.clear();
        assignmentStack.clear();
    }
    
    /**
     * Undo one propagation step
     */
    public void revert() {
    	if (assignmentStack.isEmpty()) {
    		return;
    	}
        List<Integer> assignments = assignmentStack.pop();
        for (int lit : assignments) {
        	int var = Math.abs(lit);
        	varValues[var] = 0;
        }
        candidateLiterals.clear();
    }
    
    /**
     * Propagate the given assignments, add new ones possibly
     * @param assignments
     * @return true if a conflict is deduced
     */
    public boolean propagate(List<Integer> assignments) {
        for (int lit : assignments) {
        	varValues[Math.abs(lit)] = lit;
        }
        for (int i = 0; i < assignments.size(); i++) {
            int lit = -assignments.get(i);
            List<Watch> watches = lit > 0 ? positiveLits[lit] : negativeLits[-lit];
            Iterator<Watch> iter = watches.iterator();

            while (iter.hasNext()) {
                Watch watch = iter.next();
                int blit = watch.blit;
                if (litTrue(blit)) {
                    // blit is true -> clause is true
                    continue;
                }
                if (watch.clause == null) {
                    // it is binary but blit is false -> conflict
                    if (litFalse(blit)) {
                        assignmentStack.push(new ArrayList<Integer>(assignments));
                        return true;
                    }
                    // propagation because blit must be undefined
                    varValues[Math.abs(blit)] = blit;
                    assignments.add(blit);
                    continue;
                }
                int[] clause = watch.clause;
                // normalization, simplifies following code
                if (lit == clause[1]) {
                    int tmp = lit;
                    clause[1] = clause[0];
                    clause[0] = tmp;
                }
                blit = clause[1];
                // lit is clause[0], blit is clause[1]
                // start from the 3rd literal, go until 0
                // and find a non-false literal.
                boolean watchFound = false;
                for (int cIndex = 2; cIndex < clause.length; cIndex++) {
                    int l = clause[cIndex];
                    if (!litFalse(l)) {
                        // l is the new watched literal
                        int tmp = clause[0];
                        clause[0] = l;
                        clause[cIndex] = tmp;
                        iter.remove();
                        List<Watch> newWatch = clause[0] > 0 ? positiveLits[clause[0]]
                                : negativeLits[-clause[0]];
                        newWatch.add(new Watch(blit, clause));
                        watchFound = true;
                        
                        if (cIndex + 1 == clause.length) {
                            // this is a binary clause
                            candidateLiterals.add(l);
                            candidateLiterals.add(blit);
                        }
                        break;
                    }
                }
                if (!watchFound) {
                    if (litFalse(blit)) {
                        // conflict
                        assignmentStack.push(new ArrayList<Integer>(assignments));
                        return true;
                    }
                    if (!litTrue(blit)) {
                        // propagation
                        varValues[Math.abs(blit)] = blit;
                        assignments.add(blit);
                    }
                }
            }
        }
        assignmentStack.push(new ArrayList<Integer>(assignments));
        return false;
    }
    
    /**
     * Propagate the given literals and return the list of deduced literals
     * or null if a conflict arises.
     * @param lit
     * @return list of literals or null
     */
    public List<Integer> propagate(int lit1, int lit2) {
        ArrayList<Integer> assignments = new ArrayList<Integer>();
        varValues[Math.abs(lit1)] = lit1;
        assignments.add(lit1);
        if (lit2 != 0) {
            varValues[Math.abs(lit2)] = lit2;
            assignments.add(lit2);
        }
        boolean res = propagate(assignments);
        if (res) {
        	return null;
        } else {
        	return assignments;
        }
    }

    /**
     * Propagate the given literal and return the list of deduced literals
     * or null if a conflict arises.
     * @param lit
     * @return list of literals or null
     */
    public List<Integer> propagate(int lit) {
        return propagate(lit, 0);
    }
}
