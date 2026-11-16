package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.relief.App;

public class HomeController {

    @FXML
    private Button createRequestButton;

    @FXML
    private Label greetingLabel;

    @FXML
    private Button requestCard;

    @FXML
    private Button shelterCard;

    @FXML
    private Button profileButton;

    @FXML
    private void initialize() {
        String name = App.fullName.trim();
        greetingLabel.setText(name.isEmpty() ? "Hi!" : "Hi, " + name + ".");
        String initials = "";
        if (!name.isEmpty()) {
            String[] parts = name.split("\\s+");
            initials = parts[0].substring(0, 1);
            if (parts.length > 1) {
                initials += parts[parts.length - 1].substring(0, 1);
            }
            initials = initials.toUpperCase();
        }
        profileButton.setText(initials);
    }
    
    @FXML
    void createRequest(ActionEvent event) {

    }

    @FXML
    void showHome(ActionEvent event) {

    }

    @FXML
    void showProfile(ActionEvent event) {

    }

    @FXML
    void showRequests(ActionEvent event) {

    }

    @FXML
    void showShelters(ActionEvent event) {

    }

}
