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
package freelunch.core.planning.benchmarking;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import junit.framework.TestCase;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.SasProblemAnalyzer;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.benchmarking.providers.CombinedProvider;
import freelunch.core.planning.benchmarking.providers.LogisticsBenchmarkProvider;
import freelunch.core.planning.benchmarking.providers.MultiRobotPathPlanningBenchmarkProvider;
import freelunch.core.planning.benchmarking.providers.SasFilesBenchmarkProvider;
import freelunch.core.planning.benchmarking.providers.SlidingPuzzleBenchmarkProvider;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.iterative.IterativeSatBasedSolver;
import freelunch.core.planning.sase.sasToSat.translator.DirectExistStepTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslatorSettings;
import freelunch.core.satSolving.FormulaAnalyzer;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.satSolving.FormulaAnalyzer.FormulaProperties;
import freelunch.core.utilities.Logger;
import freelunch.core.utilities.Stopwatch;

public class PlannerBenchmarking extends TestCase {
    
    private static final int TIMELIMIT = 5;
    
    public void testBenchmarkProvider() {
        String[] domains = new String[] {"parcprinter", "elevators", "transport"};
        BenchmarkProvider bp1 = new SasFilesBenchmarkProvider("testfiles/benchmarks", domains);
        BenchmarkProvider bp2 = new SasFilesBenchmarkProvider("testfiles/ipcbench");
        BenchmarkProvider bp3 = new MultiRobotPathPlanningBenchmarkProvider(2, 5, 7, 10);
        BenchmarkProvider bp4 = new LogisticsBenchmarkProvider(5, 10, 10, 15, 10, 15);
        
        BenchmarkProvider bp = new CombinedProvider(bp1, bp2, bp3, bp4);
        
        
        SasProblem p = bp.getNext();
        while (p != null) {
            System.out.println(p.getDescription());
            if (p.getMutexGroups() != null && p.getMutexGroups().size() > 0) {
                System.out.println("non trivial mutex group: " + p.getMutexGroups());
            }
            p = bp.getNext();
        }
    }
    
    public void testSasAnalyzer() {
        String[] domains = new String[] {"openstack", "visitall", "pegsol"};
        BenchmarkProvider bp1 = new SasFilesBenchmarkProvider("testfiles/benchmarks", domains);
        BenchmarkProvider bp2 = new SlidingPuzzleBenchmarkProvider(2, 3, 2, 3);
        BenchmarkProvider bp3 = new MultiRobotPathPlanningBenchmarkProvider(3, 5, 6, 12);
        BenchmarkProvider bp4 = new LogisticsBenchmarkProvider(4, 6, 7, 9, 10, 15);
        BenchmarkProvider bp = new CombinedProvider(bp1, bp2, bp3, bp4);
        SasProblem problem = bp.getNext();
        while (problem != null) {
            System.out.println(problem.getDescription());
            SasProblemAnalyzer spa = new SasProblemAnalyzer(problem);
            System.out.println(spa.analyzeSasProblem());
            problem = bp.getNext();
        }
    }
        
    public static class BenchmarkResultData {
        public String name;
        public int problems = 0;
        public int totalMakespan = 0;
        public int totalActions = 0;
        public int totalTimeoutMakespans = 0;
        public int nonexistentPlan = 0;
        public long solvedTime = 0;
        public int solved = 0;
        
        public BenchmarkResultData(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return String.format("%-15s solved %2d/%-2d, no plan %d, avg makespan %f, avg actions %f, solved time %f, total makespan %d", 
                    name, solved, problems, nonexistentPlan, totalMakespan/((float)solved), totalActions/((float)solved), solvedTime/1e9f, totalMakespan + totalTimeoutMakespans);
        }
    }
    
/**
======= Direct Exist Step (10)
logistics solved 20/20, avg makespan 4,150000, avg actions 40,200001, solved time 29,647884, total makespan 83
parcprinter solved 6/6, avg makespan 4,166667, avg actions 23,500000, solved time 0,208603, total makespan 25
multibot solved 10/12, avg makespan 3,200000, avg actions 22,200001, solved time 15,318602, total makespan 82
elevators solved 6/6, avg makespan 4,166667, avg actions 22,333334, solved time 3,862952, total makespan 25
slidingPuzzle solved 4/9, avg makespan 4,750000, avg actions 8,500000, solved time 0,162055, total makespan 71
transport solved 3/6, avg makespan 5,000000, avg actions 19,333334, solved time 17,893721, total makespan 35

======= Direct Exist Rintanen Chains
logistics solved 20/20, avg makespan 3,350000, avg actions 57,000000, solved time 77,057159, total makespan 67
parcprinter solved 6/6, avg makespan 1,166667, avg actions 24,500000, solved time 0,078947, total makespan 7
multibot solved 7/12, avg makespan 2,714286, avg actions 22,285715, solved time 7,640852, total makespan 81
elevators solved 6/6, avg makespan 4,166667, avg actions 36,166668, solved time 5,511066, total makespan 25
slidingPuzzle solved 4/9, avg makespan 4,750000, avg actions 10,500000, solved time 0,238893, total makespan 66
transport solved 5/6, avg makespan 4,000000, avg actions 43,599998, solved time 41,407082, total makespan 26

======= Direct double linked (10)
logistics solved 20/20, avg makespan 7,400000, avg actions 36,950001, solved time 7,171690
parcprinter solved 6/6, avg makespan 10,333333, avg actions 23,500000, solved time 0,191903
multibot solved 8/12, avg makespan 6,375000, avg actions 17,500000, solved time 16,011330
elevators solved 6/6, avg makespan 8,666667, avg actions 22,166666, solved time 1,181830
slidingPuzzle solved 4/9, avg makespan 8,500000, avg actions 8,500000, solved time 0,365655
transport solved 5/6, avg makespan 11,600000, avg actions 22,600000, solved time 18,528526

====== Reinforced SASE (10)
logistics solved 20/20, avg makespan 7,400000, avg actions 37,549999, solved time 15,311202, total makespan 148
parcprinter solved 6/6, avg makespan 10,333333, avg actions 23,500000, solved time 0,404684, total makespan 62
multibot solved 7/12, avg makespan 6,142857, avg actions 17,857143, solved time 17,927258, total makespan 136
elevators solved 6/6, avg makespan 8,666667, avg actions 24,000000, solved time 1,411177, total makespan 52
slidingPuzzle solved 4/9, avg makespan 8,500000, avg actions 8,500000, solved time 0,584113, total makespan 133
transport solved 6/6, avg makespan 12,000000, avg actions 23,500000, solved time 23,089146, total makespan 72


====== SASE (11)
logistics solved 20/20, avg makespan 7,400000, avg actions 37,349998, solved time 25,832684
parcprinter solved 6/6, avg makespan 11,333333, avg actions 24,000000, solved time 0,442950
multibot solved 7/12, avg makespan 7,142857, avg actions 16,857143, solved time 18,697031
elevators solved 6/6, avg makespan 8,666667, avg actions 23,500000, solved time 1,715807
slidingPuzzle solved 4/9, avg makespan 8,500000, avg actions 8,500000, solved time 1,018075
transport solved 5/6, avg makespan 11,600000, avg actions 22,400000, solved time 14,686792

======= Direct (14)
logistics solved 20/20, avg makespan 7,400000, avg actions 37,200001, solved time 28,152252
parcprinter solved 6/6, avg makespan 10,333333, avg actions 23,500000, solved time 0,754190
multibot solved 6/12, avg makespan 5,833333, avg actions 16,833334, solved time 16,121178
elevators solved 6/6, avg makespan 8,666667, avg actions 25,166666, solved time 4,661781
slidingPuzzle solved 4/9, avg makespan 8,500000, avg actions 8,500000, solved time 0,768821
transport solved 3/6, avg makespan 10,333333, avg actions 19,000000, solved time 10,176011

======= Action
logistics solved 12/20, avg makespan 7,083333, avg actions 34,250000, solved time 106,596329
parcprinter solved 6/6, avg makespan 10,333333, avg actions 23,500000, solved time 2,211122
multibot solved 4/12, avg makespan 5,500000, avg actions 14,750000, solved time 4,800957
elevators solved 6/6, avg makespan 8,666667, avg actions 27,666666, solved time 16,457840
slidingPuzzle solved 4/9, avg makespan 8,500000, avg actions 8,500000, solved time 4,190098
transport solved 0/6, avg makespan NaN, avg actions NaN, solved time 0,000000

 */

public void testPlanner() {
    String[] domains = new String[] {"parcprinter", "elevators", "transport"};
    BenchmarkProvider bp1 = new SasFilesBenchmarkProvider("testfiles/benchmarks",domains);
    BenchmarkProvider bp2 = new SlidingPuzzleBenchmarkProvider(2, 4, 2, 4);
    BenchmarkProvider bp3 = new MultiRobotPathPlanningBenchmarkProvider(3, 5, 6, 12);
    BenchmarkProvider bp4 = new LogisticsBenchmarkProvider(4, 6, 7, 9, 10, 15);
    BenchmarkProvider bp = new CombinedProvider(bp1, bp2, bp3, bp4);
    
    Map<String, BenchmarkResultData> results = new Hashtable<String, PlannerBenchmarking.BenchmarkResultData>();
    
    SasProblem problem = bp.getNext();
    while (problem != null) {
        System.out.print("Solving " + problem.getDescription() + " ");
        
        Logger.setVerbosity(-1);
        IterativeSatBasedSolver s = new IterativeSatBasedSolver(new Sat4JSolver(), new DirectExistStepTranslator(problem));
        //IterativeSatBasedSolver s = new IterativeSatBasedSolver(new Sat4JSolver(), new SelectiveTranslator(problem));
        s.getSettings().setTimelimit(TIMELIMIT);
        
        String domain = problem.getDescription().split("-")[0];
        if (results.get(domain) == null) {
            results.put(domain, new BenchmarkResultData(domain));
        }
        
        BenchmarkResultData data = results.get(domain);
        data.problems++;
        
        try {
            Stopwatch timer = new Stopwatch();
            SasParallelPlan plan = s.solve();
            timer.pause();
            System.out.println(String.format("Solved in %ss and %d steps %d sat calls", timer.elapsedFormatedSeconds(), plan.getPlanLength(), s.getLastMakepan()));
            data.solvedTime += timer.elapsedNanoSeconds();
            data.solved++;
            data.totalMakespan += plan.getPlanLength();
            data.totalActions += plan.getTotalActions();
            
            boolean valid = PlanVerifier.verifyPlan(problem, plan);
            if (!valid) {
                System.err.println("Invalid Plan");
                System.out.println(plan);
            }
        } catch (TimeoutException e) {
            int lastMakespan = s.getLastMakepan();
            data.totalTimeoutMakespans += lastMakespan;
            System.out.println("TIMEOUT at " + lastMakespan);
        } catch (NonexistentPlanException e) {
            System.out.println("Plan non existent");
        }
        problem = bp.getNext();
    }
    for (BenchmarkResultData brd : results.values()) {
        System.out.println(brd);
    }
}
    
    
    /**
======= DIRECT
Total vars: 63.711, Total clauses: 6.672.288, Total literals: 13.408.966
Solved: 15, Timeouts: 21
Total solve time: 13,604
Total Time: 242,428

======= DIRECT EXIST
Total vars: 51.777, Total clauses: 9.354.449, Total literals: 27.610.219
Solved: 16, Timeouts: 20
Total solve time: 13,256
Total Time: 230,780

======= iSASE
Total vars: 70.746, Total clauses: 1.513.547, Total literals: 3.177.869
Solved: 17, Timeouts: 19
Total solve time: 14,524
Total Time: 216,098

======= iSASE with minimal interference
Total vars: 82.680, Total clauses: 1.008.779, Total literals: 2.168.333
Solved: 17, Timeouts: 19
Total solve time: 14,450
Total Time: 214,183
{barman=0, parcprinter=6, pegsol=0, elevators=6, openstacks=0, transport=5}


======= ORIG SASE with minimal action interference
Total vars: 82.680, Total clauses: 1.008.779, Total literals: 2.168.333
Solved: 17, Timeouts: 19
Total solve time: 17,119
Total Time: 216,445
Invalid Pland: 0

======= ORIGINAL SASE
Total vars: 70.746, Total clauses: 1.504.093, Total literals: 3.170.616
Solved: 15, Timeouts: 21
Total solve time: 8,797
Total Time: 231,178

======= Action Oriented
Total vars: 51.777, Total clauses: 16.018.780, Total literals: 32.151.811
Solved: 13, Timeouts: 23
Total solve time: 23,877
Total Time: 288,631

======= Double Linked Direct
Total vars: 51.777, Total clauses: 457.349, Total literals: 979.088
Solved: 16, Timeouts: 20
Total solve time: 5,019
Total Time: 216,909

     */
    
    
    public void testAndEvaluateTranslatingPlanner() throws SatContradictionException {
        Stopwatch totalTime = new Stopwatch();
        int solved = 0;
        int timeout = 0;
        long totalSolveTimes = 0;
        long totalEncodingVars = 0;
        long totalEncodingClauses = 0;
        long totalLiterals = 0;
        int invalid = 0;
        Dictionary<String, Integer> solvedOfDomain = new Hashtable<String, Integer>();
        
        BenchmarkProvider bp = new SasFilesBenchmarkProvider("testfiles/benchmarks");
        SasProblem p = bp.getNext();
        while (p != null) {
            System.out.println("Solving " + p.getDescription());
            
            ////////////////////////////////////////////////
            // Change this to evaluate another translator
            ///////////////////////////////////////////////
            SaseTranslatorSettings sts = new SaseTranslatorSettings();
            sts.setUseOriginalGoalEncoding(false);
            sts.setUseOriginalInitialStateEncoding(false);
            //SasToSatTranslator translator = new SaseTranslator(p, sts);
            SasToSatTranslator translator = new DirectExistStepTranslator(p);
                    //DirectDoubleLinkedTranslator(p);
            
            
            IterativeSatBasedSolver s = new IterativeSatBasedSolver(new Sat4JSolver(), translator);
            s.getSettings().setTimelimit(10);
            
            FormulaProperties fp = FormulaAnalyzer.analyzeFormula(translator.makeFormulaForMakespan(2));
            fp.print();
            totalEncodingVars += fp.vars;
            totalEncodingClauses += fp.clauses;
            totalLiterals += fp.totalLiterals;
            
            String domain = p.getDescription().split("-")[0];
            if (solvedOfDomain.get(domain) == null) {
                solvedOfDomain.put(domain, 0);
            }

            try {
                Stopwatch timer = new Stopwatch();
                SasParallelPlan plan = s.solve();
                timer.pause();
                solved++;
                System.out.println("SOLVED in " + timer.elapsedFormatedSeconds());
                totalSolveTimes += timer.elapsedNanoSeconds();
                
                solvedOfDomain.put(domain, solvedOfDomain.get(domain) + 1);
                
                boolean valid = PlanVerifier.verifyPlan(p, plan);
                if (!valid) {
                    System.err.println("Invalid Plan");
                    System.out.println(plan);
                    invalid++;
                }
                //assertEquals(valid, true);

                
            } catch (TimeoutException e) {
                System.out.println("TIMEOUT");
                timeout++;
            } catch (NonexistentPlanException e) {
                // this code should not be reached
                assertTrue(false);
            }
            p = bp.getNext();
        }
        System.out.println("SUMMARY\n=======");
        System.out.println(String.format("Total vars: %d, Total clauses: %d, Total literals: %d", totalEncodingVars, totalEncodingClauses, totalLiterals));
        System.out.println(String.format("Solved: %d, Timeouts: %d", solved, timeout));
        System.out.println("Total solve time: " + String.format("%.3f",  totalSolveTimes / 1e9));
        System.out.println("Total Time: " + totalTime.elapsedFormatedSeconds());
        System.out.println("Invalid Pland: " + invalid);
        System.out.println(solvedOfDomain.toString());
    }

}
