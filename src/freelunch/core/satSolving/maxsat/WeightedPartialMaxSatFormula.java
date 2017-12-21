package freelunch.core.satSolving.maxsat;

import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class WeightedPartialMaxSatFormula {
    
    public static class WeightedClause {
        public WeightedClause(int weight, int[] literals) {
            this.weight = weight;
            this.literals = literals;
        }
        public int weight;
        public int[] literals;
    }
    

    private int variables;    
    private List<int[]> hardClauses;
    private List<WeightedClause> softClauses;
    
    public WeightedPartialMaxSatFormula(int variables) {
        this.variables = variables;
        softClauses = new ArrayList<WeightedClause>();
        hardClauses = new ArrayList<int[]>();
    }
    
    public WeightedPartialMaxSatFormula(PartialMaxSatFormula pmax) {
    	this.variables = pmax.getVariables();
    	this.hardClauses = pmax.getHardClauses();
    	this.softClauses = new ArrayList<WeightedClause>();
    	for (int[] soft : pmax.getSoftClauses()) {
    		this.softClauses.add(new WeightedClause(1, soft));
    	}
    }

    /**
     * @return the variables
     */
    public int getVariables() {
        return variables;
    }

    /**
     * @return the hardClauses
     */
    public List<int[]> getHardClauses() {
        return hardClauses;
    }

    /**
     * @return the softClauses
     */
    public List<WeightedClause> getSoftClauses() {
        return softClauses;
    }

    /**
     * Export to formula to dimacs format
     * @return
     */
    public void saveToDimacsFile(String filename) {
        try {
            @SuppressWarnings("resource")
			FileWriter out = new FileWriter(filename);
            int top = 1;
            for (WeightedClause wc : softClauses) {
                top += wc.weight;
            }
            out.write(String.format("p wcnf %d %d %d\n", variables, softClauses.size() + hardClauses.size(), top));
            for (WeightedClause soft : softClauses) {
                if (soft.weight < 1) {
                    throw new InvalidParameterException("Soft clause has non positive weight");
                }
                StringBuilder sb = new StringBuilder();
                sb.append(soft.weight);
                sb.append(" ");
                for (int lit : soft.literals) {
                    sb.append(lit);
                    sb.append(' ');
                }
                sb.append("0 \n");
                out.write(sb.toString());
            }
            for (int[] hard : hardClauses) {
                StringBuilder sb = new StringBuilder();
                sb.append(top);
                sb.append(' ');
                for (int lit : hard) {
                    sb.append(lit);
                    sb.append(' ');
                }
                sb.append("0 \n");
                out.write(sb.toString());
            }
            out.close();
        } catch (IOException e) {
            System.err.println("Saving of a weighted partial maxsat file failed.");
        }
    }

}
