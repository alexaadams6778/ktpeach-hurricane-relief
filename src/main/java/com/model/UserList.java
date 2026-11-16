package com.model;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Locale;
import java.util.Date;
import java.util.UUID;

/**
 * @author KTPeach
 * Stores and manages every user account in the Hurricane Relief System.
 * Uses the singleton pattern so the application shares one user list.
 */
public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

    /**
     * Creates the user list and loads saved users from JSON.
     */
    private UserList() {
        this.users = DataLoader.loadUsers();
    }

    /**
     * Returns the single shared UserList instance.
     *
     * @return the UserList instance
     */
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }

        return userList;
    }

    /**
     * Returns every user currently stored in the system.
     *
     * @return the list of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return the matching user, or null if no user is found
     */
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Finds a user using their username and password.
     *
     * @param username the username to search for
     * @param password the password to verify
     * @return the matching user, or null if the credentials are invalid
     */
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.exists(username, password)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param userID the unique ID to search for
     * @return the matching user, or null if no user is found
     */
    public User getUser(UUID userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Adds a user when the user and username are valid and unique.
     *
     * @param user the user to add
     * @return true if the user was added successfully
     */
    public boolean addUser(User user) {
        if (user == null || getUser(user.getUsername()) != null) {
            return false;
        }

        users.add(user);
        return true;
    }

    /**
     * Creates and saves a new user account for a supported account type.
     *
     * @param type the account type to create
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the desired username
     * @param password the desired password
     * @param email the user's email address
     * @param phoneNumber the user's phone number
     * @param dob the user's date of birth
     * @param location the user's location
     * @return the created user, or null if the type is unsupported or username exists
     */
    public User createAccount(String type, String firstName, String lastName,
                              String username, String password, String email,
                              String phoneNumber, Date dob, Location location) {

        User newUser;

        if (type.equalsIgnoreCase("Victim")) {
            newUser = new Victim(
                    UUID.randomUUID(), "Victim", firstName, lastName, username,
                    password, email, phoneNumber, dob, location,
                    new ArrayList<EmergencyContact>(),
                    new ArrayList<String>()
            );
        } else if (type.equalsIgnoreCase("Volunteer")) {
            newUser = new Volunteer(
                    UUID.randomUUID(), "Volunteer", firstName, lastName, username,
                    password, email, phoneNumber, dob, location,
                    new ArrayList<EmergencyContact>(),
                    new ArrayList<String>(), false
            );
        } else if (type.equalsIgnoreCase("Admin")) {
            newUser = new SystemAdmin(
                    UUID.randomUUID(), "Admin", firstName, lastName, username,
                    password, email, phoneNumber, dob, location,
                    new ArrayList<EmergencyContact>(),
                    new ArrayList<String>()
            );
        } else if (type.equalsIgnoreCase("ShelterManager")) {
            newUser = new ShelterManager(
                    UUID.randomUUID(), "ShelterManager", firstName, lastName,
                    username, password, email, phoneNumber, dob, location,
                    new ArrayList<EmergencyContact>(),
                    new ArrayList<String>(), null
            );
        } else {
            return null;
        }

        if (userList.addUser(newUser)) {
            save();
            return newUser;
        }

        return null;
    }

    /** Email comparison ignores case; passwords are compared exactly as entered. */
    public synchronized User loginByEmail(String email, String password) throws IOException {
        users = DataLoader.loadUsersChecked();
        if (email == null || password == null || password.isEmpty()) return null;
        String enteredEmail = email.trim();
        for (User user : users) {
            if (user.getEmail().trim().equalsIgnoreCase(enteredEmail)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /** Minimal resident signup; remaining profile details can be completed later. */
    public synchronized User signup(String firstName, String lastName, String email,
                                    String password) throws IOException {
        firstName = firstName == null ? "" : firstName.trim();
        lastName = lastName == null ? "" : lastName.trim();
        email = email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("Please enter your first and last name.");
        }
        if (!email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        if (password == null || password.length() < 8 || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Use at least 8 characters for your password.");
        }

        ArrayList<User> savedUsers = DataLoader.loadUsersChecked();
        for (User user : savedUsers) {
            if (user.getEmail().trim().equalsIgnoreCase(email)
                    || user.getUsername().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("An account already uses this email. Please log in.");
            }
        }
        User user = new Victim(UUID.randomUUID(), "Victim", firstName, lastName,
                email, password, email, "", null, new Location("", "", "", ""),
                new ArrayList<EmergencyContact>(), new ArrayList<String>());
        savedUsers.add(user);
        DataWriter.saveUsersChecked(savedUsers);
        users = savedUsers;
        return user;
    }

    /**
     * Saves all user accounts to the users JSON file.
     */
    public void save() {
        DataWriter.saveUsers();
    }
}