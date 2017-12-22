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
import java.util.Arrays;

import freelunch.core.planning.cmdline.Translator.TranslationMethod;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasIO;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachVerifier;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilityProblem;


public class SymbolicReachabilityMain {
	
	public static void main(String[] args) {
				
		if (args.length < 2) {
			System.out.println("USAGE:\n To encode sas to srt: java -jar srt.jar <encoding> <planprob.sas>");
			System.out.println("   encoding is one of " + Arrays.toString(TranslationMethod.values()));
			System.out.println(" To check srt solution: java -jar srt.jar check <encoding> <planprob.sas> <satmodel>");
			System.out.println(" To split srt to 4cnfs: java -jar srt.jar split <filename.srt>");
			System.out.println(" To combine 4cnfs to srt: java -jar srt.jar comb <i.cnf> <u.cnf> <t.cnf> <g.cnf>");
			System.out.println(" To build formula for makespan k: java -jar srt.jar make <k> <filename.srt>");
			return;
		}
		
		if (args[0].equals("split")) {
			try {
				SymbolicReachabilityProblem srp = new SymbolicReachabilityProblem(args[1]);
				srp.initialConditions.printDimacsToFile(args[1]+".i.cnf");
				srp.universalConditions.printDimacsToFile(args[1]+".u.cnf");
				srp.transitionConditions.printDimacsToFile(args[1]+".t.cnf");
				srp.goalConditions.printDimacsToFile(args[1]+".g.cnf");
				System.out.println("Split into files: " + args[1] + ".[i,u,t,g].cnf");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		if (args[0].equals("comb")) {
			SymbolicReachabilityProblem srp = new SymbolicReachabilityProblem();
			srp.initialConditions = BasicSatFormula.parseFromFile(args[1]);
			srp.universalConditions = BasicSatFormula.parseFromFile(args[2]);
			srp.transitionConditions = BasicSatFormula.parseFromFile(args[3]);
			srp.goalConditions = BasicSatFormula.parseFromFile(args[4]);
			srp.print(System.out);
			return;
		}
		
		if (args[0].equals("make")) {
			int makespan = Integer.parseInt(args[1]);
			try {
				SymbolicReachabilityProblem srp = new SymbolicReachabilityProblem(args[2]);
				System.out.println("c generated from " + args[2] + " for makespan " + makespan);
				srp.makeFormulaForMakespan(makespan).printFormula(System.out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		
		if (args[0].equals("check")) {
			try {
				TranslationMethod translation = TranslationMethod.valueOf(args[1]);
				SasProblem problem = SasIO.parse(args[2]);
				problem.compileConditionalActions();
	            SymbolicReachabilityProblemGenerator gen = new SymbolicReachabilityProblemGenerator(problem, translation);
			    // read and verify the SR solution
			    ArrayList<int[]> model = parseSymbolicReachModel(args[3]);
			    if (model == null) {
			    	model = parseCnfModel(args[3], gen.getVariables()/2);
			    }
			    SymbolicReachVerifier srVerifier = new SymbolicReachVerifier();
			    boolean srvalid = srVerifier.solutionValid(gen.getSRProblem(), model);
                if (srvalid) {
                    System.out.println("SR model is VALID");
                } else {
                    System.out.println("SR model is INVALID");
                }
                // decode and verify the plan
			    SasParallelPlan plan = gen.decodePlan(model);
			    SasProblem rescannedProblem = SasIO.parse(args[2]);
			    SasParallelPlan rescannedPlan = SasParallelPlan.loadFromString(plan.getPlanIpcFormat(), rescannedProblem);
			    boolean valid = PlanVerifier.verifyPlan(rescannedProblem, rescannedPlan);
                System.out.println(rescannedPlan.toString());
			    if (valid) {
			        System.out.println("Plan is VALID");
			    } else {
			        System.out.println("Plan is INVALID");
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		
		// Default action is encoding
        try {
			SasProblem problem = SasIO.parse(args[1]);
			problem.compileConditionalActions();
			TranslationMethod translation = TranslationMethod.valueOf(args[0]);		
            SymbolicReachabilityProblemGenerator gen = new SymbolicReachabilityProblemGenerator(problem, translation);
		    // generate a problem
		    SymbolicReachabilityProblem srp = gen.getSRProblem();
		    srp.print(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<int[]> parseCnfModel(String filename, int variables) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        ArrayList<int[]> result = new ArrayList<int[]>();
        while (line != null) {
            if (line.startsWith("v")) {
                String[] sol = line.split(" ");
                for (int i = 1; i < sol.length; i++) {
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
	
	
	private static ArrayList<int[]> parseSymbolicReachModel(String filename) throws IOException {
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
}