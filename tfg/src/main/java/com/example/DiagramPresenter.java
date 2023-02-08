package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DiagramPresenter implements Initializable {
    @FXML
    AnchorPane root;
    protected Diagram model;
    //private DiagramToCodeMapper mapper = new DiagramToCodeMapper();
    @FXML
    Line originalDataSolutionArrow;
    @FXML
    Label originalSolution;
    @FXML
    Label calculatedSolution;
    @FXML
    ComboBox decompositionSelect;
    @FXML
    ComboBox solutionSelect;
    @FXML
    VBox SizeAndBaseCaseBox;
    @FXML
    ComboBox problemSizeSelect;
    @FXML
    ComboBox baseCaseSelect;
    @FXML
    Line partialDataSolutionArrow;
    @FXML
    Button confirmDataButton;
    @FXML
    GridPane diagramGrid;
    @FXML
    Label isCorrect;
    @FXML
    VBox originalData;
    @FXML
    VBox subParameters;
    @FXML
    VBox partialSolutions;
    @FXML
    Line datasArrow;
    @FXML
    Line solutionsArrow;
    List<Node> diagramPart1 = new ArrayList<>();
    List<Node> diagramPart2 = new ArrayList<>();
    List<Node> diagramPart3 = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        //mapper.setCurrentDiagram(model);
        //model.setViewerData1();
        AnchorPane.setLeftAnchor(SizeAndBaseCaseBox,15.0);
        AnchorPane.setRightAnchor(diagramGrid,15.0);
        bindModelData();
        baseCaseSelect.setVisible(false);
        diagramPart1.forEach(ele->ele.setVisible(false));
        diagramPart2.forEach(ele->ele.setVisible(false));
        diagramPart3.forEach(ele->ele.setVisible(false));
    }
    public DiagramPresenter(Diagram model) {
        this.model = model;
    }
    protected void bindModelData() {
        originalSolution.textProperty().bind(model.originalSolProperty());
        calculatedSolution.textProperty().bind(model.calculatedSolProperty());
        model.currentProblemSize.bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
        model.currentBaseCase.bind(baseCaseSelect.getSelectionModel().selectedIndexProperty());
        model.currentReduction.bind(decompositionSelect.getSelectionModel().selectedIndexProperty());
        model.selectedSolution.bind(solutionSelect.getSelectionModel().selectedIndexProperty());

        diagramPart1.add(originalData);
        diagramPart1.add(originalDataSolutionArrow);
        diagramPart1.add(originalSolution);

        diagramPart2.add(datasArrow);
        diagramPart2.add(solutionsArrow);
        diagramPart2.add(decompositionSelect);

        diagramPart3.add(partialSolutions);
        diagramPart3.add(subParameters);
        diagramPart3.add(partialDataSolutionArrow);
        diagramPart3.add(solutionSelect);

        //diagramTitle.textProperty().bind(model.viewerValues.get("diagramTitle"));
        //baseCase.textProperty().bind(model.viewerValues.get("baseCase"));
        //recursiveCase.textProperty().bind(model.viewerValues.get("recursiveCase"));

    }
    @FXML
    protected void handleInput() {
        try {
            model.processInputs();
            for(SimpleStringProperty ele : model.getSubParameters()){
                Label lb = new Label();
                lb.setStyle(originalSolution.getStyle());
                lb.setFont(originalSolution.getFont());
                lb.setText(ele.get());
                subParameters.getChildren().add(lb);
            }
            for(SimpleStringProperty ele : model.getSubSolutions()){
                Label lb = new Label();
                lb.setStyle(originalSolution.getStyle());
                lb.setFont(originalSolution.getFont());
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
        List<TextField> tfs = new ArrayList<>();
        for(String s : model.getParams().keySet()){
            TextField tf = new TextField();
            tf.setPromptText(s);
            tf.setFont(originalSolution.getFont());
            tf.setMaxWidth(100);
            tf.setMaxHeight(30);
            tfs.add(tf);
            model.getParams().get(s).bind(tf.textProperty());
            /*tf.setOnAction(actionEvent1 -> {

            });*/
        }
        tfs.forEach(v->{
            v.textProperty().addListener(((observableValue, s, t1) -> {
                if(tfs.stream().anyMatch(tf1 -> tf1.getText().isEmpty())){
                    System.out.println("hay uno vacío");
                }
                else diagramPart2.forEach(ele->ele.setVisible(true));
            }));
        });
        originalData.getChildren().addAll(tfs);
        decompositionSelect.getItems().clear();
        decompositionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
        diagramPart1.forEach(ele->ele.setVisible(true));
        /*try{
            model.viewerValues.get("baseCase").set(DiagramToCodeMapper.mapBaseCases());
        }
        catch (Exception e){
            System.out.println(e);
        }*/
    }
    public void onDescompositionChange(ActionEvent actionEvent) {
        handleInput();
        diagramPart3.forEach(ele->ele.setVisible(true));
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
