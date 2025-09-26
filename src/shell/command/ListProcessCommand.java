package shell.command;

import file_system.Directory;

public class ListProcessCommand implements Command{
    @Override
    public void execute(Directory directory, String[] args) {
        System.out.println("Echo: ps; no args, bitch");
    }
}
