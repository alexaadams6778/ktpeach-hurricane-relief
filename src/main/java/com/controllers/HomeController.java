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
    private void initialize() {
        String name = App.fullName;
        greetingLabel.setText(name.isEmpty() ? "Hi!" : "Hi, " + name + ".");
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
