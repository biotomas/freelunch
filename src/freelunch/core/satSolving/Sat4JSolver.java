/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.satSolving;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IConstr;
import org.sat4j.specs.ISolver;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.utilities.IntVector;
import freelunch.core.utilities.Stopwatch;


/**
 * An incremental sat solver implementation using the
 * sat4j library. 
 *
 * @author Tomas Balyo
 * Oct 13, 2012
 */
public class Sat4JSolver implements IncrementalSatSolver {
	
	private ISolver solver;
	private Map<Integer, IConstr> constraintsMap;
	private int lastConstrId;
	private long satTime = 0;
	
	public Sat4JSolver() {
		solver = SolverFactory.newDefault();
		constraintsMap = new HashMap<Integer, IConstr>();
		lastConstrId = 0;
	}

	@Override
	public void setVariablesCount(int howMany) {
		solver.newVar(howMany);
	}

	@Override
	public void setTimeout(int seconds) {
		solver.setTimeout(seconds);
	}

	@Override
	public Boolean isSatisfiable() throws TimeoutException {
		try {
			return solver.isSatisfiable();
		} catch (org.sat4j.specs.TimeoutException e) {
			throw new TimeoutException();
		}
	}
	
    @Override
    public Boolean isSatisfiable(BasicSatFormula formula) throws TimeoutException {
        solver.reset();
        solver.newVar(formula.getVariables());
        try {
            for (int[] c : formula.getClauses()) {
                solver.addClause(new VecInt(c));
            }
            Stopwatch watch = new Stopwatch();
            Boolean satRes = solver.isSatisfiable();
            satTime = watch.elapsedMilliseconds();
            return satRes;
        } catch (ContradictionException e) {
            return false;
        } catch (org.sat4j.specs.TimeoutException e) {
            throw new TimeoutException();
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

	@Override
	public void addNewClause(IntVector literals) throws SatContradictionException {
		try {
			solver.addClause(new VecInt(literals.getArrayCopy()));
		} catch (ContradictionException e) {
			throw new SatContradictionException();
		}
	}

	@Override
	public int addRemovableClause(IntVector literals) throws SatContradictionException {
		try {
			IConstr constr = solver.addClause(new VecInt(literals.getArrayCopy()));
			lastConstrId++;
			constraintsMap.put(lastConstrId, constr);
			return lastConstrId;
		} catch (ContradictionException e) {
			throw new SatContradictionException();
		}
	}

	@Override
	public void addAtMostOneConstraint(IntVector literals) throws SatContradictionException {
		try {
			solver.addAtMost(new VecInt(literals.getArrayCopy()), 1);
		} catch (ContradictionException e) {
			throw new SatContradictionException();
		}
	}

	@Override
	public int addRemovableAtMostOneConstraint(IntVector literals) throws SatContradictionException {
		try {
			IConstr constr = solver.addAtMost(new VecInt(literals.getArrayCopy()), 1);
			lastConstrId++;
			constraintsMap.put(lastConstrId, constr);
			return lastConstrId;
		} catch (ContradictionException e) {
			throw new SatContradictionException();
		}
	}

	@Override
	public void removeClause(int clauseId) {
		IConstr constr = constraintsMap.get(clauseId);
		if (constr != null) {
			solver.removeConstr(constr);
		}
		constraintsMap.remove(clauseId);
	}

	@Override
	public void removeAtMostOneConstraint(int constraintId) {
		// the same as remove clause for sat4j
		removeClause(constraintId);
	}

    @Override
    public void clearRemovableClauses() {
        for (IConstr c : constraintsMap.values()) {
            solver.removeConstr(c);
        }
        constraintsMap.clear();
    }

    @Override
    public void reset() {
        solver.reset();
    }

    @Override
    public long getSolveTime() {
        return satTime;
    }

}
