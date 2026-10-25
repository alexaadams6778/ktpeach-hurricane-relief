package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a person or family requesting hurricane-relief assistance.
 * A victim can create, track, and cancel active help requests.
 */
public class Victim extends User {

    private ArrayList<HelpRequest> activeRequests;

    /**
     * Creates a victim account with their profile information.
     *
     * @param userID the victim's unique ID
     * @param type the victim account type
     * @param firstName the victim's first name
     * @param lastName the victim's last name
     * @param username the victim's username
     * @param password the victim's password
     * @param email the victim's email address
     * @param phoneNumber the victim's phone number
     * @param dob the victim's date of birth
     * @param location the victim's location
     * @param emergencyContacts the victim's emergency contacts
     * @param medicalInfo the victim's medical information
     */
    public Victim(UUID userID, String type, String firstName, String lastName,
                  String username, String password, String email,
                  String phoneNumber, Date dob, Location location,
                  ArrayList<EmergencyContact> emergencyContacts,
                  ArrayList<String> medicalInfo) {
        super(userID, type, firstName, lastName, username, password, email,
                phoneNumber, dob, location, emergencyContacts, medicalInfo);
        this.activeRequests = new ArrayList<HelpRequest>();
    }

    /**
     * Returns the help requests currently active for this victim.
     *
     * @return the list of active help requests
     */
    public ArrayList<HelpRequest> getActiveRequests() {
        return activeRequests;
    }

    /**
     * Adds a help request to this victim's active requests when it is valid.
     *
     * @param request the request to add
     */
    public void addActiveRequest(HelpRequest request) {
        if (request != null && !activeRequests.contains(request)) {
            activeRequests.add(request);
        }
    }

    /**
     * Cancels an active request for this victim.
     *
     * @param request the request to cancel
     */
    public void cancelRequest(HelpRequest request) {
        if (request != null) {
            activeRequests.remove(request);
            request.setStatus(Status.CANCELLED);
        }
    }

    /**
     * Adds a request submitted on behalf of another user.
     *
     * @param user the user the request was submitted for
     * @param request the request being submitted
     */
    public void submitRequestForOther(User user, HelpRequest request) {
        addActiveRequest(request);
    }

    /**
     * Returns formatted contact information for the victim.
     *
     * @return the victim's name, date of birth, phone number, and email
     */
    @Override
    public String toString() {
        return "Name: " + getFirstName() + " " + getLastName()
                + "\nAge: " + getDob()
                + "\nPhone: " + getPhoneNumber()
                + "\nEmail: " + getEmail();
    }
}