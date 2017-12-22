package freelunch.sat.solver.preprocessing;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.Propagator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

/**
 * Einheitspropagationsbeweisbareermächtigungsklauselerzeugungsverfahren
 */
public class OneProvableEmpoweringClauses {
	
	private class UnsatException extends RuntimeException {
		private static final long serialVersionUID = 7973652585440399100L;
	}
	
	public class Statistics {
		public int[] clsHist = {0,0,0,0,0,0,0,0,0};
		
		@Override
		public String toString() {
			return String.format("c opec added %d unit, %d binary and %d ternary clauses", clsHist[0], clsHist[1], clsHist[2]);
		}
	}
	
	private Propagator unitPropagator;
	private BasicFormula f;
	private Stack<Integer> trail;
	private Statistics stats;
	private Stopwatch watch;
	private long timelimit;
	
	/**
	 * Add one-provable empowering clauses of the specified maximum 
	 * length (in literals) to the given formula. Returns false if
	 * the formula is detected to be unsatisfiable.
	 * @param f cnf formula
	 * @param maxLength maximum length of the added clauses
	 * @return false if the formula is UNSAT
	 */
	public boolean addOneProvableEmpoweringClauses(BasicFormula f, int maxLength, long seconds) {
		stats = new Statistics();
		watch = new Stopwatch();
		timelimit = seconds*1000*1000*1000;
		if (null == UnitPropagationSimplifier.simplifyByUnitPropagation(f, true)) {
			Logger.print(1, stats.toString());
			return false;
		}
		this.f = f;
		reinitializePropagator();
		
		trail = new Stack<Integer>();
		
		try {
			for (int l = 1; l <= maxLength; l++) {
				generateUpTo(l);
			}
		} catch (UnsatException e) {
			Logger.print(1, stats.toString());
			return false;
		}
		Logger.print(1, stats.toString());
		return true;
	}
	
	private void reinitializePropagator() {
		unitPropagator = new Propagator(f.variablesCount);
		for (int[] cl : f.clauses) {
			unitPropagator.addClause(cl);
		}
	}
	
	/**
	 * Recursively generate clauses up to the given length.
	 * @param length
	 * @return
	 */
	private void generateUpTo(int length) {
		if (length == 0) {
			return;
		}
		// symmetry breaking
		int start = trail.isEmpty() ? 0 : Math.abs(trail.peek());
		for (int var = start+1; var <= f.variablesCount; var++) {
			if (watch.timeLimitExceeded(timelimit)) {
				return;
			}
			if (unitPropagator.varValues[var] != 0) {
				continue;
			}
			trail.push(var);
			List<Integer> resPos = unitPropagator.propagate(var);
			if (resPos == null) {
				trail.pop();
				addImplication(trail, -var);
				unitPropagator.revert();
				continue;
			}
			generateUpTo(length - 1);
			trail.pop();
			unitPropagator.revert();

			if (watch.timeLimitExceeded(timelimit)) {
				return;
			}

			trail.push(-var);
			List<Integer> resNeg = unitPropagator.propagate(-var);
			if (resNeg == null) {
				trail.pop();
				addImplication(trail, var);
				unitPropagator.revert();
				continue;
			}
			generateUpTo(length - 1);
			trail.pop();
			unitPropagator.revert();
			
			// resPos = intersection of resPos and resNeg
			resPos.retainAll(resNeg);
			// unit clauses
			if (resPos.size() > 1 && trail.size() == 0) {
				stats.clsHist[1] += resPos.size();
				for (int lit : resPos) {
					f.clauses.add(new int[] {lit});
				}
				if (null == UnitPropagationSimplifier.simplifyByUnitPropagation(f, true)) {
					throw new UnsatException();
				}
				reinitializePropagator();
			} else {
				for (int lit : resPos) {
					addImplication(trail, lit);
				}
			}
		}
	}
	
	/**
	 * Add clause of the form tail => head
	 * @param tail
	 * @param head
	 */
	private void addImplication(Collection<Integer> tail, int head) {
		int[] ncl = new int[tail.size()+1];
		int i = 0;
		for (int lit : tail) {
			ncl[i++] = -lit;
		}
		ncl[i++] = head;
		f.clauses.add(ncl);
		stats.clsHist[ncl.length-1]++;
		if (ncl.length == 1) {
			if (null == UnitPropagationSimplifier.simplifyByUnitPropagation(f, true)) {
				throw new UnsatException();
			}
			reinitializePropagator();
		} else {
			unitPropagator.addClause(ncl);
		}
	}

}
