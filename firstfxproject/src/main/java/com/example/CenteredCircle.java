package com.example;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;

public class CenteredCircle extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Circle c = new Circle();
        Group root = new Group(c);
        Scene scene = new Scene(root, 100, 100);
        // con esto conseguimos que las propertys del circulo estén ligadas al tamaño de
        // la ventana, que también tiene sus properties.
        c.centerXProperty().bind(scene.widthProperty().divide(2));
        c.centerYProperty().bind(scene.heightProperty().divide(2));
        c.radiusProperty().bind(Bindings.min(scene.widthProperty(),
                scene.heightProperty())
                .divide(2));

        stage.setTitle("Binding in JavaFX");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

}
