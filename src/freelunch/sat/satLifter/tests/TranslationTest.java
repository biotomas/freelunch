package freelunch.sat.satLifter.tests;

import java.util.ArrayList;
import java.util.List;

import freelunch.sat.satLifter.multiSat.MultiValuedCNF;
import freelunch.sat.satLifter.multiSat.MultiValuedCNF.Assignment;
import freelunch.sat.satLifter.multiSat.MultiValuedCNF.MVClause;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.translation.SatToMultiValuedSat;
import freelunch.sat.satLifter.translation.covering.CliqueCoverGenerator;
import freelunch.sat.satLifter.translation.covering.GreedyLeftistCliqueCoverGenerator;
import freelunch.sat.satLifter.translation.covering.NaiveRandomizedCliqueCoverGenerator;
import freelunch.sat.satLifter.translation.mutex.BasicMutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexFinder;
import freelunch.sat.satLifter.translation.mutex.MutexPair;
import junit.framework.TestCase;

public class TranslationTest extends TestCase {
    
    public void testTrivialExample() {
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
        
        System.out.println(formula);
        
        List<MutexPair> mutexPairs = finder.findMutexPairs(formula);
        
        CliqueCoverGenerator ccg = new GreedyLeftistCliqueCoverGenerator(2012, formula.variablesCount, mutexPairs);
        
        List<List<Integer>> cover = ccg.generateCliqueCover();
        
        System.out.println(cover);
        
        SatToMultiValuedSat translator = new SatToMultiValuedSat();
        MultiValuedCNF result = translator.translate(formula, cover);
        
        System.out.println(result);
    }
    
    public void testSaveToFile() {
        MultiValuedCNF mvcnf = new MultiValuedCNF();
        mvcnf.domains = new int[] {0, 4, 3, 5, 2, 2};
        mvcnf.variablesCount = mvcnf.domains.length - 1;
        
        MVClause c1 = new MVClause(5);
        c1.clause[0] = new Assignment(1, 1);
        c1.clause[1] = new Assignment(1, 2);
        c1.clause[2] = new Assignment(2, 2);
        c1.clause[3] = new Assignment(3, -1);
        c1.clause[4] = new Assignment(3, 4);
        
        mvcnf.formula.add(c1);
        
        mvcnf.printNoGoodFormat(System.out);
    }
    
    public void testNaiveOnRandom() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        MutexFinder basic = new BasicMutexFinder();
        SatToMultiValuedSat translator = new SatToMultiValuedSat();
        
        for (int i = 10; i < 30; i++) {
            int vars = i * 5;
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            List<MutexPair> mutexPairs = basic.findMutexPairs(f);
            //CliqueCoverGenerator ccg = new GreedyLeftistCliqueCoverGenerator(2012, f.variablesCount, mutexPairs);
            CliqueCoverGenerator ccg = new NaiveRandomizedCliqueCoverGenerator(2012, f.variablesCount, mutexPairs);
            List<List<Integer>> cover = ccg.generateCliqueCover();
            MultiValuedCNF result = translator.translate(f, cover);

            System.out.println(String.format("Original: %d vars, %d clauses; New: %d vars, %d clauses", 
                    f.variablesCount, f.clauses.size(), result.variablesCount, result.formula.size()));
            
        }
    }
    

}
