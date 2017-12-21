package freelunch.core.satSolving.maxsat;

import java.util.Arrays;

import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.pb.OptToPBSATAdapter;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;

import freelunch.core.satSolving.maxsat.WeightedPartialMaxSatFormula.WeightedClause;

public class Sat4JMaxsatSolver implements MaxSatSolver {

	@Override
	public int[] solvePartialMaxsat(PartialMaxSatFormula pmax) {
		return solvePartialWeightedMaxsat(new WeightedPartialMaxSatFormula(pmax));
	}

	@Override
	public int[] solvePartialWeightedMaxsat(WeightedPartialMaxSatFormula wpmax) {
		WeightedMaxSatDecorator ms = new WeightedMaxSatDecorator(SolverFactory.newDefault());
		ms.newVar(wpmax.getVariables());
		
		try {
			for (int[] hardc : wpmax.getHardClauses()) {
				ms.addHardClause(new VecInt(hardc));
			}
			for (WeightedClause wc : wpmax.getSoftClauses()) {
				ms.addSoftClause(wc.weight, new VecInt(wc.literals));
			}

			OptToPBSATAdapter opt = new OptToPBSATAdapter(new PseudoOptDecorator(ms));
			opt.isSatisfiable();
			return fixModel(opt.model());
		} catch (ContradictionException e) {
			return null;
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	private int[] fixModel(int[] model) {
		if (model == null) {
			return null;
		}
		int[] m = new int[model.length + 1];
		Arrays.fill(m, 0);
		for (int lit : model) {
			m[Math.abs(lit)] = lit;
		}
		return m;
	}


}
