package freelunch.core.planning.sase.sasToSat.translator;

import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;

public class ReinforcedSaseTranslator extends TranslatorBase implements SasToSatTranslator {

    public ReinforcedSaseTranslator(SasProblem problem) {
        super(problem);
        initializeActionVariables();
        initializeAssignmentVariables();
        initializeTransitionVariables();
        initializeActionVariableIndex();
        initializeAssignmentSupportingActions();
        computeInterferingActionPairsMinimal();
        computeMutexGroupPairs();
    }
    
    @Override
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        SasParallelPlan p =super.decodePlan(model, makespan);
        p.getPlan().remove(p.getPlanLength() - 1);
        return p;
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_initialAssignementsHold(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_transitionsImplyPreconditonAssignment(solver, time);
        universal_atMostOneAssignmentPerVariable(solver, time);
        universal_mutexActionPairsNogood(solver, time);
        universal_actionsImplyTheirTransitions(solver, time);
        universal_transitionsMustBeSupportedByAnAction(solver, time);
        universal_mutexGroups(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_transitionsImplyEffectAssignment(solver, time);
        transition_assignmetMustBeSupportedByPreviousTransition(solver, time);

    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }

}
