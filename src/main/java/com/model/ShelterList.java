package com.model;

import java.io.IOException;
import java.util.ArrayList;

import com.location.Address;
import com.location.MapTools;

/**
 * @author KTPeach
 * Stores and manages all shelters in the Hurricane Relief System.
 * Uses the singleton pattern so the application shares one shelter list.
 */
public class ShelterList {

    private static ShelterList shelterList;
    private ArrayList<Shelter> shelters;

    /**
     * Creates the shelter list and loads saved shelters from JSON.
     */
    private ShelterList() {
        shelters = DataLoader.loadShelters();
    }

    /**
     * Returns the single shared ShelterList instance.
     *
     * @return the ShelterList instance
     */
    public static ShelterList getInstance() {
        if (shelterList == null) {
            shelterList = new ShelterList();
        }
        return shelterList;
    }

    /**
     * Returns every shelter in the system.
     *
     * @return the list of shelters
     */
    public ArrayList<Shelter> getShelters() {
        return shelters;
    }

    /**
     * Returns shelters whose names contain the provided text.
     *
     * @param name the shelter name to search for
     * @return the matching shelters
     */
    public ArrayList<Shelter> getShelters(String name) {
        ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : shelters) {
            if (matchesName(shelter, name)) {
                result.add(shelter);
            }
        }
        return result;
    }

    /**
     * Returns shelters matching a name and location.
     *
     * @param name the shelter name to search for
     * @param location the shelter location to search for
     * @return the matching shelters
     */
    public ArrayList<Shelter> getShelters(String name, Location location) {
        ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : getShelters(name)) {
            if (matchesName(shelter, name)
                    && matchesLocation(shelter, location)) {
                result.add(shelter);
            }
        }
        return result;
    }

    /**
     * Returns shelters matching name, location, and pet-policy criteria.
     *
     * @param name the shelter name to search for
     * @param location the shelter location to search for
     * @param allowsPets whether the shelter must allow pets
     * @return the matching shelters
     */
    public ArrayList<Shelter> getShelters(String name, Location location,
                                          boolean allowsPets) {
        ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : getShelters(name, location)) {
            if (matchesName(shelter, name)
                    && matchesLocation(shelter, location)
                    && shelter.getAllowsPets() == allowsPets) {
                result.add(shelter);
            }
        }
        return result;
    }

    /**
     * Returns open shelters that meet pet, medical-staff, and capacity requirements.
     *
     * @param allowsPets whether the shelter must allow pets
     * @param requiresMedical whether medical staff are required
     * @param minAvailableSpaces the minimum number of spaces needed
     * @return the shelters meeting all requirements
     */
    public ArrayList<Shelter> getShelters(boolean allowsPets,
                                          boolean requiresMedical,
                                          int minAvailableSpaces) {
        ArrayList<Shelter> result = new ArrayList<Shelter>();
        for (Shelter shelter : getShelters()) {
            if (!shelter.isOpen()) {
                continue;
            }
            if (allowsPets && !shelter.getAllowsPets()) {
                continue;
            }
            if (requiresMedical && !shelter.hasMedicalStaff()) {
                continue;
            }
            if (!hasSpace(shelter, minAvailableSpaces)) {
                continue;
            }

            result.add(shelter);
        }
        return result;
    }

    /**
     * Finds the closest shelter by driving distance from a given location.
     *
     * @param location the starting location
     * @param shelters the shelters to compare
     * @return the closest shelter, or null if no shelters are available
     * @throws IOException if the map service cannot be reached
     * @throws InterruptedException if the map request is interrupted
     */
    public Shelter findShelterByDrivingDistance(
            Location location, ArrayList<Shelter> shelters)
            throws IOException, InterruptedException {

        if (shelters == null || shelters.isEmpty()) {
            return null;
        }

        MapTools mapTools = MapTools.getInstance();

        Address startingAddress = new Address(
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZipCode()
        );

        Shelter closestShelter = null;
        double closestDistance = Double.POSITIVE_INFINITY;

        for (Shelter shelter : shelters) {
            Location shelterLocation = shelter.getLocation();

            Address shelterAddress = new Address(
                    shelterLocation.getAddress(),
                    shelterLocation.getCity(),
                    shelterLocation.getState(),
                    shelterLocation.getZipCode()
            );

            double distance = mapTools.getDrivingDistance(
                    startingAddress, shelterAddress);

            if (distance < closestDistance) {
                closestDistance = distance;
                closestShelter = shelter;
            }
        }

        return closestShelter;
    }

    /**
     * Creates and adds a new shelter.
     *
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the shelter resources
     * @return true if the shelter was added successfully
     */
    public boolean addShelter(String name, Location location, int maxCapacity,
                              int currentOccupancy, boolean allowsPets,
                              boolean isOpen, ArrayList<Resource> resources) {
        return addShelter(new Shelter(name, location, maxCapacity,
                currentOccupancy, allowsPets, isOpen, resources));
    }

    /**
     * Adds an existing shelter when it is valid and its name is unique.
     *
     * @param shelter the shelter to add
     * @return true if the shelter was added successfully
     */
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

    /**
     * Removes a shelter from the list using its unique ID.
     *
     * @param shelter the shelter to remove
     * @return true if the shelter was removed successfully
     */
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

    /**
     * Removes all shelters from the in-memory shelter list.
     */
    public void clearShelters() {
        shelters.clear();
    }

    /**
     * Saves all shelters to the shelters JSON file.
     */
    public void save() {
        DataWriter.saveShelters();
    }

    /**
     * Determines whether a shelter name matches a search term.
     *
     * @param shelter the shelter to evaluate
     * @param name the name search term
     * @return true if the shelter name matches
     */
    private boolean matchesName(Shelter shelter, String name) {
        if (name == null || name.isEmpty()) {
            return true;
        }
        return shelter.getName().toLowerCase().contains(name.toLowerCase());
    }

    /**
     * Determines whether a shelter is at a specified location.
     *
     * @param shelter the shelter to evaluate
     * @param location the location to match
     * @return true if the shelter location matches
     */
    private boolean matchesLocation(Shelter shelter, Location location) {
        if (location == null) {
            return true;
        }
        return shelter.getLocation().equals(location);
    }

    /**
     * Determines whether a shelter's pet policy matches the requested policy.
     *
     * @param shelter the shelter to evaluate
     * @param allowsPets the required pet policy
     * @return true if the pet policy matches
     */
    private boolean allowPetsMatch(Shelter shelter, boolean allowsPets) {
        return shelter.getAllowsPets() == allowsPets;
    }

    /**
     * Determines whether a shelter's medical-staff availability matches a requirement.
     *
     * @param shelter the shelter to evaluate
     * @param requiresMedical whether medical staff are required
     * @return true if the medical-staff requirement matches
     */
    private boolean hasMedicalStaffMatch(Shelter shelter, boolean requiresMedical) {
        return shelter.hasMedicalStaff() == requiresMedical;
    }

    /**
     * Determines whether a shelter has enough available capacity.
     *
     * @param shelter the shelter to evaluate
     * @param minAvailableSpaces the minimum available spaces required
     * @return true if the shelter has enough space
     */
    private boolean hasSpace(Shelter shelter, int minAvailableSpaces) {
        return shelter.getAvailableSpaces() >= minAvailableSpaces;
    }
}