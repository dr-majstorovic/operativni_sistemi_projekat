package shell.command;

import file_system.Directory;

import java.util.Stack;

public class ListProcessCommand implements Command{
    @Override
    public void execute(Stack<Directory> dir_stack, String[] args) {
        System.out.println("Echo: ps; no args, bitch");
    }
}
