package com.model;
import java.util.ArrayList;

public class ShelterList {
    
    private static ShelterList shelterList;
    private ArrayList<Shelter> shelters;

    private ShelterList() {
        shelters = new ArrayList<Shelter>();
        //TODO: load shelters
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
        if(name == null || location == null){
            return false;
        }
        shelters.add(new Shelter(name, location, maxCapacity, currentOccupancy, allowsPets, isOpen, resources));
        return true;
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
        Location other = shelter.getLocation();
        return other.getCity().equalsIgnoreCase(location.getCity()) &&
               other.getState().equalsIgnoreCase(location.getState());
    }
}
