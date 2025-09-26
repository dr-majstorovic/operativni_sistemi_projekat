package shell.command;

import file_system.Directory;

import java.util.Arrays;
import java.util.Stack;

public class RemoveCommand implements Command{
    @Override
    public void execute(Stack<Directory> dir_stack, String[] args) {
        System.out.println("Echo: rm "+ Arrays.toString(args));
    }
}
