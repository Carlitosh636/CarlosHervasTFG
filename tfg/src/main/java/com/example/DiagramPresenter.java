package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DiagramPresenter implements Initializable {
    protected Diagram model;
    //private DiagramToCodeMapper mapper = new DiagramToCodeMapper();
    @FXML
    Label originalData;
    @FXML
    Label originalDataSolutionArrow;
    @FXML
    Label originalSolution;
    @FXML
    Label calculatedSolution;
    @FXML
    ComboBox reductionSelect;
    @FXML
    ComboBox solutionSelect;
    @FXML
    VBox SizeAndBaseCaseBox;
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
    Label isCorrect;
    @FXML
    VBox parametersList;
    @FXML
    VBox subParameters;
    @FXML
    VBox partialSolutions;

    /*subParameters.textProperty().bind(model.partialDataPropertyByIndex(0));
        partialSolution.textProperty().bind(model.partialSolPropertyByIndex(0));*/

    /*@FXML
    private Label diagramTitle;
    @FXML
    private Label baseCase;
    @FXML
    private Label recursiveCase;*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        reductionSelect.setVisible(false);
        baseCaseSelect.setVisible(false);
        diagramGrid.setVisible(false);

        //mapper.setCurrentDiagram(model);
        //model.setViewerData1();
        bindModelData();
    }
    public DiagramPresenter(Diagram model) {
        this.model = model;
    }
    protected void bindModelData() {
        originalSolution.textProperty().bind(model.originalSolProperty());
        originalData.textProperty().bind(model.originalDataProperty());
        calculatedSolution.textProperty().bind(model.calculatedSolProperty());
        model.currentProblemSize.bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
        model.currentBaseCase.bind(baseCaseSelect.getSelectionModel().selectedIndexProperty());
        model.currentReduction.bind(reductionSelect.getSelectionModel().selectedIndexProperty());
        model.selectedSolution.bind(solutionSelect.getSelectionModel().selectedIndexProperty());

        //diagramTitle.textProperty().bind(model.viewerValues.get("diagramTitle"));
        //baseCase.textProperty().bind(model.viewerValues.get("baseCase"));
        //recursiveCase.textProperty().bind(model.viewerValues.get("recursiveCase"));

    }
    @FXML
    protected void handleInput() {
        try {
            model.processInputs();
            diagramGrid.setVisible(true);
            for(SimpleStringProperty ele : model.getSubParameters()){
                Label lb = new Label();
                lb.setStyle(originalData.getStyle());
                lb.setFont(originalData.getFont());
                lb.setText(ele.get());
                subParameters.getChildren().add(lb);
            }
            for(SimpleStringProperty ele : model.getSubSolutions()){
                Label lb = new Label();
                lb.setStyle(originalSolution.getStyle());
                lb.setFont(originalData.getFont());
                lb.setText(ele.get());
                partialSolutions.getChildren().add(lb);
            }
            solutionSelect.getItems().clear();
            //aquí probablemente debería haber un control con semáforos
            solutionSelect.getItems().setAll(model.solutionsChoices.get(model.getCurrentReductionSolutions()));
        } catch (Exception e) {
            showErrorInputAlert(e);
        }
    }
    @FXML
    public void returnToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DiagramSelector.fxml"));
        Pane pane = loader.load();
        diagramGrid.getScene().setRoot(pane);
    }
    public void onChangeProblemSize(ActionEvent actionEvent) {
        baseCaseSelect.setVisible(true);
        baseCaseSelect.getItems().clear();
        baseCaseSelect.getItems().setAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
    }

    public void onChangeBaseCase(ActionEvent actionEvent) {
        model.setCurrentBaseCaseIndex(baseCaseSelect.getSelectionModel().getSelectedIndex());
        reductionSelect.setVisible(true);
        for(String s : model.getParams().keySet()){
            TextField tf = new TextField();
            tf.setPromptText(s);
            tf.setMaxWidth(300);
            tf.setMaxHeight(20);
            parametersList.getChildren().add(tf);
            //une cada parámetro con cada textfield
            model.getParams().get(s).bind(tf.textProperty());
        }
        reductionSelect.getItems().clear();
        reductionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
        /*try{
            model.viewerValues.get("baseCase").set(DiagramToCodeMapper.mapBaseCases());
        }
        catch (Exception e){
            System.out.println(e);
        }*/
    }

    public void onSolutionChange(ActionEvent actionEvent) throws Exception {
        try{
            String calcSol = model.calculate(solutionSelect.getSelectionModel().getSelectedIndex());
            if(model.checkSolutionsEqual(calcSol)){
                //if solutions are equal BUT it is not the correct solution
                if(solutionSelect.getSelectionModel().getSelectedIndex() != model.getCorrectSolutions().get(model.getCurrentReductionSolutions())){
                    calculatedSolution.setStyle("-fx-text-fill: red;");
                    isCorrect.setText("Incorrecto! La operación da esta solución pero no para todos los casos");
                }
                else{
                    calculatedSolution.setStyle("-fx-text-fill: green;");
                    isCorrect.setText("Correcto!");
                }

            }
            else{
                calculatedSolution.setStyle("-fx-text-fill: red;");
                isCorrect.setText("Incorrecto! Vuelve a intentarlo");
            }
        }
        catch (Exception e){
            System.out.println(e);
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
            inputErrorAlert.setContentText("Revisa el contenido, no puedes introducir un caso base o una solución como parámetro");
        }
        else{
            inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
            inputErrorAlert.setContentText("Ha ocurrido un error desconocido");
        }
        inputErrorAlert.showAndWait();
    }
}
