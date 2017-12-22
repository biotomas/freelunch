package freelunch.sat.bce.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.satLifter.sat.Propagator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class UnitPropagationSimplifier {
	
	private static int simplified = 0;
	
	/**
	 * Simplify the given formula by running unit propagation
	 * and removing sat clauses and unsat literals.
	 * @param f
	 * @return null if conflict
	 */
	public static List<Integer> simplifyByUnitPropagation(BasicFormula f, boolean simplifyClauses) {
		// Find the unit clauses
		int origClauseCount = f.clauses.size();
		Set<Integer> unitLiterals = new HashSet<Integer>();
		int[] assignment = new int[f.variablesCount+1];
		Arrays.fill(assignment, 0);
		for (int[] cl : f.clauses) {
			if (cl.length == 1) {
				unitLiterals.add(cl[0]);
				if (assignment[Math.abs(cl[0])] == -cl[0]) {
					// conflict
					return null;
				}
				assignment[Math.abs(cl[0])] = cl[0];
			}
		}
		Logger.print(1, String.format("c Formula contains %d unit clauses", unitLiterals.size()));
		if (unitLiterals.isEmpty()) {
			return new ArrayList<Integer>();
		}
		// Add the not satisfied clauses to the propagator
		Propagator propagator = new Propagator(f.variablesCount);
		for (int[] cl : f.clauses) {
			if (!clauseSat(cl, assignment)) {
				propagator.addClause(cl);
			}
		}
		// Run the propagator
		List<Integer> assignments = new ArrayList<Integer>(unitLiterals);
		int assignmentsCount = unitLiterals.size();
		boolean conflict = propagator.propagate(assignments);
		if (conflict) {
			return null;
		}
		// Collect the new assignments
		for (int i = assignmentsCount; i < assignments.size(); i++) {
			int lit = assignments.get(i);
			assignment[Math.abs(lit)] = lit;
		}

		// Simplify the formula
		simplifyFormula(f, assignment, simplifyClauses);
		Logger.print(1, String.format("c Unit propagation assigned %d variables, removed %d clauses, simplified %d clauses", 
				assignments.size(), origClauseCount - f.clauses.size(), simplified));
		return assignments;
	}
	
	public static void simplifyFormula(BasicFormula f, List<Integer> units, boolean simplifyClauses) {
		if (units.isEmpty()) {
			return;
		}
		int[] assignment = new int[f.variablesCount+1];
		Arrays.fill(assignment, 0);
		for (int lit : units) {
			assignment[Math.abs(lit)] = lit;
		}
		simplifyFormula(f, assignment, simplifyClauses);
	}
	
	public static void simplifyFormula(BasicFormula f, int[] assignment, boolean simplifyClauses) {
		List<int[]> newClauses = new ArrayList<int[]>();
		simplified = 0;
		for (int[] cl : f.clauses) {
			if (clauseSat(cl, assignment)) {
				continue;
			}
			if (simplifyClauses) {
				newClauses.add(simplifyClause(cl, assignment));
			} else {
				newClauses.add(cl);
			}
		}
		f.clauses = newClauses;
	}
	
	/**
	 * Simplify the clause given the assignment.
	 * @param cl
	 * @param assignment
	 * @return
	 */
	private static int[] simplifyClause(int[] cl, int[] assignment) {
		int removed = 0;
		for (int l : cl) {
			if (assignment[Math.abs(l)] != 0) {
				removed++;
			}
		}
		if (removed == 0) {
			return cl;
		}
		simplified++;
		int[] result = new int[cl.length - removed];
		int j = 0;
		for (int i = 0; i < cl.length; i++) {
			if (assignment[Math.abs(cl[i])] == 0) {
				result[j] = cl[i];
				j++;
			}
		}
		return result;
	}
	
	private static boolean clauseSat(int[] cl, int[] assignment) {
		for (int l : cl) {
			if (assignment[Math.abs(l)] == l) {
				return true;
			}
		}
		return false;
	}

}
