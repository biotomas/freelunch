package freelunch.sat.bce.extractors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import freelunch.sat.bce.solver.BlockedSetSolver;
import freelunch.sat.bce.solver.RandomChoiceMaker;
import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class RandomExtractor {

	protected int[] bones;
	protected int[] stars;
	protected int starsCount = 0;
	protected int bonesCount = 0;
	protected BasicFormula formula;
	protected List<List<Integer>> equivalences = null;
	protected List<Integer>[] equivalenceIndex;
	
	@SuppressWarnings("unchecked")
	protected void updateEquivalences(int[] result) {
		// first call
		if (equivalences == null) {
			equivalenceIndex = new ArrayList[result.length];
			equivalences = new LinkedList<List<Integer>>();
			List<Integer> set0 = new ArrayList<Integer>();
			List<Integer> set1 = new ArrayList<Integer>();
			for (int i = 1; i < result.length; i++) {
				int lit = result[i];
				if (lit > 0) {
					set1.add(lit);
					equivalenceIndex[lit] = set1;
				}
				if (lit < 0) {
					set0.add(-lit);
					equivalenceIndex[-lit] = set0;
				}
			}
			if (set0.size() > 0) equivalences.add(set0);
			if (set1.size() > 0) equivalences.add(set1);
		// not first call
		} else {
			List<List<Integer>> newGroups = new ArrayList<List<Integer>>();
			Iterator<List<Integer>> iter = equivalences.iterator();
			while (iter.hasNext()) {
				List<Integer> group = iter.next();
				List<Integer> set0 = new ArrayList<Integer>();
				List<Integer> set1 = new ArrayList<Integer>();
				for (int var : group) {
					if (result[var] > 0) {
						set1.add(var);
					}
					if (result[var] < 0) {
						set0.add(var);
					}
				}
				if (group.size() == set1.size() || group.size() == set0.size()) {
					continue;
				}
				iter.remove();
				for (int var : group) {
					equivalenceIndex[var] = null;
				}
				if (set1.size() > 1) {
					newGroups.add(set1);
					for (int var : set1) {
						equivalenceIndex[var] = set1;
					}
				}
				if (set0.size() > 1) {
					newGroups.add(set0);
					for (int var : set0) {
						equivalenceIndex[var] = set0;
					}
				}
			}
			equivalences.addAll(newGroups);
		}
	}

	/**
	 * Extract the potential backbones and not backbones.
	 * The input formula must be a blocked set properly ordered
	 * with the first literal of each clause being the blocking literal.
	 * @param formula
	 */
	public void extractBackBones(BasicFormula formula) {
		this.formula = formula;

		BlockedSetSolver solver = new BlockedSetSolver();
		int[] result = solver.solveBlockedFormula(formula, new RandomChoiceMaker(2013));
		bones = Arrays.copyOf(result, result.length);
		stars = new int[result.length];
		for (int i = 1; i < result.length; i++) {
			stars[i] = result[i] == 0 ? 1 : 0;
			if (stars[i] > 0) starsCount++;
			if (result[i] != 0) bonesCount++;
		}
		
		int iterations = 1;
		int extras = 0;
		while (true) {
			iterations++;
			result = solver.solveBlockedFormula(formula, new RandomChoiceMaker(2*iterations));
			int oldBones = bonesCount;
			int oldStars = starsCount;
			processSolution(result);
			if (oldBones == bonesCount && oldStars == starsCount) {
				extras++;
			} else {
				extras = 0;
			}
			if (extras > 0) {
				break;
			}
		}		
		printResults(iterations);
	}
	
	protected void printResults(int iterations) {
		// equivalence info
		int totalLength = 0;
		int equivcount = equivalences.size();
		for (List<Integer> grp : equivalences) {
			int var = grp.get(0);
			if (bones[var] == 0) {
				totalLength += grp.size();
			} else {
				equivcount--;
			}
		}
		System.out.println(String.format("csv;iterations;%d;vars;%d;stars;%d;%%;%d;backbones;%d;%%;%d;eqClasses;%d;avgEqClass;%.1f", iterations, 
				formula.variablesCount, starsCount, (100*starsCount)/formula.variablesCount,
				bonesCount, (100*bonesCount)/formula.variablesCount, equivcount, ((float)totalLength)/equivcount));
		/*
		System.out.println("Equivalence classes (W/O BackBones):" + equivcount);
		System.out.println("total length: " + totalLength);
		System.out.println(equivalences);
		// backbones
		System.out.print("positive bones: ");
		for (int i : bones) {
			if (i > 0) System.out.print(i + ", ");
		}
		System.out.println();
		System.out.print("negative bones: ");
		for (int i : bones) {
			if (i < 0) System.out.print(i + ", ");
		}
		System.out.println();
		*/
	}
	
	protected void processSolution(int[] result) {
		// check the solution
		if (!FormulaAnalyzer.solutionOk(formula, result)) {
			throw new RuntimeException("formula solution invalid");
		}
		updateEquivalences(result);
		// merge stars and update bones
		starsCount = 0;
		bonesCount = 0;
		for (int i = 1; i < result.length; i++) {
			if (result[i] != bones[i]) {
				bones[i] = 0;
			}
			if (bones[i] != 0) {
				bonesCount++;
			}
			if (result[i] == 0) {
				stars[i]++;
			}
			if (stars[i] > 0) {
				starsCount++;
			}
		}
	}
}
