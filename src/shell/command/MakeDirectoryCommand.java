package shell.command;

import java.util.Arrays;

public class MakeDirectoryCommand implements Command{
    @Override
    public void execute(String[] args) {
        System.out.println("Echo mkdir "+ Arrays.toString(args));
    }
}
