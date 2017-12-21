package freelunch.core.utilities;

import java.util.Arrays;


public class MoveToFrontHashTable {
    
    class MoveToFrontStructure {
        
        Object[] items = new Object[4];
        
        void addItem(Object item) {
            items[3] = items[2];
            items[2] = items[1];
            items[1] = items[0];
            items[0] = item;
        }
        
        boolean contains(Object item) {
            if (item.equals(items[0])) {
                return true;
            } else {
                int index = 0;
                if (item.equals(items[1]))
                    index = 1;
                else if (item.equals(items[2]))
                    index = 2;
                else if (item.equals(items[3]))
                    index = 3;
                if (index > 0) {
                    Object tmp = items[0];
                    items[0] = items[index];
                    items[index] = tmp;
                    return true;
                }
            }
            return false;
        }
    }
    
    private MoveToFrontStructure[] table;
    
    public MoveToFrontHashTable(int size) {
        table = new MoveToFrontStructure[size];
    }
    
    public void clear() {
        Arrays.fill(table, null);
    }
    
    public void add(Object item) {
        int index = Math.abs(item.hashCode()) % table.length;
        if (table[index] == null) {
            table[index] = new MoveToFrontStructure();
        }
        table[index].addItem(item);
    }
    
    public boolean contains(Object item) {
        int index = Math.abs(item.hashCode()) % table.length;
        return table[index] != null && table[index].contains(item);
    }
    
}
