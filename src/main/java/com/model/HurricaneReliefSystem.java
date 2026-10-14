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
        return requestList.getRequests();
    }

    public ArrayList<HelpRequest> findRequest(Location location) {
        return requestList.getRequests(location);
    }

    public ArrayList<HelpRequest> findRequest(Location location, boolean requiresCertification) {
        return requestList.getRequests(location, requiresCertification);
    }

    public ArrayList<HelpRequest> findRequest(Location location, boolean requiresCertification, Skill skill) {
        return requestList.getRequests(location, requiresCertification, skill);
    }

    public ArrayList<Shelter> findShelter(String name) {
        return shelterList.getShelters(name);
    }

    public ArrayList<Shelter> findShelter(String name, Location location) {
        return shelterList.getShelters(name, location);
    }

    public ArrayList<Shelter> findShelter(String name, Location location, boolean allowsPets) {
        return shelterList.getShelters(name, location, allowsPets);
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

    public Location createLocation(String address, String city, String state, String zipCode) {
        Location newLocation = new Location(address, city, state, zipCode);
        return newLocation;
    }

    public User login(String username, String password) {
        User user = userList.getUser(username, password);
        currentUser = user;
        return user;
    }

    public User createAccount(String type, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location) {
        User user = UserList.getInstance().createAccount(type, firstName, lastName, username, password, email, phoneNumber, dob, location);
        return user;
    }

    public void logout() {
        UserList.getInstance().save();
        currentUser = null;
    }

    public boolean addRequest(User author, String description, Location location, boolean requiresCertification, ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, Status status, Urgency urgency) {
        return requestList.addRequest(author, description, location, requiresCertification,
                neededSkills, numberOfPeople, numberOfPets, status, urgency);
    }

    public boolean addShelter(String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        if (!verifyAdmin(currentUser)) {
            return false;
        }
        SystemAdmin admin = (SystemAdmin) currentUser;
        return admin.addShelter(name, location, maxCapacity, currentOccupancy, allowsPets, isOpen, resources);
    }

    public void removeShelters() {
        DataWriter.deleteShelters();
    }

    public boolean verifyAdmin(User user) {
        return user instanceof SystemAdmin;
    }

    public boolean comment(HelpRequest request, User author, String timestamp, String text) {
        if (request == null || author == null || timestamp == null || text == null || text.equals("")) {
            return false;
        }
        request.addComment(new Comment(author, timestamp, text));
        return true;
    }

    public HurricaneData viewHurricaneData() {
        return null;
    }

    public boolean volunteer(User user, HelpRequest request) {
        if (!(user.getType().equalsIgnoreCase("Volunteer")) || request == null || request.getStatus() != Status.OPEN) {
            return false;
        }
        ((Volunteer) user).acceptRequest(request);
        return true;
    }

    public void saveUsers() {
        UserList.getInstance().save();
    }

    public void saveShelters() {
        ShelterList.getInstance().save();
    }

    public void saveRequests() {
        RequestList.getInstance().save();
    }

}
