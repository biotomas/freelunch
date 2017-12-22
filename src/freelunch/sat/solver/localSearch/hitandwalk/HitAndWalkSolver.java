package freelunch.sat.solver.localSearch.hitandwalk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.solver.localSearch.BaseWalkSAT;
import freelunch.sat.solver.localSearch.LSClause;

public class HitAndWalkSolver extends BaseWalkSAT {

	public HitAndWalkSolver(long seed) {
		super(null, seed);
	}
	
	@Override
	public Boolean isSatisfiable() {
        Stopwatch watch = new Stopwatch();
        lsData.totalFlips = 0;
        lsData.totalRestarts = 0;
        restartInterval = INITIAL_RESTART_INTERVAL;
        outerRestartInterval = INITIAL_RESTART_INTERVAL >> 1;
        
        // Iterations (Restarts) cycle
        while (true) {
            generateRandomAssignment(assignment);
            lsData.clManager.setAssignment(assignment);
            int flipsSinceRestart = 0;
            
            // Flips cycle
            while (true) {
            	flipsSinceRestart++;
            	lsData.totalFlips++;
            	if (shouldRestart(flipsSinceRestart)) {
            		break;
            	}
            	// Get all unsat clauses, we will satisfy all of them
            	List<LSClause> ucls = lsData.clManager.getAllUnsatClauses();
            	int unsatClauseCount = ucls.size();
            	if (unsatClauseCount == 0) {
                    bestAssignment = assignment;
            		return true;
            	}
            	Logger.print(0, "unsat clauses:"+ucls.size());
            	Map<Integer, Set<LSClause>> literalOccurences = new HashMap<Integer, Set<LSClause>>();
            	for (LSClause c : ucls) {
            		//Logger.print(0, c.toString());
            		for (int lit : c.getLiterals()) {
            			if (literalOccurences.get(lit) == null) {
            				literalOccurences.put(lit, new HashSet<LSClause>());
            			}
            			literalOccurences.get(lit).add(c);
            		}            		
            	}
            	
            	// Calculate a hitting set for the unsat clauses (using a naive greedy algorithm)
            	List<Integer> litsToFlip = new ArrayList<Integer>();
            	while (unsatClauseCount > 0) {
                	//Logger.print(0, literalOccurences.toString());
            		int bestLit = 0;
            		int bestOcc = 0;
            		for (int lit : literalOccurences.keySet()) {
            			int occ = literalOccurences.get(lit).size() + lsData.random.nextInt(3);
            			if (occ > bestOcc) {
            				bestLit = lit;
            				bestOcc = occ;
            			}
            		}
            		litsToFlip.add(bestLit);
            		//Logger.print(0, String.format("will flip %d because it has %d occurrences", bestLit, bestOcc));
            		for (LSClause c : literalOccurences.get(bestLit)) {
            			unsatClauseCount--;
            			for (int olit : c.getLiterals()) {
            				if (olit != bestLit) {
            					literalOccurences.get(olit).remove(c);
            				}
            			}
            		}
            		literalOccurences.remove(bestLit);
            	}
            	Logger.print(0, litsToFlip.toString());

            	for (int lit : litsToFlip) {
            		lsData.clManager.makeLiteralTrue(lit);
            		lsData.flipRates[Math.abs(lit)]++;
            	}

            	
                // checking time limit is somehow expensive
                if (lsData.totalFlips % 100000 == 0 && watch.timeLimitExceeded(timeLimit)) {
                    return null;
                }
                
            }
            lsData.totalRestarts++;
        }
	}
	

}
