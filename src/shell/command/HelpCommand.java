package shell.command;

public class HelpCommand implements Command{
    @Override
    public void execute(String path, String[] args) {
        System.out.println("List of available commands:");
        System.out.println(">> cd <directory> << changes working directory relative to the current one");
        System.out.println(">> dir << lists contents of current directory");
        System.out.println(">> ps << lists all the running processes in the system");
        System.out.println(">> mkdir <name> << creates a new directory within the current directory");
        System.out.println(">> run <name> << runs the process from current directory");
        System.out.println(">> mem << shows current memory usage");
        System.out.println(">> exit << shuts down the operating system");
        System.out.println(">> rm <name> << removes a file or a directory");
        System.out.println("Please don't ask for more details.");
    }
}
