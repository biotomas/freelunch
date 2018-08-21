package freelunch.core.planning.sase.sasToSat.translator;

import freelunch.core.planning.model.SasProblem;
import freelunch.core.satSolving.solvers.IncrementalSatSolver;
import freelunch.sat.model.SatContradictionException;

public class BinaryReinforcedSaseTranslator extends TranslatorBase implements SasToSatTranslator {

    public BinaryReinforcedSaseTranslator(SasProblem problem) {
        super(problem);
        initializeActionVariables();
        initializeAssignmentVariables();
        initializeTransitionVariables();
        initializeActionVariableIndex();
        initializeActionMutexHelperVariables();
        computeMutexGroupPairs();
    }
    
    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_inapplicableTransitionForbidden(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_actionMutexBinaryEnc(solver, time);
        universal_transitionsImplyEffectAssignmentNow(solver, time);
        universal_assignmetMustBeSupportedTransition(solver, time);
        universal_atMostOneAssignmentPerVariable(solver, time);
        universal_actionsImplyTheirTransitions(solver, time);
        universal_transitionsMustBeSupportedByAnAction(solver, time);
        universal_mutexGroups(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_transitionsImplyPreconditonAssignmentInPrevTime(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }

}
