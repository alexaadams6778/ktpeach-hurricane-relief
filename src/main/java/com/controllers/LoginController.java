package com.controllers;

import java.io.IOException;
import com.relief.App;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/** Login screen design only; authentication is not connected. */
public class LoginController {

    @FXML
    private TextField txt_email;

    @FXML
    private PasswordField txt_password;

    @FXML
    private void showSignup() throws IOException {
        App.setRoot("signup");
    }
}
