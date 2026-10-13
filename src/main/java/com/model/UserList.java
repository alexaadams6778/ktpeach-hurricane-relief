package com.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        this.users = DataLoader.loadUsers();
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }

        return userList;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.exists(username, password)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(UUID userID) {
        for(User user : users) {
            if(user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(User user) {
        if (user == null || getUser(user.getUsername()) != null) {
            return false;
        }

        users.add(user);
        return true;
    }

    public User createAccount(String type, String firstName, String lastName,
            String username, String password, String email, String phoneNumber,
            Date dob, Location location) {

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
        } else {
            return null; // invalid account type
        }

        if (userList.addUser(newUser)) {
            return newUser;
        }

        return null; // duplicate username
    }

    public void save() {
        DataWriter.saveUsers();
    }
}