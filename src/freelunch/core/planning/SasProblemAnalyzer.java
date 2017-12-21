package freelunch.core.planning;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.Transition;
import freelunch.core.planning.sase.sasToSat.TransitionGenerator;
import freelunch.core.planning.sase.sasToSat.translator.TopsortActionRanker;
import freelunch.core.planning.sase.sasToSat.translator.TranslatorBase;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.Logger;

public class SasProblemAnalyzer extends TranslatorBase {
    
    public SasProblemAnalyzer(SasProblem problem) {
		super(problem);
		// TODO Auto-generated constructor stub
	}

	public class SasProblemProperties {
        public String name;
        public int variables;
        public int operators;
        public int moves;
        public int condMoves;
        public int swaps;
        public int totalDomain;
        public long varMutexSize;
        public int preconditions;
        public int effects;
        public int transitions;
        public long transitionMutex;
        public long ignoredCycles;
        public long supActions;
        public long actionVars;
        public long oppActions;
        public long interferingActionPairs;
        
        @Override
        public String toString() {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("header");
            StringBuilder sb2 = new StringBuilder();
            sb2.append("data");
            for (Field f : getClass().getFields()) {
                try {
                    String fname = f.getName();
                    sb1.append(";");
                    sb1.append(fname);
                    String value = (f.get(this)).toString();
                    sb2.append(";");
                    sb2.append(value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
            return sb1.append("\n").append(sb2).toString();
        }
    }
    
    public SasProblemProperties analyzeSasProblem() {
        SasProblemProperties res = new SasProblemProperties();
        // basic information
        res.name = problem.getDescription();
        res.variables = problem.getVariables().size();
        res.operators = problem.getOperators().size();
        List<Integer> domains = new ArrayList<>();
        res.totalDomain = 0;
        res.varMutexSize = 0;
        for (StateVariable var : problem.getVariables()) {
            int dom = var.getDomain();
            domains.add(dom);
            res.totalDomain += dom;
            res.varMutexSize += dom*(dom-1)/2;
        }
        Collections.sort(domains);
        Collections.reverse(domains);
        System.out.println("domain sizes: " + domains);
        
        // action information
        res.moves = 0;
        res.condMoves = 0;
        res.swaps = 0;
        res.preconditions = 0;
        res.effects = 0;
        res.actionVars = 0;
        int actionId = 0;
        for (SasAction a : problem.getOperators()) {
            a.setId(actionId);
            actionId++;
            if (isMoveAction(a)) res.moves++;
            if (isConditionalMove(a)) res.condMoves++;
            if (isSwapAction(a)) res.swaps++;
            res.preconditions += a.getPreconditions().size();
            res.effects += a.getEffects().size();
            res.actionVars += getActionScope(a).size();
        }
        
        //transitions
        TransitionGenerator tgen = new TransitionGenerator(problem, problem.getOperators());
        res.transitions = tgen.getTransitionList().size();
        res.transitionMutex = 0;
        for (List<Transition> transList : tgen.getTransitionsByVariables()) {
            int transPerVars = transList.size();
            res.transitionMutex += transPerVars * (transPerVars-1) /2;                         
        }
        
        actions = problem.getOperators();
        assignmentIds = new int[problem.getVariables().size()][];
        for (StateVariable var : problem.getVariables()) {
            int dom = var.getDomain();
            assignmentIds[var.getId()] = new int[dom];
            for (int i = 0; i < dom; i++) {
                assignmentIds[var.getId()][i] = totalAssignments;
                totalAssignments++;
            }
        }
        initializeActionVariableIndex();
        if (assignmentSupportingActions == null) {
            initializeAssignmentSupportingActions();
        }
        res.supActions = 0;        
        res.oppActions = 0;
        
        BitSet supportFlag = new BitSet(actions.size());
        //BitSet opponentFlag = new BitSet(actions.size());

        Logger.print(0, "start opps and supps");
        
        for (SasAction a : actions) {
            
            /*
            opponentFlag.clear();
            for (Condition p : a.getPreconditions()) {
                candidateTest:
                for (SasAction candidate : actionVariableIndex[p.getVariable().getId()]) {
                    if (opponentFlag.get(candidate.getId())) {
                        continue;
                    }
                    // if the candidate has a malicious effect
                    for (Condition effect : candidate.getEffects()) {
                        if (effect.getVariable().getId() == p.getVariable().getId()) {
                            if (effect.getValue() != p.getValue()) {
                                opponentFlag.set(candidate.getId());
                                continue candidateTest;
                            }
                        }
                    }
                    // we also need to test the prevailing conditions hidden between preconditions
                    for (Condition c : candidate.getPreconditions()) {
                        if (c.getVariable().getId() != p.getVariable().getId()) {
                            continue;
                        }
                        if (c.getValue() == p.getValue()) {
                            continue;
                        }
                        // test if c is a prevailing condition
                        boolean prevailing = true;
                        for (Condition e : candidate.getEffects()) {
                            if (e.getVariable().getId() == c.getVariable().getId()) {
                                prevailing = false;
                                break;
                            }
                        }
                        if (prevailing) {
                            opponentFlag.set(candidate.getId());
                            continue candidateTest;
                        }
                    }
                }
            }
            res.oppActions += opponentFlag.cardinality();
            /**/
            
            supportFlag.clear();
            for (Condition p : a.getPreconditions()) {
                int condId = assignmentIds[p.getVariable().getId()][p.getValue()];
                List<SasAction> supporters = assignmentSupportingActions[condId];
                for (SasAction sup : supporters) {
                    supportFlag.set(sup.getId());
                }
            }
            res.supActions += supportFlag.cardinality();
        }
        
        Logger.print(0, "start ranking");
        
        TopsortActionRanker ranker = new TopsortActionRanker();
        ranker.getActionRanksNonRecursive(this, actions);
        res.ignoredCycles = ranker.getIgnoredCycles();
        Logger.print(0, "done ranking");
        
        computeInterferingActionPairsMinimal();
        res.interferingActionPairs = interferingActionPairs.size();
        
        return res;
    }
    
    @SuppressWarnings("unused")
    private static boolean conditionsCompatible(List<Condition> c1, List<Condition> c2) {
        for (Condition p1 : c1) {
            for (Condition p2 : c2) {
                if (p1.getVariable().getId() == p2.getVariable().getId()) {
                    if (p1.getValue() != p2.getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isMoveAction(SasAction action) {
        if (action.getPreconditions().size() != 1) {
            return false;
        }
        if (action.getEffects().size() != 1) {
            return false;
        }
        Condition p = action.getPreconditions().get(0);
        Condition e = action.getEffects().get(0);
        return p.getVariable().getId() == e.getVariable().getId() && e.getValue() != p.getValue();
    }
    
    private boolean isConditionalMove(SasAction action) {
        if (action.getEffects().size() != 1) {
            return false;
        }
        Condition e = action.getEffects().get(0);
        for (Condition c : action.getPreconditions()) {
            if (c.getVariable().getId() == e.getVariable().getId() && e.getValue() != c.getValue()) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isSwapAction(SasAction action) {
        if (action.getPreconditions().size() != 2) {
            return false;
        }
        if (action.getEffects().size() != 2) {
            return false;
        }
        Condition prec1 = action.getPreconditions().get(0);
        Condition prec2 = action.getPreconditions().get(1);
        Condition eff1 = action.getEffects().get(0);
        Condition eff2 = action.getEffects().get(1);
        
        if (prec1.getVariable().getId() == eff1.getVariable().getId()) {
            if (prec2.getVariable().getId() != eff2.getVariable().getId()) {
                return false;
            }
            return prec1.getValue() == eff2.getValue() && prec2.getValue() == eff1.getValue();
        }
        if (prec1.getVariable().getId() == eff2.getVariable().getId()) {
            if (prec2.getVariable().getId() != eff1.getVariable().getId()) {
                return false;
            }
            return prec1.getValue() == eff1.getValue() && prec2.getValue() == eff2.getValue();
        }
        return false;
    }

	@Override
	public void addInitialStateConstraints(IncrementalSatSolver solver)	throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addUniversalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addTransitionConstraints(IncrementalSatSolver solver, int time)	throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setGoalStateConstraints(IncrementalSatSolver solver, int time) throws SatContradictionException {
		throw new UnsupportedOperationException();
	}

}
