package shell.command;

import file_system.Directory;

import java.util.Stack;

public interface Command {
    void execute(Stack<Directory> dir_stack, String[] args);
}
