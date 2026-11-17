package com.controllers;

import java.util.Locale;
import com.model.HurricaneReliefSystem;
import com.model.Skill;
import com.model.User;
import com.model.Volunteer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VolunteerHomeController {
    @FXML private Label greetingLabel;
    @FXML private Label volunteerNameLabel;
    @FXML private Label specialtyLabel;
    @FXML private Button profileButton;

    @FXML
    private void initialize() {
        refresh();
    }

    @FXML
    private void refresh() {
        User user = HurricaneReliefSystem.getInstance().getCurrentUser();
        if (!(user instanceof Volunteer)) return;
        Volunteer volunteer = (Volunteer) user;
        String firstName = volunteer.getFirstName().trim();
        String lastName = volunteer.getLastName().trim();
        String fullName = (firstName + " " + lastName).trim();

        greetingLabel.setText(firstName.isEmpty() ? "Hi!" : "Hi, " + firstName + ".");
        volunteerNameLabel.setText(fullName.isEmpty() ? "Volunteer" : fullName + " · Volunteer");
        String initials = firstName.isEmpty() ? "" : firstName.substring(0, 1);
        if (!lastName.isEmpty()) initials += lastName.substring(0, 1);
        profileButton.setText(initials.isEmpty() ? "?" : initials.toUpperCase(Locale.ROOT));
        profileButton.setAccessibleText(fullName.isEmpty() ? "Open profile" : "Open profile for " + fullName);

        String specialty = "";
        for (Skill skill : volunteer.getSkills()) {
            if (!specialty.isEmpty()) specialty += ", ";
            specialty += skill.label;
        }
        if (specialty.isEmpty()) specialty = "Specialty not set";
        if (volunteer.getLocation() != null) {
            String city = volunteer.getLocation().getCity();
            if (city != null && !city.trim().isEmpty()) specialty += " · " + city.trim();
        }
        specialtyLabel.setText(specialty);
    }
}
