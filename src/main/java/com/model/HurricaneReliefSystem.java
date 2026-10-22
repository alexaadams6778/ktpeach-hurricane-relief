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
    private HurricaneData hurricaneData;

    private HurricaneReliefSystem() {
        userList = UserList.getInstance();
        requestList = RequestList.getInstance();
        shelterList = ShelterList.getInstance();
        hurricaneData = new HurricaneData(85, 1);
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

    public ArrayList<HelpRequest> findRequest(Location location,
                                               boolean requiresCertification,
                                               Skill skill) {
        return requestList.getRequests(location, requiresCertification, skill);
    }

    public ArrayList<Shelter> findShelter(String name) {
        return shelterList.getShelters(name);
    }

    public ArrayList<Shelter> findShelter(String name, Location location) {
        return shelterList.getShelters(name, location);
    }

    public ArrayList<Shelter> findShelter(String name, Location location,
                                          boolean allowsPets) {
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

    public Location createLocation(String address, String city, String state,
                                   String zipCode) {
        return new Location(address, city, state, zipCode);
    }

    public User login(String username, String password) {
        User user = userList.getUser(username, password);
        currentUser = user;
        return user;
    }

    public User createAccount(String type, String firstName, String lastName,
                              String username, String password, String email,
                              String phoneNumber, Date dob, Location location) {
        return userList.createAccount(type, firstName, lastName, username,
                password, email, phoneNumber, dob, location);
    }

    public void logout() {
        userList.save();
        currentUser = null;
        currentRequest = null;
    }

    public boolean addRequest(User author, String description, Location location,
                              boolean requiresCertification,
                              ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, Status status, Urgency urgency) {
        if (author == null || description == null || description.isBlank()
                || location == null || numberOfPeople < 0 || numberOfPets < 0
                || status == null || urgency == null) {
            return false;
        }

        HelpRequest request = new HelpRequest(author, description, location,
                requiresCertification, neededSkills, numberOfPeople,
                numberOfPets, status, urgency);

        if (!requestList.addRequest(request)) {
            return false;
        }

        if (author instanceof Victim) {
            ((Victim) author).addActiveRequest(request);
        }

        currentRequest = request;
        requestList.save();
        return true;
    }

    public boolean cancelRequest(User user, HelpRequest request) {
        if (user == null || request == null || request.getAuthor() == null
                || !requestList.getRequests().contains(request)
                || !request.getAuthor().getUserID().equals(user.getUserID())
                || request.getStatus() == Status.CANCELLED
                || request.getStatus() == Status.RESOLVED) {
            return false;
        }

        request.setStatus(Status.CANCELLED);

        if (request.getAuthor() instanceof Victim) {
            ((Victim) request.getAuthor()).cancelRequest(request);
        }

        if (currentRequest == request) {
            currentRequest = null;
        }

        requestList.save();
        return true;
    }

    public boolean addShelter(String name, Location location, int maxCapacity,
                              int currentOccupancy, boolean allowsPets,
                              boolean isOpen, ArrayList<Resource> resources) {
        if (!verifyAdmin(currentUser)) {
            return false;
        }

        SystemAdmin admin = (SystemAdmin) currentUser;
        return admin.addShelter(name, location, maxCapacity,
                currentOccupancy, allowsPets, isOpen, resources);
    }

    public boolean verifyAdmin(User user) {
        return user instanceof SystemAdmin;
    }

    public boolean comment(HelpRequest request, User author, String timestamp,
                           String text) {
        if (request == null || author == null || timestamp == null
                || text == null || text.isBlank()) {
            return false;
        }

        request.addComment(new Comment(author, timestamp, text));
        return true;
    }

    public HurricaneData viewHurricaneData() {
        return hurricaneData;
    }

    public void updateHurricaneData(HurricaneData hurricaneData) {
        if (hurricaneData != null) {
            this.hurricaneData = hurricaneData;
        }
    }

    public ArrayList<HelpRequest> findAvailableRequests(Volunteer volunteer) {
        ArrayList<HelpRequest> matches = new ArrayList<HelpRequest>();

        if (volunteer == null) {
            return matches;
        }

        for (HelpRequest request : requestList.getRequests()) {
            if (volunteer.canAcceptRequest(request)) {
                matches.add(request);
            }
        }

        return matches;
    }
    
    public boolean volunteer(User user, HelpRequest request) {
        if (!(user instanceof Volunteer)) {
            return false;
        }

        Volunteer volunteer = (Volunteer) user;

        if (!volunteer.canAcceptRequest(request)) {
            return false;
        }

        volunteer.acceptRequest(request);
        currentRequest = request;
        requestList.save();
        return true;
    }

    public void saveUsers() {
        userList.save();
    }

    public void saveShelters() {
        shelterList.save();
    }

    public void saveRequests() {
        requestList.save();
    }
}