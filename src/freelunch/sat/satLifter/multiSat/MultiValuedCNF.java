package freelunch.sat.satLifter.multiSat;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiValuedCNF {

    // The number of multivalued variables indexed from 1 to #variables
    public int variablesCount;
    // The domain sizes, indexed from 1 to #variables
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
        
        public Assignment(int variable, int value) {
            this.variable = variable;
            this.value = value;
        }
        
        @Override
        public String toString() {
            if (value > 0) {
                return variable + "=" + value;
            } else {
                return variable + "!=" + -value;
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
        for (int i = 1; i <= variablesCount; i++) {
            // variables are indexed from zero in nogood format
            out.println(String.format("%d %d", i - 1, domains[i]));
        }
        // constraints
        int index = 0;
        for (MVClause clause : formula) {
            printMVClause(out, clause, index);
            index++;
        }
    }
    
    private void printMVClause(PrintStream out, MVClause clause, int index) {
        Map<Integer, List<Integer>> clauseMap = new HashMap<Integer, List<Integer>>();
        for (Assignment a : clause.clause) {
            if (a.value > 0) {
                if (!clauseMap.containsKey(a.variable)) {
                    clauseMap.put(a.variable, new ArrayList<Integer>());
                }
                clauseMap.get(a.variable).add(a.value);
            } else {
                if (!clauseMap.containsKey(-a.variable)) {
                    clauseMap.put(-a.variable, new ArrayList<Integer>());
                }
                clauseMap.get(-a.variable).add(-a.value);
            }
        }
        out.println(String.format("c%d %d", index, clauseMap.keySet().size()));
        for (int lit : clauseMap.keySet()) {
            List<Integer> values = clauseMap.get(lit);
            out.print(String.format("%d %d", Math.abs(lit) - 1, lit >= 0 ? values.size() : -values.size()));
            for (int value : values) {
                out.print(String.format(" %d", value));
            }
            out.println();
        }
    }
}
