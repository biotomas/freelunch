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
package freelunch.core.planning.sase.sasToSat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.ConditionalEffect;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;



/**
 * Parsing SAS files generated by the Fast Downward planning system
 * and saving SAS problems to files.
 * The file format is specified here: http://www.fast-downward.org/TranslatorOutputFormat
 */
public class SasIO {
	
	public static void saveToFile(SasProblem problem, String filename) throws IOException {
		FileWriter out = new FileWriter(filename);
		// version and metric
		out.write("begin_version\n3\nend_version\nbegin_metric\n1\nend_metric\n");
		
		// variables
		out.write(String.format("%d\n", problem.getVariables().size()));
		for (StateVariable var : problem.getVariables()) {
			out.write(String.format("begin_variable\n%s\n-1\n%d\n", var.getName(), var.getDomain()));
			for (String atom : var.domainValues) {
				out.write(atom + "\n");
			}
			out.write("end_variable\n");
		}
		
		// mutex groups
		out.write(String.format("%d\n", problem.getMutexGroups().size()));
		for (List<Condition> group : problem.getMutexGroups()) {
			out.write("begin_mutex_group\n");
			out.write(String.format("%d\n", group.size()));
			for (Condition c : group) {
				out.write(String.format("%d %d\n", c.getVariable().getId(), c.getValue()));
			}
			out.write("end_mutex_group\n");
		}
		
		// initial state
		out.write("begin_state\n");
		for (Condition init : problem.getInitialState()) {
			out.write(String.format("%d\n", init.getValue()));
		}
		out.write("end_state\n");
		
		// goal conditions
		out.write("begin_goal\n");
		out.write(String.format("%d\n", problem.getGoal().size()));
		for (Condition goal : problem.getGoal()) {
			out.write(String.format("%d %d\n", goal.getVariable().getId(), goal.getValue()));
		}
		out.write("end_goal\n");
		
		// operators
		out.write(String.format("%d\n", problem.getOperators().size()));
		for (SasAction a : problem.getOperators()) {
			out.write("begin_operator\n");
			out.write(a.getActionInfo().getName() + "\n");
			out.write(String.format("%d\n", a.getPrevailConditions().size()));
			for (Condition prev : a.getPrevailConditions()) {
				out.write(String.format("%d %d\n", prev.getVariable().getId(), prev.getValue()));
			}
			out.write(String.format("%d\n", a.getEffects().size()));
			for (Condition eff : a.getEffects()) {
				// conditional effects are not supported
				int requiredValue = -1;
				for (Condition c : a.getPreconditions()) {
					if (c.getVariable().equals(eff.getVariable())) {
						requiredValue = c.getValue();
						break;
					}
				}
				out.write(String.format("0 %d %d %d\n", eff.getVariable().getId(), requiredValue, eff.getValue()));
			}
			out.write(String.format("%d\n", a.getCost()));
			out.write("end_operator\n");
		}
		
		// axiom rules -- not supported just write 0
		out.write("0\n");
		
	    out.close();
	}

	
	/**
	 * Parsing of the SAS file
	 * @param filename
	 * @return
	 * @throws IOException
	 */
    public static SasProblem parse(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);
        SasProblem problem = new SasProblem();
        problem.setDescription(filename);
        String line = reader.readLine();
        List<StateVariable> stateVars = new ArrayList<StateVariable>();
        List<Condition> preConditions = new ArrayList<Condition>();

        // version
        assert (line.equals("begin_version"));
        line = reader.readLine();
        int version = Integer.parseInt(line);
        if (version != 3) {
        	reader.close();
            throw new UnsupportedOperationException("Unsupported sas file version (we support 3)");
        }
        line = reader.readLine();
        assert (line.equals("end_version"));
        
        // metric
        line = reader.readLine();
        assert (line.equals("begin_metric"));
        line = reader.readLine();
        //int metric = Integer.parseInt(line);
        line = reader.readLine();
        assert (line.equals("end_metric"));
        
        // read variables
        int vars = Integer.parseInt(reader.readLine());
        for (int i = 0; i < vars; i++) {
            line = reader.readLine();
            if(!line.equals("begin_variable")){
            	reader.close();
                throw new UnsupportedOperationException();
            }
            String varName = reader.readLine();
            if(!reader.readLine().equals("-1")){
            	reader.close();
                throw new UnsupportedOperationException("Axiom variabels are not supported.");
            }
            int domSize = Integer.parseInt(reader.readLine());
            StateVariable v = new StateVariable(varName, domSize);
            v.domainValues = new String[domSize];
            for(int j = 0; j < domSize; j++){
                v.domainValues[j] = reader.readLine();
            }
            line = reader.readLine();
            if(!line.equals("end_variable")){
            	reader.close();
                throw new UnsupportedOperationException();
            }
            stateVars.add(v);
        }
        problem.setVariables(new ArrayList<StateVariable>(stateVars));
        line = reader.readLine();
        
        // mutex groups
        List<List<Condition>> mutexGroups = new ArrayList<>();

        while (!line.equals("begin_state")) {
            if (line.equals("begin_mutex_group")) {
                List<Condition> mutexGroup = new ArrayList<>();
                line = reader.readLine();
                int mutexConds = Integer.parseInt(line);
                for (int i = 0; i < mutexConds; i++) {
                    line = reader.readLine();
                    mutexGroup.add(parseCondition(line, problem));
                }
                line = reader.readLine();
                assert line.equals("end_mutex_group");
                
                // test if the MUTEX group is trivial
                int varId = mutexGroup.get(0).getVariable().getId();
                boolean trivial = true;
                for (Condition c : mutexGroup) {
                    if (c.getVariable().getId() != varId) {
                        trivial = false;
                        break;
                    }
                }
                if (!trivial) {
                    mutexGroups.add(mutexGroup);
                }
            }
            line = reader.readLine();
        }
        problem.setMutexGroups(mutexGroups);

        // initial state
        for (int i = 0; i < vars; i++) {
            line = reader.readLine();
            int state = Integer.parseInt(line);
            preConditions.add(new Condition(problem.getVariables().get(i), state));
        }
        problem.setInitialState(new ArrayList<Condition>(preConditions));
        line = reader.readLine();
        assert line.equals("end_state");

        // goal state
        preConditions.clear();
        while (!line.equals("begin_goal")) {
            line = reader.readLine();
        }
        int goals = Integer.parseInt(reader.readLine());
        for (int i = 0; i < goals; i++) {
            line = reader.readLine();
            preConditions.add(parseCondition(line, problem));
        }
        problem.setGoal(new ArrayList<Condition>(preConditions));
        line = reader.readLine();
        assert line.equals("end_goal");

        //operators
        List<SasAction> operatorList = new ArrayList<SasAction>();
        List<SasAction> conditionalOperatorList = new ArrayList<>();
        
        int operators = Integer.parseInt(reader.readLine());
        for (int i = 0; i < operators; i++) {
            line = reader.readLine();
            assert line.equals("begin_operator");
            line = reader.readLine();
            SasAction op = new SasAction(line.trim());
            
            line = reader.readLine();

            int prevailConds = Integer.parseInt(line);
            List<Condition> prevailConditions = new ArrayList<Condition>();
            for (int pc = 0; pc < prevailConds; pc++) {
                line = reader.readLine();
                prevailConditions.add(parseCondition(line, problem));
            }
            line = reader.readLine();

            int effects = Integer.parseInt(line);
            preConditions.clear();
            List<Condition> effectList = new ArrayList<Condition>();
            for (int ef = 0; ef < effects; ef++) {
                line = reader.readLine().trim();
                String[] parts = line.split(" ");
                int effectConditions = Integer.parseInt(parts[0]);
                if (effectConditions > 0) {
	                int varId = Integer.parseInt(parts[parts.length-3]);
	                StateVariable var = problem.getVariables().get(varId);
	                int reqVal = Integer.parseInt(parts[parts.length-2]);
	                int newVal = Integer.parseInt(parts[parts.length-1]);
                	if (reqVal != -1) {
                		reader.close();
                		throw new RuntimeException("Undefined behaviour, connditional effect has required value: " + line);
                	}

	                ConditionalEffect ceff = new ConditionalEffect(var, newVal);	                
                	for (int eci = 0; eci < effectConditions; eci++) {
                		int ecivarid = Integer.parseInt(parts[1+2*eci]);
                		StateVariable ecivar = problem.getVariables().get(ecivarid);
                		int ecireqval = Integer.parseInt(parts[2+2*eci]);
                		ceff.addEffectCondition(new Condition(ecivar, ecireqval));
                	}
                	op.getConditionalEffects().add(ceff);
                } else {
                	// normal effect
	                int varId = Integer.parseInt(parts[parts.length-3]);
	                StateVariable var = problem.getVariables().get(varId);
	                int reqVal = Integer.parseInt(parts[parts.length-2]);
	                int newVal = Integer.parseInt(parts[parts.length-1]);
	                // -1 means no required value
	                if (reqVal != -1) {
	                    preConditions.add(new Condition(var, reqVal));
	                }
	                effectList.add(new Condition(var, newVal));
                }
            }
            op.setEffects(new ArrayList<Condition>(effectList));
            op.setPreconditions(new ArrayList<Condition>(preConditions));
            op.getPreconditions().addAll(prevailConditions);
            line = reader.readLine();
            int cost = Integer.parseInt(line.trim());
            op.setCost(cost);
            op.setId(i);
            line = reader.readLine();
            if (!line.equals("end_operator")) {
            	reader.close();
                throw new RuntimeException("SAS format error, 'end operator' expected");
            }
            if (op.getConditionalEffects().isEmpty()) {
            	operatorList.add(op);
            } else {
            	conditionalOperatorList.add(op);
            }
        }
        problem.setOperators(new ArrayList<SasAction>(operatorList));
        problem.setConditionalOperators(new ArrayList<SasAction>(conditionalOperatorList));
        
        // axiom rules
        line = reader.readLine();
        int axioms = Integer.parseInt(line);
        if (axioms > 0) {
        	reader.close();
            throw new UnsupportedOperationException("Axiom rules are not supported.");
        }
        
        reader.close();
        return problem;
    }

    private static Condition parseCondition(String line, SasProblem problem) {
        String[] parts = line.split(" ");
        int var = Integer.parseInt(parts[0]);
        int val = Integer.parseInt(parts[1]);
        return new Condition(problem.getVariables().get(var), val);
    }

}
