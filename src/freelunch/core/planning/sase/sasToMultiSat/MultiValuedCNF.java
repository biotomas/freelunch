/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.core.planning.sase.sasToMultiSat;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiValuedCNF {

    // The number of multivalued variables indexed from 0 to #variables - 1
    public int variablesCount;
    // The domain sizes, indexed from 0 to #variables - 1
    public int[] domains;
    // The list of multivalued clauses
    public List<MVClause> formula = new ArrayList<MultiValuedCNF.MVClause>();
    
    /**
     * An equality or inequality of a variable and a value.
     * If the value is > 0 then variable = value otherwise
     * it means that variable != value. The value 0 is special
     * and should appear in any of the clauses.
     *
     * @author Tomas Balyo
     * 24.3.2013
     */
    public static class Assignment {
        public int variable;
        public int value;
        public boolean positive;
        
        public Assignment(int variable, int value, boolean positive) {
            this.variable = variable;
            this.value = value;
            this.positive = positive;
        }
        
        @Override
        public String toString() {
            if (positive) {
                return variable + "=" + value;
            } else {
                return variable + "!=" + value;
            }
        }
        
        @Override
        public boolean equals(Object obj) {
            Assignment other = (Assignment) obj;
            return (variable == other.variable) && (value == other.value);
        }
    }
    
    public static class MVClause {
        public Assignment[] clause;
        
        public MVClause(List<Assignment> clause) {
            this.clause = new Assignment[clause.size()];
            this.clause = clause.toArray(this.clause);
        }
        
        public MVClause(Assignment[] clause) {
            this.clause = Arrays.copyOf(clause, clause.length);
        }
        
        public MVClause(int size) {
            clause = new Assignment[size];
        }
        
        @Override
        public String toString() {
            return Arrays.toString(clause);
        }
    }   
    
    @Override
    public String toString() {
        return "Doms: " + Arrays.toString(domains) + " Clauses: " + formula.toString();
    }
    
    public void printNoGoodFormat(PrintStream out) {
        // header: <#domainVars> <#rangeVars> <#auxDomainVars> <#auxRangeVars> <#constraints> <#linEqs>
        out.println(String.format("%d 0 0 0 %d 0", variablesCount, formula.size()));
        // variables: <id> <domainSize>
        for (int i = 0; i < variablesCount; i++) {
            // variables are indexed from zero in nogood format
            out.println(String.format("%d %d", i, domains[i]));
        }
        // constraints
        int index = 0;
        for (MVClause clause : formula) {
            printMVClause(out, clause, index);
            index++;
        }
    }
    
    private void printMVClause(PrintStream out, MVClause clause, int index) {
        Map<Integer, List<Integer>> positiveClauseMap = new HashMap<Integer, List<Integer>>();
        Map<Integer, List<Integer>> negativeClauseMap = new HashMap<Integer, List<Integer>>();
        for (Assignment a : clause.clause) {
            if (a.positive) {
                if (!positiveClauseMap.containsKey(a.variable)) {
                    positiveClauseMap.put(a.variable, new ArrayList<Integer>());
                }
                positiveClauseMap.get(a.variable).add(a.value);
            } else {
                if (!negativeClauseMap.containsKey(a.variable)) {
                    negativeClauseMap.put(a.variable, new ArrayList<Integer>());
                }
                negativeClauseMap.get(a.variable).add(a.value);
            }
        }
        out.println(String.format("c%d %d", index, positiveClauseMap.keySet().size() + negativeClauseMap.keySet().size()));
        for (int var : positiveClauseMap.keySet()) {
            List<Integer> values = positiveClauseMap.get(var);
            out.print(String.format("%d %d", var, values.size()));
            for (int value : values) {
                out.print(String.format(" %d", value));
            }
            out.println();
        }
        for (int var : negativeClauseMap.keySet()) {
            List<Integer> values = negativeClauseMap.get(var);
            out.print(String.format("%d %d", var, -values.size()));
            for (int value : values) {
                out.print(String.format(" %d", value));
            }
            out.println();
        }
    }
}
