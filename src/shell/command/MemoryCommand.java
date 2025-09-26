package shell.command;

public class MemoryCommand implements Command{
    @Override
    public void execute(String path, String[] args) {
        System.out.println("Echo: mem; no args, bitch");
    }
}
