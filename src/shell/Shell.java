package shell;

import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to OS Simulator\n" +
                "Type 'help' to see commands");
        while(true){
            System.out.print("OS> ");
            String command = scanner.nextLine();
            System.out.println("Echo: " + command);
        }
    }
}
