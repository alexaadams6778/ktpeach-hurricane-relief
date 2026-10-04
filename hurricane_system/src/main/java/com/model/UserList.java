package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = new ArrayList<User>();
    }

    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }

        return userList;
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

    public boolean addUser(String firstName, String lastName, String username,
                           String password, String email, String phoneNumber,
                           Date dob, Location location) {
        if (getUser(username) != null) {
            return false;
        }

        User newUser = new Victim(
                UUID.randomUUID(),
                firstName,
                lastName,
                username,
                password,
                email,
                phoneNumber,
                dob,
                location,
                new ArrayList<EmergencyContact>(),
                new ArrayList<String>()
        );

        users.add(newUser);
        return true;
    }

    public boolean save() {
        // DataWriter will save users to JSON
        return true;
    }
}