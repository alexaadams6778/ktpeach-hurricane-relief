package com.model;
import java.util.ArrayList;

public class ShelterList {
    
    private static ShelterList shelterList;
    private ArrayList<Shelter> shelters;

    private ShelterList() {
        shelters = DataLoader.loadShelters();
    }

    public static ShelterList getInstance() {
        if (shelterList == null) {
            shelterList = new ShelterList();
        }
        return shelterList;
    }

    public ArrayList<Shelter> getShelters() {
        return shelters;
    }

    public ArrayList<Shelter> getShelters(String name) {
         ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : shelters) {
            if (matchesName(shelter, name)) {
                result.add(shelter);
            }
        }
        return result;
    }

    public ArrayList<Shelter> getShelters(String name, Location location) {
        ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : getShelters(name)) {
            if (matchesName(shelter, name) && matchesLocation(shelter, location)) {
                result.add(shelter);
            }
        }
        return result;
    }

    public ArrayList<Shelter> getShelters(String name, Location location, boolean allowsPets) {
        ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : getShelters(name, location)) {
            if (matchesName(shelter, name) && matchesLocation(shelter, location) && shelter.getAllowsPets() == allowsPets) {
                result.add(shelter);
            }
        }
        return result;
    }

    public boolean addShelter(String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        return addShelter(new Shelter(name, location, maxCapacity, currentOccupancy, allowsPets, isOpen, resources));
    }

    public boolean addShelter(Shelter shelter) {
        if (shelter == null) {
            return false;
        }
        for (Shelter s : getShelters()) {
            if (shelter.getName().equalsIgnoreCase(s.getName())) {
                return false;
            }
        }
        shelters.add(shelter);
        return true;
    }

    public boolean removeShelter(Shelter shelter) {
        if (shelter == null) {
            return false;
        }
        for (Shelter s : shelters) {
            if (s.getShelterID().equals(shelter.getShelterID())) {
                shelters.remove(shelter);
                return true;
            }
        }
        return false;
    }

    public void clearShelters() {
        shelters.clear();
    }
    
    public void save() {
        DataWriter.saveShelters();
    }

    private boolean matchesName(Shelter shelter, String name) {
        if(name == null || name.isEmpty()) {
            return true;
        }
        return shelter.getName().toLowerCase().contains(name.toLowerCase());
    }

    private boolean matchesLocation(Shelter shelter, Location location) {
        if(location == null) {
            return true;
        }
        return shelter.getLocation().equals(location);
    }
}
