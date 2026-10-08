package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Volunteer extends User {

    private ArrayList<Skill> skills;
    private ArrayList<String> certifications;
    private boolean isAvailable;
    private ArrayList<HelpRequest> assignedRequests;
    private double hours;
    private boolean onCall;
    private boolean hasVehicle;

    public Volunteer(UUID userID, String firstName, String lastName, String username,
                     String password, String email, String phoneNumber, Date dob,
                     Location location,
                     ArrayList<EmergencyContact> emergencyContacts,
                     ArrayList<String> medicalInfo, boolean hasVehicle) {
        super(userID, firstName, lastName, username, password, email, phoneNumber,
                dob, location, emergencyContacts, medicalInfo);

        this.skills = new ArrayList<Skill>();
        this.certifications = new ArrayList<String>();
        this.isAvailable = true;
        this.assignedRequests = new ArrayList<HelpRequest>();
        this.hours = 0;
        this.onCall = false;
        this.hasVehicle = hasVehicle;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<HelpRequest> getAssignedRequests() {
        return assignedRequests;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean hasVehicle() {
        return hasVehicle;
    }

    public void addSkill(Skill skill) {
        if (skill != null && !skills.contains(skill)) {
            skills.add(skill);
        }
    }

    public void uploadCertification() {
        // A later UI flow will provide the certification information.
    }

    public void acceptRequest(HelpRequest request) {
        if (request != null && !assignedRequests.contains(request)) {
            assignedRequests.add(request);
            request.setStatus(Status.CLAIMED);
            isAvailable = false;
        }
    }

    public void releaseRequest(HelpRequest request) {
        assignedRequests.remove(request);

        if (assignedRequests.isEmpty()) {
            isAvailable = true;
        }
    }

    public void flagRequest(HelpRequest request) {
        // A reason/status for the flag will be added with HelpRequest.
    }

    public void updateRequestStatus(HelpRequest request) {
        if (request != null) {
            request.updateStatus();
        }
    }
}