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

    }

    public static HurricaneReliefSystem getInstance() {
        if (hurricaneReliefSystem == null) {
            return new HurricaneReliefSystem();
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

    public ArrayList<Shelter> findRequest(String name, Location location, boolean allowsPets) {
        return null;
    }

    public User createAccount(String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location) {
        return null;
    }

    public User login(String username, String password) {
        return null;
    }

    public void logout() {

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
