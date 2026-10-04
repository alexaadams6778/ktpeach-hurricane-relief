package com.model;
import java.util.UUID;
import java.util.Date;
import java.util.ArrayList;

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
    
    public boolean verifyAccount() {
        return true;
    }

    public void updateProfile() {

    }

    public void changeUsername(String username) {
    
    }

    public void changePassword(String password) {

    }

    public void requestAccountDeletion() {

    }

    public boolean exists(String username, String password) {
        return true;
    }

}
