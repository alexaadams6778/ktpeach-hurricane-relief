package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ShelterManager extends User {

    private Shelter assignedShelter;

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

    public Shelter getAssignedShelter() {
        return assignedShelter;
    }

    public void setAssignedShelter(Shelter assignedShelter) {
        this.assignedShelter = assignedShelter;
    }
}