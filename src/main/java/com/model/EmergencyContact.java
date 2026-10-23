package com.model;

/**
 * @author KTPeach
 * EmergencyContact, contact information for someone to notify in an emergency.
 */

public class EmergencyContact {

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phoneNumber;
    private String relationship;

    /**
     * Constructor for an EmergencyContact object.
     * @param firstName the first name as a String
     * @param lastName the last name as a String
     * @param age the age as an int
     * @param email the email address as a String
     * @param phoneNumber the phone number as a String
     * @param relationship the relationship to the user as a String
     */

    public EmergencyContact(String firstName, String lastName, int age, String email,
                            String phoneNumber, String relationship) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.relationship = relationship;
    }

    /**
     * Returns the first name
     * @return the first name as a String
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name
     * @return the last name as a String
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the age
     * @return the age as an int
     */

    public int getAge() {
        return age;
    }

    /**
     * Returns the email
     * @return the email as a String
     */

    public String getEmail() {
        return email;
    }

    /**
     * Returns the phone number
     * @return the phone number as a String
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the relationship
     * @return the relationship as a String
     */

    public String getRelationship() {
        return relationship;
    }

    /**
     * Returns the name, relationship, and phone number
     * @return the formatted information as a String
     */

    @Override
    public String toString() {
        return "Name: " + getFirstName() + " " + getLastName() + "\n" +
        "Age: " + getAge() + "\n" + "Phone Number: " + getPhoneNumber() +
        "\n" + "Email: " + getEmail();

    }
}