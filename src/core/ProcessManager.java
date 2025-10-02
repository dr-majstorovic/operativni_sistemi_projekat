package core;

import java.util.LinkedList;
import java.util.Queue;
import process.PCB;
import process.Process;
import process.ProcessState;

public class ProcessManager implements Scheduler {
    private Queue<Process> processList;

    public ProcessManager() {
        this.processList = new LinkedList<>();
    }

    public void addProcess(Process p) {
        p.unblock();
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
        while (!processList.isEmpty()) {
            Process p = processList.poll();
            if (p.getState() == ProcessState.READY) {
                p.start();
                return p;
            } else {
                // ako je blokiran, samo ga vrati nazad u red
                processList.add(p);
            }
        }
        return null;
    }

    public void listProcesses(MemoryManager memoryManager) {
        for (Process p : processList) {
            PCB pcb = p.getPCB();
            int usedMem = pcb.getCodeLimit() + pcb.getStackLimit();

            System.out.println("Process " + p.getId() +
                    " | Instr at: " + pcb.getCodeBase() +
                    " | RAM used: " + usedMem +
                    " | Executed: " + p.getExecutedInstructions());
        }
    }

    public Queue<Process> getProcessList() {
        return processList;
    }

    public Process createProcess(String path, Kernel kernel, int stackSize) {
        // 1. učitaj kod sa HDD-a
        String code = kernel.getHddManager().readFile(path);
        if (code == null) {
            throw new RuntimeException("Nema fajla na putanji: " + path);
        }

        // 2. napravi novi proces
        int newPid = kernel.getNextPid();   // kernel drži brojač PID-ova
        Process p = new Process(newPid, code);

        // 3. alociraj memoriju i PCB
        PCB pcb = kernel.getMemoryManager().allocateMemory(p, stackSize);
        if (pcb == null) {
            throw new RuntimeException("Nema dovoljno RAM-a za proces " + newPid);
        }

        // 4. poveži PCB sa procesom
        p.setPCB(pcb);

        // 5. dodaj u red spremnih procesa
        this.addProcess(p);

        return p;
    }

    public boolean blockProcess(int pid) {
        for (Process p : processList) {
            if (p.getId() == pid) {
                p.block();
                return true;
            }
        }
        return false; // proces nije pronađen
    }

    public boolean unblockProcess(int pid) {
        for (Process p : processList) {
            if (p.getId() == pid) {
                p.unblock();
                return true;
            }
        }
        return false; // proces nije pronađen
    }

    public Process findByPid(int pid) {
        for (Process p : processList) {
            if (p.getId() == pid) return p;
        }
        return null;
    }
}