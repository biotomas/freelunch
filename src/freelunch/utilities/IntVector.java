package freelunch.utilities;

import java.util.Arrays;
import java.util.List;

public class IntVector {


    private int[] vector;
    private int last;

    /**
     * Create a new INT vector of the given initial size
     * @param initSize
     */
    public IntVector(int initSize) {
        vector = new int[initSize];
        last = 0;
    }
    
    public static IntVector makeUnit(int lit) {
    	IntVector iv = new IntVector(1);
    	iv.add(lit);
    	return iv;
    }
    
    public IntVector(int[] vector) {
        this.vector = vector;
        last = vector.length;
    }

    /**
     * Add x to the vector
     * @param x
     */
    public void add(int x) {
        if (last >= vector.length) {
            vector = Arrays.copyOf(vector, 2*last);
        }
        vector[last] = x;
        last++;
    }

    /**
     * Clear the array
     */
    public void clear() {
        last = 0;
    }

    /**
     * Returns a copy of the generated array, the vector can be
     * reused after calling a clear
     * @return
     */
    public int[] getArrayCopy() {
        return Arrays.copyOf(vector, last);
    }
    
    public static int[] listToArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        int i = 0;
        for (int e : list) {
            ret[i++] = e;
        }
        return ret;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(vector);
    }
}
