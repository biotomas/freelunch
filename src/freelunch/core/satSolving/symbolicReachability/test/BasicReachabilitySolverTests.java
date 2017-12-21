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
package freelunch.core.satSolving.symbolicReachability.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.cmdline.Translator;
import freelunch.core.planning.cmdline.Translator.TranslationMethod;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.problemGenerator.LogisticsProblemGenerator;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import freelunch.core.planning.sase.sasToSat.symbolicReachability.SymbolicReachabilityProblemGenerator;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.satSolving.symbolicReachability.Sat4jReachSolver;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachVerifier;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilityProblem;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilitySolver;

public class BasicReachabilitySolverTests extends TestCase {
    
    public void testGenerator() {
        SasProblem p = createProblem(5).getSasProblem();
        Solver s = new IncrementalSolver(p);
        try {
            SasParallelPlan plan = s.solve();
            System.out.println(plan);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NonexistentPlanException e) {
            e.printStackTrace();
        }
    }

    public void testTrivialExample() {
        SasProblem p = createProblem(7).getSasProblem();
        SymbolicReachabilityProblemGenerator srpGen = new SymbolicReachabilityProblemGenerator(p, TranslationMethod.sase);
        SymbolicReachabilityProblem prob = srpGen.getSRProblem();
        prob.print(System.out);
        System.out.println("---------");
        SymbolicReachabilitySolver solver = new Sat4jReachSolver(); //WalkieReachSolver();
        ArrayList<int[]> solution = solver.solve(prob);
        
        //malicious change to test the verifiers false negativeness
        //solution.get(2)[16] *= -1;
        
        SymbolicReachVerifier ver = new SymbolicReachVerifier();
        boolean valid = ver.solutionValid(prob, solution);
        System.out.println(valid);
        
        SasParallelPlan plan = srpGen.decodePlan(solution);
        System.out.println(plan);
        
        boolean planOk = PlanVerifier.verifyPlan(p, plan);
        System.out.println(planOk);
    }
    
    private SasProblemBuilder createProblem(int n) {
        SasProblemBuilder prob = new SasProblemBuilder();
        
        StateVariable var = prob.newVariable("x", n);
        
        prob.addInitialStateCondition(new Condition(var, 0));
        prob.addGoalCondition(new Condition(var, n-1));
        
        for (int i = 0; i+1 < n; i++) {
            SasAction a = prob.newAction(new StringActionInfo(String.format("%d->%d", i, i+1)));
            a.getPreconditions().add(new Condition(var, i));
            a.getEffects().add(new Condition(var, i+1));
        }
        
        return prob;
    }
    
    public void testAllTranslationMethods() {
    	for (TranslationMethod m : TranslationMethod.values()) {
    		System.out.println("======= Testing translation method " + m.toString());
    		testTranslationMethod(m);
    	}
    }

    private void testTranslationMethod(TranslationMethod tm) {
        SasProblem p = LogisticsProblemGenerator.generateProblem(3, 10, 5, 10).getSasProblem();
        
        SymbolicReachabilityProblemGenerator pgen = new SymbolicReachabilityProblemGenerator(p, tm);
        SymbolicReachabilityProblem prob = pgen.getSRProblem();
        SymbolicReachabilitySolver solver = new Sat4jReachSolver();
        ArrayList<int[]> result = solver.solve(prob);
        
        SasToSatTranslator translator = Translator.makeTranslator(p, tm, 5);
        
        SasParallelPlan plan = translator.decodePlan(result);
        boolean valid = PlanVerifier.verifyPlan(p, plan);
        System.out.println(plan.toString());
        if (valid) {
            System.out.println("Plan is VALID");
        } else {
            System.out.println("Plan is INVALID");
        }
    }

}
