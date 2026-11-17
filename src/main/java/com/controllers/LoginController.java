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

public class LoginController {
    @FXML private TextField txt_email;
    @FXML private PasswordField txt_password;
    @FXML private Label feedback;
    @FXML private TextField txt_visiblePassword;
    @FXML private CheckBox showPasswordCheckBox;

    @FXML
    private void login() {
        showFeedback("");
        if (txt_email.getText().trim().isEmpty() || txt_password.getText().isEmpty()) {
            showFeedback("Please enter your email and password.");
            return;
        }
        User user;
        try {
            user = HurricaneReliefSystem.getInstance().loginByEmail(
                    txt_email.getText(), txt_password.getText());
        } catch (IOException e) {
            showFeedback("Could not read users.json. Check the file and run from the project root.");
            return;
        }
        if (user == null) {
            showFeedback("Email or password is incorrect.");
            txt_password.clear();
            return;
        }
        txt_password.clear();
        try {
            App.setRoot("home");
        } catch (IOException e) {
            showFeedback("You are logged in, but the home screen could not open.");
        }
    }

    @FXML
    private void initialize() {
        txt_visiblePassword.textProperty().bindBidirectional(txt_password.textProperty());
        txt_visiblePassword.visibleProperty().bind(showPasswordCheckBox.selectedProperty());
        txt_visiblePassword.managedProperty().bind(txt_visiblePassword.visibleProperty());
        txt_password.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());
        txt_password.managedProperty().bind(txt_password.visibleProperty());
    }

    @FXML
    private void showSignup() throws IOException {
        App.setRoot("signup");
    }

    private void showFeedback(String message) {
        feedback.setText(message);
        feedback.setVisible(!message.isEmpty());
        feedback.setManaged(!message.isEmpty());
    }
}
