package freelunch.sat.satLifter.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import freelunch.sat.satLifter.sat.Propagator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import junit.framework.TestCase;

public class PropagatorTest extends TestCase {
	
	public void testRevertOnRandom() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        Random rnd = new Random(2012);
        
        for (int i = 10; i < 100; i++) {
            int vars = i * 50;
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            Propagator pr = new Propagator(f.variablesCount);
            for (int[] cl : f.clauses) {
                pr.addClause(cl);
            }
            int lit1 = rnd.nextInt(vars) + 1;
            int lit2 = rnd.nextInt(vars) + 1;
            int lit3 = rnd.nextInt(vars) + 1;
            
            Set<Integer> sres = new HashSet<Integer>();
            List<Integer> res = pr.propagate(lit1);
            if (res == null) {
            	continue;
            }
            if (res != null) sres.addAll(res);
            pr.propagate(lit2);
            pr.revert();
            res = pr.propagate(lit3);
            if (res != null) sres.addAll(res);

            List<Integer> res2 = trivialPropagate(f, lit1, lit3);
            if (res2 == null) {
            	assertNull(res);
            } else {
            	assertTrue(sres.containsAll(res2));
            	assertTrue(res2.containsAll(sres));
            }
        }
	}
	
	public void testPropagatorRevert() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        int vars = 5;
        BasicFormula f = fgen.getRandomFormula(vars, vars/2, vars);
        //f.printDimacs(System.out);
        //System.out.println("======");
		Propagator p1 = new Propagator(vars);
		Propagator p2 = new Propagator(vars);
		for (int[] cl : f.clauses) {
			p1.addClause(cl);
			p2.addClause(cl);
		}
		List<Integer> res;
		res = p1.propagate(-4);
		res = p1.propagate(2);
		assertNull(res);
		p1.revert();
		res = p1.propagate(-2);
		assertNotNull(res);
	}
    
    public void testPropagatorBasic() {
        Propagator p = new Propagator(10);
        
        p.addClause(new int[]{1, 2, 3});
        p.addClause(new int[]{-2, 3});
        p.addClause(new int[]{-3, 4});
        p.addClause(new int[]{-2, -4, 5});
        p.addClause(new int[]{-1, -5, 9});

        assertEquals(null, p.propagate(2,-5));
        p.restartPropagator();
        assertTrue(p.propagate(2).containsAll(Arrays.asList(2,3,4,5)));
    }
    
    public void testPropagatorOnRandom() {
        RandomFormulaGenerator fgen = new RandomFormulaGenerator(2013);
        Random rnd = new Random(2012);
        
        for (int i = 10; i < 100; i++) {
            int vars = i * 50;
            BasicFormula f = fgen.getRandomFormula(vars, vars, 5*vars);
            Propagator p = new Propagator(f.variablesCount);
            for (int[] cl : f.clauses) {
                p.addClause(cl);
            }
            int lit1 = rnd.nextInt(vars) + 1;
            int lit2 = rnd.nextInt(vars) + 1;
            List<Integer> result1 = trivialPropagate(f, lit1, lit2);
            List<Integer> result2 = p.propagate(lit1, lit2);
            
            if (result1 == null || result2 == null) {
                assertNull(result1);
                assertNull(result2);
            } else {
                assertTrue(result1.containsAll(result2));
                assertTrue(result2.containsAll(result1));
            }
        }
    }
    
    
    private ArrayList<Integer> trivialPropagate(BasicFormula f, int lit1, int lit2) {
        int[] values = new int[f.variablesCount+1];
        ArrayList<Integer> assignments = new ArrayList<Integer>();
        Arrays.fill(values, 0);
        values[Math.abs(lit1)] = lit1;
        values[Math.abs(lit2)] = lit2;
        assignments.add(lit1);
        assignments.add(lit2);
        
        while (true) {
            boolean newAssignment = false;
            for (int[] cl : f.clauses) {
                int freeLits = 0;
                int unit = 0;
                boolean sat = false;
                for (int lit : cl) {
                    if (values[Math.abs(lit)] == lit) {
                        sat = true;
                        break;
                    }
                    if (values[Math.abs(lit)] == 0) {
                        unit = lit;
                        freeLits++;
                    }
                }
                if (!sat && freeLits == 0) {
                    // conflict
                    return null;
                }
                if (!sat && freeLits == 1) {
                    // propagation
                    assignments.add(unit);
                    values[Math.abs(unit)] = unit;
                    newAssignment = true;
                }
            }
            if (newAssignment == false) {
                break;
            }
        }
        
        return assignments;
    }

}
