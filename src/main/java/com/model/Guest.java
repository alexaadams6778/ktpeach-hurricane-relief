package com.model;

import java.util.ArrayList;

/**
 * Represents a guest who can submit a help request without creating an account.
 * A guest is identified by their phone number and current location.
 */
public class Guest {

    private String phoneNumber;
    private Location location;

    /**
     * Creates a guest with a phone number and location.
     *
     * @param phoneNumber the guest's phone number
     * @param location the guest's location
     */
    public Guest(String phoneNumber, Location location) {
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * Creates and adds a new help request for this guest.
     *
     * @param description the description of the needed assistance
     * @param numberOfPeople the number of people needing help
     * @param numberOfPets the number of pets needing help
     * @param petInfo information about the pets
     * @param urgency the urgency level of the request
     * @return the newly created help request
     */
    public HelpRequest createRequest(String description, int numberOfPeople,
                                     int numberOfPets, String petInfo,
                                     Urgency urgency) {
        HelpRequest request = new HelpRequest(
                null,
                description,
                location,
                false,
                new ArrayList<Skill>(),
                numberOfPeople,
                numberOfPets,
                petInfo,
                Status.OPEN,
                urgency
        );

        request.setGuestPhoneNumber(phoneNumber);
        RequestList.getInstance().addRequest(request);
        return request;
    }

    /**
     * Updates a help request created by this guest.
     *
     * @param request the request to update
     * @param description the updated request description
     * @param location the updated request location
     * @param numberOfPeople the updated number of people needing help
     * @param numberOfPets the updated number of pets needing help
     * @param urgency the updated urgency level
     */
    public void updateRequest(HelpRequest request, String description,
                              Location location, int numberOfPeople,
                              int numberOfPets, Urgency urgency) {
        if (request == null || request.getGuestPhoneNumber() == null) {
            return;
        }

        if (!request.getGuestPhoneNumber().equals(phoneNumber)) {
            return;
        }

        if (request.getStatus() == Status.CANCELLED
                || request.getStatus() == Status.RESOLVED) {
            return;
        }

        request.setDescription(description);
        request.setLocation(location);
        request.setNumberOfPeople(numberOfPeople);
        request.setNumberOfPets(numberOfPets);
        request.setUrgency(urgency);
    }

    /**
     * Cancels a help request created by this guest.
     *
     * @param request the request to cancel
     */
    public void cancelRequest(HelpRequest request) {
        if (request == null || request.getGuestPhoneNumber() == null) {
            return;
        }

        if (request.getGuestPhoneNumber().equals(phoneNumber)) {
            request.setStatus(Status.CANCELLED);
        }
    }
}