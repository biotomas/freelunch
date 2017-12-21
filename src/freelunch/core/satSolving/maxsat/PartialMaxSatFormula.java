package freelunch.core.satSolving.maxsat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartialMaxSatFormula {
	
	private int variables;
	
	private List<int[]> softClauses;
	private List<int[]> hardClauses;
	
	public PartialMaxSatFormula(int variables) {
		this.variables = variables;
		softClauses = new ArrayList<int[]>();
		hardClauses = new ArrayList<int[]>();
	}
	
	/**
	 * @return the variables
	 */
	public int getVariables() {
		return variables;
	}
	/**
	 * @return the softClauses
	 */
	public List<int[]> getSoftClauses() {
		return softClauses;
	}
	/**
	 * @return the hardClauses
	 */
	public List<int[]> getHardClauses() {
		return hardClauses;
	}
	
	/**
	 * Export to formula to dimacs format
	 * @return
	 */
	public void saveToDimacsFile(String filename) {
	    try {
    	    FileWriter out = new FileWriter(filename);
    	    int top = softClauses.size() + 1;
    	    out.write(String.format("p wcnf %d %d %d\n", variables, softClauses.size() + hardClauses.size(), top));
            for (int[] soft : softClauses) {
                StringBuilder sb = new StringBuilder();
                sb.append("1 ");
                for (int lit : soft) {
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
	        System.err.println("Saving of a partial maxsat file failed.");
	    }
	}

}
