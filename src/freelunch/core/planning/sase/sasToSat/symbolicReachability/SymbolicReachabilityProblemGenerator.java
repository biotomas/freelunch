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
package freelunch.core.planning.sase.sasToSat.symbolicReachability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freelunch.core.planning.cmdline.Translator;
import freelunch.core.planning.cmdline.Translator.TranslationMethod;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.satSolving.BasicSatFormulaGenerator;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachVerifier;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilityProblem;


public class SymbolicReachabilityProblemGenerator {
    
    private SasToSatTranslator translator = null;
    private int variables;

    public SymbolicReachabilityProblemGenerator(SasProblem problem, TranslationMethod translation) {
        translator = Translator.makeTranslator(problem, translation, 5);
        translator.setMaxTimespan(0);
        variables = translator.setMaxTimespan(1);
    }
    
    public SasParallelPlan decodePlan(List<int[]> model) {
        return translator.decodePlan(model);
    }
    
    public SasParallelPlan decodePlanFromFile(String filename) throws IOException {
    	ArrayList<int[]> model = getSrtModelFromFile(filename);
	    SymbolicReachVerifier srVerifier = new SymbolicReachVerifier();
	    boolean srvalid = srVerifier.solutionValid(getSRProblem(), model);
        if (srvalid) {
            System.out.println("SR model is VALID");
        } else {
            System.out.println("SR model is INVALID");
        }
        // decode and verify the plan
	    SasParallelPlan plan = decodePlan(model);
	    return plan;
    }
    
    public ArrayList<int[]> getSrtModelFromFile(String filename) throws IOException {
	    ArrayList<int[]> model = parseSymbolicReachModel(filename);
	    if (model == null) {
	    	model = parseCnfModel(filename, variables/2);
	    }
	    return model;    	
    }
    
	private ArrayList<int[]> parseCnfModel(String filename, int variables) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        ArrayList<int[]> result = new ArrayList<int[]>();
        while (line != null) {
            if (!line.startsWith("c")) {
                String[] sol = line.split(" ");
                for (int i = 0; i < sol.length; i++) {
                	int lit = Integer.parseInt(sol[i]);
                	if (lit == 0) {
                		continue;
                	}
                	int var = Math.abs(lit);
                	int m = var / (variables);
                	int v = var % (variables);
                	if (v == 0) {
                		m--; v = variables;
                	}
                	while (result.size() <= m) {
                		result.add(new int[variables+1]);
                	}
                	result.get(m)[v] = lit > 0 ? v : -v;
                }
            }
            line = reader.readLine();
        }
        reader.close();
        return result;
	}
	
	
	private ArrayList<int[]> parseSymbolicReachModel(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        ArrayList<int[]> result = new ArrayList<int[]>();
        while (line != null) {
            if (line.startsWith("solution")) {
                String[] sol = line.split(" ");
                int vars = Integer.parseInt(sol[1]);
                int makespan = Integer.parseInt(sol[2]);
                for (int time = 0; time < makespan; time++) {
                    String ln = reader.readLine();
                    String[] lits = ln.split(" ");
                    int[] submodel = new int[vars + 1];
                    for (String lit : lits) {
                        int l = Integer.parseInt(lit);
                        int var = Math.abs(l);
                        submodel[var] = l;
                    }
                    result.add(submodel);
                }
                reader.close();
                return result;
            }
            line = reader.readLine();
        }
        reader.close();
        return null;
	}

    
    public int getVariables() {
    	return variables;
    }
	
	public SymbolicReachabilityProblem getSRProblem() {
			
        BasicSatFormulaGenerator solver = new BasicSatFormulaGenerator();
		SymbolicReachabilityProblem srProblem = new SymbolicReachabilityProblem();
		
		try {
			// action variables
			srProblem.actionVariables = translator.getActionVariables();
			// initial conditions
			solver.setVariablesCount(variables / 2);
			translator.addInitialStateConstraints(solver);
			srProblem.initialConditions = solver.getFormula();
			solver.clear();
			// goal conditions
			solver.setVariablesCount(variables / 2);
			translator.setGoalStateConstraints(solver, 0);
			srProblem.goalConditions = solver.getFormula();
			solver.clear();
			// universal conditions
			solver.setVariablesCount(variables / 2);
			translator.addUniversalStateConstraints(solver, 0);
			srProblem.universalConditions = solver.getFormula();
			solver.clear();
			// transition conditions
			solver.setVariablesCount(variables);
			translator.addTransitionConstraints(solver, 0);
			srProblem.transitionConditions = solver.getFormula();
		} catch (SatContradictionException e) {
			e.printStackTrace();
		}
		
		return srProblem;
	}

}
