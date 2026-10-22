module com.relief {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires javafx.web;
    requires json.simple;

    opens com.relief to javafx.fxml;
    exports com.relief;

    opens com.model to javafx.fxml;
    exports com.model;
}
