package core;

import memory.RAM;
import process.PCB;
import process.Process;

import java.util.HashMap;
import java.util.Map;

public class MemoryManager {
    private RAM ram;
    private Map<Process, PCB> processTable; // čuva početnu adresu svakog procesa

    public MemoryManager(RAM ram) {
        this.ram = ram;
        this.processTable = new HashMap<>();
    }

    public PCB allocateMemory(Process p, int stackSize) {
        String[] instr = p.getInstructions();
        int codeSize = instr.length;
        int total = codeSize + stackSize;
        int startAddr = findFreeSpace(total);
        if (startAddr == -1) return null;

        for (int i = 0; i < codeSize; i++) {
            ram.getMemory().put(startAddr + i, instr[i]);
        }
        for (int i = codeSize; i < total; i++) {
            ram.getMemory().put(startAddr + i, "FREE");
        }

        PCB pcb = p.getPCB();

        pcb.setCodeBase(startAddr);
        pcb.setCodeLimit(codeSize);
        pcb.setStackBase(startAddr + codeSize);
        pcb.setStackLimit(stackSize);

        processTable.put(p, pcb);
        return pcb;
    }

    public int findFreeSpace(int size) {
        int consecutive = 0;
        int start = -1;
        for (int addr = 0; addr < ram.getSize(); addr++) {
            String value = ram.getMemory().getOrDefault(addr, "FREE");
            if ("FREE".equals(value)) {
                if (consecutive == 0) start = addr;
                consecutive++;
                if (consecutive == size) return start;
            } else {
                consecutive = 0;
            }
        }
        return -1;
    }

    public void showUsage() {
        int used = 0;
        int total = ram.getSize();

        for (String value : ram.getMemory().values()) {
            if (value != null && !value.equals("FREE")) {
                used++;
            }
        }

        System.out.println("RAM usage: " + used + " / " + total + " slots (" +
                (100.0 * used / total) + "%)");
    }

    public PCB getProcessMemoryAddress(Process p) {
        return processTable.get(p);
    }

    public String read(int address) {
        return ram.getMemory().getOrDefault(address, null);
    }

    public void write(int address, String value) {
        if (address < 0 || address >= ram.getSize()) {
            throw new IllegalArgumentException("Neispravna memorijska adresa: " + address);
        }
        ram.getMemory().put(address, value);
    }

    public PCB getPCB(Process p) {
        return processTable.get(p);
    }

    public void freeMemory(Process p) {
        PCB pcb = processTable.remove(p);
        if (pcb == null) return;
        int total = pcb.getCodeLimit() + pcb.getStackLimit();
        for (int i = 0; i < total; i++) {
            ram.getMemory().put(pcb.getCodeBase() + i, "FREE");
        }
    }
}