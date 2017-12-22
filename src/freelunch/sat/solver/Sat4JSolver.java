package freelunch.sat.solver;

import java.util.Arrays;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class Sat4JSolver implements SatSolver {
	
	private ISolver solver;
	
	public Sat4JSolver() {
		solver = SolverFactory.newDefault();
	}

    @Override
	public void setTimeout(long nanoseconds) {
    	int seconds = (int)(nanoseconds / 10e9);
		solver.setTimeout(seconds);
	}

    @Override
    public Boolean isSatisfiable(BasicFormula formula) {
        solver.reset();
        solver.newVar(formula.variablesCount);
        solver.setExpectedNumberOfClauses(formula.clauses.size());
        try {
            for (int[] c : formula.clauses) {
                solver.addClause(new VecInt(c));
            }
            return solver.isSatisfiable();
        } catch (ContradictionException e) {
            return false;
        } catch (TimeoutException e) {
            throw null;
        }
    }

    @Override
	public int[] getModel() {
		int[] sat4jModel = solver.model();
		int[] model = new int[solver.nVars()+1];
		Arrays.fill(model, 0);
		for (int lit : sat4jModel) {
			model[Math.abs(lit)] = lit;
		}
		return model;
	}

}