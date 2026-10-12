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
        if (getUser(user.getUsername()) != null) {
            return false;
        }

        if (user.getType().equals("Victim")) {
            User newUser = new Victim(UUID.randomUUID(), user.getType(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
            user.getEmail(), user.getPhoneNumber(), user.getDob(), user.getLocation(), user.getEmergencyContacts(), user.getMedicalInfo());
            users.add(newUser);
        } else if (user.getType().equals("Volunteer")) {
            User newUser = new Volunteer(UUID.randomUUID(), user.getType(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
            user.getEmail(), user.getPhoneNumber(), user.getDob(), user.getLocation(), user.getEmergencyContacts(), user.getMedicalInfo(), false);
            users.add(newUser);
        } else {
            User newUser = new SystemAdmin(UUID.randomUUID(), user.getType(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
            user.getEmail(), user.getPhoneNumber(), user.getDob(), user.getLocation(), user.getEmergencyContacts(), user.getMedicalInfo());
            users.add(newUser);
        }

        return true;
    }

    public void save() {
        DataWriter.saveUsers();
    }
}