package shell.command;

import file_system.Directory;

import java.util.Stack;

public class MemoryCommand implements Command{
    @Override
    public void execute(Stack<Directory> dir_stack, String[] args) {
        System.out.println("Echo: mem; no args, bitch");
    }
}
