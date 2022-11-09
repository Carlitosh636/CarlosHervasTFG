package com.example;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DiagramView extends GridPane {
        private final Diagram model;
        TextField parameters = new TextField();
        Label originalDataSolutionArrow = new Label("=====================>");
        Label originalSolution = new Label();

        Label datasArrow = new Label(" |\n |\n |\n |\n |");
        ComboBox reductionSelect = new ComboBox();
        Label subParameters = new Label();
        Label partialSolution = new Label();
        ComboBox solutionSelect = new ComboBox();
        Label partialDataSolutionArrow = new Label("=====================>");
        Label solutionsArrow = new Label(" |\n |\n |\n |\n |");

        public DiagramView(Diagram model) {
                this.model = model;
                setLayout();
                setStyle();
                bindModelData();
        }

        private void setLayout() {
                this.setHgap(10);
                this.setVgap(10);
                this.setPadding(new Insets(10, 10, 10, 10));
                this.add(parameters,0,0);
                this.add(originalDataSolutionArrow,1,0);
                this.add(originalSolution,2,0);
                this.add(datasArrow,0,1);
                this.add(reductionSelect,0,2);
                this.add(subParameters,0,3);
                this.add(partialDataSolutionArrow,1,3);
                this.add(partialSolution,2,3);
                this.add(solutionsArrow,2,1);
                this.add(solutionSelect,2,2);

        }
        private void setStyle() {
                this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

                parameters.setStyle("-fx-font: 24 arial;");
                reductionSelect.setStyle("-fx-font: 24 arial;");
                solutionSelect.setStyle("-fx-font: 24 arial;");
                originalDataSolutionArrow.setStyle("-fx-font: 24 arial;");
                originalSolution.setStyle("-fx-font: 24 arial;");
                datasArrow.setStyle("-fx-font: 24 arial;");
                subParameters.setStyle("-fx-font: 24 arial;");
                partialDataSolutionArrow.setStyle("-fx-font: 24 arial;");
                partialSolution.setStyle("-fx-font: 24 arial;");
                solutionsArrow.setStyle("-fx-font: 24 arial;");
        }
        private void bindModelData() {
                parameters.textProperty().bindBidirectional(model.getInputsProperty());
                originalSolution.textProperty().bind(model.originalSolProperty());
                subParameters.textProperty().bind(model.partialDataProperty());
                partialSolution.textProperty().bind(model.partialSolProperty());
        }
        public void showErrorInputAlert(){
                Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
                inputErrorAlert.setTitle("Error");
                inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
                inputErrorAlert.setContentText("Revisa el contenido, debe ser en el formato 'A,B' ");
                inputErrorAlert.showAndWait();
        }


        
}
