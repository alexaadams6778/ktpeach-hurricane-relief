package com.model;
import java.util.ArrayList;
import java.util.UUID;
/**
 * @author KTPeach
 * Represents a shelter in the hurricane relief system.
 */
public class Shelter {
    
    private UUID shelterID;
    private String name;
    private Location location;
    private int maxCapacity;
    private int currentOccupancy;
    private boolean allowsPets;
    private boolean isOpen;
    private ArrayList<Resource> resources;
    private ArrayList<String> occupants;
    private ArrayList<String> pets;
    private boolean hasMedicalStaff;
/**
     * Creates a Shelter object with the specified parameters.
     * @param shelterID the unique identifier for the shelter
     * @param name the name of the shelter
     * @param location the location of the shelter
     * @param maxCapacity the maximum capacity of the shelter
     * @param currentOccupancy the current occupancy of the shelter
     * @param allowsPets whether the shelter allows pets
     * @param isOpen whether the shelter is open
     * @param resources the list of resources available at the shelter
     */
    public Shelter(UUID shelterID, String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        this.shelterID = shelterID;
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;
        this.occupants = new ArrayList<String>();
        this.pets = new ArrayList<String>();
        this.hasMedicalStaff = false;
    }
  
    /**
     * Creates a Shelter object with the specified parameters, generating a new UUID for the shelter.
     * @param name the name of the shelter
     * @param location the location of the shelter
     * @param maxCapacity the maximum capacity of the shelter
     * @param currentOccupancy the current occupancy of the shelter
     * @param allowsPets whether the shelter allows pets
     * @param isOpen whether the shelter is open
     * @param resources the list of resources available at the shelter
     */
    public Shelter(String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        this.shelterID = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;
        this.occupants = new ArrayList<String>();
        this.pets = new ArrayList<String>();
        this.hasMedicalStaff = false;
    }

    public Shelter(UUID shelterID, String name, Location location, int maxCapacity, 
               int currentOccupancy, boolean allowsPets, boolean isOpen, 
               ArrayList<Resource> resources, ArrayList<String> occupants, 
               ArrayList<String> pets, boolean hasMedicalStaff) {
        this.shelterID = shelterID;
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;
        if (occupants != null)
            this.occupants = occupants;
        if (pets != null) 
            this.pets = pets;
        this.hasMedicalStaff = hasMedicalStaff;
    }

    public Shelter(String name, Location location, int maxCapacity, 
               int currentOccupancy, boolean allowsPets, boolean isOpen, 
               ArrayList<Resource> resources, ArrayList<String> occupants, 
               ArrayList<String> pets, boolean hasMedicalStaff) {
        this.shelterID = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;
        if (occupants != null)
            this.occupants = occupants;
        if (pets != null) 
            this.pets = pets;
        this.hasMedicalStaff = hasMedicalStaff;
    }

    /**
     * Returns the unique identifier of the shelter.
     * @return the shelterID as a UUID
     */
    public UUID getShelterID() {
        return shelterID;
    }
    /**
     * Returns the name of the shelter.
     * @return the name as a String
     */
    public String getName() {
        return name;
    }
/**
     * Returns the location of the shelter.
     * @return the location as a Location object
     */
    public Location getLocation() {
        return location;
    }
/**
     * Returns the maximum capacity of the shelter.
     * @return the maxCapacity as an int
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }
/**
     * Returns the current occupancy of the shelter.
     * @return the currentOccupancy as an int
     */
    public int getCurrentOccupancy() {
        return currentOccupancy;
    }
/**
     * Returns whether the shelter allows pets.
     * @return true if the shelter allows pets, false otherwise
     */
    public boolean getAllowsPets() {
        return allowsPets;
    }

    /**
     * Returns all occupants of shelter
     * @return the ArrayList of occupants in shelter
     */
    public ArrayList<String> getOccupants() {
        return occupants;
    }

    /**
     * Returns all pets in shelter
     * @return the ArrayList of pets in shelter
     */
    public ArrayList<String> getPets() {
        return pets;
    }

/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public void setName(String name) {
        this.name = name;
    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public void setLocation(Location location) {
        this.location = location;
    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public void setAllowsPets(boolean allowsPets) {
        this.allowsPets = allowsPets;
    }

    /**
     * Sets occupants ArrayList to inputed ArrayList
     * @param occupants
     */
    public void setOccupants(ArrayList<String> occupants) {
        this.occupants = occupants;
    }

    /**
     * Sets pets ArrayList to inputed ArrayList
     * @param pets
     */
    public void setPets(ArrayList<String> pets) {
        this.pets = pets;
    }

    /**
     * Sets hasMedicalStaff boolean value to inputed value
     * @param hasMedicalStaff
     */
    public void setHasMedicalStaff(boolean hasMedicalStaff) {
        this.hasMedicalStaff = hasMedicalStaff;
    }

/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Determines if the shelter has medical staff
     * @return Returns true if shelter has medical staff, otherwise returns false
     */
    public boolean hasMedicalStaff() {
        return hasMedicalStaff;
    }

    
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public boolean hasVacancy() {
        return isOpen && currentOccupancy < maxCapacity;
    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public ArrayList<Resource> getAvailableResources() {
        ArrayList<Resource> available = new ArrayList<Resource>();
        for (Resource resource : resources) {
            if (resource.getQuantity() > 0) {
                available.add(resource);
            }
        }
        return available;
    }

    /**
     * Returns the number of available spaces left in the shelter
     * @return int value representing spaces left in shelter
     */
    public int getAvailableSpaces() {
        return Math.max(0, maxCapacity - currentOccupancy);
    }

/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public void checkInUser(User user) {
        if(user != null && hasVacancy()) {
            currentOccupancy++;
        }

    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public void updateOperationalStatus() {
        isOpen = !isOpen;
    }
/**
     * Returns the list of resources available at the shelter.
     * @return the resources as an ArrayList of Resource objects
     */
    public String toString() {
        return "\nName: " + getName() + "\nLocation: " + getLocation() + "\nMax Capacity: " + getMaxCapacity() +
        "\nCurrent Occupancy: " + getCurrentOccupancy() + "\nAllows Pets: " + getAllowsPets() + "\nOpen: " + isOpen();

    }

}
