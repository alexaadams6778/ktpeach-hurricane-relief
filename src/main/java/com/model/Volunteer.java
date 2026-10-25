package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a volunteer who can be matched with and assigned to help requests.
 * A volunteer can store skills, certifications, vehicle information, and assigned requests.
 */
public class Volunteer extends User {

    private ArrayList<Skill> skills;
    private ArrayList<String> certifications;
    private boolean isAvailable;
    private ArrayList<HelpRequest> assignedRequests;
    private boolean hasVehicle;
    private String vehicleDescription;
    private boolean approved;

    /**
     * Creates a volunteer account with its profile and availability information.
     *
     * @param userID the volunteer's unique ID
     * @param userType the volunteer account type
     * @param firstName the volunteer's first name
     * @param lastName the volunteer's last name
     * @param username the volunteer's username
     * @param password the volunteer's password
     * @param email the volunteer's email address
     * @param phoneNumber the volunteer's phone number
     * @param dob the volunteer's date of birth
     * @param location the volunteer's location
     * @param emergencyContacts the volunteer's emergency contacts
     * @param medicalInfo the volunteer's medical information
     * @param hasVehicle whether the volunteer has a vehicle
     */
    public Volunteer(UUID userID, String type, String firstName,
                     String lastName, String username, String password,
                     String email, String phoneNumber, Date dob,
                     Location location,
                     ArrayList<EmergencyContact> emergencyContacts,
                     ArrayList<String> medicalInfo, boolean hasVehicle) {
        super(userID, type, firstName, lastName, username, password,
                email, phoneNumber, dob, location, emergencyContacts,
                medicalInfo);

        this.skills = new ArrayList<Skill>();
        this.certifications = new ArrayList<String>();
        this.isAvailable = true;
        this.assignedRequests = new ArrayList<HelpRequest>();
        this.hasVehicle = hasVehicle;
        this.vehicleDescription = "";
        this.approved = false;
    }

    /**
     * Returns the volunteer's skills.
     *
     * @return the list of volunteer skills
     */
    public ArrayList<Skill> getSkills() {
        return skills;
    }

    /**
     * Returns the requests currently assigned to the volunteer.
     *
     * @return the list of assigned help requests
     */
    public ArrayList<HelpRequest> getAssignedRequests() {
        return assignedRequests;
    }

    /**
     * Determines whether the volunteer is available for a new request.
     *
     * @return true if the volunteer is available
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Determines whether the volunteer has a vehicle.
     *
     * @return true if the volunteer has a vehicle
     */
    public boolean hasVehicle() {
        return hasVehicle;
    }

    /**
     * Returns the volunteer's vehicle description.
     *
     * @return the vehicle description
     */
    public String getVehicleDescription() {
        return vehicleDescription;
    }

    /**
     * Updates the volunteer's vehicle description.
     *
     * @param vehicleDescription the new vehicle description
     */
    public void setVehicleDescription(String vehicleDescription) {
        if (vehicleDescription != null) {
            this.vehicleDescription = vehicleDescription;
        }
    }

    /**
     * Determines whether the volunteer has been approved.
     *
     * @return true if the volunteer is approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Updates the volunteer approval status.
     *
     * @param approved the new approval status
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Determines whether the volunteer has a specified skill.
     *
     * @param skill the skill to search for
     * @return true if the volunteer has the skill
     */
    public boolean hasSkill(Skill skill) {
        return skill != null && skills.contains(skill);
    }

    /**
     * Adds a skill to the volunteer profile if it is valid and not already present.
     *
     * @param skill the skill to add
     */
    public void addSkill(Skill skill) {
        if (skill != null && !skills.contains(skill)) {
            skills.add(skill);
        }
    }

    /**
     * Adds a certification to the volunteer profile if it is valid and unique.
     *
     * @param certification the certification to add
     */
    public void addCertification(String certification) {
        if (certification != null && !certification.isBlank()
                && !certifications.contains(certification)) {
            certifications.add(certification);
        }
    }

    /**
     * Determines whether this volunteer can accept a help request.
     *
     * @param request the request to evaluate
     * @return true if the volunteer is available and has the required qualifications
     */
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

    /**
     * Assigns an eligible help request to this volunteer.
     *
     * @param request the help request to accept
     */
    public void acceptRequest(HelpRequest request) {
        if (request != null && !assignedRequests.contains(request)) {
            assignedRequests.add(request);
            request.setAssignedVolunteer(this);
            request.setStatus(Status.ASSIGNED);
            isAvailable = false;
        }
    }

    /**
     * Removes a request from the volunteer's assignments.
     *
     * @param request the request to release
     */
    public void releaseRequest(HelpRequest request) {
        assignedRequests.remove(request);

        if (assignedRequests.isEmpty()) {
            isAvailable = true;
        }
    }

    /**
     * Advances the status of an assigned request when it is in progress.
     *
     * @param request the assigned request to update
     */
    public void updateRequestStatus(HelpRequest request) {
        if (request != null && assignedRequests.contains(request)
                && (request.getStatus() == Status.ASSIGNED)) {
            request.updateStatus();
        }
    }

    /**
     * Returns a copy of the volunteer's certifications.
     *
     * @return a copy of the certification list
     */
    public ArrayList<String> getCertifications() {
        return new ArrayList<String>(certifications);
    }

    /**
     * Restores a saved active request to the volunteer's assigned-request list.
     *
     * @param request the request to restore
     */
    public void restoreAssignment(HelpRequest request) {
        if (request != null && request.isActiveAssignment()
                && !assignedRequests.contains(request)) {
            assignedRequests.add(request);
        }
        isAvailable = assignedRequests.isEmpty();
    }
}