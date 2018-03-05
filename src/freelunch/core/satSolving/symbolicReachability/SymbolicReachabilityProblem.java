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
package freelunch.core.satSolving.symbolicReachability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.core.satModelling.modelObjects.BasicSatFormula;


public class SymbolicReachabilityProblem {
	public List<Integer> actionVariables;
	
	public BasicSatFormula initialConditions;
	public BasicSatFormula universalConditions;
	public BasicSatFormula goalConditions;
	public BasicSatFormula transitionConditions;

	public void print(PrintStream out) {
		out.println("c action-vars " + actionVariables.toString());
		out.println(String.format("i cnf %d %d", initialConditions.getVariables(), initialConditions.getClauses().size()));
		initialConditions.printClauses(out);
		out.println(String.format("u cnf %d %d", universalConditions.getVariables(), universalConditions.getClauses().size()));
		universalConditions.printClauses(out);
		out.println(String.format("g cnf %d %d", goalConditions.getVariables(), goalConditions.getClauses().size()));
		goalConditions.printClauses(out);
		out.println(String.format("t cnf %d %d", transitionConditions.getVariables(), transitionConditions.getClauses().size()));
		transitionConditions.printClauses(out);
	}
	
	public void printToFile(String filename) throws IOException {
        File f = new File(filename);
        PrintStream ps = new PrintStream(f);
        print(ps);
        ps.close();
	}
	
	public BasicSatFormula makeFormulaForMakespan(int makespan) {
        int sig = initialConditions.getVariables();
        List<int[]> clauses = new ArrayList<int[]>();
        
        //initial conds
        for (int[] clause : initialConditions.getClauses()) {
            clauses.add(makeClause(clause, sig, 0));
        }
        //goal conds
        for (int[] clause : goalConditions.getClauses()) {
            clauses.add(makeClause(clause, sig, makespan-1));
        }
        //universal conds
        for (int i = 0; i < makespan; i++) {
            for (int[] clause : universalConditions.getClauses()) {
                clauses.add(makeClause(clause, sig, i));
            }
        }
        //transitional conds
        for (int i = 0; i+1 < makespan; i++) {
            for (int[] clause : transitionConditions.getClauses()) {
                clauses.add(makeClause(clause, sig, i));
            }
        }
        
        return new BasicSatFormula(sig*makespan, clauses);
    }
    
    private int[] makeClause(int[] clause, int sig, int time) {
        int[] result = Arrays.copyOf(clause, clause.length);
        for (int i = 0; i < clause.length; i++) {
            int var = Math.abs(result[i]);
            var += sig*time;
            result[i] = result[i] > 0 ? var : -var;
        }        
        return result;
    }
	
	public SymbolicReachabilityProblem() {
	}
	
	public SymbolicReachabilityProblem(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        BasicSatFormula currentfla = null;
		while (line != null) {
            if (line.startsWith("c")) {
				line = reader.readLine();
                continue;
            }
            if (line.contains("cnf")) {
                String[] tokens = line.split(" +");
                int variables = Integer.parseInt(tokens[2]);
                currentfla = new BasicSatFormula(variables, new ArrayList<int[]>());
            	
            	switch (line.charAt(0)) {
				case 'i':
					initialConditions = currentfla;
					break;
				case 'u':
					universalConditions = currentfla;
					break;
				case 't':
					transitionConditions = currentfla;
					break;
				case 'g':
					goalConditions = currentfla;
					break;
				default:
					throw new InvalidParameterException("Invalid problem definition line: " + line);
				}
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
        	currentfla.getClauses().add(literals);
			line = reader.readLine();
		}
        reader.close();
	}
}
