module tfg {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.github.mustachejava;


    opens com.example to javafx.fxml;
    exports com.example;
}