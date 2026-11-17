package com.controllers;

import java.util.Locale;
import com.model.HurricaneReliefSystem;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AdminHomeController {
    @FXML private Label greetingLabel;
    @FXML private Label adminNameLabel;
    @FXML private Button profileButton;

    @FXML
    private void initialize() {
        refresh();
    }

    @FXML
    private void refresh() {
        User user = HurricaneReliefSystem.getInstance().getCurrentUser();
        if (!(user.getType().equalsIgnoreCase("ShelterManager"))) return;
        String firstName = user.getFirstName().trim();
        String lastName = user.getLastName().trim();
        String fullName = (firstName + " " + lastName).trim();

        greetingLabel.setText(firstName.isEmpty() ? "Hi!" : "Hi, " + firstName + ".");
        adminNameLabel.setText(fullName.isEmpty() ? "Shelter Manager" : fullName + " · Shelter Manager");
        String initials = firstName.isEmpty() ? "" : firstName.substring(0, 1);
        if (!lastName.isEmpty()) initials += lastName.substring(0, 1);
        profileButton.setText(initials.isEmpty() ? "?" : initials.toUpperCase(Locale.ROOT));
        profileButton.setAccessibleText(fullName.isEmpty() ? "Open profile" : "Open profile for " + fullName);
    }
}
