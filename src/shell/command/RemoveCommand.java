package shell.command;

import file_system.Directory;

import java.util.Arrays;

public class RemoveCommand implements Command{
    @Override
    public void execute(Directory directory, String[] args) {
        System.out.println("Echo: rm "+ Arrays.toString(args));
    }
}
