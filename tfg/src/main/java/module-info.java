module tfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;

    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.builders;
    opens com.example.builders to javafx.fxml;
    exports com.example.algorithms;
    opens com.example.algorithms to javafx.fxml;
    exports com.example.exceptions;
    opens com.example.exceptions to javafx.fxml;
    exports com.example.model;
    opens com.example.model to javafx.fxml;
    exports com.example.utils;
    opens com.example.utils to javafx.fxml;
    exports com.example.enums;
    opens com.example.enums to javafx.fxml;
    exports com.example.presenter;
    opens com.example.presenter to javafx.fxml;
}