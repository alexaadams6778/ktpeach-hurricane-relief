package com.model;
import java.util.Date;
import java.util.ArrayList;

public class UserList {
    
    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = new ArrayList<User>();
    }

    public static UserList getInstance() {
        if (userList == null) {
            return new UserList();
        }
        return userList;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(String username) {
        return null;
    }

    public User getUser(String username, String password) {
        return null;
    }

    public boolean addUser(String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location) {
        return true;
    }

    public boolean save() {
        return true;
    }

}
