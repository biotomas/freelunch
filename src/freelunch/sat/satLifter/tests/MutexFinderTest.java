package freelunch.sat.satLifter.tests;

import java.util.ArrayList;
import java.util.List;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.translation.mutex.BasicMutexFinder;
import freelunch.sat.satLifter.translation.mutex.ImprovedMutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexPair;
import junit.framework.TestCase;

public class MutexFinderTest extends TestCase {
    
    public void testBasicMutexFinder() {
        
        MutexFinder finder = new BasicMutexFinder();
        BasicFormula formula = new BasicFormula();
        formula.variablesCount = 10;
        
        formula.clauses = new ArrayList<int[]>();
        formula.clauses.add(new int[]{1, 2, 3});
        formula.clauses.add(new int[]{-2, 3});
        formula.clauses.add(new int[]{-3, 4});
        formula.clauses.add(new int[]{-2, -4, 5});
        formula.clauses.add(new int[]{-1, -5, 9});
        
        System.out.println(finder.findMutexPairs(formula));

    }

    public void testImprovedMutexFinder() {
        
        MutexFinder finder = new ImprovedMutexFinder();
        BasicFormula formula = new BasicFormula();
        formula.variablesCount = 10;
        
        formula.clauses = new ArrayList<int[]>();
        formula.clauses.add(new int[]{1, 2, 3});
        formula.clauses.add(new int[]{-2, 3});
        formula.clauses.add(new int[]{-3, 4});
        formula.clauses.add(new int[]{-2, -4, 5});
        formula.clauses.add(new int[]{-1, -5, 9});
        
        System.out.println(finder.findMutexPairs(formula));

    }
    
    public void testImprovedFinderTime() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        MutexFinder improved = new ImprovedMutexFinder();
        
        for (int i = 10; i < 30; i++) {
            int vars = i * 20;
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            List<MutexPair> resultImproved = improved.findMutexPairs(f);
            System.out.println(resultImproved.size());
        }
        
    }
    
    public void testBasicFinderTime() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        MutexFinder basic = new BasicMutexFinder();
        
        for (int i = 10; i < 30; i++) {
            int vars = i * 20;
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            List<MutexPair> resultBasic = basic.findMutexPairs(f);
            System.out.println(resultBasic.size());
        }
    }
    
    public void testFindersOnRandom() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        MutexFinder basic = new BasicMutexFinder();
        MutexFinder improved = new ImprovedMutexFinder();
        
        for (int i = 10; i < 30; i++) {
            int vars = i * 5;
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            
            List<MutexPair> resultBasic = basic.findMutexPairs(f);
            List<MutexPair> resultImproved = improved.findMutexPairs(f);
            
            assertTrue(resultBasic.containsAll(resultImproved));
            System.out.println(resultBasic.size() + " " + resultImproved.size());
        }
    }

}
