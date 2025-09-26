package shell.command;

import file_system.Directory;

public class ExitCommand implements Command{
    @Override
    public void execute(Directory directory, String[] args) {
        try {
            Thread.sleep(300);
            System.out.println("Shutting down...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("   =================================\n" +
                    "   ||   So long, and thanks for   ||\n" +
                    "   ||        all the fish!        ||\n" +
                    "   =================================\n");
            System.exit(0);
        }
    }
}
