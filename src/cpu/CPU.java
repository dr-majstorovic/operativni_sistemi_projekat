package cpu;

import core.Kernel;
import process.Process;
import memory.RAM;

public class CPU {
    private Register IP;   // Instruction Pointer
    private Register SP;   // Stack Pointer
    private boolean busy;
    private int clockCycle;
    private Kernel kernel;

    public CPU(Kernel kernel) {
        this.kernel = kernel;
        this.IP = new Register("IP", "0", "0");
        this.SP = new Register("SP", "0", "0");
        this.busy = false;
        this.clockCycle = 0;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public int getClockCycle() {
        return clockCycle;
    }

    // CPU cycle
    public void step() {
        String instruction = fetch();
        String decoded = decode(instruction);
        execute(decoded);
        clockCycle++;
    }

    private String fetch() {
        return kernel.getMemoryManager().read(IP.getValueAsInt());
    }

    private String decode(String instruction) {
        return instruction.trim().toUpperCase();
    }

    private void execute(String instruction) {
        String[] x = instruction.split(" ");
        switch (x[0].strip()) {
            case "PUSH":
                // uzmi podatak, stavi na stek
                break;
            case "POP":
                // skini sa steka
                break;
            case "ADD":
                // uzmi dva sa steka, saberi, vrati na stek
                break;
            case "SUB":
                // isto za oduzimanje
                break;
            case "HALT":
                busy = false;
                break;
            default:
                throw new IllegalArgumentException("Nepoznata instrukcija: " + instruction);
        }
    }
}
