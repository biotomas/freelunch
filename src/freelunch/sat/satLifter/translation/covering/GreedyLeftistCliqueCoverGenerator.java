package freelunch.sat.satLifter.translation.covering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import freelunch.sat.satLifter.translation.mutex.MutexPair;

public class GreedyLeftistCliqueCoverGenerator extends BaseCliqueCoverGenerator implements CliqueCoverGenerator {

    public GreedyLeftistCliqueCoverGenerator(long seed, int variables, List<MutexPair> mutexPairs) {
        super(seed, variables, mutexPairs);
    }

    @Override
    public List<List<Integer>> generateCliqueCover() {
        
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<List<Integer>> extendableCliques = new LinkedList<List<Integer>>();
        
        Collections.shuffle(literals, random);
        HashSet<Integer> usedVariables = new HashSet<Integer>();
        
        // 1st phase: generate size 2 cliques greedily
        for (int lit : literals) {
            if (usedVariables.contains(Math.abs(lit))) {
                continue;
            }
            // find a neighbor with the highest number of common neighbors
            int bestNeighbor = 0;
            int commonNeighbor = 0;
            for (int neighbor : mutexHelper.getMutexLits(lit)) {
                if (usedVariables.contains(Math.abs(neighbor))) {
                    continue;
                }
                Set<Integer> intersection = new HashSet<Integer>(mutexHelper.getMutexLits(lit));
                intersection.retainAll(mutexHelper.getMutexLits(neighbor));
                int score = intersection.size();
                if (score > 0) {
                    bestNeighbor = neighbor;
                    for (int common : intersection) {
                        if (!usedVariables.contains(Math.abs(common))) {
                            commonNeighbor = common;
                        }
                    }
                }
            }

            if (commonNeighbor != 0) {
                List<Integer> clique = new ArrayList<Integer>();
                clique.add(lit);
                usedVariables.add(Math.abs(lit));
                clique.add(bestNeighbor);
                usedVariables.add(Math.abs(bestNeighbor));
                clique.add(commonNeighbor);
                usedVariables.add(Math.abs(commonNeighbor));
                extendableCliques.add(clique);
            }
        }
        
        // 2nd phase: keep extending the cliques one by one until possible
        while (extendableCliques.size() > 0) {
            Iterator<List<Integer>> iter = extendableCliques.iterator();
            while (iter.hasNext()) {
                List<Integer> clique = iter.next();
                boolean extended = false;
                int lit = clique.get(random.nextInt(clique.size()));
                for (int candidate : mutexHelper.getMutexLits(lit)) {
                    if (usedVariables.contains(Math.abs(candidate))) {
                        continue;
                    }
                    if (fits(candidate, clique)) {
                        clique.add(candidate);
                        usedVariables.add(Math.abs(candidate));
                        extended = true;
                        // only add one literal to the clique
                        //break;
                    }
                }
                if (!extended) {
                    result.add(clique);
                    iter.remove();
                }
            }
        }
        
        // 3rd phase: add the singletons
        for (int lit : literals) {
            if (!usedVariables.contains(Math.abs(lit))) {
                List<Integer> clique = new ArrayList<Integer>();
                clique.add(lit);
                usedVariables.add(Math.abs(lit));
                result.add(clique);
            }
        }
        return result;
    }
    
    
}
