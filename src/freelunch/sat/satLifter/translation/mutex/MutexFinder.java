package freelunch.sat.satLifter.translation.mutex;

import java.util.List;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public interface MutexFinder {
    
    /**
     * Find all the mutex pairs of a formula
     * @param formula
     * @return
     */
    public List<MutexPair> findMutexPairs(BasicFormula formula);

}
