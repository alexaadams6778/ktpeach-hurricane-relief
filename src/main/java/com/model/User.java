package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class User {

    private UUID userID;
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

    public User(UUID userID, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location, 
                ArrayList<EmergencyContact> emergencyContacts, ArrayList<String> medicalInfo) {
                    this.userID = userID;
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

    public User(String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location, 
                ArrayList<EmergencyContact> emergencyContacts, ArrayList<String> medicalInfo) {
                    this.userID = UUID.randomUUID();
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

    public UUID getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public ArrayList<String> getMedicalInfo() {
        return medicalInfo;
    }

    public boolean verifyAccount() {
        return true;
    }

    public void updateProfile() {
        
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void requestAccountDeletion() {
        
    }

    public boolean exists(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + username + ")";
    }
}