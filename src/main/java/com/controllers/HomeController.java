package com.controllers;

import java.util.Locale;
import com.model.HurricaneReliefSystem;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomeController {
    @FXML private Label greetingLabel;
    @FXML private Button profileButton;

    @FXML
    private void initialize() {
        refresh();
    }

    @FXML
    private void refresh() {
        User user = HurricaneReliefSystem.getInstance().getCurrentUser();
        String name = user == null ? "" : (user.getFirstName() + " " + user.getLastName()).trim();
        greetingLabel.setText(name.isEmpty() ? "Hi!" : "Hi, " + name + ".");

        String initials = "?";
        if (!name.isEmpty()) {
            String[] parts = name.split("\\s+");
            initials = parts[0].substring(0, 1);
            if (parts.length > 1) initials += parts[parts.length - 1].substring(0, 1);
        }
        profileButton.setText(initials.toUpperCase(Locale.ROOT));
        profileButton.setAccessibleText(name.isEmpty() ? "Open profile" : "Open profile for " + name);
    }
}
