package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Label enunciado = new Label("Esto es un enunciado de ejemplo");

        HBox diagramBox = new HBox();
        diagramBox.setPrefSize(600, 300);
        diagramBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        HBox lowerBox = new HBox();
        lowerBox.setPrefSize(600, 200);
        lowerBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: red;");

        VBox selectionBox = new VBox();
        selectionBox.setPrefSize(200, 600);
        selectionBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: green;");

        Button okBtn = new Button("Ok");
        Button otroBtn = new Button("otroboton");
        Button otro = new Button("grg");
        Button jaja = new Button("herhtvb");

        diagramBox.setPadding(new Insets(50));
        diagramBox.getChildren().addAll(okBtn,otroBtn);
        lowerBox.setPadding(new Insets(50));
        lowerBox.getChildren().addAll(otro,jaja);

        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: lightgray;");
        root.add(enunciado, 0, 0, 1, 1);
        root.add(diagramBox, 0, 1, 3, 3);
        root.add(lowerBox, 0, 4, 3, 3);
        root.add(selectionBox, 4, 1, 3, 1);

        GridPane.setHgrow(diagramBox, Priority.ALWAYS);
        GridPane.setVgrow(diagramBox, Priority.ALWAYS);
        GridPane.setVgrow(selectionBox, Priority.ALWAYS);
        GridPane.setHgrow(selectionBox, Priority.ALWAYS);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Mi TFG");
        stage.show();

    }

}
