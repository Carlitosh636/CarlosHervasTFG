package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class DiagramPresenter implements Initializable {
    protected Diagram model;
    @FXML
    TextField parameters;
    @FXML
    Label originalDataSolutionArrow;
    @FXML
    Label originalSolution;
    @FXML
    ComboBox reductionSelect;
    @FXML
    ComboBox solutionSelect;
    @FXML
    VBox NandBaseCaseBox;
    @FXML
    ComboBox problemSizeSelect;
    @FXML
    ComboBox baseCaseSelect;
    @FXML
    Label partialDataSolutionArrow;
    @FXML
    Button confirmDataButton;
    @FXML
    GridPane diagramGrid;
    @FXML
    Label originalData;
    @FXML
    public Label parametersFormat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        reductionSelect.setVisible(false);
        baseCaseSelect.setVisible(false);
        diagramGrid.setVisible(false);
        bindModelData();
    }
    public DiagramPresenter(Diagram model) {
        this.model = model;
    }
    protected void bindModelData() {
        parameters.textProperty().bindBidirectional(model.getInputsProperty());
        originalSolution.textProperty().bind(model.originalSolProperty());
        originalData.textProperty().bind(model.originalDataProperty());
        parametersFormat.textProperty().bind(model.parametersFormatProperty());
    }
    @FXML
    protected void handleInput() {
        try {
            model.processInputs();
            diagramGrid.setVisible(true);
            solutionSelect.getItems().clear();
            solutionSelect.getItems().setAll(model.solutionsChoices.get(model.getCurrentReductionSolutions()));

        } catch (Exception e) {
            showErrorInputAlert(e);
        }
    }
    public void onChangeProblemSize(ActionEvent actionEvent) {
        model.setCurrentProblemSize(problemSizeSelect.getSelectionModel().getSelectedIndex());
        baseCaseSelect.setVisible(true);
        baseCaseSelect.getItems().clear();
        baseCaseSelect.getItems().setAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
    }

    public void onChangeBaseCase(ActionEvent actionEvent) {
        model.setCurrentBaseCaseIndex(baseCaseSelect.getSelectionModel().getSelectedIndex());
        reductionSelect.setVisible(true);
        reductionSelect.getItems().clear();
        reductionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
    }

    public void onChangeReduction(ActionEvent actionEvent) {
        model.setCurrentReduction(reductionSelect.getSelectionModel().getSelectedIndex());
    }

    public void onSolutionChange(ActionEvent actionEvent) {
        if(solutionSelect.getSelectionModel().getSelectedIndex() == model.getCorrectChoices().get(model.getCurrentReductionSolutions())){
            System.out.println("Correcto!");
        }
        else{
            System.out.println("Incorrecto! Vuelve a intentarlo");
        }
    }
    public void showErrorInputAlert(Exception e){
        Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputErrorAlert.setTitle("Error");
        if(e instanceof RuntimeException){
            inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
            inputErrorAlert.setContentText("Revisa el contenido y que concuerde con el formato");
        }
        if(e instanceof BaseCaseException){
            inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
            inputErrorAlert.setContentText("Revisa el contenido, no puedes introducir un caso base como parámetro");
        }
        //si no se que es el error, poner el básico "introducelo de nuevo"
        else{
            inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
            inputErrorAlert.setContentText("Revisa el contenido y que concuerde con el formato");
        }
        inputErrorAlert.showAndWait();
    }
}
