package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author KTPeach
 * Represents a request for hurricane-relief assistance.
 * Stores the request's needs, location, status, assigned volunteer,
 * comments, and selected shelter.
 */
public class HelpRequest {

    private UUID requestID;
    private User author;
    private String description;
    private Location location;
    private boolean requiresCertification;
    private ArrayList<Skill> neededSkills;
    private int numberOfPeople;
    private int numberOfPets;
    private String petInfo;
    private Status status;
    private Urgency urgency;
    private HurricaneData hurricaneData;
    private ArrayList<Comment> commentThread;
    private String guestPhoneNumber;
    private User assignedVolunteer;
    private Shelter selectedShelter;
    private boolean requiresMedicalStaff;

    /**
     * Creates a request using an existing ID, such as when loading saved data.
     *
     * @param requestID the unique request ID
     * @param author the user who created the request
     * @param description the description of needed assistance
     * @param location the location where help is needed
     * @param requiresCertification whether a certified volunteer is needed
     * @param neededSkills the skills needed for the request
     * @param numberOfPeople the number of people needing help
     * @param numberOfPets the number of pets needing help
     * @param petInfo information about the pets
     * @param status the request status
     * @param urgency the urgency level
     */
    public HelpRequest(UUID requestID, User author, String description,
                       Location location, boolean requiresCertification,
                       ArrayList<Skill> neededSkills, int numberOfPeople,
                       int numberOfPets, String petInfo, Status status,
                       Urgency urgency) {
        this.requestID = requestID;
        this.author = author;
        this.description = description;
        this.location = location;
        this.requiresCertification = requiresCertification;
        this.neededSkills = neededSkills == null
                ? new ArrayList<Skill>()
                : new ArrayList<Skill>(neededSkills);
        this.numberOfPeople = numberOfPeople;
        this.numberOfPets = numberOfPets;
        this.petInfo = petInfo;
        this.status = status == null ? Status.OPEN : status;
        this.urgency = urgency;
        this.hurricaneData = new HurricaneData(85, 1);
        this.commentThread = new ArrayList<Comment>();
        this.guestPhoneNumber = null;
        this.assignedVolunteer = null;
    }

    /**
     * Creates a new request and automatically generates a unique request ID.
     *
     * @param author the user who created the request
     * @param description the description of needed assistance
     * @param location the location where help is needed
     * @param requiresCertification whether a certified volunteer is needed
     * @param neededSkills the skills needed for the request
     * @param numberOfPeople the number of people needing help
     * @param numberOfPets the number of pets needing help
     * @param petInfo information about the pets
     * @param status the initial request status
     * @param urgency the urgency level
     */
    public HelpRequest(User author, String description, Location location,
                       boolean requiresCertification,
                       ArrayList<Skill> neededSkills, int numberOfPeople,
                       int numberOfPets, String petInfo, Status status,
                       Urgency urgency) {
        this(UUID.randomUUID(), author, description, location,
                requiresCertification, neededSkills, numberOfPeople,
                numberOfPets, petInfo, status, urgency);
    }

    /**
     * Returns the unique request ID.
     *
     * @return the request ID
     */
    public UUID getRequestID() {
        return requestID;
    }

    /**
     * Returns the user who created the request.
     *
     * @return the request author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Returns the volunteer assigned to handle this request.
     *
     * @return the assigned volunteer, or null when unassigned
     */
    public User getAssignedVolunteer() {
        return assignedVolunteer;
    }

    /**
     * Assigns a volunteer to handle this request.
     *
     * @param assignedVolunteer the volunteer to assign, or null to unassign
     */
    public void setAssignedVolunteer(User assignedVolunteer) {
        if (assignedVolunteer == null || assignedVolunteer instanceof Volunteer) {
            this.assignedVolunteer = assignedVolunteer;
        }
    }

    /**
     * Returns the request description.
     *
     * @return the request description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the location where help is needed.
     *
     * @return the request location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Determines whether the request requires a certified volunteer.
     *
     * @return true if certification is required
     */
    public boolean requiresCertification() {
        return requiresCertification;
    }

    /**
     * Returns the skills needed for this request.
     *
     * @return the list of needed skills
     */
    public ArrayList<Skill> getNeededSkills() {
        return neededSkills;
    }

    /**
     * Returns the number of people needing assistance.
     *
     * @return the number of people
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Returns the number of pets needing assistance.
     *
     * @return the number of pets
     */
    public int getNumberOfPets() {
        return numberOfPets;
    }

    /**
     * Returns the pet information for this request.
     *
     * @return the pet information
     */
    public String getPetInfo() {
        return petInfo;
    }

    /**
     * Returns the current request status.
     *
     * @return the request status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the urgency level of the request.
     *
     * @return the request urgency
     */
    public Urgency getUrgency() {
        return urgency;
    }

    /**
     * Returns the hurricane data linked to this request.
     *
     * @return the hurricane data
     */
    public HurricaneData getHurricaneData() {
        return hurricaneData;
    }

    /**
     * Returns the comments on this request.
     *
     * @return the request comment thread
     */
    public ArrayList<Comment> getCommentThread() {
        return commentThread;
    }

    /**
     * Returns the phone number for the guest who created this request.
     *
     * @return the guest phone number, or null for account-based requests
     */
    public String getGuestPhoneNumber() {
        return guestPhoneNumber;
    }

    /**
     * Adds a comment to this request when the comment is valid.
     *
     * @param comment the comment to add
     */
    public void addComment(Comment comment) {
        if (comment != null) {
            commentThread.add(comment);
        }
    }

    /**
     * Advances an assigned or claimed request to in progress.
     */
    public void updateStatus() {
        if (status == Status.ASSIGNED) {
            status = Status.IN_PROGRESS;
        }
    }

    /**
     * Updates the request description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    /**
     * Updates the request location.
     *
     * @param location the new location
     */
    public void setLocation(Location location) {
        if (location != null) {
            this.location = location;
        }
    }

    /**
     * Updates the number of people needing help.
     *
     * @param numberOfPeople the updated number of people
     */
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    /**
     * Updates the number of pets needing help.
     *
     * @param numberOfPets the updated number of pets
     */
    public void setNumberOfPets(int numberOfPets) {
        this.numberOfPets = numberOfPets;
    }

    /**
     * Updates the pet information for this request.
     *
     * @param petInfo the updated pet information
     */
    public void setPetInfo(String petInfo) {
        this.petInfo = petInfo;
    }

    /**
     * Updates the request status when the provided status is valid.
     *
     * @param status the new request status
     */
    public void setStatus(Status status) {
        if (status != null) {
            this.status = status;
        }
    }

    /**
     * Updates the urgency level when the provided value is valid.
     *
     * @param urgency the new urgency level
     */
    public void setUrgency(Urgency urgency) {
        if (urgency != null) {
            this.urgency = urgency;
        }
    }

    /**
     * Updates the hurricane data for this request.
     *
     * @param hurricaneData the updated hurricane information
     */
    public void setHurricaneData(HurricaneData hurricaneData) {
        this.hurricaneData = hurricaneData;
    }

    /**
     * Stores the phone number of the guest who created this request.
     *
     * @param guestPhoneNumber the guest's phone number
     */
    public void setGuestPhoneNumber(String guestPhoneNumber) {
        this.guestPhoneNumber = guestPhoneNumber;
    }

    /**
     * Checks whether this request matches location, certification, and skill criteria.
     *
     * @param location the location to match
     * @param requiresCertification whether certification must be required
     * @param skill an optional required skill
     * @return true if this request matches all criteria
     */
    public boolean isARequest(Location location, boolean requiresCertification,
                              Skill skill) {
        boolean sameLocation = this.location == null
                ? location == null
                : this.location.equals(location);

        return sameLocation
                && this.requiresCertification == requiresCertification
                && (skill == null || neededSkills.contains(skill));
    }

    /**
     * Returns a concise display string for the request.
     *
     * @return the description, status, and assigned volunteer username
     */
    @Override
    public String toString() {
        String handler = assignedVolunteer == null
                ? "Unassigned"
                : assignedVolunteer.getUsername();

        return description + " - " + status.label + " - " + handler;
    }

    /**
     * Returns the shelter selected for this request.
     *
     * @return the selected shelter, or null if one has not been selected
     */
    public Shelter getSelectedShelter() {
        return selectedShelter;
    }

    /**
     * Selects a shelter for this request.
     *
     * @param selectedShelter the selected shelter
     */
    public void setSelectedShelter(Shelter selectedShelter) {
        this.selectedShelter = selectedShelter;
    }

    /**
     * Determines whether medical staff are required for this request.
     *
     * @return true if medical staff are required
     */
    public boolean requiresMedicalStaff() {
        return requiresMedicalStaff;
    }

    /**
     * Updates whether medical staff are required.
     *
     * @param requiresMedicalStaff true when medical staff are required
     */
    public void setRequiresMedicalStaff(boolean requiresMedicalStaff) {
        this.requiresMedicalStaff = requiresMedicalStaff;
    }

    /**
     * Determines whether the request is actively assigned or in progress.
     *
     * @return true if the request has an active assignment
     */
    public boolean isActiveAssignment() {
        return status == Status.ASSIGNED || status == Status.IN_PROGRESS;
    }

    /**
     * Determines whether the request has reached a final status.
     *
     * @return true if the request is complete, resolved, or cancelled
     */
    public boolean isTerminal() {
        return status == Status.COMPLETE || status == Status.CANCELLED;
    }
}