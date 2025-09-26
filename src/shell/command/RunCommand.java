package shell.command;

import java.util.Arrays;

public class RunCommand implements Command{
    @Override
    public void execute(String path, String[] args) {
        System.out.println("Echo: run "+ Arrays.toString(args));
    }
}
