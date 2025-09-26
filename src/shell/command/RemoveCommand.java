package shell.command;

import java.util.Arrays;

public class RemoveCommand implements Command{
    @Override
    public void execute(String path, String[] args) {
        System.out.println("Echo: rm "+ Arrays.toString(args));
    }
}
