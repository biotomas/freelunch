package freelunch.sat.bce.eliminators;

import java.util.ArrayList;
import java.util.List;

import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class StupidEliminator implements BCEliminator {
	
	private long timelimit = 0;

	@Override
	public ArrayList<int[]> eliminateBlockedClauses(BasicFormula formula) {
		Stopwatch watch = new Stopwatch();
		ArrayList<int[]> result = new ArrayList<int[]>();
		List<int[]> remainingClauses = new ArrayList<int[]>(formula.clauses);
		int iter = 0;
		while (true) {
			if (watch.timeLimitExceeded(timelimit)) {
				return null;
			}
			iter++;
			System.out.println("Iteration " + iter);
			int elim = result.size();
			for (int[] cl : formula.clauses) {
				if (result.contains(cl))
					continue;
				//check if clause is blocked
				litLoop:
				for (int lit : cl) {
					for (int[] cand : remainingClauses) {
						if (!containsLit(cand, -lit)) {
							continue;
						}
						if (oneConflict(cl, cand)) {
							continue litLoop;
						}
					}
					result.add(cl);
					remainingClauses.remove(cl);
					break;
				}
			}
			if (elim == result.size()) {
				break;
			}
		}
		
		return result;
	}
	
	private boolean containsLit(int[] cl, int lit) {
		for (int l : cl) {
			if (l == lit) {
				return true;
			}
		}
		return false;
	}
	
	private boolean oneConflict(int[] c1, int[] c2) {
		int conflicts = 0;
		for (int l1 : c1) {
			for (int l2 : c2) {
				if (l1 == -l2) {
					conflicts++;
				}
			}
		}
		return conflicts == 1;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
		this.timelimit = nanoseconds;
	}

}
