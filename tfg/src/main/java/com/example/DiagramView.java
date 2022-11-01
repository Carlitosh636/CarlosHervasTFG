package com.example;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DiagramView extends GridPane {
        private final Diagram model;
        /*TextArea enunciado = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc quis sem lectus. Maecenas vitae volutpat felis.\nVivamus fringilla pellentesque tincidunt.\nNulla et nisl ac tortor tempus sagittis id ac turpis. Ut sed posuere justo. Nam sollicitudin metus ac iaculis tempus.\nEtiam fermentum tristique tellus in blandit. Phasellus non congue ante. Aenean nec lorem ipsum.\n" +
                "\n");
        Label originalData = new Label();
        Label originalDataSolutionArrow = new Label("=====================>");
        Label originalSolution = new Label();
        Label datasArrow = new Label(" |\n |\n |\n |\n |");
        Label emptylabel = new Label();
        Label partialData = new Label();
        Label partialSolution = new Label();
        Label partialDataSolutionArrow = new Label("=====================>");
        GridPane diagramPane = new GridPane();
        BorderPane diagramBox = new BorderPane();
        VBox lowerBox = new VBox();
        Label inputErrorLabel = new Label("El valor introducido no es correcto para el problema. Introduzca datos válidos");
        Label inputInstructions=new Label("Escribe la base (a) y el exponente (b), separados por una coma");
        TextField inputField = new TextField();
        Label reductionInstructions=new Label("Elige el método de reducción");
        ComboBox reductionSelect = new ComboBox();
        Label baseCaseInstructions=new Label("Introduce el caso base");
        TextField baseCaseField= new TextField();
        Button confirmDataButton = new Button("Introducir valores");
        VBox selectionBox = new VBox();
        Label solutionSelectionLabel = new Label("Selecciona el paso restante (marcado con ??)");
        ComboBox solutionSelection = new ComboBox();
        Button submitSolution = new Button("Introducir solución");
        Label solutionStatus = new Label();*/

        TextField parameters = new TextField();
        Label originalDataSolutionArrow = new Label("=====================>");
        Label originalSolution = new Label();

        Label datasArrow = new Label(" |\n |\n |\n |\n |");
        ComboBox reductionSelect = new ComboBox();
        Label subParameters = new Label();
        Label partialSolution = new Label();
        ComboBox solutionSelection = new ComboBox();
        Label partialDataSolutionArrow = new Label("=====================>");
        Label emptylabel = new Label();
        Label solutionsArrow = new Label(" |\n |\n |\n |\n |");


        public DiagramView(Diagram model) {
                this.model = model;
                setLayout();
                bindModelData();

        }
        private void setLayout() {
                this.setHgap(10);
                this.setVgap(10);
                this.add(parameters,0,0);
                this.add(originalDataSolutionArrow,1,0);
                this.add(originalSolution,2,0);
                this.add(datasArrow,0,1);
                this.add(reductionSelect,0,2);
                this.add(subParameters,0,3);
                this.add(partialDataSolutionArrow,1,3);
                this.add(partialSolution,2,3);
                this.add(solutionsArrow,2,1);
                this.add(solutionSelection,2,2);

                this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");


                reductionSelect.getItems().add("-1 al exponente");
                reductionSelect.getItems().add("/2 al exponente");

        }
        private void bindModelData() {
                parameters.textProperty().bindBidirectional(model.getInputsProperty());
                originalSolution.textProperty().bind(model.originalSolProperty());
                subParameters.textProperty().bind(model.partialDataProperty());
                partialSolution.textProperty().bind(model.partialSolProperty());
        }



        
}
