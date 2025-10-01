package core;

import cpu.CPU;
import memory.HDD;
import memory.RAM;

public class Kernel {
    private CPU cpu;
    private MemoryManager memoryManager;
    private HDDManager hddManager;
    private FileSystemManager fileSystemManager;
    private ProcessManager processManager;

    public Kernel() {
        this.memoryManager = new MemoryManager(new RAM(1024));
        this.hddManager = new HDDManager(new HDD(10000));
        this.fileSystemManager = new FileSystemManager();
        this.processManager = new ProcessManager();
        this.cpu = new CPU(this);
    }

    public void start() {
        System.out.println("Kernel starting...");

        // učitavanje inicijalnih procesa sa diska
        processManager.loadInitialProcesses(hddManager, memoryManager);

        // glavna scheduling petlja
        while (true) {
            Process nextProcess = processManager.getNextProcess();
            if (nextProcess == null) {
                System.out.println("No processes left. System halting...");
                break;
            }

            cpu.runProcess(nextProcess);

            processManager.updateProcessState(nextProcess, cpu.getState());
        }
    }
    public CPU getCpu() {
        return cpu;
    }

    public FileSystemManager getFileSystemManager() {
        return fileSystemManager;
    }

    public ProcessManager getProcessManager() {
        return processManager;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public HDDManager getHddManager() {
        return hddManager;
    }
}
