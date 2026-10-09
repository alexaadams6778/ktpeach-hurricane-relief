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
  
  public Shelter(String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        this.shelterID = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;

    }

    public UUID getShelterID() {
        return shelterID;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public boolean getAllowsPets() {
        return allowsPets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public void setAllowsPets(boolean allowsPets) {
        this.allowsPets = allowsPets;
    }

    public boolean isOpen() {
        return isOpen;
    }

    

    public boolean hasVacancy() {
        return isOpen && currentOccupancy < maxCapacity;
    }

    public ArrayList<Resource> getAvailableResources() {
        ArrayList<Resource> available = new ArrayList<Resource>();
        for (Resource resource : resources) {
            if (resource.getQuantity() > 0) {
                available.add(resource);
            }
        }
        return available;
    }

    public void checkInUser(User user) {
        if(user != null && hasVacancy()) {
            currentOccupancy++;
        }

    }

    public void updateOperationalStatus() {
        isOpen = !isOpen;
    }

    public String toString() {
        return "\nName: " + getName() + "\nLocation: " + getLocation() + "\nMax Capacity: " + getMaxCapacity() +
        "\nCurrent Occupancy: " + getCurrentOccupancy() + "\nAllows Pets: " + getAllowsPets() + "\nOpen: " + isOpen();

    }

}
