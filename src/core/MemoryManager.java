package core;

import memory.RAM;
import process.Process;

import java.util.HashMap;
import java.util.Map;

public class MemoryManager {
    private RAM ram;
    private Map<Process, Integer> processTable; // čuva početnu adresu svakog procesa

    public MemoryManager(RAM ram) {
        this.ram = ram;
        this.processTable = new HashMap<>();
    }

    public boolean allocateMemory(Process p) {
        int size = p.getSize();
        int startAddr = findFreeSpace(size);
        if (startAddr == -1) return false; // nema mjesta

        for (int i = 0; i < size; i++) {
            ram.getMemory().put(startAddr + i, p.getInstructions()[i]);
        }
        processTable.put(p, startAddr);
        return true;
    }

    public int findFreeSpace(int size) {
        int consecutive = 0;
        int start = -1;
        for (int addr = 0; addr < ram.getSize(); addr++) {
            String value = ram.getMemory().getOrDefault(addr, "FREE");
            if (value.equals("FREE")) {
                if (consecutive == 0) start = addr;
                consecutive++;
                if (consecutive == size) return start;
            } else {
                consecutive = 0;
            }
        }
        return -1; // nema dovoljno prostora
    }

    public Integer getProcessMemoryAddress(Process p) {
        return processTable.get(p);
    }

    public String read(int address) {
        return ram.getMemory().getOrDefault(address, null);
    }
}
