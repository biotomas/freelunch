/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.satModelling.modelObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicSatFormula {
	
	private int variables;
	private List<int[]> clauses;
	
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
				if (line.contains("s SATISFIABLE")) {
					isSat = true;
				}
				if (!line.contains("c") && line.contains("v")) {
					String[] parts = line.split(" +");
					for (String p : parts) {
						try {
							int l = Integer.parseInt(p);
							int var = Math.abs(l);
							if (var > vars) {
								System.out.println(line);
							}
							model[var] = l;
						} catch (NumberFormatException e) {
							//ignore;
						}
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
	
    /**
     * Parse a dimacs formula file into a basic formula
     * @param filename
     * @return the basic formula or null
     */
    public static BasicSatFormula parseFromFile(String filename) {
        List<int[]> clauseList = new ArrayList<int[]>();
    	BasicSatFormula formula = new BasicSatFormula(0, clauseList);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
	            if (line.startsWith("c")) {
					line = reader.readLine();
	                continue;
	            }
	            if (line.startsWith("p")) {
	                String[] tokens = line.split(" +");
	                formula.variables = Integer.parseInt(tokens[2]);
	                line = reader.readLine();
	                continue;
	            }
	            line = line.trim();
	            if (line.isEmpty()) {
	                line = reader.readLine();
	            	continue;
	            }
	            String[] lits = line.split(" +");
	            int litInClause = 0;
	            int clauseLength = lits.length - 1;
	            int[] literals = new int[clauseLength];
	            for (String slit : lits) {
	                if ("".equals(slit)) {
	                    continue;
	                }
	                int lit = Integer.parseInt(slit);
	                if (lit == 0) {
	                    break;
	                }
	                literals[litInClause] = lit;
	                litInClause++;
	            }
            	formula.clauses.add(literals);
				line = reader.readLine();
			}
	    	reader.close();
	    	return formula;
		} catch (IOException e) {
			try {
				reader.close();
			} catch (Exception e2) {
			}
			return null;
		}
    }
    
    public void printDimacsToFile(String filename) throws IOException {
        FileWriter out = new FileWriter(filename);
        out.write(String.format("p cnf %d %d\n", variables, clauses.size()));
        int varsize = 2 + (int) Math.log10(variables);
        for (int[] cl : clauses) {
            StringBuilder sb = new StringBuilder(varsize*cl.length + 3);
            for (int lit : cl) {
                sb.append(lit);
                sb.append(" ");
            }
            sb.append("0 \n");
            out.write(sb.toString());
        }
        out.close();
    }

	
	public BasicSatFormula(int variables, List<int[]> clauses) {
		this.variables = variables;
		this.clauses = clauses;
	}
	
	public int getVariables() {
		return variables;
	}
	
	public List<int[]> getClauses() {
		return clauses;
	}
	
	private String clauseToString(int[] lits) {
		StringBuilder sb = new StringBuilder();
		for (int l : lits) {
			sb.append(l);
			sb.append(" ");
		}
		sb.append("0");
		return sb.toString();
	}
	
	public void printClauses(PrintStream out) {
		for (int[] cl : clauses) {
			out.println(clauseToString(cl));
		}
	}
	
	public void printFormula(PrintStream out) {
	    out.println(String.format("p cnf %d %d", variables, clauses.size()));
	    printClauses(out);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("p cnf %d %d\n", variables, clauses.size()));
		for (int[] cl : clauses) {
			sb.append(clauseToString(cl));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public boolean validateSolution(int[] model) {
		int clsnum = 0;
		clauseLoop:
		for (int[] cl : clauses) {
			clsnum++;
			for (int l : cl) {
				if (model[Math.abs(l)] == l) 
					continue clauseLoop;
			}
			System.out.println(String.format("The %d. clause (%s) is not satisfied", clsnum, Arrays.toString(cl)));
			return false;
		}
		return true;
	}

	public static void validateCnfFile(String filename) {
		BufferedReader reader = null;
		int declaredVars = -1;
		int declaredCls = -1;
		int clauses = 0;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			mainloop:
			while (line != null) {
	            if (line.startsWith("c")) {
					line = reader.readLine();
	                continue;
	            }
	            if (line.startsWith("p")) {
	                String[] tokens = line.split(" ");
	                declaredVars = Integer.parseInt(tokens[2]);
	                declaredCls = Integer.parseInt(tokens[3]);
	                line = reader.readLine();
	                continue;
	            }
	            if (line.isEmpty()) {
	                line = reader.readLine();
	            	continue;
	            }
	            if (declaredVars < 0) {
	            	System.out.println("Problem definition line missing.");
	            	break;
	            }
	            String[] lits = line.split(" ");
	            for (int i = 0; i < lits.length; i++) {
	            	String slit = lits[i];
	                int lit = Integer.parseInt(slit);
	                int var = Math.abs(lit);
	                if (var > declaredVars) {
	                	System.out.println("Invalid variable " + var + " in line " + line);
	                	break mainloop;
	                }
	                if (i+1 == lits.length && lit != 0) {
	                	System.out.println("Clause not terminated by 0: " + line);
	                	break mainloop;
	                }
	            }
	            clauses++;
				line = reader.readLine();
			}
			if (clauses != declaredCls) {
				System.out.println("Clause count not matching declared value. clauses:"+clauses);
			}
	    	reader.close();
		} catch (IOException e) {
			try {
				reader.close();
			} catch (Exception e2) {
			}
			System.out.println("File could not be opened and read.");
			return;
		}
	}

}
