package shell.command;

import file_system.Directory;

import java.util.Stack;

public class NewCommand implements Command{
    @Override
    public void execute(Stack<Directory> dir_stack, String[] args) {
        if(args.length > 0){
            dir_stack.peek().newFile(args[0].strip());
        }else {
            System.out.println("System: you need to enter a desired name. Run 'help'");
        }
    }
}
