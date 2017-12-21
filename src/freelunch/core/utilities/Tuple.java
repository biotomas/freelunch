package freelunch.core.utilities;

/**
 * A tuple of two objects of different type
 *
 * @author Tomas Balyo
 * 8.7.2013
 */
public class Tuple<Type1, Type2> {
    
    public Type1 first;
    public Type2 second;
    
    public Tuple(Type1 first, Type2 second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", first.toString(), second.toString());
    }
    
    @Override
    public int hashCode() {
    	return first.hashCode() * second.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Tuple<?, ?>) {
    		Tuple<?,?> o = (Tuple<?, ?>) obj;
    		return o.first.equals(first) && o.second.equals(second);
    	} else {
    		return false;
    	}
    }

}
