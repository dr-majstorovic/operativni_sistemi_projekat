package core;

import memory.HDD;

import java.util.Map;

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
}