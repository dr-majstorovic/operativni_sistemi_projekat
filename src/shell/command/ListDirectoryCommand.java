package shell.command;

import file_system.Directory;
import file_system.File;

import java.util.Stack;

public class ListDirectoryCommand implements Command{
    @Override
    public void execute(Stack<Directory> dir_stack, String[] args) {
        System.out.println("-- Directories:");
        for(Directory x: dir_stack.peek().getDirectories()){
            System.out.println("    " + x.getName());
        }
        System.out.println("-- Files:");
        for(File x: dir_stack.peek().getFiles()){
            System.out.println("    " + x.getName());
        }
        System.out.println("-- End");
    }
}
