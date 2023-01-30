package com.example;

import com.github.mustachejava.Mustache;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
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
    Label originalData;
    @FXML
    Label parametersFormat;
    @FXML
    Label isCorrect;
    @FXML
    WebView htmlViewer;
    WebEngine webEngine;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        reductionSelect.setVisible(false);
        baseCaseSelect.setVisible(false);
        diagramGrid.setVisible(false);
        parameters.setVisible(false);
        webEngine = htmlViewer.getEngine();
        try {
            loadPage("index.mustache");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        calculatedSolution.textProperty().bind(model.calculatedSolProperty());
        model.currentProblemSize.bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
        model.currentBaseCase.bind(baseCaseSelect.getSelectionModel().selectedIndexProperty());
        model.currentReduction.bind(reductionSelect.getSelectionModel().selectedIndexProperty());

    }
    @FXML
    protected void handleInput() {
        try {
            model.processInputs();
            //ver si se puede sustituir por una función reload
            loadPage("index.mustache");
            diagramGrid.setVisible(true);
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
        parameters.getScene().setRoot(pane);
    }
    public void onChangeProblemSize(ActionEvent actionEvent) {
        baseCaseSelect.setVisible(true);
        baseCaseSelect.getItems().clear();
        baseCaseSelect.getItems().setAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
    }

    public void onChangeBaseCase(ActionEvent actionEvent) {
        model.setCurrentBaseCaseIndex(baseCaseSelect.getSelectionModel().getSelectedIndex());
        reductionSelect.setVisible(true);
        parameters.setVisible(true);
        reductionSelect.getItems().clear();
        reductionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
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
        //si no se que es el error, poner el básico "introducelo de nuevo"
        else{
            inputErrorAlert.setHeaderText("Error al introducir los datos de entrada");
            inputErrorAlert.setContentText("Ha ocurrido un error desconocido");
        }
        inputErrorAlert.showAndWait();
    }
    public void loadPage(String path) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile(path);
        StringWriter writer = new StringWriter();
        Map<String,String> viewValues = model.parseValuesForViewer();
        m.execute(writer,new DiagramWebViewer(
                viewValues.get("diagramTitle"),
                viewValues.get("parameters"),
                viewValues.get("baseCase"),
                viewValues.get("returnValue"),
                viewValues.get("recursiveCase")
        )).flush();
        //debe ser un loadContent ya que estamos cargando un String, no una URL
        webEngine.loadContent(writer.toString());
    }
}
