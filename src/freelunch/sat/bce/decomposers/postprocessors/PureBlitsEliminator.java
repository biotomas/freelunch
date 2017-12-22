package freelunch.sat.bce.decomposers.postprocessors;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

/**
 * Literals which are not shared between the blocked sets
 * and their blocking appearance is pure (when blocking literal
 * always positive or always negative) can be set to true.
 * 
 * THIS METHOD DOES NOT PRESERVE SATISFIABILITY !!!
 * of the large+small formula. Counterexample:
 * F = {(1),(-1,2), (-2,3)}
 * S = {(1)} L = {(-1, 2), (-2, 3)}
 */
@Deprecated
public class PureBlitsEliminator implements DecompositionPostprocessor {

	private BitSet commonVars;
	
	@Override
	public int moveToLarge(BasicFormula large, BasicFormula small) {
		commonVars = FormulaAnalyzer.commonVariables(small, large);
		
		List<Integer> ncpbLarge = notCommonPureBlits(large);
		int oldLargeSize = large.clauses.size();
		UnitPropagationSimplifier.simplifyFormula(large, ncpbLarge, false);
		addUnits(large, ncpbLarge);
		
		List<Integer> ncpbSmall = notCommonPureBlits(small);
		int oldSmallSize = small.clauses.size();
		UnitPropagationSimplifier.simplifyFormula(small, ncpbSmall, false);
		addUnits(small, ncpbSmall);
		
		Logger.print(1, String.format("c not-common pure-BLits in large: %d, in small %d, removed %d and %d clauses", 
				ncpbLarge.size(), ncpbSmall.size(), oldLargeSize - large.clauses.size(), oldSmallSize - small.clauses.size()));
		return 0;
	}
	
	private void addUnits(BasicFormula f, List<Integer> units) {
		for (int lit : units) {
			f.clauses.add(new int[]{lit});
		}
	}
	
	
	private List<Integer> notCommonPureBlits(BasicFormula f) {
		List<Integer> result = new ArrayList<Integer>();
		// has positive occurrence
		BitSet positiveOcc = new BitSet(f.variablesCount+1);
		// has negative occurrence
		BitSet negativeOcc = new BitSet(f.variablesCount+1);
		positiveOcc.clear();
		negativeOcc.clear();
		for (int[] cl : f.clauses) {
			int blit = cl[0];
			if (blit > 0) {
				positiveOcc.set(blit);
			} else {
				negativeOcc.set(-blit);
			}
		}
		BitSet isPositive = (BitSet) positiveOcc.clone();
		// positiveOcc will now contain the pure BLits
		positiveOcc.xor(negativeOcc);
		// positiveOcc will now contain non-shared pure BLits
		positiveOcc.andNot(commonVars);
		for (int var = 1; var <= f.variablesCount; var++) {
			if (positiveOcc.get(var)) {
				if (isPositive.get(var)) {
					result.add(var);
				} else {
					result.add(-var);
				}
			}
		}
		return result;
	}

	@Override
	public void setTimeLimit(long nanoseconds) {
	}
	
	

}
