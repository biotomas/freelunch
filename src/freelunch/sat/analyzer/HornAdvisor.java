package freelunch.sat.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freelunch.sat.bce.utilities.BCESimplifier;
import freelunch.sat.bce.utilities.BinaryHeap;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class HornAdvisor extends HiddenHornMaximizer {
	
	private class ScoredVariable implements Comparable<ScoredVariable> {
		
		public int id;
		public int score;
		public boolean shouldFlip;
		
		public ScoredVariable(int id, int score) {
			this.id = id;
			this.score = score;
		}

		@Override
		public int compareTo(ScoredVariable o) {
			return o.score - score;
		}
		
		@Override
		public int hashCode() {
			return id;
		}
		
		@Override
		public boolean equals(Object obj) {
			ScoredVariable o = (ScoredVariable) obj;
			return o.id == id; 
		}

		@Override
		public String toString() {
			return String.format("%d(%d)", id, score);
		}
	}
	
	private Map<Integer, ScoredVariable> varIndex;
	
	/**
	 * Idea: urob UP a BCE a potom vezmi premennu ktoru minim z oboch dosadeni spravi co najmenej non-horn klauzul
	 * niektore klauzule zmiznu uplne, niektore budu mat menej pozitivnych literalov. Takto postupne odstranuj premenne
	 * TODO otestovat
	 * @param f
	 * @param outAdvice
	 * @param outFlipped
	 */
	public List<Integer> preprocessFormula(BasicFormula f, int adviceSize) {
		// do unit propagation simplification
		UnitPropagationSimplifier.simplifyByUnitPropagation(f, true);
		// remove blocked clauses (includes removing pure literals)
		BCESimplifier.simplify(f);

		List<Integer> advice = new ArrayList<Integer>();
		initializeIndices(f);
		varIndex = new HashMap<Integer, HornAdvisor.ScoredVariable>();
		BinaryHeap<ScoredVariable> heap = new BinaryHeap<HornAdvisor.ScoredVariable>();
		varIndex = new HashMap<Integer, HornAdvisor.ScoredVariable>();
		for (int var = 1; var <= f.variablesCount; var++) {
			ScoredVariable sv = new ScoredVariable(var, 0);
			updateScore(sv);
			heap.add(sv);
			varIndex.put(var, sv);
		}
		
		for (int adv = 0; adv < adviceSize; adv++) {
			ScoredVariable best = heap.removeMinimum();
			if (best == null) {
				break;
			}
			int bvar = best.id;
			advice.add(bvar);

			Set<Integer> dirtyVars = new HashSet<Integer>();
			for (int[] cl : occurrence.get(bvar)) {
				for (int i = 0; i < cl.length; i++) {
					if (cl[i] == bvar && best.shouldFlip) {
						cl[i]*=-1;
					}
					dirtyVars.add(Math.abs(cl[i]));
				}
			}
			for (int[] cl : occurrence.get(-bvar)) {
				for (int i = 0; i < cl.length; i++) {
					if (cl[i] == -bvar && best.shouldFlip) {
						cl[i]*=-1;
					}
					dirtyVars.add(Math.abs(cl[i]));
				}
			}
			dirtyVars.remove(bvar);
			for (int dirty : dirtyVars) {
				ScoredVariable sv = varIndex.get(dirty);
				updateScore(sv);
				heap.update(sv);
			}
		}
		return advice;
	}
	
	private void updateScore(ScoredVariable sv) {
		int var = sv.id;
		int posOcc = occurrence.get(var).size();
		int negOcc = occurrence.get(-var).size();
		if (posOcc == negOcc) {
			sv.score = posOcc;
			return;
		}
		int flipScore = 0, notFlipScore = 0;
		if (posOcc <= negOcc) {
			notFlipScore = posOcc;
		} else {
			notFlipScore = Math.min(posOcc, negOcc + computeAlmostHorn(var));
		}
		
		if (negOcc <= posOcc) {
			flipScore = negOcc;
		} else {
			flipScore = Math.min(negOcc, posOcc + computeBarelyHorn(var));
		}

		sv.score = Math.max(flipScore, notFlipScore);
		if (flipScore > notFlipScore) {
			sv.shouldFlip = true;
		}
		
	}

	private int computeAlmostHorn(int var) {
		int almostHorn = 0;
		for (int[] cl : occurrence.get(var)) {
			if (HornAnalyzer.isAlmostHorn(cl)) {
				almostHorn++;
			}
		}
		return almostHorn;
	}
	
	private int computeBarelyHorn(int var) {
		int barelyHorn = 0;
		for (int[] cl : occurrence.get(-var)) {
			if (HornAnalyzer.isBarelyHorn(cl)) {
				barelyHorn++;
			}
		}
		return barelyHorn;		
	}

}
