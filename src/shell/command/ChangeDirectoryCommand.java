package shell.command;

import java.util.Arrays;

public class ChangeDirectoryCommand implements Command{
    @Override
    public void execute(String[] args) {
        System.out.println("Echo: cd " + Arrays.toString(args));
    }
}
