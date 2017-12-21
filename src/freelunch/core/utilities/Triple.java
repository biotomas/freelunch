package freelunch.core.utilities;

/**
 * Represents 3 objects of different type
 *
 * @author Tomas Balyo
 * 8.7.2013
 */
public class Triple<Type1, Type2, Type3> {
    
    public Type1 first;
    public Type2 second;
    public Type3 third;
    
    public Triple(Type1 first, Type2 second, Type3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    @Override
    public String toString() {
        return String.format("[%s,%s,%s]", first.toString(), second.toString(), third.toString());
    }
    

}
