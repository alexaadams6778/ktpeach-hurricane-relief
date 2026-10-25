package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a general user of the Hurricane Relief System.
 * Stores account, contact, location, emergency-contact, and medical information.
 */
public abstract class User {

    private UUID userID;
    private String type;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Date dob;
    private Location location;
    private ArrayList<EmergencyContact> emergencyContacts;
    private ArrayList<String> medicalInfo;

    /**
     * Creates a user using an existing unique ID, such as when loading saved data.
     *
     * @param userID the user's unique identifier
     * @param type the user's account type
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the user's login username
     * @param password the user's login password
     * @param email the user's email address
     * @param phoneNumber the user's phone number
     * @param dob the user's date of birth
     * @param location the user's location
     * @param emergencyContacts the user's emergency contacts
     * @param medicalInfo the user's medical information
     */
    public User(UUID userID, String type, String firstName, String lastName,
                String username, String password, String email,
                String phoneNumber, Date dob, Location location,
                ArrayList<EmergencyContact> emergencyContacts,
                ArrayList<String> medicalInfo) {
        this.userID = userID;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.location = location;
        this.emergencyContacts = emergencyContacts;
        this.medicalInfo = medicalInfo;
    }

    /**
     * Creates a new user and automatically generates a unique ID.
     *
     * @param type the user's account type
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the user's login username
     * @param password the user's login password
     * @param email the user's email address
     * @param phoneNumber the user's phone number
     * @param dob the user's date of birth
     * @param location the user's location
     * @param emergencyContacts the user's emergency contacts
     * @param medicalInfo the user's medical information
     */
    public User(String type, String firstName, String lastName,
                String username, String password, String email,
                String phoneNumber, Date dob, Location location,
                ArrayList<EmergencyContact> emergencyContacts,
                ArrayList<String> medicalInfo) {
        this.userID = UUID.randomUUID();
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.location = location;
        this.emergencyContacts = emergencyContacts;
        this.medicalInfo = medicalInfo;
    }

    /**
     * Returns the user's unique ID.
     *
     * @return the user's unique ID
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * Returns the user's account type.
     *
     * @return the user type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the user's password.
     *
     * @return the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the user's date of birth.
     *
     * @return the date of birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Returns the user's location.
     *
     * @return the user's location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the user's emergency contacts.
     *
     * @return the list of emergency contacts
     */
    public ArrayList<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    /**
     * Returns the user's medical information.
     *
     * @return the list of medical information entries
     */
    public ArrayList<String> getMedicalInfo() {
        return medicalInfo;
    }

    /**
     * Changes the user's username.
     *
     * @param username the new username
     */
    public void changeUsername(String username) {
        this.username = username;
    }

    /**
     * Changes the user's password.
     *
     * @param password the new password
     */
    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * Checks whether the provided login credentials match this user.
     *
     * @param username the username to verify
     * @param password the password to verify
     * @return true if both credentials match this user
     */
    public boolean exists(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Returns a readable representation of the user.
     *
     * @return the user's name and username
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + username + ")";
    }
}