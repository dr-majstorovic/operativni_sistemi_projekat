package memory;

import java.util.HashMap;
import java.util.Map;

public class HDD {
    private int size;
    private Map<String, String> memory;

    public HDD(int size) {
        this.size = size;
        this.memory = new HashMap<>();
    }

    public int getSize() {
        return size;
    }

    public Map<String, String> getMemory() {
        return memory;
    }
}