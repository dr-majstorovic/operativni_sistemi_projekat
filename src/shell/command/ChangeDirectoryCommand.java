package shell.command;

import file_system.Directory;

import java.util.Arrays;
import java.util.Stack;

public class ChangeDirectoryCommand implements Command{

    @Override
    public void execute(Stack<Directory> dir_stack, String[] args) {
        //if(args.length>0 && )
        String new_directory = args[0];
    }
}
