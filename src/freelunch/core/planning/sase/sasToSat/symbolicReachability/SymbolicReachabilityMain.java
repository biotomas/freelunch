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
import freelunch.core.satSolving.symbolicReachability.SymbolicReachVerifier;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilityProblem;


public class SymbolicReachabilityMain {
	
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("USAGE: java -jar srt.jar <encoding> <filename.sas> [<solution-file>]");
			System.out.println("  encoding is one of" + Arrays.toString(TranslationMethod.values()));
			return;
		}
		
        try {
			SasProblem problem = SasIO.parse(args[1]);
			TranslationMethod translation = TranslationMethod.valueOf(args[0]);
			
            SymbolicReachabilityProblemGenerator gen = new SymbolicReachabilityProblemGenerator(problem, translation);
			if (args.length == 2) {
			    // generate a problem
			    SymbolicReachabilityProblem srp = gen.getSRProblem();
			    srp.print(System.out);
			} else {
			    // read and verify the SR solution
			    ArrayList<int[]> model = parseSymbolicReachModel(args[2]);
			    SymbolicReachVerifier srVerifier = new SymbolicReachVerifier();
			    boolean srvalid = srVerifier.solutionValid(gen.getSRProblem(), model);
                if (srvalid) {
                    System.out.println("SR model is VALID");
                } else {
                    System.out.println("SR model is INVALID");
                }
                // decode and verify the plan
			    SasParallelPlan plan = gen.decodePlan(model);
			    boolean valid = PlanVerifier.verifyPlan(problem, plan);
                System.out.println(plan.toString());
			    if (valid) {
			        System.out.println("Plan is VALID");
			    } else {
			        System.out.println("Plan is INVALID");
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int makespan;
	
	private static ArrayList<int[]> parseSymbolicReachModel(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        ArrayList<int[]> result = new ArrayList<int[]>();
        while (line != null) {
            if (line.startsWith("solution")) {
                String[] sol = line.split(" ");
                int vars = Integer.parseInt(sol[1]);
                makespan = Integer.parseInt(sol[2]);
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
