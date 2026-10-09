package com.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        //Waiting on DataLoader loadUsers()
        users = new ArrayList<User>();
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

    public boolean addUser(String firstName, String lastName, String username,
                           String password, String email, String phoneNumber,
                           Date dob, Location location) {
        if (getUser(username) != null) {
            return false;
        }

        // format so it's not only victim

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

    public void save() {
        DataWriter.saveUsers();
    }
}