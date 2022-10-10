package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiagramView extends GridPane {
        private final Diagram model;
        Label enunciado = new Label("Esto es un enunciado de ejemplo");

        HBox diagramBox = new HBox();
        HBox lowerBox = new HBox();
        VBox selectionBox = new VBox();
        Button okBtn = new Button("Ok");
        Button otroBtn = new Button("otroboton");
        TextField inputField = new TextField();
        Button confirmDataButton = new Button("Enter parameters");

        public DiagramView(Diagram model) {
                this.model = model;
                setLayout();
                bindModelData();

        }
        private void setLayout() {
                this.setHgap(10);
                this.setVgap(10);
                this.setStyle("-fx-background-color: lightgray;");
                this.add(enunciado, 0, 0, 1, 1);
                this.add(diagramBox, 0, 1, 3, 3);
                this.add(lowerBox, 0, 4, 3, 3);
                this.add(selectionBox, 4, 1, 3, 1);

                //crecimiento vertical y horizontal 
                GridPane.setHgrow(diagramBox, Priority.ALWAYS);
                GridPane.setVgrow(diagramBox, Priority.ALWAYS);
                GridPane.setVgrow(selectionBox, Priority.ALWAYS);
                GridPane.setHgrow(selectionBox, Priority.ALWAYS);
                
                //poner elementos de cada objeto
                diagramBox.setPadding(new Insets(50));
                diagramBox.getChildren().addAll(inputField,confirmDataButton);
                lowerBox.setPadding(new Insets(50));
                lowerBox.getChildren().addAll(okBtn,otroBtn);
                inputField.setPromptText("Introduce los numeros de input");

        }

        private void bindModelData() {
                inputField.textProperty().bind(model.getInputsProperty());
        }



        
}
