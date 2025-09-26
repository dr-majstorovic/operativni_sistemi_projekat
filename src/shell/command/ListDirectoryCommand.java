package shell.command;

import file_system.Directory;
import file_system.File;

public class ListDirectoryCommand implements Command{
    @Override
    public void execute(Directory directory, String[] args) {
        System.out.println("-- Directories:");
        for(Directory x: directory.getDirectories()){
            System.out.println("    " + x.getName());
        }
        System.out.println("-- Files:");
        for(File x: directory.getFiles()){
            System.out.println("    " + x.getName());
        }
        System.out.println("-- End");
    }
}
