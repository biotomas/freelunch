package freelunch.sat.satLifter.translation.covering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import freelunch.sat.satLifter.translation.mutex.MutexPair;

public abstract class BaseCliqueCoverGenerator {
    //NaiveRandomizedCliqueCoverGenerator
    protected Random random;
    protected MutexHelper mutexHelper;
    protected List<Integer> literals;
    
    public BaseCliqueCoverGenerator(long seed, int variables, List<MutexPair> mutexPairs) {
        random = new Random(seed);
        mutexHelper = new MutexHelper(variables, mutexPairs);
        literals = new ArrayList<Integer>();
        for (int i = 1; i <= variables; i++) {
            literals.add(i);
            literals.add(-i);
        }
    }
   
    protected boolean fits(int candidate, List<Integer> clique) {
        for (int lit : clique) {
            if (!mutexHelper.isMutex(candidate, lit)) {
                return false;
            }
        }
        return true;
    }

}
