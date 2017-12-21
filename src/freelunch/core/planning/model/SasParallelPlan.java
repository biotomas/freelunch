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
package freelunch.core.planning.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.core.utilities.Stopwatch;


public class SasParallelPlan {

    private List<List<SasAction>> plan;
    private Stopwatch solverTime;

    /**
     * Linearize the parallel plan (have one action at a time)
     */
    public void linearize() {
        List<List<SasAction>> newPlan = new ArrayList<List<SasAction>>();
        for (List<SasAction> set : plan) {
            for (SasAction action : set) {
                List<SasAction> nstep = new ArrayList<SasAction>(1);
                nstep.add(action);
                newPlan.add(nstep);
            }
        }
        plan = newPlan;
    }
    
    public SasParallelPlan copy() {
        List<List<SasAction>> ncore = new ArrayList<>();
        for (List<SasAction> ps : plan) {
            ncore.add(new ArrayList<>(ps));
        }
        SasParallelPlan spp = new SasParallelPlan(ncore);
        return spp;
    }

    public void removeEmptySegments() {
        List<List<SasAction>> newPlan = new ArrayList<>();
        for (List<SasAction> segment : plan) {
            if (!segment.isEmpty()) {
                newPlan.add(segment);
            }
        }
        plan = newPlan;
    }

    public SasParallelPlan(List<List<SasAction>> plan) {
        this.plan = plan;
    }

    public List<List<SasAction>> getPlan() {
        return plan;
    }

    public int getPlanLength() {
        return plan.size();
    }
    
    public int getPlanCost() {
        int cost = 0;
        for (List<SasAction> acts : plan) {
            for (SasAction act : acts) {
                cost += act.getCost();
            }
        }
        return cost;
    }



    public int getTotalActions() {
        int actions = 0;
        for (List<SasAction> ops : plan) {
            actions += ops.size();
        }
        return actions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int time = 0;
        for (List<SasAction> ops : plan) {
            sb.append(time);
            sb.append(": ");
            for (SasAction op : ops) {
                sb.append(String.format("(%s) ", op.getActionInfo().getName()));
            }
            sb.append('\n');
            time++;
        }
        return sb.toString();
    }

    /**
     * Replace the set of actions between from and to with a new set of actions
     * 
     * @param from
     * @param to
     * @param subplan
     *            new actions
     */
    public void replaceSubplan(int from, int to, SasParallelPlan subplan) {
        List<List<SasAction>> newPlan = new ArrayList<List<SasAction>>();
        newPlan.addAll(plan.subList(0, from));
        newPlan.addAll(subplan.plan);
        newPlan.addAll(plan.subList(to, getPlanLength()));
        plan = newPlan;
    }

    /**
     * @param solverTime
     *            the solverTime to set
     */
    public void setSolverTime(Stopwatch solverTime) {
        this.solverTime = solverTime;
    }

    /**
     * @return the solverTime
     */
    public Stopwatch getSolverTime() {
        return solverTime;
    }

    public void saveToFileIpcFormat(String filename) throws IOException {
        File f = new File(filename);
        Writer out = new BufferedWriter(new FileWriter(f));
        long time = 0;
        for (List<SasAction> step : plan) {
            for (SasAction a : step) {
                out.write(String.format("%d: (%s)\n", time, a.getActionInfo().getName()));
                time++;
            }
        }
        out.close();
    }
    
    public void saveToFile(String filename) throws IOException {
        File f = new File(filename);
        Writer out = new BufferedWriter(new FileWriter(f));
        for (List<SasAction> step : plan) {
            for (SasAction a : step) {
                out.write(String.format("(%s)\n", a.getActionInfo().getName()));
            }
        }
        out.close();
    }
    
    public String getPlanIpcFormat() {
        String planString = "";
        for (List<SasAction> step : plan) {
            for (SasAction a : step) {
                planString += String.format("(%s)\n", a.getActionInfo().getName());
            }
        }
        return planString;
    }
    
    public static SasParallelPlan loadFromFile(String filename, SasProblem problem) {
    	String planString = "";
        try {
            FileReader fr;
            fr = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
            	planString += line;
                line = reader.readLine();
            }
            fr.close();
            return loadFromString(planString, problem);
        } catch (IOException e) {
            System.err.println("IO expception while loading plan file.");
            return null;
        }
    }
    
    public static SasParallelPlan loadFromString(String planString, SasProblem problem) {
        Map<String, SasAction> actions = new HashMap<String, SasAction>();
        for (SasAction a : problem.getOperators()) {
            actions.put(a.getActionInfo().getName().toUpperCase(), a);
        }
        for (SasAction a : problem.getConditionalOperators()) {
            actions.put(a.getActionInfo().getName().toUpperCase(), a);
        }
        List<List<SasAction>> plan = new ArrayList<List<SasAction>>();
        for (String line : planString.split("\n")) {
            if (line.startsWith("(")) {
                String actionName = line.substring(1, line.length() - 1).toUpperCase();
                if (!actions.containsKey(actionName)) {
                    System.err.println("Action not found in the problem: " + actionName);
                    return null;
                }
                List<SasAction> step = new ArrayList<SasAction>(1);
                step.add(actions.get(actionName));
                plan.add(step);
            }
        }
        return new SasParallelPlan(plan);
    }

}
