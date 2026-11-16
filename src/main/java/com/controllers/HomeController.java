package com.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import com.model.HelpRequest;
import com.model.HurricaneReliefSystem;
import com.model.Shelter;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import com.model.Location;

public class HomeController {
    @FXML private Label greetingLabel;
    @FXML private Button profileButton;
    @FXML private Button requestCard;
    @FXML private Button shelterCard;
    @FXML private Button viewRequestsButton;
    @FXML private Label requestsHeading;
    @FXML private Label emptyRequests;
    @FXML private Label emptyShelters;
    @FXML private Label requestTitle;
    @FXML private Label requestPeople;
    @FXML private Label requestStatus;
    @FXML private Label shelterName;
    @FXML private Label shelterAvailability;
    @FXML private Label shelterFeatures;

    private List<HelpRequest> requests = new ArrayList<>();
    private List<Shelter> shelters = new ArrayList<>();
    private User user;

    @FXML
    private void initialize() {
        refresh();
    }

    private void refresh() {
        HurricaneReliefSystem system = HurricaneReliefSystem.getInstance();
        user = system.getCurrentUser();
        String name = user == null ? "" : (user.getFirstName() + " " + user.getLastName()).trim();
        greetingLabel.setText(name.isEmpty() ? "Hi!" : "Hi, " + name + ".");
        String initials = "?";
        if (!name.isEmpty()) {
            String[] parts = name.split("\\s+");
            initials = parts[0].substring(0, 1);
            if (parts.length > 1) initials += parts[parts.length - 1].substring(0, 1);
        }
        profileButton.setText(initials.toUpperCase(Locale.ROOT));
        profileButton.setAccessibleText(name.isEmpty() ? "Open profile" : "Open profile for " + name);

        requests = getUserRequests(user, system.findRequest());
        requestsHeading.setText(requests.isEmpty() ? "Your requests" : "Your requests (" + requests.size() + ")");
        setShown(requestCard, !requests.isEmpty());
        setShown(emptyRequests, requests.isEmpty());
        viewRequestsButton.setDisable(requests.isEmpty());
        emptyRequests.setText(user == null ? "Log in to see your requests."
                : "No requests yet. Your requests will appear here once you create one.");
        if (!requests.isEmpty()) {
            HelpRequest request = requests.get(0);
            requestTitle.setText(shortText(request.getDescription(), 85));
            requestPeople.setText(peopleSummary(request));
            requestStatus.setText(statusText(request));
            requestCard.setAccessibleText(request.getDescription() + ". "
                    + peopleSummary(request) + ". " + statusText(request));
        }

        shelters = getLocalShelters(user, system.findShelter(""));
        setShown(shelterCard, !shelters.isEmpty());
        setShown(emptyShelters, shelters.isEmpty());
        emptyShelters.setText(user == null ? "Log in to see shelters for your saved location."
                : !hasLocation(user) ? "Add your location to your profile to see shelter options in your area."
                : "No open shelters with space are saved for your ZIP code or city and state.");
        if (!shelters.isEmpty()) {
            Shelter shelter = shelters.get(0);
            shelterName.setText(shelter.getName());
            shelterAvailability.setText(availability(shelter));
            shelterFeatures.setText(features(shelter));
            shelterCard.setAccessibleText(shelter.getName() + ". "
                    + availability(shelter) + ". " + features(shelter));
        }
    }

    @FXML private void showHome() { refresh(); }

    @FXML private void showRequests() {
        refresh();
        String message = requests.isEmpty() ? emptyRequests.getText() : requests.stream()
                .map(request -> request.getDescription() + "\n" + peopleSummary(request)
                        + "\nStatus: " + statusText(request))
                .collect(Collectors.joining("\n\n"));
    }

    @FXML private void showShelters() {
        refresh();
        String message = shelters.isEmpty() ? emptyShelters.getText() : shelters.stream()
                .map(shelter -> shelter.getName() + "\n" + availability(shelter)
                        + "\n" + features(shelter))
                .collect(Collectors.joining("\n\n"));
    }

    @FXML private void createRequest() {
    }

    @FXML private void showProfile() {
    }

    private String peopleSummary(HelpRequest request) {
        int people = request.getNumberOfPeople();
        int pets = request.getNumberOfPets();
        return people + (people == 1 ? " person" : " people")
                + " · " + pets + (pets == 1 ? " pet" : " pets");
    }

    private String statusText(HelpRequest request) {
        return request.getStatus() == null ? "Status unavailable" : request.getStatus().label;
    }

    private String availability(Shelter shelter) {
        int spaces = shelter.getAvailableSpaces();
        return "Open · " + spaces + (spaces == 1 ? " space available" : " spaces available");
    }

    private String features(Shelter shelter) {
        return (shelter.getAllowsPets() ? "Pets welcome" : "No pets") + " · "
                + (shelter.hasMedicalStaff() ? "Medical staff" : "No medical staff listed");
    }

    private String shortText(String text, int limit) {
        if (text == null || text.isBlank()) return "Help request";
        return text.length() <= limit ? text : text.substring(0, limit - 1) + "…";
    }

    private void setShown(Node node, boolean shown) {
        node.setVisible(shown);
        node.setManaged(shown);
    }

    public ArrayList<HelpRequest> getUserRequests(User user, List<HelpRequest> requests) {
        ArrayList<HelpRequest> matches = new ArrayList<>();
        if (user == null || user.getUserID() == null || requests == null) return matches;
        for (HelpRequest request : requests) {
            if (request != null && request.getAuthor() != null
                    && user.getUserID().equals(request.getAuthor().getUserID())) {
                matches.add(request);
            }
        }
        return matches;
    }

    public boolean hasLocation(User user) {
        if (user == null || user.getLocation() == null) return false;
        Location location = user.getLocation();
        return !clean(location.getZipCode()).isEmpty()
                || (!clean(location.getCity()).isEmpty() && !clean(location.getState()).isEmpty());
    }

    public ArrayList<Shelter> getLocalShelters(User user, ArrayList<Shelter> shelters) {
        ArrayList<Shelter> matches = new ArrayList<>();
        if (!hasLocation(user) || shelters == null) return matches;
        Location location = user.getLocation();
        for (Shelter shelter : shelters) {
            if (shelter != null && shelter.hasVacancy()
                    && sameArea(location, shelter.getLocation())) {
                matches.add(shelter);
            }
        }
        return matches;
    }

    private boolean sameArea(Location user, Location shelter) {
        if (shelter == null) return false;
        String zip = clean(user.getZipCode());
        if (!zip.isEmpty() && zip.equalsIgnoreCase(clean(shelter.getZipCode()))) return true;
        String city = clean(user.getCity());
        String state = clean(user.getState());
        return !city.isEmpty() && !state.isEmpty()
                && city.equalsIgnoreCase(clean(shelter.getCity()))
                && state.equalsIgnoreCase(clean(shelter.getState()));
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

}
