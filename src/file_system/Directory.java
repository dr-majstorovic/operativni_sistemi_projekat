package file_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Directory {
    private String name;
    private List<File> files;
    private List<Directory> directories;

    public Directory(String name){
        this.name = name;
        files = new ArrayList<>();
        directories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void newDirectory(String name){
        for(Directory x: directories){
            if(Objects.equals(x.getName(), name)){
                System.out.println("Denied: directory with that name already exists.");
                return;
            }
            if(Objects.equals(name, "")) {
                System.out.println("Denied: directory name must contain at least one character.");
                return;
            }
        }
        Directory n_dir = new Directory(name);
        directories.add(n_dir);
        System.out.println("System: made a new directory named '" + n_dir.name + "'");
    }

    public void newFile(String name){
        for(File x: files){
            if(Objects.equals(x.getName(), name)){
                System.out.println("Denied: file with that name already exists.");
                return;
            }
            if(Objects.equals(name, "")){
                System.out.println("Denied: file name must contain at least one character");
                return;
            }
        }
        File n_file = new File(name);
        files.add(n_file);
        System.out.println("System: made a new file named '" + n_file.getName() + "'");
    }
}
