package com.example;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DiagramView extends GridPane {
        private final Diagram model;
        TextArea enunciado = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quis sem lectus. Maecenas vitae volutpat felis.\nVivamus fringilla pellentesque tincidunt.\nNulla et nisl ac tortor tempus sagittis id ac turpis. Ut sed posuere justo. Nam sollicitudin metus ac iaculis tempus.\nEtiam fermentum tristique tellus in blandit. Phasellus non congue ante. Aenean nec lorem ipsum.\n" +
                "\n");
        Label originalData = new Label();
        Label originalDataSolutionArrow = new Label("=====================>");
        Label originalSolution = new Label();
        Label datasArrow = new Label(" |\n |\n |\n |\n |");
        Label emptylabel = new Label();
        Label solutionsArrow = new Label(" |\n |\n |\n |\n |");
        Label partialData = new Label();
        Label partialSolution = new Label();
        Label partialDataSolutionArrow = new Label("=====================>");
        GridPane diagramPane = new GridPane();
        BorderPane diagramBox = new BorderPane();
        VBox lowerBox = new VBox();
        Label inputErrorLabel = new Label("El valor introducido no es correcto para el problema. Introduzca datos válidos");
        VBox selectionBox = new VBox();
        Label solutionSelectionLabel = new Label("Selecciona el paso restante (marcado con ??)");
        ComboBox solutionSelection = new ComboBox();
        Label inputInstructions=new Label("Escribe la base (a) y el exponente (b), separados por una coma");
        TextField inputField = new TextField();
        Label reductionInstructions=new Label("Elige el método de reducción");
        ComboBox reductionSelect = new ComboBox();
        Label baseCaseInstructions=new Label("Elige el caso base");
        TextField baseCaseField= new TextField();
        Button confirmDataButton = new Button("Introducir valores");

        public DiagramView(Diagram model) {
                this.model = model;
                setLayout();
                bindModelData();

        }
        private void setLayout() {
                this.setHgap(10);
                this.setVgap(10);
                this.setStyle("-fx-background-color: lightgray;");
                this.add(diagramBox, 0, 1, 3, 4);
                this.add(lowerBox, 0, 4, 3, 4);
                this.add(selectionBox, 4, 1, 4,1);

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

                enunciado.setEditable(false);
                enunciado.setBackground(Background.EMPTY);

                diagramBox.setTop(enunciado);
                diagramBox.setCenter(diagramPane);

                reductionSelect.getItems().add("-1 al exponente");
                reductionSelect.getItems().add("/2 al exponente");

                diagramPane.setStyle("-fx-background-color: DAE6F3;");
                lowerBox.setPadding(new Insets(50));
                lowerBox.getChildren().addAll(inputInstructions,inputErrorLabel,inputField,reductionInstructions,reductionSelect,baseCaseInstructions,baseCaseField,confirmDataButton);

                solutionSelection.getItems().add("multiplicar por 'a'");
                solutionSelection.getItems().add("multiplicar por 'b'");
                solutionSelection.getItems().add("elevar a 'a'");

                selectionBox.getChildren().addAll(solutionSelectionLabel,solutionSelection);
                inputField.setPromptText("Introduce los numeros de input");
        }
        private void bindModelData() {
                inputField.textProperty().bindBidirectional(model.getInputsProperty());
                originalData.textProperty().bind(model.originalDataProperty());
                originalSolution.textProperty().bind(model.originalSolProperty());
                partialData.textProperty().bind(model.partialDataProperty());
                partialSolution.textProperty().bind(model.partialSolProperty());
                baseCaseField.textProperty().bindBidirectional(model.baseCaseProperty());
        }



        
}
