package shell.command;

import java.util.Arrays;

public class ListDirectoryCommand implements Command{
    @Override
    public void execute(String[] args) {
        System.out.println("Echo: dir " + Arrays.toString(args));
    }
}
