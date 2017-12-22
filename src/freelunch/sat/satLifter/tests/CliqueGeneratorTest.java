package freelunch.sat.satLifter.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.translation.covering.CliqueCoverGenerator;
import freelunch.sat.satLifter.translation.covering.GreedyLeftistCliqueCoverGenerator;
import freelunch.sat.satLifter.translation.covering.NaiveRandomizedCliqueCoverGenerator;
import freelunch.sat.satLifter.translation.mutex.BasicMutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexPair;
import junit.framework.TestCase;

public class CliqueGeneratorTest extends TestCase {
    
    public void testGreedyRandomized() {
        
        MutexFinder finder = new BasicMutexFinder();
        BasicFormula formula = new BasicFormula();
        formula.variablesCount = 10;
        
        formula.clauses = new ArrayList<int[]>();
        formula.clauses.add(new int[]{1, 2, 3});
        formula.clauses.add(new int[]{-2, 3});
        formula.clauses.add(new int[]{-3, 4});
        formula.clauses.add(new int[]{-2, -4, 5});
        formula.clauses.add(new int[]{-1, -5, 9});
        formula.clauses.add(new int[]{-5, -6});
        formula.clauses.add(new int[]{-5, -7});
        formula.clauses.add(new int[]{-7, -6});
        
        List<MutexPair> mutexPairs = finder.findMutexPairs(formula);
        
        System.out.println(mutexPairs);
        System.out.println("----------");
        
        CliqueCoverGenerator ccg = new NaiveRandomizedCliqueCoverGenerator(2012, formula.variablesCount, mutexPairs);
        //CliqueCoverGenerator ccg = new GreedyLeftistCliqueCoverGenerator(2012, formula.variablesCount, mutexPairs);
        for (int i = 1; i < 10; i++) {
            System.out.println(ccg.generateCliqueCover());
        }

    }
    
    public void testNaiveOnRandom() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        MutexFinder basic = new BasicMutexFinder();
        
        for (int i = 10; i < 30; i++) {
            int vars = i * 5;
            System.out.println(vars);
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            List<MutexPair> mutexPairs = basic.findMutexPairs(f);
            //CliqueCoverGenerator ccg = new NaiveRandomizedCliqueCoverGenerator(2012, f.variablesCount, mutexPairs);
            CliqueCoverGenerator ccg = new GreedyLeftistCliqueCoverGenerator(2012, f.variablesCount, mutexPairs);
            for (int j = 1; j < 10; j++) {
                analyzeCliqueCover(ccg.generateCliqueCover());
            }
            System.out.println("========");
        }
    }
    
    private void analyzeCliqueCover(List<List<Integer>> cover) {
        List<Integer> sizes = new ArrayList<Integer>();
        for (List<Integer> clique : cover) {
            sizes.add(clique.size());
        }
        Collections.sort(sizes);
        Collections.reverse(sizes);
        System.out.print(cover.size() + ": ");
        System.out.println(sizes);
    }

}
