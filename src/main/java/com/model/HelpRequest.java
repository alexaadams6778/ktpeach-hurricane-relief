package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A request for help with its location, needs, status, and comments.
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
    private Status status;
    private Urgency urgency;
    private HurricaneData hurricaneData;
    private ArrayList<Comment> commentThread;

    /**
     * Creates a HelpRequest object when given a UUID (already existing user)
     * @param requestID the unique identifier of the request as a UUID
     * @param author the user who created the request as a User object
     * @param description the description of the help needed as a String
     * @param location the location as a Location object
     * @param requiresCertification whether the request requires a certified volunteer as a boolean
     * @param neededSkills the required skills as an ArrayList of Skill enum
     * @param numberOfPeople the number of people needing help as an int
     * @param numberOfPets the number of pets needing help as an int
     * @param status the status of the request as a Status enum
     * @param urgency the priority of the request as an Urgency enum
     */
    public HelpRequest(UUID requestID, User author, String description,
                       Location location, boolean requiresCertification,
                       ArrayList<Skill> neededSkills, int numberOfPeople,
                       int numberOfPets, Status status, Urgency urgency) {
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
        this.status = status == null ? Status.OPEN : status;
        this.urgency = urgency;
        this.hurricaneData = new HurricaneData(85, 1);
        this.commentThread = new ArrayList<Comment>();
    }

    /**
     * Creates a HelpRequest object without a UUID (new user)
     * @param author the user who created the request as a User object
     * @param description the description of the help needed as a String
     * @param location the location as a Location object
     * @param requiresCertification whether the request requires a certified volunteer as a boolean
     * @param neededSkills the required skills as an ArrayList of Skill enum
     * @param numberOfPeople the number of people needing help as an int
     * @param numberOfPets the number of pets needing help as an int
     * @param status the status of the request as a Status enum
     * @param urgency the priority of the request as an Urgency enum
     */
    public HelpRequest(User author, String description, Location location,
                       boolean requiresCertification, ArrayList<Skill> neededSkills,
                       int numberOfPeople, int numberOfPets, Status status,
                       Urgency urgency) {
        this(UUID.randomUUID(), author, description, location,
                requiresCertification, neededSkills, numberOfPeople,
                numberOfPets, status, urgency);
    }

    /**
     * Returns the request ID
     * @return the request ID as a UUID
     */

    public UUID getRequestID() {
        return requestID;
    }

    /**
     * Returns the author
     * @return the author as a User
     */

    public User getAuthor() {
        return author;
    }

    /**
     * Returns the description
     * @return the description as a String
     */

    public String getDescription() {
        return description;
    }

    /**
     * Returns the location
     * @return the location as a Location
     */

    public Location getLocation() {
        return location;
    }

    /**
     * Returns whether the request requires a certified volunteer
     * @return true if certification is required as a boolean
     */

    public boolean requiresCertification() {
        return requiresCertification;
    }

    /**
     * Returns the needed skills
     * @return the needed skills as an ArrayList of Skill enum
     */

    public ArrayList<Skill> getNeededSkills() {
        return neededSkills;
    }

    /**
     * Returns the number of people
     * @return the number of people as an int
     */

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Returns the number of pets
     * @return the number of pets as an int
     */

    public int getNumberOfPets() {
        return numberOfPets;
    }

    /**
     * Returns the status
     * @return the status as a Status enum
     */

    public Status getStatus() {
        return status;
    }

    /**
     * Returns the urgency
     * @return the urgency as an Urgency enum
     */

    public Urgency getUrgency() {
        return urgency;
    }

    /**
     * Returns the hurricane data
     * @return the hurricane data as a HurricaneData object
     */

    public HurricaneData getHurricaneData() {
        return hurricaneData;
    }

    /**
     * Returns the comment thread
     * @return the comment thread as an ArrayList of Comment objects
     */

    public ArrayList<Comment> getCommentThread() {
        return commentThread;
    }

    /**
     * Adds a comment to the request if the comment is not null
     * @param comment the comment as a Comment object
     */

    public void addComment(Comment comment) {
        if (comment != null) {
            commentThread.add(comment);
        }
    }

    /**
     * Updates an open request to claimed, a claimed request to in progress, or an
     * in-progress request to resolved
     */

    public void updateStatus() {
        if (status == Status.OPEN) {
            status = Status.CLAIMED;
        } else if (status == Status.CLAIMED) {
            status = Status.IN_PROGRESS;
        } else if (status == Status.IN_PROGRESS) {
            status = Status.RESOLVED;
        }
    }

    /**
     * Sets the request status if the given status isn't null
     * @param status the status of the request as a Status enum
     */

    public void setStatus(Status status) {
        if (status != null) {
            this.status = status;
        }
    }

    /**
     * Sets the request priority if the given urgency is not null
     * @param urgency the priority of the request as an Urgency enum
     */

    public void setUrgency(Urgency urgency) {
        if (urgency != null) {
            this.urgency = urgency;
        }
    }

    /**
     * Sets the hurricane data
     * @param hurricaneData the hurricane information as a HurricaneData object
     */

    public void setHurricaneData(HurricaneData hurricaneData) {
        this.hurricaneData = hurricaneData;
    }

    /**
     * Checks the location, certification requirement, and optional skill of a request
     * @param location the location to match
     * @param requiresCertification whether the request requires a certified volunteer as a boolean
     * @param skill the skill as a Skill enum
     * @return true if it's a match as a boolean
     */

    public boolean isARequest(Location location, boolean requiresCertification, Skill skill) {
        boolean sameLocation = this.location == null ? location == null : this.location.equals(location);
        return sameLocation && this.requiresCertification == requiresCertification && (skill == null || neededSkills.contains(skill));
    }

    /**
     * Returns the description and status
     * @return the formatted information as a String
     */

    @Override
    public String toString() {
        return description + " - " + status.label;
    }
}