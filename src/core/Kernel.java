package core;

import cpu.CPU;
import file_system.Directory;
import file_system.File;
import memory.HDD;
import memory.RAM;
import process.PCB;
import process.Process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Kernel {
    private CPU cpu;
    private MemoryManager memoryManager;
    private HDDManager hddManager;
    private FileSystemManager fileSystemManager;
    private ProcessManager processManager;
    private int pidCounter = 0;
    private Directory currentDirectory;

    public Kernel() {
        this.memoryManager = new MemoryManager(new RAM(1024));
        this.hddManager = new HDDManager(new HDD(10000));
        this.fileSystemManager = new FileSystemManager();
        this.processManager = new ProcessManager();
        this.cpu = new CPU(this);
        currentDirectory = fileSystemManager.getCurrentDirectory();
    }

    public void start() {
        System.out.println("Kernel booting...");

        // učitavanje inicijalnog stanja iz memory.txt
        loadMemoryTxt("src/memory.txt");

        System.out.println("__        __   _                            _ \n" +
                "\\ \\      / /__| | ___ ___  _ __ ___   ___  | |\n" +
                " \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | |\n" +
                "  \\ V  V /  __/ | (_| (_) | | | | | |  __/ |_|\n" +
                "   \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___| (_)\n" +
                "                                              \n" +
                "         Welcome to OS simulator\n" +
                "         Type 'help' for commands\n");

        // Shell petlja
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("os> ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            String arg = (parts.length > 1) ? parts[1] : "";

            switch (cmd) {
                case "help":
                    System.out.println("List of available commands:");
                    System.out.println(">> cd <directory> << changes working directory relative to the current one (.. for return to parent directory)");
                    System.out.println(">> dir << lists contents of current directory");
                    System.out.println(">> ps << lists all the running processes in the system");
                    System.out.println(">> mkdir <name> << creates a new directory within the current directory");
                    System.out.println(">> run <name> << runs the process from current directory");
                    System.out.println(">> mem << shows current memory usage");
                    System.out.println(">> exit << shuts down the operating system");
                    System.out.println(">> rm <name> << removes a file or a directory");
                    System.out.println("Please don't ask for more details.");
                    break;

                case "cd":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: cd <dir>");
                    } else if (arg.equals("..")) {
                        if (currentDirectory.getParent() != null) {
                            currentDirectory = currentDirectory.getParent();
                            System.out.println("Now in directory: " + currentDirectory.getName());
                        } else {
                            System.out.println("Already at root.");
                        }
                    } else if (arg.equals("/")) {
                        currentDirectory = fileSystemManager.getRootDirectory();
                        System.out.println("Now in root directory.");
                    } else {
                        Directory target = fileSystemManager.findSubDirectory(currentDirectory, arg);
                        if (target != null) {
                            currentDirectory = target;
                            System.out.println("Now in directory: " + target.getName());
                        } else {
                            System.out.println("Directory not found: " + arg);
                        }
                    }
                    break;

                case "dir":
                    System.out.println("Directory: " + currentDirectory.getName());
                    System.out.println("Subdirectories:");
                    for (Directory d : currentDirectory.getSubDirectories()) {
                        System.out.println("  [D] " + d.getName());
                    }
                    System.out.println("Files:");
                    for (File f : currentDirectory.getFiles()) {
                        System.out.println("  [F] " + f.getName());
                    }
                    break;

                case "ps":
                    processManager.listProcesses(memoryManager);
                    break;

                case "mkdir":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: mkdir <name>");
                    } else {
                        Directory newDir = new Directory(arg);
                        currentDirectory.addDirectory(newDir);
                        System.out.println("Directory created: " + arg);
                    }
                    break;

                case "run":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: run <file>");
                    } else {
                        int stackSize = 16;

                        String code = hddManager.readFromDisk(arg);
                        if (code == null) {
                            System.out.println("Nema koda na putanji: " + arg);
                            break;
                        }

                        int newPid = getNextPid();
                        Process p = new Process(newPid, code);

                        PCB pcb = memoryManager.allocateMemory(p, stackSize);
                        if (pcb != null) {
                            p.setPCB(pcb);
                            processManager.addProcess(p);
                            System.out.println("Proces pokrenut: PID=" + newPid + " path=" + arg);
                        } else {
                            System.out.println("Nema dovoljno memorije za proces: " + arg);
                        }
                    }
                    break;

                case "mem":
                    memoryManager.showUsage();
                    break;

                case "rm":
                    if (arg.isEmpty()) {
                        System.out.println("Usage: rm <path>");
                    } else {
                        // prvo probaj obrisati fajl
                        if (fileSystemManager.removeFile(arg)) {
                            hddManager.removeFromDisk(arg);
                            System.out.println("File removed: " + arg);
                        } else if (fileSystemManager.removeDirectory(arg)) {
                            System.out.println("Directory removed: " + arg);
                        } else {
                            System.out.println("Path not found: " + arg);
                        }
                    }
                    break;

                case "block":
                    if (!arg.isEmpty()) {
                        try {
                            int pid = Integer.parseInt(arg);
                            if (processManager.blockProcess(pid)) {
                                System.out.println("Process " + pid + " blocked.");
                            } else {
                                System.out.println("Process not found: " + pid);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid PID format.");
                        }
                    } else {
                        System.out.println("Usage: block <pid>");
                    }
                    break;

                case "unblock":
                    if (!arg.isEmpty()) {
                        try {
                            int pid = Integer.parseInt(arg);
                            if (processManager.unblockProcess(pid)) {
                                System.out.println("Process " + pid + " unblocked.");
                            } else {
                                System.out.println("Process not found: " + pid);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid PID format.");
                        }
                    } else {
                        System.out.println("Usage: unblock <pid>");
                    }
                    break;

                case "exit":
                    running = false;
                    break;

                default:
                    System.out.println("Nepoznata komanda: " + cmd);
            }
        }

        sc.close();
        try {
            Thread.sleep(300);
            System.out.println("Shutting down...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("   =================================\n" +
                    "   ||   So long, and thanks for   ||\n" +
                    "   ||        all the fish!        ||\n" +
                    "   =================================\n");
            System.exit(0);
        }
    }

    private void loadMemoryTxt(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty() || line.startsWith("#")) continue; // preskoči prazne i komentare

                String[] parts = line.split(" ", 2);
                String cmd = parts[0];
                String arg = (parts.length > 1) ? parts[1] : "";

                switch (cmd.toUpperCase()) {
                    case "DIR":
                        fileSystemManager.addDirectory("root", new Directory(arg));
                        System.out.println("Dodajem direktorijum: " + arg);
                        break;

                    case "FILE":
                        // format: FILE path=... content=...
                        String[] kv = arg.split(" content=");
                        String path = kv[0].replace("path=", "").trim();
                        String content = kv.length > 1 ? kv[1] : "";
                        hddManager.writeToDisk(path, content);
                        fileSystemManager.addFile("root", new File(path, content));
                        System.out.println("Dodajem fajl: " + path);
                        break;

                    case "PROCESS":
                        // format: PROCESS path stackSize
                        String[] procParts = arg.split(" ");
                        String procPath = procParts[0];
                        int stackSize = (procParts.length > 1) ? Integer.parseInt(procParts[1]) : 16;

                        String code = hddManager.readFromDisk(procPath);
                        if (code == null) {
                            System.out.println("Nema koda na putanji: " + procPath);
                            break;
                        }

                        int newPid = getNextPid();
                        Process p = new Process(newPid, code);
                        PCB pcb = memoryManager.allocateMemory(p, stackSize);
                        if (pcb != null) {
                            p.setPCB(pcb);
                            processManager.addProcess(p);
                            System.out.println("Proces učitan: PID=" + newPid + " path=" + procPath);
                        } else {
                            System.out.println("Nema dovoljno memorije za proces: " + procPath);
                        }
                        break;

                    default:
                        System.out.println("Nepoznata komanda u memory.txt: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Greška pri učitavanju memory.txt", e);
        }
    }

    private String buildPath(Directory dir, String name) {
        if (dir == null) return name; // fallback
        if (dir.getParent() == null) { // znači da je korijen
            return dir.getName() + "/" + name;
        }
        return buildPath(dir.getParent(), dir.getName()) + "/" + name;
    }

    private void runProcess(String path, int stackSize) {
        String code = hddManager.readFromDisk(path);
        if (code == null) {
            System.out.println("Nema koda na putanji: " + path);
            return;
        }

        int pid = getNextPid();
        Process p = new Process(pid, code);
        PCB pcb = memoryManager.allocateMemory(p, stackSize);
        if (pcb != null) {
            p.setPCB(pcb);
            processManager.addProcess(p);
            System.out.println("Proces učitan: PID=" + pid + " path=" + path);
        } else {
            System.out.println("Nema dovoljno memorije za proces " + path);
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

    public int getNextPid() {
        return pidCounter++;
    }
}