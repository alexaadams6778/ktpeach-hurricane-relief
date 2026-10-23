package com.model;
import java.io.IOException;
import java.util.ArrayList;

import com.location.Address;
import com.location.MapTools;

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

    /**
     * Returns shelters that meet required criteria
     * @param allowsPets
     * @param requiresMedical
     * @param minAvailableSpaces
     * @return ArrayList of shelters that meet given criteria
     */
    public ArrayList<Shelter> getShelters(boolean allowsPets, boolean requiresMedical, int minAvailableSpaces) {
        ArrayList<Shelter> result = new ArrayList<>();
        for(Shelter shelter : getShelters()) {
            if(!shelter.isOpen()) continue;
            if(allowsPets && !shelter.getAllowsPets()) continue;
            if(requiresMedical && !shelter.hasMedicalStaff()) continue;
            if(!hasSpace(shelter, minAvailableSpaces)) continue;

            result.add(shelter);
        }
        return result;
    }

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

    // if(allowPetsMatch(shelter, allowsPets) && hasMedicalStaffMatch(shelter, requiresMedical) && hasSpace(shelter, minAvailableSpaces))
    /**
     * Checks if shelter meets criteria with pets
     * @param shelter
     * @param allowsPets
     * @return true if both parameters match and false if they dont
     */
    private boolean allowPetsMatch(Shelter shelter, boolean allowsPets) {
        return shelter.getAllowsPets() == allowsPets;
    }

    /**
     * Checks if shelter meets criteria with Medical Staff
     * @param shelter
     * @param requiresMedical
     * @return true if it does and false if not
     */
    private boolean hasMedicalStaffMatch(Shelter shelter, boolean requiresMedical) {
        return shelter.hasMedicalStaff() == requiresMedical;
    }

    /**
     * Checks if shelter has enough space
     * @param shelter
     * @param minAvailableSpaces
     * @return true if it does and false if not
     */
    private boolean hasSpace(Shelter shelter, int minAvailableSpaces) {
        return shelter.getAvailableSpaces() >= minAvailableSpaces;
    }
}
