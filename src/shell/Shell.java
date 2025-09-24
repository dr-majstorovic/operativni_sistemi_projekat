package shell;

import shell.command.*;

import java.util.*;

public class Shell {
    public static void main(String[] args) {
        Map<String, Command> commands = loadCommands();

        Scanner scanner = new Scanner(System.in);
        System.out.println("__        __   _                            _ \n" +
                "\\ \\      / /__| | ___ ___  _ __ ___   ___  | |\n" +
                " \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | |\n" +
                "  \\ V  V /  __/ | (_| (_) | | | | | |  __/ |_|\n" +
                "   \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___| (_)\n" +
                "                                              \n" +
                "         Welcome to OS simulator\n" +
                "         Type 'help' for commands\n");
        while(true){
            System.out.print("OS> ");
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            switch (command[0]){
                case "help": commands.get("help").execute(null); break;
                case "cd": commands.get("cd").execute(Arrays.copyOfRange(command, 1, command.length)); break;
                case "dir": commands.get("dir").execute(Arrays.copyOfRange(command, 1, command.length)); break;
                case "ps": commands.get("ps").execute(null); break;
                case "mkdir": commands.get("mkdir").execute(Arrays.copyOfRange(command, 1, command.length)); break;
                case "run": commands.get("run").execute(Arrays.copyOfRange(command, 1, command.length)); break;
                case "mem": commands.get("mem").execute(null); break;
                case "exit": commands.get("exit").execute(null); break;
                case "rm": commands.get("rm").execute(Arrays.copyOfRange(command, 1, command.length)); break;
                default:
                    System.out.println("invalid command, dude"); break;
            }
        }
    }

    private static Map<String, Command> loadCommands(){
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        commands.put("cd", new ChangeDirectoryCommand());
        commands.put("dir", new ListDirectoryCommand());
        commands.put("ps", new ListProcessCommand());
        commands.put("mkdir", new MakeDirectoryCommand());
        commands.put("run", new RunCommand());
        commands.put("mem", new MemoryCommand());
        commands.put("exit", new ExitCommand());
        commands.put("rm", new RemoveCommand());

        return commands;
    }
}
