package com.example;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DiagramView {
        private final GridPane root;
        private final Diagram model;
        TextField parameters = new TextField();
        Label originalDataSolutionArrow = new Label("=====================>");
        Label originalSolution = new Label();
        Label datasArrow = new Label(" |\n |\n |\n |\n |");
        ComboBox reductionSelect = new ComboBox();
        Label subParameters = new Label();
        Label partialSolution = new Label();
        ComboBox solutionSelect = new ComboBox();
        VBox NandBaseCaseBox = new VBox();
        ComboBox problemSizeSelect = new ComboBox();
        ComboBox baseCaseSelect = new ComboBox();
        Label partialDataSolutionArrow = new Label("=====================>");
        Label solutionsArrow = new Label(" |\n |\n |\n |\n |");
        Button confirmDataButton = new Button("Confirmar datos");

        public DiagramView(Diagram model) {
                this.model = model;
                this.root=new GridPane();
                setLayout();
                setStyle();
                bindModelData();
        }

        private void setLayout() {
                root.setHgap(10);
                root.setVgap(10);
                root.setPadding(new Insets(10, 10, 10, 10));

                NandBaseCaseBox.setPadding(new Insets(5,5,5,5));
                NandBaseCaseBox.getChildren().addAll(problemSizeSelect,baseCaseSelect);

                root.add(NandBaseCaseBox,0,0);
                root.add(parameters,1,0);
                root.add(originalDataSolutionArrow,2,0);
                root.add(originalSolution,3,0);
                root.add(datasArrow,1,1);
                root.add(reductionSelect,0,1);
                root.add(confirmDataButton,0,2);
                root.add(subParameters,1,3);
                root.add(partialDataSolutionArrow,2,3);
                root.add(partialSolution,3,3);
                root.add(solutionsArrow,3,1);
                root.add(solutionSelect,3,2);

        }
        private void setStyle() {
                root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
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
        public GridPane getRoot(){
                return this.root;
        }

        
}
