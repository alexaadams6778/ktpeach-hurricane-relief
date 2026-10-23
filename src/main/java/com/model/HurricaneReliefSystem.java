package com.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import com.location.Address;
import com.location.MapTools;

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

    public ArrayList<Shelter> findShelter(boolean allowsPets, boolean requiresMedical, int minCapacity) {
        return shelterList.getShelters(allowsPets, requiresMedical, minCapacity);
    }

    public Shelter findShelterByDrivingDistance(
            Location location, ArrayList<Shelter> shelters)
            throws IOException, InterruptedException {

        if (shelters == null || shelters.isEmpty()) {
            return null;
        }

        MapTools mapTools = MapTools.getInstance();

        Address startingAddress = new Address(
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZipCode()
        );

        Shelter closestShelter = null;
        double closestDistance = Double.POSITIVE_INFINITY;

        for (Shelter shelter : shelters) {
            Location shelterLocation = shelter.getLocation();

            Address shelterAddress = new Address(
                    shelterLocation.getAddress(),
                    shelterLocation.getCity(),
                    shelterLocation.getState(),
                    shelterLocation.getZipCode()
            );

            double distance = mapTools.getDrivingDistance(
                    startingAddress, shelterAddress);

            if (distance < closestDistance) {
                closestDistance = distance;
                closestShelter = shelter;
            }
        }

        return closestShelter;
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
                              int numberOfPets, String petInfo, Status status, Urgency urgency) {
        if (author == null || description == null || description.isBlank()
                || location == null || numberOfPeople < 0 || numberOfPets < 0
                || status == null || urgency == null) {
            return false;
        }

        HelpRequest request = new HelpRequest(author, description, location,
                requiresCertification, neededSkills, numberOfPeople,
                numberOfPets, petInfo, status, urgency);

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
                || request.getStatus() == Status.RESOLVED 
                || request.getStatus() == Status.COMPLETE) {
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
        requestList.save();
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
    
    /**
     * Returns all open requests ranked for a volunteer. Requests with matching
    * skills and a nearby location appear first.
    * @param volunteer the volunteer viewing available requests
    * @return the ranked list of open help requests
    */
    public ArrayList<HelpRequest> getRecommendedRequests(Volunteer volunteer) {
        ArrayList<HelpRequest> openRequests = new ArrayList<HelpRequest>();

        if (volunteer == null) {
            return openRequests;
        }

        for (HelpRequest request : requestList.getRequests()) {
            if (request.getStatus() == Status.OPEN) {
                openRequests.add(request);
            }
        }

        openRequests.sort((firstRequest, secondRequest) ->
            Integer.compare(
                    getRecommendationScore(volunteer, secondRequest),
                    getRecommendationScore(volunteer, firstRequest)));

        return openRequests;
    }

    /**
     * Creates an explanation describing why a request is recommended to a
     * volunteer.
     * @param volunteer the volunteer viewing the request
     * @param request the request being evaluated
     * @return a recommendation explanation for the request
     */
    public String getRecommendationExplanation(Volunteer volunteer,
                                            HelpRequest request) {
        if (volunteer == null || request == null) {
            return "No recommendation is available.";
        }

        boolean skillMatch = volunteer.getSkills()
                .containsAll(request.getNeededSkills());
        boolean nearby = isNearby(volunteer.getLocation(), request.getLocation());

        if (skillMatch && nearby) {
            return "Recommended: matches your specialty skills and is located "
                    + "nearby in " + request.getLocation().getCity() + ".";
        }

        if (skillMatch) {
            return "Recommended: matches your specialty skills.";
        }

        if (nearby) {
            return "Recommended: located nearby in "
                    + request.getLocation().getCity() + ".";
        }

        return "Open request: does not currently match your specialty "
                + "or nearby location.";
    }

    /**
     * Formats a request with its recommendation explanation for display.
     * @param volunteer the volunteer viewing the request
     * @param request the request being displayed
     * @return formatted request information and its recommendation explanation
     */
    public String formatRecommendedRequest(Volunteer volunteer,
                                        HelpRequest request) {
        return request.toString() + System.lineSeparator()
                + getRecommendationExplanation(volunteer, request);
    }

    /**
     * Calculates a score used to rank an open request for a volunteer.
     * @param volunteer the volunteer being matched
     * @param request the request being ranked
     * @return the request recommendation score
     */
    private int getRecommendationScore(Volunteer volunteer,
                                    HelpRequest request) {
        int score = 0;

        if (volunteer.getSkills().containsAll(request.getNeededSkills())) {
            score += 10;
        }

        if (isNearby(volunteer.getLocation(), request.getLocation())) {
            score += 5;
        }

        return score;
    }

    /**
     * Determines whether two locations are in the same city and state.
     * @param firstLocation the first location to compare
     * @param secondLocation the second location to compare
     * @return true when both locations are in the same city and state
     */
    private boolean isNearby(Location firstLocation, Location secondLocation) {
        if (firstLocation == null || secondLocation == null) {
            return false;
        }

        return firstLocation.getCity().equalsIgnoreCase(
                secondLocation.getCity())
                && firstLocation.getState().equalsIgnoreCase(
                secondLocation.getState());
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

    public boolean updateRequestStatus(User user, HelpRequest request) {
        if (!(user instanceof Volunteer) || request == null) {
            return false;
        }

        Volunteer volunteer = (Volunteer) user;

        if (!volunteer.getAssignedRequests().contains(request)
                || (request.getStatus() != Status.ASSIGNED
                && request.getStatus() != Status.CLAIMED)) {
            return false;
        }

        volunteer.updateRequestStatus(request);
        currentRequest = request;
        requestList.save();
        return true;
    }

    public boolean checkInFamily(Shelter shelter, ArrayList<String> memberNames, String pet) {
        if(shelter == null) return false;
        
        return shelter.checkInFamily(memberNames, pet);
    }
    
    public boolean completeRequest(User user, HelpRequest request) {
        if (!(user instanceof ShelterManager)
                && !(user instanceof SystemAdmin)) {
            System.out.println(
                    "Only a shelter manager can mark a relief request as complete.");
            return false;
        }

        if (request == null
                || !requestList.getRequests().contains(request)
                || (request.getStatus() != Status.ASSIGNED
                && request.getStatus() != Status.CLAIMED
                && request.getStatus() != Status.IN_PROGRESS)) {
            return false;
        }

        request.setStatus(Status.COMPLETE);

        User assignedVolunteer = request.getAssignedVolunteer();

        if (assignedVolunteer instanceof Volunteer) {
            ((Volunteer) assignedVolunteer).releaseRequest(request);
        }

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