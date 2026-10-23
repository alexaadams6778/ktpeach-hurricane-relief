package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Victim extends User {

    private ArrayList<HelpRequest> activeRequests;

    public Victim(UUID userID, String type, String firstName, String lastName,
                  String username, String password, String email,
                  String phoneNumber, Date dob, Location location,
                  ArrayList<EmergencyContact> emergencyContacts,
                  ArrayList<String> medicalInfo) {
        super(userID, type, firstName, lastName, username, password, email,
                phoneNumber, dob, location, emergencyContacts, medicalInfo);
        this.activeRequests = new ArrayList<HelpRequest>();
    }

    public ArrayList<HelpRequest> getActiveRequests() {
        return activeRequests;
    }

    public void addActiveRequest(HelpRequest request) {
        if (request != null && !activeRequests.contains(request)) {
            activeRequests.add(request);
        }
    }

    public void cancelRequest(HelpRequest request) {
        if (request != null) {
            activeRequests.remove(request);
            request.setStatus(Status.CANCELLED);
        }
    }

    public void submitRequestForOther(User user, HelpRequest request) {
        addActiveRequest(request);
    }
}