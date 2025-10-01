package shell;

import file_system.Directory;
import shell.command.*;

import java.util.*;

public class Shell {
    public static void main(String[] args) {
        Map<String, Command> commands = loadCommands();
        String current_path = "root";
        Directory root = new Directory("root");
        Stack<Directory> dir_stack = new Stack<>();
        dir_stack.push(root);

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
            System.out.print(current_path + "> ");
            String line = scanner.nextLine();
            String[] command = line.split(" ");
            switch (command[0]){
                case "help": commands.get("help").execute(dir_stack, null); break;
                case "cd": {
                    commands.get("cd").execute(dir_stack, Arrays.copyOfRange(command, 1, command.length));
                } break;
                case "dir": commands.get("dir").execute(dir_stack, Arrays.copyOfRange(command, 1, command.length)); break;
                case "ps": commands.get("ps").execute(dir_stack, null); break;
                case "mkdir": commands.get("mkdir").execute(dir_stack, Arrays.copyOfRange(command, 1, command.length)); break;
                case "new": commands.get("new").execute(dir_stack, Arrays.copyOfRange(command, 1, command.length)); break;
                case "run": commands.get("run").execute(dir_stack, Arrays.copyOfRange(command, 1, command.length)); break;
                case "mem": commands.get("mem").execute(dir_stack, null); break;
                case "exit": commands.get("exit").execute(dir_stack, null); break;
                case "rm": commands.get("rm").execute(dir_stack, Arrays.copyOfRange(command, 1, command.length)); break;
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
        commands.put("new", new NewCommand());
        commands.put("run", new RunCommand());
        commands.put("mem", new MemoryCommand());
        commands.put("exit", new ExitCommand());
        commands.put("rm", new RemoveCommand());

        return commands;
    }
}
