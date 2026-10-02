package com.model;
import java.util.ArrayList;

public class ShelterList {
    
    private static ShelterList shelterList;
    private ArrayList<Shelter> shelters;

    private ShelterList() {
        shelters = new ArrayList<Shelter>();
    }

    public static ShelterList getInstance() {
        if (shelterList == null) {
            return new ShelterList();
        }
        return shelterList;
    }

    public ArrayList<Shelter> getRequests(String name) {
        return null;
    }

    public ArrayList<Shelter> getRequests(String name, Location location) {
        return null;
    }

    public ArrayList<Shelter> getRequests(String name, Location location, boolean allowsPets) {
        return null;
    }

    public boolean addShelter(String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        return true;
    }
    
    public boolean save() {
        return true;
    }

}
