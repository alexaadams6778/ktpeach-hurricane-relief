package com.model;
import java.util.UUID;
import java.util.ArrayList;

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

    public HelpRequest(UUID requestID, User author, String description, Location location, boolean requiresCertification, ArrayList<Skill> neededSkills, int numberOfPeople,
                       int numberOfPets, Status status, Urgency urgency) {
                        this.requestID = requestID;
                        this.author = author;
                        this.description = description;
                        this.location = location;
                        this.requiresCertification = requiresCertification;
                        this.neededSkills = neededSkills;
                        this.numberOfPeople = numberOfPeople;
                        this.numberOfPets = numberOfPets;
                        this.status = status;
                        this.urgency = urgency;
                        this.hurricaneData = new HurricaneData(85, 1);
                        this.commentThread = new ArrayList<Comment>();
                       }

    public HelpRequest(User author, String description, Location location, boolean requiresCertification, ArrayList<Skill> neededSkills, int numberOfPeople,
                       int numberOfPets, Status status, Urgency urgency) {
                        this.requestID = UUID.randomUUID();
                        this.author = author;
                        this.description = description;
                        this.location = location;
                        this.requiresCertification = requiresCertification;
                        this.neededSkills = neededSkills;
                        this.numberOfPeople = numberOfPeople;
                        this.numberOfPets = numberOfPets;
                        this.status = status;
                        this.urgency = urgency;
                        this.hurricaneData = new HurricaneData(85, 1);
                        this.commentThread = new ArrayList<Comment>();
                       }

    public void addComment(Comment comment) {

    }

    public void updateStatus() {

    }

    public boolean isARequest(Location location, boolean requiresCertification, Skill skill) {
        return true;
    }

}
