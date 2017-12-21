package freelunch.core.planning.sase.sasToSat.translator;

import freelunch.core.planning.model.SasProblem;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;

public class CompactDirect extends TranslatorBase implements SasToSatTranslator {

    public CompactDirect(SasProblem problem) {
        super(problem);
        initializeActionVariables();
        initializeAssignmentVariables();
        initializeAssignmentSupportingActions();
        computeInterferingActionPairsMinimal();
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_inapplicableActionsForbidden(solver);
        initial_assignmentsMustBeSupportedByActionsOrInitialState(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_atMostOneAssignmentPerVariable(solver, time);
        universal_mutexActionPairsNogood(solver, time);
        universal_actionImpliesItsEffects(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_actionRequiresItsPreconditions(solver, time);
        transition_assignmentsMustBeSupportedByActionsNowOrAssignmentsBefore(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }
}
