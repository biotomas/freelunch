package freelunch.core.satModelling.modelObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PseudoBooleanFormula {
	
	public static class PseudoBooleanTermList {
		public int[] weigths;
		public int[] variables;
		public int lastIndex;
		
		public PseudoBooleanTermList(int variablesCount) {
			weigths = new int[variablesCount];
			variables = new int[variablesCount];
			lastIndex = 0;
		}
		
		public void addTerm(int weigth, int variable) {
			weigths[lastIndex] = weigth;
			variables[lastIndex] = variable;
			lastIndex++;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < weigths.length; i++) {
				sb.append(String.format("%d x%d ", weigths[i], variables[i]));
			}
			return sb.toString();
		}
	}

	public static class PseudoBooleanObjectiveFunction extends PseudoBooleanTermList {

		public PseudoBooleanObjectiveFunction(int variablesCount) {
			super(variablesCount);
		}
		
		@Override
		public String toString() {
			return "min: " + super.toString() + ";";
		}
		
	}
	
	
	public static class PseudoBooleanEquality extends PseudoBooleanTermList {
		public int rigthHandValue;

		public void setRightHandValue(int rigthHandValue) {
			this.rigthHandValue = rigthHandValue;
		}
				
		public PseudoBooleanEquality(int variablesCount) {
			super(variablesCount);
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
			return super.toString() + String.format(">= %d;", rigthHandValue); 
		}
	}
	
	private int variables;
	private List<PseudoBooleanEquality> equalities;
	private PseudoBooleanObjectiveFunction objective = null;
	
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
	
	public void setObjective(PseudoBooleanObjectiveFunction objective) {
		this.objective = objective;
	}
	
	public void printFormula(PrintStream out) {
		out.println(String.format("* #variable= %d #constraint= %d", 
				variables, equalities.size()));
		if (objective != null) {
			out.println(objective.toString());
		}
        for (PseudoBooleanEquality pbe : equalities) {
            out.println(pbe.toString());
        }
	}
	
	public void printToFile(String filename) throws IOException {
        PrintStream pout = new PrintStream(filename);
        printFormula(pout);
        pout.close();
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
