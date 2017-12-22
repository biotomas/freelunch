package freelunch.sat.satLifter.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freelunch.sat.satLifter.multiSat.MultiValuedCNF;
import freelunch.sat.satLifter.multiSat.MultiValuedCNF.Assignment;
import freelunch.sat.satLifter.multiSat.MultiValuedCNF.MVClause;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class SatToMultiValuedSat {
    
    public MultiValuedCNF translate(BasicFormula formula, List<List<Integer>> cover) {
        MultiValuedCNF multiCnf = new MultiValuedCNF();
        
        TranslationHelper thelper = new TranslationHelper(cover, formula.variablesCount);
        multiCnf.domains = thelper.getDomainSizes();
        multiCnf.variablesCount = cover.size();
        
        for (int[] clause : formula.clauses) {
            MVClause mvClause = new MVClause(clause.length);
            
            for (int i = 0; i < clause.length; i++) {
                int lit = clause[i];
                mvClause.clause[i] = new Assignment(thelper.getMultiVariableForLiteral(lit), thelper.getValueForLiteral(lit));
            }
            if (!tautologicalClause(mvClause)) {
                multiCnf.formula.add(mvClause);
            }
        }
        return multiCnf;
    }
    
    private boolean tautologicalClause(MVClause clause) {
        Map<Integer, Integer> unequalities = new HashMap<Integer, Integer>();
        for (Assignment a : clause.clause) {
            if (a.value > 0) {
                continue;
            }
            if (unequalities.containsKey(a.variable)) {
                if (unequalities.get(a.variable) != a.value) {
                    return true;
                }
            }
            unequalities.put(a.variable, a.value);
        }
        return false;
    }

}
