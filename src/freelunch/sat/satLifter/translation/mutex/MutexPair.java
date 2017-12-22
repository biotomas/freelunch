package freelunch.sat.satLifter.translation.mutex;

public class MutexPair {
    
    private int literal1;
    private int literal2;
    
    public MutexPair(int literal1, int literal2) {
        this.literal1 = Math.min(literal1, literal2);
        this.literal2 = Math.max(literal1, literal2);
    }
    
    public int getLiteral1() {
        return literal1;
    }

    public int getLiteral2() {
        return literal2;
    }
    
    @Override
    public String toString() {
        return String.format("{%d,%d}", literal1, literal2);
    }
    
    @Override
    public int hashCode() {
        return (literal1 * 100000) + literal2;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MutexPair) {
            MutexPair other = (MutexPair) obj;
            return (literal1 ==  other.literal1 && literal2 == other.literal2);
        }
        return false;
    }
}