package freelunch.planning.sase.sasToSat.translator;

import java.util.List;

import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.SasProblem;
import freelunch.planning.sase.sasToSat.TransitionGenerator;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.IncrementalSatSolver;
import freelunch.sat.model.SatContradictionException;
import freelunch.sat.modelling.modelObjects.PseudoBooleanFormula;

public class SelectiveTranslator implements SasToSatTranslator {
    
    private SasToSatTranslator translator;
    private boolean usingExist;
    private SasProblem problem;
    
    public SelectiveTranslator(SasProblem problem) {
        this.problem = problem;
        TransitionGenerator tgen = new TransitionGenerator(problem, problem.getOperators());
        int transitionCount = tgen.getTransitionList().size();
        int varCount = problem.getVariables().size();
        
        if (transitionCount / varCount > 10) {
            translator = new CompactReinforcedSaseTranslator(problem);
            usingExist = false;
        } else {
        	translator = new DirectExistStepTranslator(problem, 4);
            usingExist = true;
        }
    }
    
	@Override
	public PseudoBooleanFormula makePseudoBooleanFormulaForMakespan(int makespan) {
		return translator.makePseudoBooleanFormulaForMakespan(makespan);
	}

    @Override
    public CnfSatFormula makeFormulaForMakespan(int makespan) {
        if (usingExist) {
            int ranking = 6;
            if (makespan % 2 == 0) {
                ranking = 4;
            }
            translator = new DirectExistStepTranslator(problem, ranking);
        }
        return translator.makeFormulaForMakespan(makespan);
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        translator.addInitialStateConstraints(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        translator.addUniversalStateConstraints(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        translator.addTransitionConstraints(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        translator.setGoalStateConstraints(solver, time);
    }

    @Override
    public void unsetLastGoalStateConstraints(IncrementalSatSolver solver) {
        translator.unsetLastGoalStateConstraints(solver);
    }

    @Override
    public int setMaxTimespan(int time) {
        return translator.setMaxTimespan(time);
    }

    @Override
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        return translator.decodePlan(model, makespan);
    }

    @Override
    public SasParallelPlan decodePlan(List<int[]> model) {
        return translator.decodePlan(model);
    }

	@Override
	public List<Integer> getActionVariables() {
		return translator.getActionVariables();
	}



}
