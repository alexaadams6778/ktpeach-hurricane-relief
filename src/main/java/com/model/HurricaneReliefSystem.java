package com.model;
import java.util.ArrayList;
import java.util.Date;

public class HurricaneReliefSystem {
    
    private static HurricaneReliefSystem hurricaneReliefSystem;
    private UserList userList;
    private RequestList requestList;
    private ShelterList shelterList;
    private User currentUser;
    private HelpRequest currentRequest;
    private Shelter currentShelter;
    private HurricaneData currentData;

    
    private HurricaneReliefSystem() {
        userList = UserList.getInstance();
        requestList = RequestList.getInstance();
        shelterList = ShelterList.getInstance();
    }

    public static HurricaneReliefSystem getInstance() {
        if (hurricaneReliefSystem == null) {
            hurricaneReliefSystem = new HurricaneReliefSystem();
        }
        return hurricaneReliefSystem;
    }

    public ArrayList<HelpRequest> findRequest() {
        return null;
    }

    public ArrayList<HelpRequest> findRequest(Location location) {
        return null;
    }

    public ArrayList<HelpRequest> findRequest(Location location, boolean requiresCertification) {
        return null;
    }

    public ArrayList<HelpRequest> findRequest(Location location, boolean requiresCertification, Skill skill) {
        return null;
    }

    public ArrayList<Shelter> findShelter(String name) {
        return null;
    }

    public ArrayList<Shelter> findShelter(String name, Location location) {
        return null;
    }

    public ArrayList<Shelter> findShelter(String name, Location location, boolean allowsPets) {
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Shelter getCurrentShelter() {
        return currentShelter;
    }

    public HelpRequest getCurrentRequest() {
        return currentRequest;
    }

    public User createAccount(String type, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location) {
        if(userList.addUser(type, firstName, lastName, username, password, email, phoneNumber, dob, location)){
            currentUser = userList.getUser(username, password);
            return currentUser;
        }
        return null;
    }

    public User login(String username, String password) {
        User user = userList.getUser(username, password);
        currentUser = user;
        return user;
    }

    public void logout() {
        UserList.getInstance().save();
        currentUser = null;
    }

    public boolean addRequest(User author, String description, Location location, boolean requiresCertification, ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, Status status, Urgency urgency) {
                                return true;
                              }

    public boolean comment(HelpRequest request, User author, String timestamp, String text) {
        return true;
    }

    public HurricaneData viewHurricaneData() {
        return null;
    }

    public boolean volunteer(User user, HelpRequest request) {
        return true;
    }
}
