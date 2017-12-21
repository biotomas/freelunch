/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.core.planning.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import freelunch.core.planning.benchmarking.BenchmarkProvider;
import freelunch.core.planning.benchmarking.providers.LogisticsBenchmarkProvider;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver;
import freelunch.core.planning.forwardSearch.ForwardSearchSettings;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver.ForwardSearchStatistics;
import freelunch.core.planning.forwardSearch.heuristics.ForwardSearchSelectorFunction;
import freelunch.core.planning.forwardSearch.heuristics.NeverRestartHeuristic;
import freelunch.core.planning.forwardSearch.heuristics.StateVariablesValueGoalDistanceHeuristic;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.sase.preprocessing.DoubleActionCodec;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolverSettings;

public class DoubleActionCodecTest extends TestCase {
    
    public void testMakeActionPairs() {
        
        SasAction a1 = new SasAction(new StringActionInfo("a1"));
        a1.setPreconditions(new ArrayList<Condition>());
        a1.setEffects(new ArrayList<Condition>());
        
        SasAction a2 = new SasAction(new StringActionInfo("a2"));
        a2.setPreconditions(new ArrayList<Condition>());
        a2.setEffects(new ArrayList<Condition>());
        
        SasAction a3 = new SasAction(new StringActionInfo("a3"));
        a3.setPreconditions(new ArrayList<Condition>());
        a3.setEffects(new ArrayList<Condition>());

        StateVariable var0 = new StateVariable(0, 3);
        StateVariable var1 = new StateVariable(1, 3);
        StateVariable var2 = new StateVariable(2, 3);
        
        a1.getPreconditions().add(new Condition(var0, 0));
        a1.getPreconditions().add(new Condition(var1, 0));
        a1.getEffects().add(new Condition(var1, 1));
        
        a2.getPreconditions().add(new Condition(var0, 0));
        a2.getEffects().add(new Condition(var2, 1));
        
        a3.getPreconditions().add(new Condition(var1, 1));
        a3.getPreconditions().add(new Condition(var2, 1));
        a3.getEffects().add(new Condition(var0, 2));
        
        List<SasAction> actions = new ArrayList<SasAction>();
        actions.add(a1);
        actions.add(a2);
        actions.add(a3);
        
        System.out.println(actions);
        
        System.out.println("====================");

        DoubleActionCodec dac = new DoubleActionCodec();
        System.out.println(dac.makeActionPairs(actions));
    }
    
    public void testLogisticsSase() {
        BenchmarkProvider pp = new LogisticsBenchmarkProvider(3, 5, 3, 6, 10, 12);
        SasProblem sasProb = pp.getNext();
        
        DoubleActionCodec codec = new DoubleActionCodec();
       
        while (sasProb != null) {
            
            List<SasAction> doubleActions = codec.makeActionPairs(sasProb.getOperators());
            System.out.println(String.format("Original actions: %d, double actions: %d", sasProb.getOperators().size(), doubleActions.size()));
            //sasProb.getOperators().addAll(doubleActions);
            sasProb.setOperators(doubleActions);
            IncrementalSolverSettings settings = new IncrementalSolverSettings();
            settings.setTimelimit(10);
            LogisticsBenchmarkTest.solveProblem(new IncrementalSolver(sasProb, settings), sasProb, false, false);
            sasProb = pp.getNext();
        }
    }
    
    public void testLogisticsBFS() {
        DoubleActionCodec codec = new DoubleActionCodec();
        BenchmarkProvider pp = new LogisticsBenchmarkProvider(9, 12, 10, 13, 10, 12);
        //ProblemProvider pp = LogisticsBenchmarkTest.getHardSet();
        SasProblem sasProb = pp.getNext();
        while (sasProb != null) {
            List<SasAction> doubleActions = codec.makeActionPairs(sasProb.getOperators());
            System.out.println(String.format("Original actions: %d, double actions: %d", sasProb.getOperators().size(), doubleActions.size()));
            //sasProb.getOperators().addAll(doubleActions);
            //sasProb.setOperators(doubleActions);
            
            ForwardSearchSettings fss = new ForwardSearchSettings();
            ForwardSearchSelectorFunction sh = new StateVariablesValueGoalDistanceHeuristic(sasProb, 2012);
            fss.setHeuristic(sh);
            fss.setRestartHeuristic(new NeverRestartHeuristic());
            fss.setTimelimit(10);
            BasicForwardSearchSolver s = new BasicForwardSearchSolver(sasProb, fss); 
            LogisticsBenchmarkTest.solveProblem(s, sasProb, false, false);
            ForwardSearchStatistics statistics = s.getStatistics();
            System.out.println(String.format("%d;%d;%d;%d;%d", statistics.getIterations(), statistics.backtracksSinceRestart, statistics.maxDepth, statistics.depth, statistics.restarts));
            sasProb = pp.getNext();
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
