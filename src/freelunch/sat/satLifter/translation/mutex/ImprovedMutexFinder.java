package freelunch.sat.satLifter.translation.mutex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.satLifter.sat.Propagator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ImprovedMutexFinder implements MutexFinder {

    @Override
    public List<MutexPair> findMutexPairs(BasicFormula formula) {
        HashSet<Integer> binaryClauseLiterals = new HashSet<Integer>();
        Propagator propagator = new Propagator(formula.variablesCount);
        for (int[] clause : formula.clauses) {
            propagator.addClause(clause);
            if (clause.length == 2) {
                binaryClauseLiterals.add(clause[0]);
                binaryClauseLiterals.add(clause[1]);
            }
        }
        
        List<MutexPair> mutexPairs = new ArrayList<MutexPair>();
        
        List<Integer> literals = new ArrayList<Integer>();
        for (int lit = 1; lit <= formula.variablesCount; lit++) {
            literals.add(lit);
            literals.add(-lit);
        }
        
        for (int lit : literals) {
            Set<Integer> mutexLits = new HashSet<Integer>();
            
            propagator.restartPropagator();
            List<Integer> deduced = propagator.propagate(lit);
            int[] varValuesAfterLit = Arrays.copyOf(propagator.varValues, propagator.varValues.length);
            
            if (deduced == null) {
                mutexLits.addAll(literals);
            } else {
                for (int deducedLit : deduced) {
                    mutexLits.add(-deducedLit);
                }
                Set<Integer> candidates = new HashSet<Integer>(propagator.getCandidateLiterals());
                candidates.addAll(binaryClauseLiterals);
                for (int candLit : candidates) {
                    int candidate = -candLit;
                    if (candidate <= lit) {
                        // symmetry breaking
                        continue;
                    }
                    if (mutexLits.contains(candidate)) {
                        // already known to be mutex
                        continue;
                    }
                    propagator.varValues = Arrays.copyOf(varValuesAfterLit, propagator.varValues.length);
                    if (propagator.propagate(candidate) == null) {
                        mutexLits.add(candidate);
                    }
                }
            }
            for (int mutex : mutexLits) {
                if (lit < mutex) {
                    mutexPairs.add(new MutexPair(lit, mutex));
                }
            }
        }
        return mutexPairs;
    }

}
