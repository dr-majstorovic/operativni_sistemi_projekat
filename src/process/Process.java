package process;

import java.util.Arrays;

public class Process {
    private int id;
    private ProcessState state;
    private String code;
    private PCB pcb;
    private int executedCount;

    public Process(int id, String code) {
        this.id = id;
        this.code = code;
        this.state = ProcessState.NEW;
        pcb = new PCB(id);
        executedCount = 0;
    }

    public void start() {
        this.state = ProcessState.RUNNING;
        System.out.println("Process " + id + " started.");
    }

    public void terminate() {
        this.state = ProcessState.TERMINATED;
        System.out.println("Process " + id + " terminated.");
    }

    public void block() {
        this.state = ProcessState.BLOCKED;
        System.out.println("Process " + id + " blocked.");
    }

    public void unblock() {
        this.state = ProcessState.READY;
        System.out.println("Process " + id + " unblocked.");
    }

    public int getId() {
        return id;
    }

    public ProcessState getState() {
        return state;
    }

    public String[] getInstructions() {
        return Arrays.stream(code.split(";"))
                .map(String::strip)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    public PCB getPCB() { return pcb; }
    public void setPCB(PCB pcb) { this.pcb = pcb; }

    public int getSize(){
        return getInstructions().length;
    }

    public int getExecutedInstructions() {
        return executedCount;
    }

    public void executedCountIncrement(){
        executedCount++;
    }

    @Override
    public String toString() {
        return "Process{id=" + id + ", state=" + state + ", code=" + code + "}";
    }

}
