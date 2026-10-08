package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Victim extends User {

    private ArrayList<HelpRequest> activeRequests;

    public Victim(UUID userID, String firstName, String lastName, String username,
                  String password, String email, String phoneNumber, Date dob,
                  Location location, ArrayList<EmergencyContact> emergencyContacts,
                  ArrayList<String> medicalInfo) {
        super(userID, firstName, lastName, username, password, email, phoneNumber,
                dob, location, emergencyContacts, medicalInfo);
        this.activeRequests = new ArrayList<HelpRequest>();
    }

    public ArrayList<HelpRequest> getActiveRequests() {
        return activeRequests;
    }

    public void createRequest(User author, String description, Location location,
                              boolean requiresCertification,
                              ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, Status status, Urgency urgency) {
        HelpRequest request = new HelpRequest(
                author,
                description,
                location,
                requiresCertification,
                neededSkills,
                numberOfPeople,
                numberOfPets,
                status,
                urgency
        );

        activeRequests.add(request);
    }

    public void updateRequest(HelpRequest request) {
        if (request != null && !activeRequests.contains(request)) {
            activeRequests.add(request);
        }
    }

    public void cancelRequest(HelpRequest request) {
        activeRequests.remove(request);
        request.setStatus(Status.CANCELLED);
    }

    public void submitRequestForOther(User user, HelpRequest request) {
        if (request != null && !activeRequests.contains(request)) {
            activeRequests.add(request);
        }
    }
}