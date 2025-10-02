package core;

import memory.HDD;

public class HDDManager {
    private HDD hdd;

    public HDDManager(HDD hdd) {
        this.hdd = hdd;
    }

    // 1. upis na disk
    public void writeToDisk(String address, String data) {
        hdd.getMemory().put(address, data);
    }

    // 2. čitanje sa diska
    public String readFromDisk(String address) {
        return hdd.getMemory().get(address);
    }

    // 3. provjera da li postoji podatak na adresi
    public boolean isDataInDisk(String address) {
        return hdd.getMemory().containsKey(address);
    }

    // Upis fajla na HDD
    public void createFile(String path, String content) {
        hdd.getMemory().put(path, content);
    }

    // Čitanje fajla sa HDD-a
    public String readFile(String path) {
        return hdd.getMemory().get(path);
    }

    // Dodavanje direktorijuma (samo marker da postoji)
    public void createDirectory(String path) {
        hdd.getMemory().put(path, "DIR");
    }

    public void removeFromDisk(String address) {
        hdd.getMemory().remove(address);
    }
}