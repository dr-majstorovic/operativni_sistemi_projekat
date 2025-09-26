package shell.command;

import file_system.Directory;

public class MemoryCommand implements Command{
    @Override
    public void execute(Directory directory, String[] args) {
        System.out.println("Echo: mem; no args, bitch");
    }
}
