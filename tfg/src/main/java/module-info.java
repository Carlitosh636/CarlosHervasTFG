module tfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;

    opens com.example to javafx.fxml;
    exports com.example;
}