package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.IOException;
import javafx.event.ActionEvent;
import com.relief.App;


public class LoginController {

    @FXML
    private TextField txt_email;

    @FXML
    private Label feedback;

    @FXML
    private TextField txt_firstName;

    @FXML
    private TextField txt_lastName;

    @FXML
    private Button guestButton;

    @FXML
    private Label heading;

    @FXML
    private VBox nameGroup;

    @FXML
    private PasswordField txt_password;

    @FXML
    private Label passwordHint;

    @FXML
    private Button submitButton;

    @FXML
    private Label submitLabel;

    @FXML
    private Label subtitle;

    @FXML
    private Hyperlink switchLink;

    @FXML
    private Label switchPrompt;

    @FXML
    private VBox termsGroup;

    @FXML
    void back(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
        void login(ActionEvent event) throws IOException {
            String firstName = txt_firstName.getText().trim();
            String lastName = txt_lastName.getText().trim();
            String fullName = (txt_firstName + " " + txt_lastName).trim();
            App.setRoot("home");
    }

}
