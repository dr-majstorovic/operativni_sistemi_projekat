package file_system;

import java.util.ArrayList;
import java.util.List;

public class Directory {
    private String name;
    private List<File> files;
    private List<Directory> subDirectories;

    public Directory(String name) {
        this.name = name;
        this.files = new ArrayList<>();
        this.subDirectories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void addDirectory(Directory dir) {
        subDirectories.add(dir);
    }

    @Override
    public String toString() {
        return "Directory{name='" + name + "', files=" + files.size() +
                ", subDirs=" + subDirectories.size() + "}";
    }
}
