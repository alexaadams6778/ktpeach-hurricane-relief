package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a system administrator in the Hurricane Relief System.
 * A system administrator can approve volunteers, manage shelters,
 * remove inappropriate requests, and update hurricane information.
 */
public class SystemAdmin extends User {

    private ArrayList<Shelter> sheltersManaged;

    /**
     * Creates a system administrator account with profile information.
     *
     * @param userID the administrator's unique ID
     * @param userType the administrator account type
     * @param firstName the administrator's first name
     * @param lastName the administrator's last name
     * @param username the administrator's username
     * @param password the administrator's password
     * @param email the administrator's email address
     * @param phoneNumber the administrator's phone number
     * @param dob the administrator's date of birth
     * @param location the administrator's location
     * @param emergencyContacts the administrator's emergency contacts
     * @param medicalInfo the administrator's medical information
     */
    public SystemAdmin(UUID userID, String userType, String firstName,
                       String lastName, String username, String password,
                       String email, String phoneNumber, Date dob,
                       Location location,
                       ArrayList<EmergencyContact> emergencyContacts,
                       ArrayList<String> medicalInfo) {
        super(userID, userType, firstName, lastName, username, password,
                email, phoneNumber, dob, location, emergencyContacts,
                medicalInfo);
        this.sheltersManaged = new ArrayList<Shelter>();
    }

    /**
     * Approves a volunteer account.
     *
     * @param volunteer the volunteer to approve
     */
    public void approveVolunteer(Volunteer volunteer) {
        if (volunteer != null) {
            volunteer.setApproved(true);
        }
    }

    /**
     * Removes a help request from the system and saves the update.
     *
     * @param request the request to remove
     */
    public void removeRequest(HelpRequest request) {
        RequestList requestList = RequestList.getInstance();

        if (requestList.removeRequest(request)) {
            requestList.save();
        }
    }

    /**
     * Creates and adds a shelter managed by this administrator.
     *
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the shelter resources
     * @return true if the shelter was added successfully
     */
    public boolean addShelter(String name, Location location, int maxCapacity,
                              int currentOccupancy, boolean allowsPets,
                              boolean isOpen, ArrayList<Resource> resources) {
        ShelterList shelterList = ShelterList.getInstance();
        Shelter shelter = new Shelter(name, location, maxCapacity,
                currentOccupancy, allowsPets, isOpen, resources);

        if (shelterList.addShelter(shelter)) {
            sheltersManaged.add(shelter);
            return true;
        }
        return false;
    }

    /**
     * Updates a managed shelter when its ID matches an existing shelter.
     *
     * @param updatedShelter the shelter containing updated information
     */
    public void updateShelterInfo(Shelter updatedShelter) {
        if (updatedShelter == null) {
            return;
        }

        for (Shelter existing : sheltersManaged) {
            if (existing.getShelterID().equals(
                    updatedShelter.getShelterID())) {
                existing.setName(updatedShelter.getName());
                existing.setLocation(updatedShelter.getLocation());
                existing.setMaxCapacity(updatedShelter.getMaxCapacity());
                existing.setCurrentOccupancy(
                        updatedShelter.getCurrentOccupancy());
                existing.setAllowsPets(updatedShelter.getAllowsPets());

                if (existing.isOpen() != updatedShelter.isOpen()) {
                    existing.updateOperationalStatus();
                }
            }
        }
    }

    /**
     * Removes a shelter from this administrator's managed-shelter list.
     *
     * @param shelter the shelter to remove
     */
    public void removeManagedShelter(Shelter shelter) {
        sheltersManaged.remove(shelter);
    }

    /**
     * Updates hurricane data for active help requests and saves the changes.
     *
     * @param hurricaneData the updated hurricane information
     */
    public void updateHurricaneData(HurricaneData hurricaneData) {
        if (hurricaneData != null) {
            HurricaneReliefSystem.getInstance()
                    .updateHurricaneData(hurricaneData);

            for (HelpRequest request : RequestList.getInstance()
                    .getRequests()) {
                if (request.getStatus() != Status.RESOLVED
                        && request.getStatus() != Status.CANCELLED) {
                    request.setHurricaneData(hurricaneData);
                }
            }

            RequestList.getInstance().save();
        }
    }
}