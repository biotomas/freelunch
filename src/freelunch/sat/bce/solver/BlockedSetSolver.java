package freelunch.sat.bce.solver;

import java.util.ArrayList;
import java.util.Arrays;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class BlockedSetSolver {
	
	public static final int Unset = 0;
	
	/**
	 * Start with a fully Unset assignment
	 * @param formula
	 * @param chooser
	 * @return
	 */
	public int[] solveBlockedFormula(BasicFormula formula, ChoiceMaker chooser) {
		int[] assignment = new int[formula.variablesCount + 1];
		Arrays.fill(assignment, Unset);
		return solveBlockedFormula(formula, chooser, assignment);
	}
		
	/**
	 * Start with a given assignment which may contain Unset variables.
	 * @param formula
	 * @param chooser
	 * @param assignment
	 * @return
	 */
	public int[] solveBlockedFormula(BasicFormula formula, ChoiceMaker chooser, int[] assignment) {
		
		if (!(formula.clauses instanceof ArrayList<?>)) {
			formula.clauses = new ArrayList<int[]>(formula.clauses);
		}
		
		ArrayList<int[]> stack = (ArrayList<int[]>) formula.clauses;
		ArrayList<Integer> choices = new ArrayList<Integer>();
		
		stackcycle:
		for (int i = stack.size() - 1; i >= 0; i--) {
			int[] clause = stack.get(i);
			choices.clear();
			for (int lit : clause) {
				int var = Math.abs(lit);
				// clause is satisfied
				if (assignment[var] == lit) {
					continue stackcycle;
				}
				// variable is undefined
				if (assignment[var] == 0) {
					choices.add(lit);
				}
			}
			if (choices.size() > 0) {
				// choose an undefined variable
				int lit = choices.get(0);
				if (choices.size() > 1) {
					lit = chooser.chooseLiteral(choices, assignment);
				}
				int var = Math.abs(lit);
				assignment[var] = lit;
			} else {
				// flip the blocking (first) literal
				int first = clause[0];
				int var = Math.abs(first);
				assignment[var] = first;
			}
		}
		return assignment;
	}

}
