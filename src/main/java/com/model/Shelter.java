package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a hurricane-relief shelter.
 * Stores capacity, occupancy, pet policy, resources, occupants,
 * and medical-staff availability.
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
     * Creates a shelter using an existing ID, such as when loading saved data.
     *
     * @param shelterID the shelter's unique ID
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the resources available at the shelter
     */
    public Shelter(UUID shelterID, String name, Location location,
                   int maxCapacity, int currentOccupancy, boolean allowsPets,
                   boolean isOpen, ArrayList<Resource> resources) {
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
     * Creates a shelter and automatically generates a unique ID.
     *
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the resources available at the shelter
     */
    public Shelter(String name, Location location, int maxCapacity,
                   int currentOccupancy, boolean allowsPets, boolean isOpen,
                   ArrayList<Resource> resources) {
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

    /**
     * Creates a shelter using saved ID, resource, occupancy, pet, and staffing data.
     *
     * @param shelterID the shelter's unique ID
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the resources available at the shelter
     * @param occupants the current shelter occupants
     * @param pets the pets currently at the shelter
     * @param hasMedicalStaff whether medical staff are available
     */
    public Shelter(UUID shelterID, String name, Location location,
                   int maxCapacity, int currentOccupancy, boolean allowsPets,
                   boolean isOpen, ArrayList<Resource> resources,
                   ArrayList<String> occupants, ArrayList<String> pets,
                   boolean hasMedicalStaff) {
        this.shelterID = shelterID;
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = currentOccupancy;
        this.allowsPets = allowsPets;
        this.isOpen = isOpen;
        this.resources = resources;
        if (occupants != null) {
            this.occupants = occupants;
        } else {
            occupants = new ArrayList<String>();
        }
        if (pets != null) {
            this.pets = pets;
        } else {
            pets = new ArrayList<String>();
        }
        this.hasMedicalStaff = hasMedicalStaff;
    }

    /**
     * Creates a new shelter with occupancy, pet, and staffing information.
     *
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the resources available at the shelter
     * @param occupants the current shelter occupants
     * @param pets the pets currently at the shelter
     * @param hasMedicalStaff whether medical staff are available
     */
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
        this.occupants = (occupants != null) ? occupants : new ArrayList<>();
        this.pets = (pets != null) ? pets : new ArrayList<>();
    }

    /**
     * Returns the unique shelter ID.
     *
     * @return the shelter ID
     */
    public UUID getShelterID() {
        return shelterID;
    }

    /**
     * Returns the shelter name.
     *
     * @return the shelter name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the shelter location.
     *
     * @return the shelter location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the maximum number of people the shelter can hold.
     *
     * @return the maximum capacity
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Returns the current number of people staying at the shelter.
     *
     * @return the current occupancy
     */
    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    /**
     * Determines whether the shelter allows pets.
     *
     * @return true if pets are allowed
     */
    public boolean getAllowsPets() {
        return allowsPets;
    }

    /**
     * Returns the current occupants at the shelter.
     *
     * @return the list of occupant names
     */
    public ArrayList<String> getOccupants() {
        return occupants;
    }

    /**
     * Returns the pets currently staying at the shelter.
     *
     * @return the list of pet information
     */
    public ArrayList<String> getPets() {
        return pets;
    }

    /**
     * Updates the shelter name.
     *
     * @param name the new shelter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the shelter location.
     *
     * @param location the new shelter location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Updates the maximum shelter capacity.
     *
     * @param maxCapacity the new maximum capacity
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Updates the current shelter occupancy.
     *
     * @param currentOccupancy the new current occupancy
     */
    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    /**
     * Updates whether the shelter allows pets.
     *
     * @param allowsPets true if pets are allowed
     */
    public void setAllowsPets(boolean allowsPets) {
        this.allowsPets = allowsPets;
    }

    /**
     * Updates the list of shelter occupants.
     *
     * @param occupants the updated occupant list
     */
    public void setOccupants(ArrayList<String> occupants) {
        this.occupants = occupants;
    }

    /**
     * Updates the list of pets at the shelter.
     *
     * @param pets the updated pet list
     */
    public void setPets(ArrayList<String> pets) {
        this.pets = pets;
    }

    /**
     * Updates whether the shelter has medical staff.
     *
     * @param hasMedicalStaff true if medical staff are available
     */
    public void setHasMedicalStaff(boolean hasMedicalStaff) {
        this.hasMedicalStaff = hasMedicalStaff;
    }

    /**
     * Determines whether the shelter is open.
     *
     * @return true if the shelter is open
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Determines whether medical staff are available at the shelter.
     *
     * @return true if medical staff are available
     */
    public boolean hasMedicalStaff() {
        return hasMedicalStaff;
    }

    /**
     * Determines whether the shelter is open and has at least one available space.
     *
     * @return true if the shelter has vacancy
     */
    public boolean hasVacancy() {
        return isOpen && currentOccupancy < maxCapacity;
    }

    /**
     * Returns resources that currently have a quantity greater than zero.
     *
     * @return the available resources
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
     * Returns the number of unoccupied spaces in the shelter.
     *
     * @return the available spaces
     */
    public int getAvailableSpaces() {
        return Math.max(0, maxCapacity - currentOccupancy);
    }

    /**
     * Checks a user into the shelter when it has vacancy.
     *
     * @param user the user checking in
     */
    public void checkInUser(User user) {
        if (user != null && hasVacancy()) {
            currentOccupancy++;
            String occupantToAdd = user.getFirstName() + " "
                    + user.getLastName();
            occupants.add(occupantToAdd);
        }
    }

    /**
     * Checks a family and optional pet into the shelter when capacity and pet policy allow it.
     *
     * @param memberNames the names of family members checking in
     * @param petNameAndType the pet's name and type, or null when there is no pet
     * @return true if the family was checked in successfully
     */
    public boolean checkInFamily(ArrayList<String> memberNames,
                                 String petNameAndType) {
        if (memberNames.size() > getAvailableSpaces()) {
            return false;
        }
        if (petNameAndType != null && getAllowsPets() == false) {
            return false;
        }

        for (String name : memberNames) {
            occupants.add(name);
        }
        currentOccupancy += memberNames.size();

        boolean hasPet = petNameAndType != null
                && !petNameAndType.trim().isEmpty();

        if (hasPet && !getAllowsPets()) {
            return false;
        }
        if (hasPet) {
            pets.add(petNameAndType);
        }

        return true;
    }

    /**
     * Switches the shelter between open and closed status.
     */
    public void updateOperationalStatus() {
        isOpen = !isOpen;
    }

    /**
     * Returns formatted details about the shelter.
     *
     * @return the shelter information as a string
     */
    @Override
    public String toString() {
        String medicalStaffYN = hasMedicalStaff ? "Yes" : "No";

        return "\nName: " + getName()
                + "\nLocation: " + getLocation()
                + "\nMax Capacity: " + getMaxCapacity()
                + "\nCurrent Occupancy: " + getCurrentOccupancy()
                + "\nAllows Pets: " + getAllowsPets()
                + "\nOpen: " + isOpen()
                + "\nAvailable Spaces: " + getAvailableSpaces()
                + "\nMedical Staff: " + medicalStaffYN
                + "\nPets List: " + getPets()
                + "\nOccupants List: " + getOccupants();
    }
}