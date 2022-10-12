package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DiagramView extends GridPane {
        private final Diagram model;
        Label enunciado = new Label("El enunciado necesitará su propia ventana, a poder ser que el y el diagrama se superpongan para ir alternandose");
        Label originalData = new Label();
        Label originalDataSolutionArrow = new Label("=====================>");
        Label originalSolution = new Label();
        Label datasArrow = new Label("|\n|\n|\n|\n|");
        Label emptylabel = new Label();
        Label solutionsArrow = new Label("|\n|\n|\n|\n|");
        Label partialData = new Label();
        Label partialSolution = new Label();
        Label partialDataSolutionArrow = new Label("=====================>");
        GridPane diagramPane = new GridPane();
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
                diagramBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
                selectionBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: red;");
                lowerBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: green;");
                diagramPane.add(originalData,0,0);
                diagramPane.add(originalDataSolutionArrow,1,0);
                diagramPane.add(originalSolution,2,0);
                diagramPane.add(datasArrow,0,1);
                diagramPane.add(emptylabel,1,1);
                diagramPane.add(solutionsArrow,2,1);
                diagramPane.add(partialData,0,2);
                diagramPane.add(partialDataSolutionArrow,1,2);
                diagramPane.add(partialSolution,2,2);

                diagramPane.setStyle("-fx-background-color: DAE6F3;");
                diagramBox.getChildren().addAll(diagramPane);
                lowerBox.setPadding(new Insets(50));
                lowerBox.getChildren().addAll(inputField,confirmDataButton);
                selectionBox.getChildren().addAll(otroBtn);
                inputField.setPromptText("Introduce los numeros de input");

        }

        private void bindModelData() {
                inputField.textProperty().bindBidirectional(model.getInputsProperty());

        }



        
}
