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
    private String vehicleDescription;

    public Volunteer(UUID userID, String userType, String firstName, String lastName, String username,
                     String password, String email, String phoneNumber, Date dob,
                     Location location,
                     ArrayList<EmergencyContact> emergencyContacts,
                     ArrayList<String> medicalInfo, boolean hasVehicle) {
        super(userID, userType, firstName, lastName, username, password, email, phoneNumber,
                dob, location, emergencyContacts, medicalInfo);

        this.skills = new ArrayList<Skill>();
        this.certifications = new ArrayList<String>();
        this.isAvailable = true;
        this.assignedRequests = new ArrayList<HelpRequest>();
        this.hours = 0;
        this.onCall = false;
        this.hasVehicle = hasVehicle;
        this.vehicleDescription = "";
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

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        if (vehicleDescription != null) {
            this.vehicleDescription = vehicleDescription;
        }
    }

    public boolean hasSkill(Skill skill) {
        return skill != null && skills.contains(skill);
    }

    public void addSkill(Skill skill) {
        if (skill != null && !skills.contains(skill)) {
            skills.add(skill);
        }
    }

    public void addCertification(String certification) {
        if (certification != null && !certification.isBlank()
                && !certifications.contains(certification)) {
            certifications.add(certification);
        }
    }

    public boolean canAcceptRequest(HelpRequest request) {
        if (request == null || request.getStatus() != Status.OPEN
                || !isAvailable) {
            return false;
        }

        if (request.requiresCertification() && certifications.isEmpty()) {
            return false;
        }

        return skills.containsAll(request.getNeededSkills());
    }

    public void uploadCertification() {
        // A later UI flow will provide the certification information.
    }

    public void acceptRequest(HelpRequest request) {
        if (request != null && !assignedRequests.contains(request)) {
            assignedRequests.add(request);
            request.setAssignedVolunteer(this);
            request.setStatus(Status.ASSIGNED);
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
        if (request != null && assignedRequests.contains(request)
                && (request.getStatus() == Status.ASSIGNED
                || request.getStatus() == Status.CLAIMED)) {
            request.updateStatus();
        }
    }
}