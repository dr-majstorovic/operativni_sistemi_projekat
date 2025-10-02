package process;

import process.ProcessState;

public class PCB {
    private final int pid;
    private ProcessState state;

    // memory layout
    private int codeBase;   // adresa gde stoji kod
    private int codeLimit;  // broj instrukcija (veličina koda)
    private int stackBase;  // adresa gde počinje stek
    private int stackLimit; // koliko slotova stek ima

    // cpu state (offseti relativni na segment)
    private int pc; // offset u kod segmentu (IP)
    private int sp; // offset u steku (broj elemenata)

    // --- konstruktori ---
    // 1) samo PID (najjednostavniji, punimo ostatak kasnije)
    public PCB(int pid) {
        this.pid = pid;
        this.state = ProcessState.NEW;
        this.codeBase = -1;
        this.codeLimit = 0;
        this.stackBase = -1;
        this.stackLimit = 0;
        this.pc = 0;
        this.sp = 0;
    }

    // 2) kompletan konstruktor (kad sve znam odmah)
    public PCB(int pid, int codeBase, int codeLimit, int stackBase, int stackLimit) {
        this.pid = pid;
        this.state = ProcessState.NEW;
        this.codeBase = codeBase;
        this.codeLimit = codeLimit;
        this.stackBase = stackBase;
        this.stackLimit = stackLimit;
        this.pc = 0;
        this.sp = 0;
    }

    // --- getteri / setteri ---
    public int getPid() { return pid; }
    public ProcessState getState() { return state; }
    public void setState(ProcessState state) { this.state = state; }

    public int getCodeBase() { return codeBase; }
    public void setCodeBase(int codeBase) { this.codeBase = codeBase; }
    public int getCodeLimit() { return codeLimit; }
    public void setCodeLimit(int codeLimit) { this.codeLimit = codeLimit; }

    public int getStackBase() { return stackBase; }
    public void setStackBase(int stackBase) { this.stackBase = stackBase; }
    public int getStackLimit() { return stackLimit; }
    public void setStackLimit(int stackLimit) { this.stackLimit = stackLimit; }

    public int getPc() { return pc; }
    public void setPc(int pc) { this.pc = pc; }

    public int getSp() { return sp; }
    public void setSp(int sp) { this.sp = sp; }
}
