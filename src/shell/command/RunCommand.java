package shell.command;

import file_system.Directory;

import java.util.Arrays;

public class RunCommand implements Command{
    @Override
    public void execute(Directory directory, String[] args) {
        System.out.println("Echo: run "+ Arrays.toString(args));
    }
}
