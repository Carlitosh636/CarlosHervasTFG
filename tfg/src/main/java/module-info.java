module tfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens com.example to javafx.fxml;
    exports com.example;
}