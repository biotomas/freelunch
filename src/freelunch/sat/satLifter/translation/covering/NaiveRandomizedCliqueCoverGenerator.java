package freelunch.sat.satLifter.translation.covering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import freelunch.sat.satLifter.translation.mutex.MutexPair;

public class NaiveRandomizedCliqueCoverGenerator extends BaseCliqueCoverGenerator implements CliqueCoverGenerator {

    public NaiveRandomizedCliqueCoverGenerator(long seed, int variables, List<MutexPair> mutexPairs) {
        super(seed, variables, mutexPairs);
    }

    @Override
    public List<List<Integer>> generateCliqueCover() {
        
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        Collections.shuffle(literals, random);
        HashSet<Integer> usedVariables = new HashSet<Integer>();
        
        for (int lit : literals) {
            if (usedVariables.contains(Math.abs(lit))) {
                continue;
            }
            usedVariables.add(Math.abs(lit));
            List<Integer> clique = new ArrayList<Integer>();
            clique.add(lit);
            
            List<Integer> neighbours = new ArrayList<Integer>(mutexHelper.getMutexLits(lit));
            Collections.shuffle(neighbours, random);
            
            for (int candidate : neighbours) {
                if (usedVariables.contains(Math.abs(candidate))) {
                    continue;
                }
                if (fits(candidate, clique)) {
                    clique.add(candidate);
                    usedVariables.add(Math.abs(candidate));
                }
            }
            result.add(clique);
        }
        return result;
    }
    
}
