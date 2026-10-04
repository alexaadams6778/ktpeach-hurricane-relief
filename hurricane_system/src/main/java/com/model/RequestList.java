package com.model;

import java.util.ArrayList;

public class RequestList {

    private static RequestList requestList;
    private ArrayList<HelpRequest> requests;

    private RequestList() {
        requests = new ArrayList<HelpRequest>();
    }

    public static RequestList getInstance() {
        if (requestList == null) {
            return new RequestList();
        }
        return requestList;
    }

    public ArrayList<HelpRequest> getRequests() {
        return requests;
    }

    public ArrayList<HelpRequest> getRequests(Location location) {
        return null;
    }

    public ArrayList<HelpRequest> getRequests(Location location, boolean requiresCertification) {
        return null;
    }

    public ArrayList<HelpRequest> getRequests(Location location, boolean requiresCertification, Skill skill) {
        return null;
    }

    public boolean addRequest(User author, String description, Location location, boolean requiresCertification, ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, Status status, Urgency urgency) {
                                return true;
                              }
    
    public boolean save() {
        return true;
    }

}
