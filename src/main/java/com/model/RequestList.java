package com.model;

import java.util.ArrayList;

/**
 * Stores and manages all help requests in the Hurricane Relief System.
 * Uses the singleton pattern so the application shares one request list.
 */
public class RequestList {

    private static RequestList requestList;
    private ArrayList<HelpRequest> requests;

    /**
     * Creates the request list and loads saved requests from JSON.
     */
    private RequestList() {
        requests = DataLoader.loadRequests();
    }

    /**
     * Returns the single shared RequestList instance.
     *
     * @return the RequestList instance
     */
    public static RequestList getInstance() {
        if (requestList == null) {
            requestList = new RequestList();
        }

        return requestList;
    }

    /**
     * Returns every help request in the system.
     *
     * @return the list of help requests
     */
    public ArrayList<HelpRequest> getRequests() {
        return requests;
    }

    /**
     * Returns help requests at a specified location.
     * A null location returns all requests.
     *
     * @param location the location to search for
     * @return the matching help requests
     */
    public ArrayList<HelpRequest> getRequests(Location location) {
        ArrayList<HelpRequest> matches = new ArrayList<HelpRequest>();

        for (HelpRequest request : requests) {
            if (location == null || request.getLocation().equals(location)) {
                matches.add(request);
            }
        }

        return matches;
    }

    /**
     * Returns help requests matching a location and certification requirement.
     *
     * @param location the location to search for
     * @param requiresCertification whether certification is required
     * @return the matching help requests
     */
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

    /**
     * Returns help requests matching location, certification, and skill criteria.
     *
     * @param location the location to search for
     * @param requiresCertification whether certification is required
     * @param skill the required skill, or null to ignore skill
     * @return the matching help requests
     */
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

    /**
     * Creates and adds a help request when the supplied information is valid.
     *
     * @param author the user creating the request
     * @param description the description of needed assistance
     * @param location the location where help is needed
     * @param requiresCertification whether a certified volunteer is needed
     * @param neededSkills the skills needed for the request
     * @param numberOfPeople the number of people needing help
     * @param numberOfPets the number of pets needing help
     * @param petInfo information about the pets
     * @param status the initial request status
     * @param urgency the urgency level
     * @return true if the request was created and added successfully
     */
    public boolean addRequest(User author, String description, Location location,
                              boolean requiresCertification,
                              ArrayList<Skill> neededSkills, int numberOfPeople,
                              int numberOfPets, String petInfo, Status status,
                              Urgency urgency) {
        if (author == null || description == null || description.isBlank()
                || location == null || numberOfPeople < 0 || numberOfPets < 0
                || status == null || urgency == null) {
            return false;
        }

        HelpRequest request = new HelpRequest(author, description, location,
                requiresCertification, neededSkills, numberOfPeople,
                numberOfPets, petInfo, status, urgency);

        return addRequest(request);
    }

    /**
     * Adds an existing help request when it is valid and not already stored.
     *
     * @param request the request to add
     * @return true if the request was added successfully
     */
    public boolean addRequest(HelpRequest request) {
        if (request == null || requests.contains(request)) {
            return false;
        }

        requests.add(request);
        return true;
    }

    /**
     * Removes a help request from the list.
     *
     * @param request the request to remove
     * @return true if the request was removed successfully
     */
    public boolean removeRequest(HelpRequest request) {
        if (request == null) {
            return false;
        }

        return requests.remove(request);
    }

    /**
     * Saves every help request to the requests JSON file.
     */
    public void save() {
        DataWriter.saveRequests();
    }
}