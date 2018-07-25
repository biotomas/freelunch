package freelunch.core.satModelling.modelObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PseudoBooleanFormula {
	
	/**
	 * The equality looks like: 
	 * 	1 x1 -3 x4 5 x6 >= -3
	 */
	public static class PseudoBooleanEquality {
		public int[] weigths;
		public int[] variables;
		public int rigthHandValue;
		public int lastIndex;
		
		public PseudoBooleanEquality(int variablesCount) {
			weigths = new int[variablesCount];
			variables = new int[variablesCount];
			lastIndex = 0;
		}
		
		public void addTerm(int weigth, int variable) {
			weigths[lastIndex] = weigth;
			variables[lastIndex] = variable;
			lastIndex++;
		}
		
		public void setRightHandValue(int rigthHandValue) {
			this.rigthHandValue = rigthHandValue;
		}
		
		public static PseudoBooleanEquality makeFromClause(int[] lits) {
			PseudoBooleanEquality pbe = new PseudoBooleanEquality(lits.length);
			int negs = 0;
			for (int l : lits) {
				if (l > 0) {
					pbe.addTerm(1, l);
				} else {
					negs++;
					pbe.addTerm(-1, -l);
				}
			}
			pbe.setRightHandValue(1 - negs);
			return pbe;
		}

		public static PseudoBooleanEquality makeFromAtMostOneConstraint(int[] lits) {
			PseudoBooleanEquality pbe = new PseudoBooleanEquality(lits.length);
			int negs = 0;
			for (int l : lits) {
				if (l > 0) {
					pbe.addTerm(-1, l);
				} else {
					pbe.addTerm(1, -l);
					negs++;
				}
			}
			pbe.setRightHandValue(negs - 1);
			return pbe;
		}
			
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < weigths.length; i++) {
				sb.append(String.format("%d x%d ", weigths[i], variables[i]));
			}
			sb.append(String.format(">= %d", rigthHandValue));
			return sb.toString();
		}
	}
	
	
	private int variables;
	private List<PseudoBooleanEquality> equalities;
	
	public PseudoBooleanFormula(int variables) {
		this.variables = variables;
		equalities = new ArrayList<>();
	}
	
	public int getVariables() {
		return variables;
	}
	
	public void addEquality(PseudoBooleanEquality pbe) {
		equalities.add(pbe);
	}
	
	public void printFormula(PrintStream out) {
        for (PseudoBooleanEquality pbe : equalities) {
            out.println(pbe.toString());
        }
	}
	
	public void printToFile(String filename) throws IOException {
        FileWriter out = new FileWriter(filename);
        out.write(String.format("* planning problem with %d variables\n", variables));
        for (PseudoBooleanEquality pbe : equalities) {
            out.write(pbe.toString());
            out.write('\n');
        }
        out.close();
	}
	
	public static int[] parseSolutionFromFile(String filename, int vars) {
		int[] model = new int[vars+1];
		boolean isSat = false;
		Arrays.fill(model, 0);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains("s UNSATISFIABLE")) {
					reader.close();
					return null;
				}
				if (line.contains("s SATISFIABLE") || line.contains("s OPTIMUM FOUND")) {
					isSat = true;
				}
				if (line.startsWith("v")) {
					String[] parts = line.split(" +");
					for (String p : parts) {
						int lit = 0;
						if (p.startsWith("x")) {
							lit = Integer.parseInt(p.substring(1));
						}
						if (p.startsWith("-x")) {
							lit = -Integer.parseInt(p.substring(2));							
						}					
						int var = Math.abs(lit);
						model[var] = lit;
					}
				}
				line = reader.readLine();
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSat ? model : null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (PseudoBooleanEquality pbe : equalities) {
			sb.append(pbe.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
