package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.relief.App;

public class HomeController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("login");
    }
}
