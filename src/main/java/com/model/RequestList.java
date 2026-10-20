package com.model;

import java.util.ArrayList;

public class RequestList {

    private static RequestList requestList;
    private ArrayList<HelpRequest> requests;

    private RequestList() {
        requests = DataLoader.loadRequests();
    }

    public static RequestList getInstance() {
        if (requestList == null) {
            requestList = new RequestList();
        }

        return requestList;
    }

    public ArrayList<HelpRequest> getRequests() {
        return requests;
    }

    public ArrayList<HelpRequest> getRequests(Location location) {
        ArrayList<HelpRequest> matches = new ArrayList<HelpRequest>();

        for (HelpRequest request : requests) {
            if (location == null || request.getLocation().equals(location)) {
                matches.add(request);
            }
        }

        return matches;
    }

    public ArrayList<HelpRequest> getRequests(Location location,
                                              boolean requiresCertification) {
        ArrayList<HelpRequest> matches = new ArrayList<HelpRequest>();

        for (HelpRequest request : getRequests(location)) {
            if (request.requiresCertification() == requiresCertification) {
                matches.add(request);
            }
        }

        return matches;
    }

    public ArrayList<HelpRequest> getRequests(Location location,
                                              boolean requiresCertification,
                                              Skill skill) {
        ArrayList<HelpRequest> matches = new ArrayList<HelpRequest>();

        for (HelpRequest request : getRequests(location, requiresCertification)) {
            if (skill == null || request.getNeededSkills().contains(skill)) {
                matches.add(request);
            }
        }

        return matches;
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

        return addRequest(request);
    }

    public boolean addRequest(HelpRequest request) {
        if (request == null || requests.contains(request)) {
            return false;
        }

        requests.add(request);
        return true;
    }

    public void save() {
        DataWriter.saveRequests();
    }
}