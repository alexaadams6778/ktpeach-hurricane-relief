package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a manager assigned to operate a shelter.
 * A shelter manager can be authorized to complete relief requests.
 */
public class ShelterManager extends User {

    private Shelter assignedShelter;

    /**
     * Creates a shelter manager with profile information and an assigned shelter.
     *
     * @param userID the manager's unique ID
     * @param type the manager's account type
     * @param firstName the manager's first name
     * @param lastName the manager's last name
     * @param username the manager's username
     * @param password the manager's password
     * @param email the manager's email address
     * @param phoneNumber the manager's phone number
     * @param dob the manager's date of birth
     * @param location the manager's location
     * @param emergencyContacts the manager's emergency contacts
     * @param medicalInfo the manager's medical information
     * @param assignedShelter the shelter managed by this user
     */
    public ShelterManager(UUID userID, String type, String firstName,
                          String lastName, String username, String password,
                          String email, String phoneNumber, Date dob,
                          Location location,
                          ArrayList<EmergencyContact> emergencyContacts,
                          ArrayList<String> medicalInfo,
                          Shelter assignedShelter) {
        super(userID, type, firstName, lastName, username, password,
                email, phoneNumber, dob, location, emergencyContacts,
                medicalInfo);

        this.assignedShelter = assignedShelter;
    }

    /**
     * Returns the shelter assigned to this manager.
     *
     * @return the assigned shelter
     */
    public Shelter getAssignedShelter() {
        return assignedShelter;
    }

    /**
     * Updates the shelter assigned to this manager.
     *
     * @param assignedShelter the shelter to assign
     */
    public void setAssignedShelter(Shelter assignedShelter) {
        this.assignedShelter = assignedShelter;
    }
}