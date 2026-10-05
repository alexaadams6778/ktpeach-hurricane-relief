package com.model;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;

public class Volunteer extends User {
    
    private boolean hasVehicle;

    public Volunteer(UUID userID, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location,
                     ArrayList<EmergencyContact> emergencyContacts, ArrayList<String> medicalInfo, boolean hasVehicle) {
                        super(userID, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                        this.hasVehicle = hasVehicle;
                     }

    public void addSkill(Skill skill) {

    }

    public void uploadCertification() {

    }

    public void acceptRequest(HelpRequest request) {

    }

    public void releaseRequest(HelpRequest request) {

    }

    public void flagRequest(HelpRequest request) {

    }

    public void updateRequestStatus(HelpRequest request) {
        
    }

}
