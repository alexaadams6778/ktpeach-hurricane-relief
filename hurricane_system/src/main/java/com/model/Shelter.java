package com.model;
import java.util.ArrayList;
import java.util.UUID;

public class Shelter {
    
    private UUID shelterID;
    private String name;
    private Location location;
    private int maxCapacity;
    private int currentOccupancy;
    private boolean allowsPets;
    private boolean isOpen;
    private ArrayList<Resource> resources;

    public Shelter(UUID shelterID, String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        this.shelterID = shelterID;
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;
    }

    public boolean hasVacancy() {
        return true;
    }

    public ArrayList<Resource> getAvailableResources() {
        return null;
    }

    public void checkInUser(User user) {

    }

    public void updateOperationalStatus() {
        
    }

}
