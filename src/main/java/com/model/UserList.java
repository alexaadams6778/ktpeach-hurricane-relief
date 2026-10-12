package com.model;

import java.util.ArrayList;
import java.util.UUID;

// Potentially create a createaccount method to replace long HurricaneReliefSystem long method. 
// Simplify HurricaneReliefSystem to then call this new method
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

    public void save() {
        DataWriter.saveUsers();
    }
}