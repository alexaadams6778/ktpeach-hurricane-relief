package com.model;

/**
 * @author KTPeach
 * Represents a person to contact in an emergency.
 * Stores contact information and their relationship to the user.
 */
public class EmergencyContact {

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phoneNumber;
    private String relationship;

    /**
     * Creates an emergency contact.
     *
     * @param firstName the contact's first name
     * @param lastName the contact's last name
     * @param age the contact's age
     * @param email the contact's email address
     * @param phoneNumber the contact's phone number
     * @param relationship the contact's relationship to the user
     */
    public EmergencyContact(String firstName, String lastName, int age,
                            String email, String phoneNumber,
                            String relationship) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.relationship = relationship;
    }

    /**
     * Returns the contact's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the contact's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the contact's age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the contact's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the contact's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the contact's relationship to the user.
     *
     * @return the relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Returns formatted contact information.
     *
     * @return the name, age, phone number, and email of this contact
     */
    @Override
    public String toString() {
        return "Name: " + getFirstName() + " " + getLastName()
                + "\nAge: " + getAge()
                + "\nPhone Number: " + getPhoneNumber()
                + "\nEmail: " + getEmail();
    }
}