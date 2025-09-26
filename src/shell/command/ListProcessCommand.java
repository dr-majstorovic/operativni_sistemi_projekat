package shell.command;

public class ListProcessCommand implements Command{
    @Override
    public void execute(String path, String[] args) {
        System.out.println("Echo: ps; no args, bitch");
    }
}
