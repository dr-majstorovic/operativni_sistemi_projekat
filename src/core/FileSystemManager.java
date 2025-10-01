package core;

import file_system.Directory;
import file_system.File;

public class FileSystemManager {
    private Directory rootDirectory;

    public FileSystemManager() {
        this.rootDirectory = new Directory("root");
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

    public Directory getRootDirectory() {
        return rootDirectory;
    }
}