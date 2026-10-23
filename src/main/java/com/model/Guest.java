package com.model;

import java.util.ArrayList;
/**
 * @author KTPeach
 * Guest User.
 */
public class Guest {

    private String phoneNumber;
    private Location location;
/**
     * Constructs a Guest object with the specified phone number and location.
     *
     * @param phoneNumber the phone number of the guest
     * @param location    the location of the guest
     */
    public Guest(String phoneNumber, Location location) {
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * Creates a help request for the guest.
     *
     * @param description   the description of the help request
     * @param numberOfPeople the number of people in need
     * @param numberOfPets  the number of pets in need
     * @param urgency       the urgency level of the request
     * @return the created help request
     */
    public HelpRequest createRequest(String description, int numberOfPeople,
                                     int numberOfPets, String petInfo, Urgency urgency) {
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
     * Updates an existing help request for the guest.
     *
     * @param request        the help request to update
     * @param description    the updated description of the help request
     * @param location       the updated location of the help request
     * @param numberOfPeople the updated number of people in need
     * @param numberOfPets   the updated number of pets in need
     * @param urgency          the updated urgency level of the request
     */
    public void updateRequest(HelpRequest request, String description, Location location,
                              int numberOfPeople, int numberOfPets, Urgency urgency) {
        if (request == null || request.getGuestPhoneNumber() == null) {
            return;
        }

        if (!request.getGuestPhoneNumber().equals(phoneNumber)) {
            return;
        }

        if (request.getStatus() == Status.CANCELLED || request.getStatus() == Status.RESOLVED) {
            return;
        }

        request.setDescription(description);
        request.setLocation(location);
        request.setNumberOfPeople(numberOfPeople);
        request.setNumberOfPets(numberOfPets);
        request.setUrgency(urgency);
    }
/**
     * Cancels an existing help request for the guest.
     *
     * @param request the help request to cancel
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