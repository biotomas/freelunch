package freelunch.sat.satLifter.tests;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class RandomFormulaGenerator {
    
    private Random random;
    
    public RandomFormulaGenerator(long seed) {
        random = new Random(seed);
    }
    
    public BasicFormula getRandomSatisfiableFormula(int variables, int clauses3) {
    	return getRandomSatisfiableFormula(variables, clauses3, 0.35f, 0.25f, 0.1f);
    }

    	
    public BasicFormula getRandomSatisfiableFormula(int variables, int clauses3, float pl1, float pl2, float pl3) {
    	BasicFormula f = new BasicFormula();
    	f.variablesCount = variables;
    	f.clauses = new ArrayList<int[]>();
    	// generate the planted solution
    	BitSet solution = new BitSet(variables+1);
    	for (int var = 1; var <= variables; var++) {
    		solution.set(var, random.nextBoolean());
    	}
    	while (clauses3 > 0) {
            int l1,l2,l3;
            l1 = randomLiteral(variables);
            l2 = randomLiteral(variables);
            while (Math.abs(l1) == Math.abs(l2)) {
                l2 = randomLiteral(variables);
            }
            l3 = randomLiteral(variables);
            while (Math.abs(l1) == Math.abs(l3) || Math.abs(l2) == Math.abs(l3)) {
                l3 = randomLiteral(variables);
            }          
            // count the number of sat lits
            int satlits = 0;
            if (solution.get(Math.abs(l1)) == (l1 > 0)) satlits++;
            if (solution.get(Math.abs(l2)) == (l2 > 0)) satlits++;
            if (solution.get(Math.abs(l3)) == (l3 > 0)) satlits++;
            
            
            // filter clauses
            if (satlits == 0) continue;
            // the constants below are made up randomly
            if (satlits == 1 && random.nextFloat() > pl1) continue;
            if (satlits == 2 && random.nextFloat() > pl2) continue;
            if (satlits == 3 && random.nextFloat() > pl3) continue;
            /**/

            // add the clause
            clauses3--;
            int[] clause = {l1,l2,l3};
            f.clauses.add(clause);
    	}
    	return f;
    }
    
    public BasicFormula getRandomFormula(int variables, int clauses2, int clauses3) {
    	return getRandomFormula(variables, 0, clauses2, clauses3);
    }
    
    public BasicFormula getRandomFormula(int variables, int units, int clauses2, int clauses3) {
        BasicFormula formula = new BasicFormula();
        formula.variablesCount = variables;
        formula.clauses = new ArrayList<int[]>();
        
        for (int i = 0; i < units; i++) {
        	formula.clauses.add(new int[] {randomLiteral(variables)});
        }
        
        for (int i = 0; i < clauses2; i++) {
            int l1,l2;
            l1 = randomLiteral(variables);
            l2 = randomLiteral(variables);
            while (Math.abs(l1) == Math.abs(l2)) {
                l2 = randomLiteral(variables);
            }
            int[] clause = {l1,l2};
            formula.clauses.add(clause);
            
        }

        for (int i = 0; i < clauses3; i++) {
            int l1,l2,l3;
            l1 = randomLiteral(variables);
            l2 = randomLiteral(variables);
            while (Math.abs(l1) == Math.abs(l2)) {
                l2 = randomLiteral(variables);
            }
            l3 = randomLiteral(variables);
            while (Math.abs(l1) == Math.abs(l3) || Math.abs(l2) == Math.abs(l3)) {
                l3 = randomLiteral(variables);
            }
            int[] clause = {l1,l2,l3};
            formula.clauses.add(clause);
            
        }
        return formula;
    }
    
    private int randomLiteral(int variables) {
        int var = 1 + random.nextInt(variables);
        return random.nextBoolean() ? var : -var;
    }

}
