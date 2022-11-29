package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DiagramPresenter implements Initializable {
    private Diagram model;
    @FXML
    TextField parameters = new TextField();
    @FXML
    Label originalDataSolutionArrow = new Label("=====================>");
    @FXML
    Label originalSolution = new Label();
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
    Button confirmDataButton = new Button("Confirmar datos");

    public DiagramPresenter() {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        solutionSelect.setVisible(false);
        reductionSelect.setVisible(false);
        baseCaseSelect.setVisible(false);
        bindModelData();
    }
    public DiagramPresenter(Diagram model) {
        this.model = model;
    }
    private void bindModelData() {
        parameters.textProperty().bindBidirectional(model.getInputsProperty());
        originalSolution.textProperty().bind(model.originalSolProperty());
        subParameters.textProperty().bind(model.partialDataProperty());
        partialSolution.textProperty().bind(model.partialSolProperty());
    }
    @FXML
    private void handleInput() {
        try {
            model.processInputs();
            solutionSelect.setVisible(true);
            solutionSelect.getItems().clear();
            solutionSelect.getItems().setAll(model.solutionsChoices.get(model.getCurrentReductionSolutions()));

        } catch (Exception e) {
            showErrorInputAlert();
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
    public void showErrorInputAlert(){
        Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputErrorAlert.setTitle("Error");
        inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
        inputErrorAlert.setContentText("Revisa el contenido, debe ser en el formato 'A,B' ");
        inputErrorAlert.showAndWait();
    }
}
