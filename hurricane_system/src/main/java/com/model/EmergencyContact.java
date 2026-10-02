package com.model;

public class EmergencyContact {
    
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String relationship;

    public EmergencyContact(String firstName, String lastName, String email, String phoneNumber, String relationship) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.relationship = relationship;
    }

}
