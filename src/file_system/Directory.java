package file_system;

import java.util.ArrayList;
import java.util.List;

public class Directory {
    private String name;
    private Directory parent;
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
        dir.setParent(this);
        subDirectories.add(dir);
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public boolean removeFile(String name) {
        return files.removeIf(f -> f.getName().equals(name));
    }

    public boolean removeDirectory(String name) {
        return subDirectories.removeIf(d -> d.getName().equals(name));
    }

    @Override
    public String toString() {
        return "Directory{name='" + name + "', files=" + files.size() +
                ", subDirs=" + subDirectories.size() + "}";
    }
}