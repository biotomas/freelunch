package freelunch.sat.satLifter.translation.mutex;

import java.util.ArrayList;
import java.util.List;

import freelunch.sat.satLifter.sat.Propagator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class BasicMutexFinder implements MutexFinder {

    @Override
    public List<MutexPair> findMutexPairs(BasicFormula formula) {
        Propagator propagator = new Propagator(formula.variablesCount);
        for (int[] clause : formula.clauses) {
            propagator.addClause(clause);
        }
        
        ArrayList<MutexPair> mutexPairs = new ArrayList<MutexPair>();

        ArrayList<Integer> literals = new ArrayList<Integer>();
        for (int i = 1; i <= formula.variablesCount; i++) {
            literals.add(i);
            literals.add(-i);
        }
        
        for (int litInd1 = 0; litInd1 < literals.size(); litInd1++) {
            for (int litind2 = litInd1 + 1; litind2 < literals.size(); litind2++) {
                int lit1 = literals.get(litInd1);
                int lit2 = literals.get(litind2);
                if (lit1 == -lit2) {
                    mutexPairs.add(new MutexPair(lit2, lit1));
                } else {
                    propagator.restartPropagator();
                    if (propagator.propagate(lit1, lit2) == null) {
                        mutexPairs.add(new MutexPair(lit2, lit1));
                    }
                }
            }
        }
        
        return mutexPairs;
    }

}
