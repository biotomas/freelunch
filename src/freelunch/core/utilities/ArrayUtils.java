package freelunch.core.utilities;

import java.util.Random;

public class ArrayUtils {
    
    public static void shuffle(int[] array, long seed) {
        Random rnd = new Random(seed);
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
    
    public static int[] sequence(int n) {
        int res[] = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = i;            
        }
        return res;
    }
    
    public static int[] randArray(int n, long seed) {
        int[] res = sequence(n);
        shuffle(res, seed);
        return res;
    }
    
    public static void invert(int[] array, int n) {
        for (int i = 0; i < array.length; i++) {
            array[i] = n - array[i];
        }
    }
    
    public static void reverse(int[] array) {
        int l = array.length - 1;
        for (int i = 0; i < l/2; i++) {
            int a = array[i];
            array[i] = array[l - i];
            array[l-i] = a;
        }
    }
    
    public static <T> int find(T[] array, T object) {
        for (int i = 0; i < array.length; i++) {
            if (object.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

}
