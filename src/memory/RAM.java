package memory;

import java.util.HashMap;
import java.util.Map;

public class RAM {
    public int size;
    public Map<Integer, String> memory;

    public RAM(int size){
        this.size = size;
        memory = new HashMap<>();
    }

    public int getSize(){
        return size;
    }

    public Map<Integer, String> getMemory() {
        return memory;
    }
}
