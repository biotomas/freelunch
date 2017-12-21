package freelunch.core.satSolving;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import freelunch.core.satModelling.modelObjects.BasicSatFormula;

public class RandomFormulaGenerator {
    
    private Random random;
    
    public RandomFormulaGenerator(long seed) {
        random = new Random(seed);
    }
    
    public BasicSatFormula getRandomSat(int variables) {
    	return getRandomSat(variables, 0, (4*variables) + (variables >> 2));
    }
    
    
    public BasicSatFormula getRandomSat(int variables, int clauses2, int clauses3) {
    	return getRandomSat(variables, 0, clauses2, clauses3);
    }
    
    public BasicSatFormula getRandomSat(int variables, int units, int clauses2, int clauses3) {
        List<int[]> clauses = new ArrayList<int[]>();
        
        for (int i = 0; i < units; i++) {
        	clauses.add(new int[] {randomLiteral(variables)});
        }
        
        for (int i = 0; i < clauses2; i++) {
            int l1,l2;
            l1 = randomLiteral(variables);
            l2 = randomLiteral(variables);
            while (Math.abs(l1) == Math.abs(l2)) {
                l2 = randomLiteral(variables);
            }
            int[] clause = {l1,l2};
            clauses.add(clause);
            
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
            clauses.add(clause);
            
        }
        return new BasicSatFormula(variables, clauses);
    }
    
    private int randomLiteral(int variables) {
        int var = 1 + random.nextInt(variables);
        return random.nextBoolean() ? var : -var;
    }

}
