package com.model;

import java.util.ArrayList;
import java.util.UUID;

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

    public HelpRequest(User author, String description, Location location,
                       boolean requiresCertification, ArrayList<Skill> neededSkills,
                       int numberOfPeople, int numberOfPets, Status status,
                       Urgency urgency) {
        this(UUID.randomUUID(), author, description, location,
                requiresCertification, neededSkills, numberOfPeople,
                numberOfPets, status, urgency);
    }

    public UUID getRequestID() {
        return requestID;
    }

    public User getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public boolean requiresCertification() {
        return requiresCertification;
    }

    public ArrayList<Skill> getNeededSkills() {
        return neededSkills;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public int getNumberOfPets() {
        return numberOfPets;
    }

    public Status getStatus() {
        return status;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public HurricaneData getHurricaneData() {
        return hurricaneData;
    }

    public ArrayList<Comment> getCommentThread() {
        return commentThread;
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            commentThread.add(comment);
        }
    }

    public void updateStatus() {
        if (status == Status.OPEN) {
            status = Status.CLAIMED;
        } else if (status == Status.CLAIMED) {
            status = Status.IN_PROGRESS;
        } else if (status == Status.IN_PROGRESS) {
            status = Status.RESOLVED;
        }
    }

    public void setStatus(Status status) {
        if (status != null) {
            this.status = status;
        }
    }

    public void setUrgency(Urgency urgency) {
        if (urgency != null) {
            this.urgency = urgency;
        }
    }

    public void setHurricaneData(HurricaneData hurricaneData) {
        this.hurricaneData = hurricaneData;
    }

    public boolean isARequest(Location location, boolean requiresCertification,
                              Skill skill) {
        boolean sameLocation = this.location == null
                ? location == null
                : this.location.equals(location);

        return sameLocation
                && this.requiresCertification == requiresCertification
                && (skill == null || neededSkills.contains(skill));
    }

    @Override
    public String toString() {
        return description + " - " + status.label;
    }
}