package com.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import com.location.Address;
import com.location.MapTools;

/**
 * Provides the main facade for the Hurricane Relief System.
 * Coordinates users, help requests, shelters, recommendations,
 * and hurricane information.
 */
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

    /**
     * Returns the single shared HurricaneReliefSystem instance.
     * @return the HurricaneReliefSystem instance
     */
    public static HurricaneReliefSystem getInstance() {
        if (hurricaneReliefSystem == null) {
            hurricaneReliefSystem = new HurricaneReliefSystem();
        }
        return hurricaneReliefSystem;
    }

    /**
     * Returns every help request in the system.
     * @return the list of help requests
     */
    public ArrayList<HelpRequest> findRequest() {
        return requestList.getRequests();
    }

    /**
     * Finds help requests at a specified location.
    * @param location the location to search for
    * @return the matching help requests
    */
    public ArrayList<HelpRequest> findRequest(Location location) {
        return requestList.getRequests(location);
    }

    /**
     * Finds help requests matching a location and certification requirement.
     * @param location the location to search for
     * @param requiresCertification whether certification is required
     * @return the matching help requests
     */
    public ArrayList<HelpRequest> findRequest(Location location, boolean requiresCertification) {
        return requestList.getRequests(location, requiresCertification);
    }

    /**
     * Finds help requests matching location, certification, and skill criteria.
     * @param location the location to search for
     * @param requiresCertification whether certification is required
     * @param skill the required volunteer skill
     * @return the matching help requests
     */
    public ArrayList<HelpRequest> findRequest(Location location,
                                               boolean requiresCertification,
                                               Skill skill) {
        return requestList.getRequests(location, requiresCertification, skill);
    }

    /**
     * Finds shelters with a matching name.
     * @param name the shelter name to search for
     * @return the matching shelters
     */
    public ArrayList<Shelter> findShelter(String name) {
        return shelterList.getShelters(name);
    }

    /**
     * Finds shelters matching a name and location.
     * @param name the shelter name to search for
     * @param location the shelter location to search for
     * @return the matching shelters
     */
    public ArrayList<Shelter> findShelter(String name, Location location) {
        return shelterList.getShelters(name, location);
    }

    /**
     * Finds shelters matching name, location, and pet policy.
     * @param name the shelter name to search for
     * @param location the shelter location to search for
     * @param allowsPets whether the shelter must allow pets
     * @return the matching shelters
     */
    public ArrayList<Shelter> findShelter(String name, Location location,
                                          boolean allowsPets) {
        return shelterList.getShelters(name, location, allowsPets);
    }

    /**
     * Finds shelters that meet family, medical-staff, and capacity needs.
     * @param allowsPets whether the shelter must allow pets
     * @param requiresMedical whether medical staff are required
     * @param minCapacity the minimum available capacity needed
     * @return the matching shelters
     */
    public ArrayList<Shelter> findShelter(boolean allowsPets, boolean requiresMedical, int minCapacity) {
        return shelterList.getShelters(allowsPets, requiresMedical, minCapacity);
    }

    /**
     * Finds the closest suitable shelter by driving distance.
     * @param location the starting location
     * @param shelters the shelters to compare
     * @return the closest suitable shelter, or null when none is found
     * @throws IOException if the map service cannot be reached
     * @throws InterruptedException if the map request is interrupted
     */
    public Shelter findShelterByDrivingDistance(Location location, ArrayList<Shelter> shelters) throws IOException, InterruptedException {
            return shelterList.findShelterByDrivingDistance(location, shelters);
    }

    /**
     * Returns the currently logged-in user.
     * @return the current user, or null when no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the shelter currently selected by the system.
     * @return the current shelter
     */
    public Shelter getCurrentShelter() {
        return currentShelter;
    }

    /**
     * Returns the help request currently selected by the system.
     * @return the current help request
     */
    public HelpRequest getCurrentRequest() {
        return currentRequest;
    }

    /**
     * Creates a location from address information.
     * @param address the street address
     * @param city the city
     * @param state the state abbreviation
     * @param zipCode the ZIP code
     * @return the newly created location
     */
    public Location createLocation(String address, String city, String state,
                                   String zipCode) {
        return new Location(address, city, state, zipCode);
    }

    /**
     * Logs a user into the system.
     * @param username the user's username
     * @param password the user's password
     * @return the logged-in user, or null if the credentials are invalid
     */
    public User login(String username, String password) {
        User user = userList.getUser(username, password);
        currentUser = user;
        return user;
    }

    /**
     * Creates a new user account.
     * @param type the account role
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param username the desired username
     * @param password the account password
     * @param email the user's email address
     * @param phoneNumber the user's phone number
     * @param dob the user's date of birth
     * @param location the user's location
     * @return the new user account, or null if it could not be created
     */
    public User createAccount(String type, String firstName, String lastName,
                              String username, String password, String email,
                              String phoneNumber, Date dob, Location location) {
        return userList.createAccount(type, firstName, lastName, username,
                password, email, phoneNumber, dob, location);
    }

    /**
     * Saves user data and clears the current user session.
     */
    public void logout() {
        userList.save();
        currentUser = null;
        currentRequest = null;
    }

    /**
     * Creates and saves a help request.
     * @param author the user creating the request
     * @param description details about the needed assistance
     * @param location the location where help is needed
     * @param requiresCertification whether a certified volunteer is needed
     * @param neededSkills the skills needed for the request
     * @param numberOfPeople the number of people needing assistance
     * @param numberOfPets the number of pets needing assistance
     * @param petInfo information about the pets
     * @param status the initial request status
     * @param urgency the request urgency level
     * @return true if the request was created successfully
     */
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

        if (author.getType().equalsIgnoreCase("Victim")) {
            ((Victim) author).addActiveRequest(request);
        }

        currentRequest = request;
        requestList.save();
        return true;
    }

    /**
    * Cancels a help request when it belongs to the specified user.
    * @param user the user requesting cancellation
    * @param request the request to cancel
    * @return true if the request was cancelled successfully
    */
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

        if (request.getAuthor().getType().equalsIgnoreCase("Victim")) {
            ((Victim) request.getAuthor()).cancelRequest(request);
        }

        if (currentRequest == request) {
            currentRequest = null;
        }

        requestList.save();
        return true;
    }

    /**
     * Adds a shelter when the current user is a system administrator.
     * @param name the shelter name
     * @param location the shelter location
     * @param maxCapacity the maximum shelter capacity
     * @param currentOccupancy the current number of occupants
     * @param allowsPets whether pets are allowed
     * @param isOpen whether the shelter is open
     * @param resources the shelter's available resources
     * @return true if the shelter was added successfully
     */
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

    /**
     * Determines whether a user has system administrator permissions.
     * @param user the user to check
     * @return true if the user is a SystemAdmin
     */
    public boolean verifyAdmin(User user) {
        return user.getType().equalsIgnoreCase("SystemAdmin");
    }

    /**
     * Adds a comment to a help request.
     * @param request the request receiving the comment
     * @param author the user writing the comment
     * @param timestamp the time the comment was created
     * @param text the comment text
     * @return true if the comment was added successfully
     */
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

    /**
     * Returns the current hurricane information.
     * @return the current hurricane data
     */
    public HurricaneData viewHurricaneData() {
        return hurricaneData;
    }

    /**
     * Replaces the current hurricane information.
     * @param hurricaneData the updated hurricane data
     */
    public void updateHurricaneData(HurricaneData hurricaneData) {
        if (hurricaneData != null) {
            this.hurricaneData = hurricaneData;
        }
    }

    /**
     * Returns open requests a volunteer is qualified to accept.
     * @param volunteer the volunteer searching for requests
     * @return the available matching requests
     */
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
    public String formatRecommendedRequest(Volunteer volunteer, HelpRequest request) {
        String assignedTo = "Unassigned";
        if (request.getAssignedVolunteer() != null) {
            assignedTo = request.getAssignedVolunteer().getUsername();
        }
        return "----------------------------------------\n"
                + "Description: " + request.getDescription() + "\n"
                + "Location: " + request.getLocation() + "\n"
                + "Status: " + request.getStatus().label + "\n"
                + "Assigned to: " + assignedTo + "\n"
                + getRecommendationExplanation(volunteer, request) + "\n";
    }

    /**
     * Calculates a score used to rank an open request for a volunteer.
     * @param volunteer the volunteer being matched
     * @param request the request being ranked
     * @return the request recommendation score
     */
    private int getRecommendationScore(Volunteer volunteer, HelpRequest request) {
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

    /**
     * Returns open requests a volunteer is qualified to accept.
     * @param volunteer the volunteer searching for requests
     * @return the available matching requests
     */
    public boolean volunteer(User user, HelpRequest request) {
        if (!(user.getType().equalsIgnoreCase("Volunteer"))) {
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

    /**
     * Adds an emergency contact to the currently logged-in user.
     * @param emergencyContact the emergency contact to add
     * @return true if the contact was added successfully
     */
    public boolean addEmergencyContact(EmergencyContact emergencyContact) {
        return getCurrentUser().getEmergencyContacts().add(emergencyContact);
    }

    /**
     * Advances the status of a request assigned to a volunteer.
     * @param user the volunteer updating the request
     * @param request the assigned request
     * @return true if the status was updated successfully
     */
    public boolean updateRequestStatus(User user, HelpRequest request) {
        if (!(user.getType().equalsIgnoreCase("Volunteer")) || request == null) {
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

    /**
     * Checks a family and optional pet into a shelter.
     * @param shelter the shelter receiving the family
     * @param memberNames the names of family members checking in
     * @param pet the pet information, if applicable
     * @return true if the family was checked in successfully
     */
    public boolean checkInFamily(Shelter shelter, ArrayList<String> memberNames, String pet) {
        if(shelter == null) return false;
        
        return shelter.checkInFamily(memberNames, pet);
    }
    
    /**
     * Marks a request complete when the user has manager or administrator permission.
     * @param user the user attempting to complete the request
     * @param request the request to complete
     * @return true if the request was completed successfully
     */
    public boolean completeRequest(User user, HelpRequest request) {
        if (!(user.getType().equalsIgnoreCase("ShelterManager"))
                && !(user.getType().equalsIgnoreCase("SystemAdmin"))) {
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

        if (assignedVolunteer.getType().equalsIgnoreCase("Volunteer")) {
            ((Volunteer) assignedVolunteer).releaseRequest(request);
        }

        currentRequest = request;
        requestList.save();
        return true;
    }

    /**
     * Saves all user data to JSON.
     */
    public void saveUsers() {
        userList.save();
    }

    /**
     * Saves all shelter data to JSON.
     */
    public void saveShelters() {
        shelterList.save();
    }

    /**
     * Saves all help-request data to JSON.
     */
    public void saveRequests() {
        requestList.save();
    }
}