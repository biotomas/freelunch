package freelunch.sat.satLifter.translation.covering;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.satLifter.translation.mutex.MutexPair;

public class MutexHelper {
    
    private Set<Integer>[] mutexIndex;
    private int variables;
    
    @SuppressWarnings("unchecked")
    public MutexHelper(int variables, List<MutexPair> mutexPairs) {
        this.variables = variables;
        mutexIndex = new Set[2*variables+1];
        for (int i = 0; i < mutexIndex.length; i++) {
            mutexIndex[i] = new HashSet<Integer>();
        }
        for (MutexPair pair : mutexPairs) {
            mutexIndex[litToVar(pair.getLiteral1())].add(pair.getLiteral2());
            mutexIndex[litToVar(pair.getLiteral2())].add(pair.getLiteral1());
        }
    }
    
    public boolean isMutex(int x, int y) {
        return mutexIndex[litToVar(x)].contains(y);
    }
    
    public Set<Integer> getMutexLits(int x) {
        return mutexIndex[litToVar(x)];        
    }
    
    
    private int litToVar(int lit) {
        return lit + variables;
    }

}
