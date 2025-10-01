package process;

import java.util.ArrayList;
import java.util.List;

public class Process {
    private int id;
    private ProcessState state;
    private String code;

    public Process(int id, String code) {
        this.id = id;
        this.code = code;
        this.state = ProcessState.NEW;
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

    public String[] getInstructions(){
        String[] instructions = code.split(";");
        for (String i: instructions)
            i = i.strip();

        return instructions;
    }

    public int getSize(){
        return getInstructions().length;
    }

    @Override
    public String toString() {
        return "Process{id=" + id + ", state=" + state + ", code=" + code + "}";
    }
}


