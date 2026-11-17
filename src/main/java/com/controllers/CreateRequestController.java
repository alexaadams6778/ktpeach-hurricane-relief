package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/** Create request screen preview. Saving requests is not wired yet. */
public class CreateRequestController {
    @FXML private Label titleLabel, subtitleLabel, peopleCount, petCount, summaryLabel, continueLabel;
    @FXML private Label stepLabel1, stepLabel2, stepLabel3;
    @FXML private Region stepBar1, stepBar2, stepBar3;
    @FXML private VBox stepOne, stepTwo, stepThree;
    @FXML private Button continueButton;
    @FXML private TextField petInfoField;
    @FXML private ToggleGroup topicGroup, urgencyGroup;
    private int step = 1;
    private int people = 1;
    private int pets = 0;

    @FXML
    private void initialize() {
        showStep(1);
    }

    @FXML private void next() {
        if (step < 3) {
            showStep(step + 1);
        } else {
            showPreview("Submit request preview",
                    "The form is complete. Submitting requests is not connected yet. No request has been created.");
        }
    }

    @FXML private void back() {
        if (step > 1) {
            showStep(step - 1);
        }
    }

    @FXML private void addPerson() {
        people++;
        peopleCount.setText("" + people);
    }

    @FXML private void removePerson() {
        if (people > 1) {
            people--;
            peopleCount.setText("" + people);
        }
    }

    @FXML private void addPet() {
        pets++;
        petCount.setText("" + pets);
        setShown(petInfoField, true);
    }

    @FXML private void removePet() {
        if (pets > 0) {
            pets--;
            petCount.setText("" + pets);
            setShown(petInfoField, pets > 0);
        }
    }

    @FXML private void addPhotos() {
        showPreview("Add photos", "Photo upload is not connected yet.");
    }

    private void showStep(int newStep) {
        step = newStep;
        setShown(stepOne, step == 1);
        setShown(stepTwo, step == 2);
        setShown(stepThree, step == 3);

        if (step == 1) {
            titleLabel.setText("Create request");
            subtitleLabel.setText("Step 1 of 3 · What do you need?");
        } else if (step == 2) {
            titleLabel.setText("Who needs support?");
            subtitleLabel.setText("Step 2 of 3 · Include everyone coming with you.");
        } else {
            titleLabel.setText("Where can we find you?");
            subtitleLabel.setText("Step 3 of 3 · Location and optional photos.");
            updateSummary();
        }

        setStyle(stepBar1, "step-bar-active", step >= 1);
        setStyle(stepBar2, "step-bar-active", step >= 2);
        setStyle(stepBar3, "step-bar-active", step >= 3);
        setStyle(stepLabel1, "step-label-active", step == 1);
        setStyle(stepLabel2, "step-label-active", step == 2);
        setStyle(stepLabel3, "step-label-active", step == 3);

        continueLabel.setText(step == 3 ? "Submit request" : "Continue");
        continueButton.setAccessibleText(continueLabel.getText());
    }

    private void updateSummary() {
        String topic = "No topic";
        if (topicGroup.getSelectedToggle() != null) {
            topic = ((ToggleButton) topicGroup.getSelectedToggle()).getText();
        }
        String urgency = "No";
        if (urgencyGroup.getSelectedToggle() != null) {
            urgency = ((ToggleButton) urgencyGroup.getSelectedToggle()).getText();
        }
        String summary = topic + " · " + urgency + " urgency · " + people
                + (people == 1 ? " person" : " people");
        if (pets > 0) {
            summary = summary + " · " + pets + (pets == 1 ? " pet" : " pets");
        }
        summaryLabel.setText(summary);
    }

    private void setStyle(Node node, String styleClass, boolean on) {
        if (on && !node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        } else if (!on) {
            node.getStyleClass().remove(styleClass);
        }
    }

    private void setShown(Node node, boolean shown) {
        node.setVisible(shown);
        node.setManaged(shown);
    }

    private void showPreview(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(continueButton.getScene().getWindow());
        alert.setTitle("KTPeach");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
