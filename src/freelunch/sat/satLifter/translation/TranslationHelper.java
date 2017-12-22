package freelunch.sat.satLifter.translation;

import java.util.List;

public class TranslationHelper {
    
    private int[] varToMultiVar;
    private int[] varToValue;
    private int[] sizes;
    
    public TranslationHelper(List<List<Integer>> mutexCover, int variables) {
        varToMultiVar = new int[variables+1];
        varToValue = new int[variables+1];
        sizes = new int[mutexCover.size() + 1];
        
        int varId = 1;
        for (List<Integer> clique : mutexCover) {
            // one extra domain value for the "none of these values" value
            sizes[varId] = clique.size() + 1;
            int valueId = 1;
            for (int lit : clique) {
                int var = Math.abs(lit);
                varToMultiVar[var] = varId;
                if (lit > 0) {
                    varToValue[var] = valueId;
                } else {
                    varToValue[var] = -valueId;
                }
                valueId++;
            }
            varId++;
        }
    }
    
    public int[] getDomainSizes() {
        return sizes;
    }
    
    /**
     * Return the id of the multi-variable where the variable of
     * @param lit
     * @return
     */
    public int getMultiVariableForLiteral(int lit) {
        int var = Math.abs(lit);
        return varToMultiVar[var];
    }
    
    public int getValueForLiteral(int lit) {
        int var = Math.abs(lit);
        int valId = varToValue[var];
        if (lit > 0) {
            return valId;
        } else {
            return -valId;
        }
    }

}
