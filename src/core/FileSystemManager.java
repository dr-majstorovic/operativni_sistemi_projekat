package core;
import file_system.Directory;
import file_system.File;

import java.util.Arrays;

public class FileSystemManager {
    private Directory rootDirectory;
    private Directory currentDirectory;

    public FileSystemManager() {
        this.rootDirectory = new Directory("root");
        this.currentDirectory = rootDirectory;
    }

    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Directory dir) {
        this.currentDirectory = dir;
    }

    // Dodavanje fajla u određeni path
    public void addFile(String path, File file) {
        Directory dir = navigateToDirectory(path);
        if (dir != null) {
            dir.addFile(file);
        } else {
            System.out.println("Directory not found: " + path);
        }
    }

    // Dodavanje direktorijuma u određeni path
    public void addDirectory(String path, Directory d) {
        Directory dir = navigateToDirectory(path);
        if (dir != null) {
            dir.addDirectory(d);
        } else {
            System.out.println("Directory not found: " + path);
        }
    }

    // Navigacija kroz path
    public Directory navigateToDirectory(String path) {
        String[] parts = path.split("/");
        Directory current = rootDirectory;

        for (int i = 1; i < parts.length; i++) { // preskače "root"
            String dirName = parts[i];
            boolean found = false;

            for (Directory d : current.getSubDirectories()) {
                if (d.getName().equals(dirName)) {
                    current = d;
                    found = true;
                    break;
                }
            }

            if (!found) return null;
        }
        return current;
    }

    // brisanje fajla iz direktorijuma
    public boolean removeFile(String path) {
        String[] parts = path.split("/");
        String fileName = parts[parts.length - 1];
        String dirPath = String.join("/", Arrays.copyOf(parts, parts.length - 1));

        Directory dir = navigateToDirectory(dirPath);
        if (dir != null) {
            return dir.removeFile(fileName);
        }
        return false;
    }

    // brisanje direktorijuma
    public boolean removeDirectory(String path) {
        String[] parts = path.split("/");
        String dirName = parts[parts.length - 1];
        String parentPath = String.join("/", Arrays.copyOf(parts, parts.length - 1));

        Directory parent = navigateToDirectory(parentPath);
        if (parent != null) {
            return parent.removeDirectory(dirName);
        }
        return false;
    }

    public Directory getRootDirectory() {
        return rootDirectory;
    }

    public Directory findSubDirectory(Directory current, String name) {
        for (Directory d : current.getSubDirectories()) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }
}