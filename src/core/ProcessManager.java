package core;

import java.util.LinkedList;
import java.util.Queue;
import process.Process;

public class ProcessManager implements Scheduler {
    private Queue<Process> processList;

    public ProcessManager() {
        this.processList = new LinkedList<>();
    }

    public void addProcess(Process p) {
        p.unblock(); // kad ga dodamo, ide u READY stanje
        processList.add(p);
        System.out.println("Added process " + p.getId());
    }

    public void removeProcess(Process p) {
        processList.remove(p);
        System.out.println("Removed process " + p.getId());
    }

    public boolean isEmpty() {
        return processList.isEmpty();
    }

    // SCHEDULER: uzima prvi spreman proces iz reda
    @Override
    public Process scheduleNextProcess() {
        if (!processList.isEmpty()) {
            Process p = processList.poll(); // uzmi prvi iz reda
            p.start();
            return p;
        }
        return null;
    }
}

