package shell.command;

public class ListProcessCommand implements Command{
    @Override
    public void execute(String[] args) {
        System.out.println("Echo: ps; no args, bitch");
    }
}
