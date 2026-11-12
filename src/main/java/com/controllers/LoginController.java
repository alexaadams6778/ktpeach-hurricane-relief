package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/** Account screen preview. Account persistence and authentication are not wired yet. */
public class LoginController {
    @FXML private Label heading, subtitle, passwordHint, submitLabel, switchPrompt, feedback;
    @FXML private VBox nameGroup, termsGroup;
    @FXML private TextField fullNameField, emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button submitButton;
    @FXML private Hyperlink switchLink;
    private boolean createAccount = true;

    @FXML private void toggleMode() {
        createAccount = !createAccount;
        setShown(nameGroup, createAccount);
        setShown(termsGroup, createAccount);
        setShown(passwordHint, createAccount);
        heading.setText(createAccount ? "Create an account" : "Welcome back");
        subtitle.setText(createAccount ? "Keep track of your family’s requests." : "Log in to keep track of your family’s requests.");
        passwordField.clear();
        passwordField.setPromptText(createAccount ? "Create a password" : "Enter your password");
        submitLabel.setText(createAccount ? "Create account" : "Log in");
        submitButton.setAccessibleText(submitLabel.getText());
        switchPrompt.setText(createAccount ? "Already have an account?" : "Don’t have an account?");
        switchLink.setText(createAccount ? "Log in" : "Create account");
        switchLink.setVisited(false);
        setShown(feedback, false);
        if (createAccount) fullNameField.requestFocus();
        else emailField.requestFocus();
    }

    @FXML private void submit() {
        if (createAccount && fullNameField.getText().trim().isEmpty()) {
            invalid("Please enter your full name.", fullNameField);
        } else if (!emailField.getText().trim().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            invalid("Please enter a valid email address.", emailField);
        } else if (passwordField.getText().isEmpty()
                || (createAccount && passwordField.getText().length() < 8)) {
            invalid(createAccount ? "Use at least 8 characters for your password." : "Please enter your password.", passwordField);
        } else {
            setShown(feedback, false);
            showPreview(createAccount ? "Create account preview" : "Log in preview",
                    "The form is complete. Account creation and authentication are not connected yet. No account has been created and you have not been signed in.");
            passwordField.clear();
        }
    }

    @FXML private void showShelters() {
        showPreview("Find a shelter", "Guest shelter search is not connected yet. This preview cannot show live shelter availability.");
    }

    @FXML private void showTerms() {
        showPreview("Terms of Service", "The Terms of Service have not been added to this project yet.");
    }

    @FXML private void showPrivacy() {
        showPreview("Privacy Policy", "The Privacy Policy has not been added to this project yet.");
    }

    private void invalid(String message, TextField field) {
        feedback.setText(message);
        setShown(feedback, true);
        field.requestFocus();
    }

    private void setShown(Node node, boolean shown) {
        node.setVisible(shown);
        node.setManaged(shown);
    }

    private void showPreview(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(submitButton.getScene().getWindow());
        alert.setTitle("KTPeach");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
