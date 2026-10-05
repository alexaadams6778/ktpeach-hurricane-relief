package com.model;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class Victim extends User {
    
    private ArrayList<HelpRequest> activeRequests;

    public Victim(UUID userID, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location,
                       ArrayList<EmergencyContact> emergencyContacts, ArrayList<String> medicalInfo) {
                        super(userID, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                        this.activeRequests = new ArrayList<HelpRequest>();
                       }

    public void createRequest(User author, String description, Location location, boolean requiresCertification, ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, Status status, Urgency urgency) {

                              }

    public void updateRequest(HelpRequest request) {

    }

    public void cancelRequest(HelpRequest request) {

    }

    public void submitRequestForOther(User user, HelpRequest request) {
        
    }

}
