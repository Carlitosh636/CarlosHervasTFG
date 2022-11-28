package com.example;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DiagramView {
        private GridPane root;

        public void setModel(Diagram model) {
                this.model = model;
        }

        private Diagram model;
        @FXML
        TextField parameters = new TextField();
        @FXML
        Label originalDataSolutionArrow = new Label("=====================>");
        @FXML
        Label originalSolution = new Label();
        @FXML
        Label datasArrow = new Label(" |\n |\n |\n |\n |");
        @FXML
        ComboBox reductionSelect = new ComboBox();
        @FXML
        Label subParameters = new Label();
        @FXML
        Label partialSolution = new Label();
        @FXML
        ComboBox solutionSelect = new ComboBox();
        @FXML
        VBox NandBaseCaseBox = new VBox();
        @FXML
        ComboBox problemSizeSelect = new ComboBox();
        @FXML
        ComboBox baseCaseSelect = new ComboBox();
        @FXML
        Label partialDataSolutionArrow = new Label("=====================>");
        @FXML
        Label solutionsArrow = new Label(" |\n |\n |\n |\n |");
        @FXML
        Button confirmDataButton = new Button("Confirmar datos");

        public DiagramView(){

        }
        public DiagramView(Diagram model) {
                this.model = model;
                this.root=new GridPane();
                bindModelData();
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
