package freelunch.sat.analyzer;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class HiddenHornMaximizer {
	
	protected Map<Integer, Set<int[]>> occurrence;
	protected Map<int[], Integer> positiveLits;
	protected Set<int[]> nonHorn;
	protected BitSet flippedVars;
	protected int hornCount;
	protected int flips;
	
	protected void addOccurrence(int lit, int[] cl) {
		occurrence.get(lit).add(cl);
	}
	
	protected void initializeIndices(BasicFormula f) {
		flips = 0;
		flippedVars = new BitSet(f.variablesCount+1);
		occurrence = new HashMap<Integer, Set<int[]>>();
		for (int i = 1; i <= f.variablesCount; i++) {
			occurrence.put(i, new HashSet<int[]>());
			occurrence.put(-i, new HashSet<int[]>());
		}
		positiveLits = new HashMap<int[], Integer>();
		nonHorn = new HashSet<int[]>();
		for (int[] cl : f.clauses) {
			int positive = 0;
			for (int lit : cl) {
				addOccurrence(lit, cl);
				if (lit > 0) {
					positive++;
				}
			}
			positiveLits.put(cl, positive);
			if (positive > 1) {
				nonHorn.add(cl);
			}
		}
		hornCount = f.clauses.size() - nonHorn.size();
	}
	
	/**
	 * Make the given formula Horn (at most one positive literal) as much as possible by flipping literals.
	 * Return a bit-set containing the flipped variables.
	 * @param f
	 * @param seconds time limit for hornification
	 * @return a bitset with flipped literals
	 */
	public BitSet hornify(BasicFormula f, int seconds) {
		initializeIndices(f);
		
		Logger.print(1, String.format("c initial horn cls %d (%d%%), timelit: %d",
				hornCount, (hornCount*100)/f.clauses.size(), seconds));
		
		Stopwatch watch = new Stopwatch();
		while (!nonHorn.isEmpty()) {
			if (watch.elapsedSeconds() > seconds) {
				break;
			}
			
			Iterator<int[]> iter = nonHorn.iterator();
			int[] cl = iter.next();
			iter.remove();
			if (positiveLits.get(cl) < 2) {
				continue;
			}
			int bestVar = 0;
			int bestScore = Integer.MIN_VALUE;
			int posLits = 0;
			for (int lit : cl) {
				int var = Math.abs(lit);
				boolean flipped = flippedVars.get(var);
				// if it is a positive literal
				if ((flipped && lit < 0) || (!flipped && lit > 0)) {
					posLits++;
					int makes = makeScore(var);
					int breaks = breakScore(var);
					int score = makes - breaks;
					if (score > bestScore) {
						bestScore = score;
						bestVar = var;
					}
				}
			}
			assert (posLits < 2);
			if (bestScore > 0) {
				flipVariable(bestVar);
			}
		}
		Logger.print(1, String.format("c final horn cls %d (%d%%)",
				hornCount, (hornCount*100)/f.clauses.size()));
		Logger.print(1, String.format("c total flips %d (%d%% of vars)",
				flips, (flips*100)/f.variablesCount));
		Logger.print(1, String.format("c flipped vars %d (%d%% of vars)",
				flippedVars.cardinality(), (flippedVars.cardinality()*100)/f.variablesCount));
		for (int[] cl : f.clauses) {
			for (int i = 0; i < cl.length; i++) {
				int var = Math.abs(cl[i]);
				if (flippedVars.get(var)) {
					cl[i] = -cl[i];
				}
			}
		}
		return flippedVars;
	}
	
	protected void flipVariable(int var) {
		// the clauses where the number of positive literals decreases
		int lit = flippedVars.get(var) ? -var : var;
		if (occurrence.containsKey(lit)) {
			for (int[] cl : occurrence.get(lit)) {
				int pl = positiveLits.get(cl);
				positiveLits.put(cl, pl-1);
				if (pl == 2) {
					hornCount++;
				}
			}
		}
		// the clauses where the number of positive literal increases
		lit = flippedVars.get(var) ? var : -var;
		if (occurrence.containsKey(lit)) {
			for (int[] cl : occurrence.get(lit)) {
				int pl = positiveLits.get(cl);
				positiveLits.put(cl, pl+1);
				if (pl == 1) {
					nonHorn.add(cl);
					hornCount--;
				}
			}
		}
		flippedVars.flip(var);
		flips++;
	}
	
	/**
	 * How many non-Horn clauses would become Horn if we flipped the given variable
	 * @param var
	 * @return
	 */
	protected int makeScore(int var) {
		int make = 0;
		int lit = flippedVars.get(var) ? -var : var;
		if (occurrence.containsKey(lit)) {
			for (int[] cl : occurrence.get(lit)) {
				int pl = positiveLits.get(cl);
				if (pl == 2) {
					make++;
				}
			}
		}
		return make;
	}

	/**
	 * How many Horn clauses would become non-Horn if we flipped the given variable
	 * @param var
	 * @return
	 */
	protected int breakScore(int var) {
		int breaks = 0;
		int lit = flippedVars.get(var) ? var : -var;
		if (occurrence.containsKey(lit)) {
			for (int[] cl : occurrence.get(lit)) {
				int pl = positiveLits.get(cl);
				if (pl == 1) {
					breaks++;
				}
			}
		}
		return breaks;
	}

}
