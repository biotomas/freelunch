package freelunch.sat.model;

import java.util.ArrayList;
import java.util.List;

import freelunch.planning.model.TimeoutException;
import freelunch.sat.modelling.modelObjects.PseudoBooleanFormula;
import freelunch.sat.modelling.modelObjects.PseudoBooleanFormula.PseudoBooleanEquality;
import freelunch.sat.modelling.modelObjects.PseudoBooleanFormula.PseudoBooleanObjectiveFunction;

public class PseudoBooleanFormulaGenerator implements IncrementalSatSolver {

	private int variables;
	private List<int[]> clauses;
	private List<int[]> amoConstraints;
	private PseudoBooleanObjectiveFunction objective;
	
	public PseudoBooleanFormulaGenerator() {
		variables = 0;
		clauses = new ArrayList<>();
		amoConstraints = new ArrayList<>();
	}
	
	public void setObjectiveFunction(PseudoBooleanObjectiveFunction objective) {
		this.objective = objective;
	}
	
	public PseudoBooleanFormula getFormula() {
		PseudoBooleanFormula fla = new PseudoBooleanFormula(variables);
		fla.setObjective(objective);
		for (int[] cl : clauses) {
			fla.addEquality(PseudoBooleanEquality.makeFromClause(cl));
		}
		for (int[] amo : amoConstraints) {
			fla.addEquality(PseudoBooleanEquality.makeFromAtMostOneConstraint(amo));
		}
		return fla;
	}

	@Override
	public Boolean isSatisfiable(CnfSatFormula formula) throws TimeoutException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getSolveTime() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[] getModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTimeout(int seconds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVariablesCount(int howMany) {
		this.variables = howMany;
	}

	@Override
	public Boolean isSatisfiable() throws TimeoutException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addNewClause(int[] literals) throws SatContradictionException {
		clauses.add(literals);
	}

	@Override
	public int addRemovableClause(int[] literals) throws SatContradictionException {
		clauses.add(literals);
		return 0;
	}

	@Override
	public void removeClause(int clauseId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearRemovableClauses() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAtMostOneConstraint(int[] literals) throws SatContradictionException {
		amoConstraints.add(literals);
	}

	@Override
	public void addNativeAtMostOneConstraint(int[] literals) throws SatContradictionException {
		addAtMostOneConstraint(literals);
	}

	@Override
	public void reset() {
		clauses.clear();
		amoConstraints.clear();
		variables = 0;
	}

	@Override
	public void addDNF(int[][] terms) throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

}
