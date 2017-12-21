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
package freelunch.core.planning.sase.optimizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;


public class PlanLoader {

    public static SasParallelPlan loadPlanFromFile(String filename, SasProblem sasProblem) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fr);

        List<List<SasAction>> plan = new ArrayList<List<SasAction>>();
        Map<String, SasAction> operators = new HashMap<String, SasAction>(sasProblem.getOperators().size());
        for (SasAction o : sasProblem.getOperators()) {
            operators.put(o.getActionInfo().getName(), o);
        }

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith(";")) { // comment
                continue;
            }
            if (line.isEmpty()) {
                continue;
            }
            String actionName = extractActionName(line);
            if (actionName == null) {
                continue;
            }
            int time = Integer.parseInt(line.substring(0, line.indexOf(':')).trim());
            SasAction action = operators.get(actionName);
            if (action == null) {
            	reader.close();
                throw new RuntimeException("Action not found among the actions of the SAS problem: " + actionName);
            }
            if (time >= plan.size()) {
                List<SasAction> op = new ArrayList<SasAction>();
                op.add(action);
                plan.add(op);
            } else {
                plan.get(time).add(action);
            }
        }

        reader.close();
        return new SasParallelPlan(plan);
    }

    private static String extractActionName(String line) {
        int start = line.indexOf('(');
        int end = line.indexOf(')');
        if (start != -1 && end != -1) {
            return line.substring(1 + start, end).toLowerCase();
        } else {
            return null;
        }
    }

}
