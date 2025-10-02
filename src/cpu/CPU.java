package cpu;

import core.Kernel;
import process.PCB;
import process.Process;
import process.ProcessState;

public class CPU {
    private Register IP;   // Instruction Pointer
    private Register SP;   // Stack Pointer
    private boolean busy;
    private int clockCycle;
    private Kernel kernel;
    private PCB running;

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
        if (running == null) throw new IllegalStateException("Nema aktivnog procesa");
        int absAddr = running.getCodeBase() + IP.getValueAsInt();
        return kernel.getMemoryManager().read(absAddr);
    }

    private String decode(String instruction) {
        return instruction.trim().toUpperCase();
    }

    private void execute(String instruction) {
        Process proc = kernel.getProcessManager().findByPid(running.getPid());
        String[] x = instruction.split(" ");
        switch (x[0].strip()) {
            case "PUSH":
                int val = Integer.parseInt(x[1]);
                push(val);
                IP.setValue(String.valueOf(IP.getValueAsInt() + 1));
                if (proc != null) {
                    proc.executedCountIncrement();
                }
                break;
            case "POP":
                pop();
                IP.setValue(String.valueOf(IP.getValueAsInt() + 1));
                if (proc != null) {
                    proc.executedCountIncrement();
                }
                break;
            case "ADD":
                int a = pop();
                int b = pop();
                push(a + b);
                IP.setValue(String.valueOf(IP.getValueAsInt() + 1));
                if (proc != null) {
                    proc.executedCountIncrement();
                }
                break;
            case "SUB":
                int x1 = pop();
                int x2 = pop();
                push(x2 - x1);
                IP.setValue(String.valueOf(IP.getValueAsInt() + 1));
                if (proc != null) {
                    proc.executedCountIncrement();
                }
                break;
            case "MUL":
                int m1 = pop();
                int m2 = pop();
                push(m1 * m2);
                IP.setValue(String.valueOf(IP.getValueAsInt() + 1));
                if (proc != null) {
                    proc.executedCountIncrement();
                }
                break;
            case "DIV":
                int d1 = pop();
                int d2 = pop();
                if (d1 == 0) throw new ArithmeticException("Division by zero!");
                push(d2 / d1);
                IP.setValue(String.valueOf(IP.getValueAsInt() + 1));
                if (proc != null) {
                    proc.executedCountIncrement();
                }
                break;
            case "HALT":
                busy = false;
                break;
            default:
                throw new IllegalArgumentException("Nepoznata instrukcija: " + instruction);
        }
    }

    private void push(int value) {
        if (running == null) throw new IllegalStateException("Nema aktivnog procesa");
        int spOffset = SP.getValueAsInt();               // broj elemenata u steku
        if (spOffset >= running.getStackLimit()) {
            throw new RuntimeException("Stack overflow u procesu " + running.getPid());
        }
        int addr = running.getStackBase() + spOffset;
        kernel.getMemoryManager().write(addr, String.valueOf(value)); // memorija cuva String
        SP.setValue(String.valueOf(spOffset + 1)); // povecaj offset
    }

    private int pop() {
        if (running == null) throw new IllegalStateException("Nema aktivnog procesa");
        int spOffset = SP.getValueAsInt();
        if (spOffset <= 0) throw new RuntimeException("Stack underflow u procesu " + running.getPid());
        spOffset = spOffset - 1;
        int addr = running.getStackBase() + spOffset;
        String s = kernel.getMemoryManager().read(addr);
        SP.setValue(String.valueOf(spOffset)); // update offset
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Neispravan podatak na steku: " + s);
        }
    }

    private int peek() {
        int spOffset = SP.getValueAsInt();
        if (spOffset <= 0) throw new RuntimeException("Stack underflow peek");
        int addr = running.getStackBase() + (spOffset - 1);
        return Integer.parseInt(kernel.getMemoryManager().read(addr));
    }

    public void setRunning(PCB pcb) {
        // prije prelaska: staru naredbu već treba biti sačuvana od scheduler-a,
        // ali možemo i ovdje osigurati atomicno save prethodnog
        this.running = pcb;
        if (pcb != null) {
            IP.setValue(String.valueOf(pcb.getPc()));
            SP.setValue(String.valueOf(pcb.getSp()));
            pcb.setState(ProcessState.RUNNING);
            this.busy = true;
        } else {
            this.busy = false;
        }
    }

    public void saveState() {
        if (running == null) return;
        running.setPc(IP.getValueAsInt());
        running.setSp(SP.getValueAsInt());
    }
}