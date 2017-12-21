package freelunch.core.planning.sase.sasToSat.translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.BasicSatFormulaGenerator;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;

public class MiniBinTranslator extends TranslatorBase implements SasToSatTranslator {

    public MiniBinTranslator(SasProblem problem) {
        super(problem);
        initializeAssignmentVariables();
        initializeVarUnchangedVariables();
        initializeBinaryActionVariables();
    }

    @Override
    public void addInitialStateConstraints(IncrementalSatSolver solver) throws SatContradictionException {
        initial_initialAssignementsHold(solver);
    }

    @Override
    public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        universal_binaryActionsImplyPreconditionsAndNoChanges(solver, time);
        universal_atMostOneAssignmentPerVariable(solver, time);
    }

    @Override
    public void addTransitionConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        transition_binaryActionsImplyEffects(solver, time);
        transition_unchangedVarAssignmentsMustNotChange(solver, time);
    }

    @Override
    public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
        goal_goalAssignementsHold(solver, time);
    }
    
    @Override
    public BasicSatFormula makeFormulaForMakespan(int makespan) {
        BasicSatFormulaGenerator sgen = new BasicSatFormulaGenerator();
        binaryActionVariables.setDimensionSize(1, makespan);
        varUnchangedVariables.setDimensionSize(1, makespan);
        assignmentVariables.setDimensionSize(1, makespan + 1);
        int vars = varManager.getTotalVarsCount();
        sgen.setVariablesCount(vars);
        try {
            addInitialStateConstraints(sgen);
            for (int time = 0; time < makespan; time++) {
                addUniversalStateConstraints(sgen, time);
                addTransitionConstraints(sgen, time);
            }
            universal_atMostOneAssignmentPerVariable(sgen, makespan);
            setGoalStateConstraints(sgen, makespan);
        } catch (SatContradictionException e) {
            return null;
        }
        BasicSatFormula formula = sgen.getFormula();
        return formula;
    }

    
    public SasParallelPlan decodePlan(int[] model, int makespan) {
        List<List<SasAction>> plan = new ArrayList<List<SasAction>>(makespan);
        List<SasAction> selectedActions = new ArrayList<SasAction>();
        
        int log = 32 - Integer.numberOfLeadingZeros(actions.size() - 1);
        int cap = 2 * Integer.highestOneBit(actions.size());

        for (int time = 0; time < makespan; time++) {
            selectedActions.clear();
            int ind = 0;
            int count = 0;
            for (SasAction action : problem.getOperators()) {
                
                // first we get the binary code of the action
                int[] binCode = new int[log];
                Arrays.fill(binCode, -1);            
                if (count < cap - actions.size()) {
                    for (int bit = 1; bit < log; bit++) {
                        int on = (ind & (1 << bit)) >> bit;
                        int val = 2*on - 1;
                        int lit = val * binaryActionVariables.getVariable(bit, time);
                        binCode[bit] = lit;
                    }
                    ind += 2;
                } else {
                    for (int bit = 0; bit < log; bit++) {
                        int on = (ind & (1 << bit)) >> bit;
                        int val = 2*on - 1;
                        int lit = val * binaryActionVariables.getVariable(bit, time);
                        binCode[bit] = lit;
                    }
                    ind += 1;
                }
                count++;
                // shorter codes
                if (binCode[0] == -1) {
                    binCode = Arrays.copyOfRange(binCode, 1, log);
                }
                
                boolean match = true;
                for (int lit : binCode) {
                    if (model[Math.abs(lit)] != lit) {
                        match = false;
                    }
                }
                if (match) {
                    selectedActions.add(action);
                }
            }
            plan.add(new ArrayList<SasAction>(selectedActions));
        }
        return new SasParallelPlan(plan);
    }
}
