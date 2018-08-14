package freelunch.core.satSolving.solvers;

import java.util.ArrayList;
import java.util.List;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satModelling.modelObjects.PseudoBooleanFormula;
import freelunch.core.satModelling.modelObjects.PseudoBooleanFormula.PseudoBooleanEquality;
import freelunch.core.satModelling.modelObjects.PseudoBooleanFormula.PseudoBooleanObjectiveFunction;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.IntVector;

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
	public Boolean isSatisfiable(BasicSatFormula formula) throws TimeoutException {
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
	public void addNewClause(IntVector literals) throws SatContradictionException {
		clauses.add(literals.getArrayCopy());
	}

	@Override
	public int addRemovableClause(IntVector literals) throws SatContradictionException {
		clauses.add(literals.getArrayCopy());
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
	public void addAtMostOneConstraint(IntVector literals) throws SatContradictionException {
		amoConstraints.add(literals.getArrayCopy());
	}

	@Override
	public int addRemovableAtMostOneConstraint(IntVector literals) throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAtMostOneConstraint(int constraintId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset() {
		clauses.clear();
		amoConstraints.clear();
		variables = 0;
	}

}
