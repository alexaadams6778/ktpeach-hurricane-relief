package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

/** Home-page interactions. Cards show mockup data until backend wiring is added. */
public class HomeController {
    @FXML private void showHome() { /* Already on the home page. */ }

    @FXML private void createRequest() {
        showPreview("Create request", "The request form is not connected yet. This home-page preview does not submit a help request.");
    }

    @FXML private void showRequests() {
        showPreview("Your requests", "Example request: Transport & medical support\n4 people · 1 pet\nFinding a volunteer\n\nThis is sample data from the design, not a live request status.");
    }

    @FXML private void showShelters() {
        showPreview("Find a shelter", "Example shelter: Military Magnet Academy\n25 spaces available · Pets welcome · Medical staff\n\nThese are mockup details. Live shelter availability is not connected yet.");
    }

    @FXML private void showProfile() {
        showPreview("Profile", "Frank Carter (FC) is the example profile shown in this design. Account and sign-in features are not connected to this page yet.");
    }

    private void showPreview(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("KTPeach");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
