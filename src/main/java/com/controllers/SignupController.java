package com.controllers;

import java.io.IOException;
import com.model.HurricaneReliefSystem;
import com.model.User;
import com.relief.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class SignupController {
    @FXML private TextField txt_firstName;
    @FXML private TextField txt_lastName;
    @FXML private TextField txt_email;
    @FXML private PasswordField txt_password;
    @FXML private Label feedback;
    @FXML private TextField txt_visiblePassword;
    @FXML private CheckBox showPasswordCheckBox;

    @FXML
    private void initialize() {
        txt_visiblePassword.textProperty()
                .bindBidirectional(txt_password.textProperty());

        txt_visiblePassword.visibleProperty()
                .bind(showPasswordCheckBox.selectedProperty());
        txt_visiblePassword.managedProperty()
                .bind(showPasswordCheckBox.selectedProperty());

        txt_password.visibleProperty()
                .bind(showPasswordCheckBox.selectedProperty().not());
        txt_password.managedProperty()
                .bind(showPasswordCheckBox.selectedProperty().not());
    }

    @FXML
    private void signup() {
        showFeedback("");
        User user;
        try {
            user = HurricaneReliefSystem.getInstance().signup(
                    txt_firstName.getText(), txt_lastName.getText(),
                    txt_email.getText(), txt_password.getText());
        } catch (IllegalArgumentException e) {
            showFeedback(e.getMessage());
            return;
        } catch (IOException e) {
            showFeedback("Your account could not be saved. Check users.json and folder permissions, then try again.");
            return;
        }
        App.fullName = (user.getFirstName() + " " + user.getLastName()).trim();
        txt_password.clear();
        try {
            App.setRoot("home");
        } catch (IOException e) {
            showFeedback("Your account was saved, but the home screen could not open. You can log in next time.");
        }
    }

    @FXML
    private void showLogin() throws IOException {
        App.setRoot("login");
    }

    private void showFeedback(String message) {
        feedback.setText(message);
        feedback.setVisible(!message.isEmpty());
        feedback.setManaged(!message.isEmpty());
    }
}
