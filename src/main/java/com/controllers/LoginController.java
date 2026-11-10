package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.relief.App;

public class LoginController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("home");
    }
}