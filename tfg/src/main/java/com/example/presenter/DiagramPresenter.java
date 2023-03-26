package com.example.presenter;

import com.example.diagrams.BaseDiagram;
import com.example.exceptions.BaseCaseException;
import com.example.exceptions.IncorrectSelectionException;
import com.example.model.Arrow;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DiagramPresenter implements Initializable {
    @FXML
    AnchorPane root;
    protected BaseDiagram model;
    //private DiagramToCodeMapper mapper = new DiagramToCodeMapper();
    @FXML
    public TextArea heading;
    @FXML
    StackPane originalDataSolutionArrow;
    @FXML
    StackPane partialDataSolutionArrow;
    @FXML
    StackPane datasArrow;
    @FXML
    StackPane solutionsArrow;
    @FXML
    Label originalSolution;
    @FXML
    Label calculatedSolution;
    @FXML
    ComboBox<String> decompositionSelect;
    @FXML
    ComboBox<String> solutionSelect;
    @FXML
    VBox SizeAndBaseCaseBox;
    @FXML
    ComboBox<String> problemSizeSelect;
    @FXML
    ComboBox<String> baseCaseSelect;
    @FXML
    GridPane diagramGrid;
    @FXML
    VBox originalData;
    @FXML
    VBox subParameters;
    @FXML
    VBox subSolutions;
    private HashMap<String,String> errorMessageMap = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        //mapper.setCurrentDiagram(model);
        //model.setViewerData1();
        AnchorPane.setLeftAnchor(SizeAndBaseCaseBox,15.0);
        AnchorPane.setRightAnchor(diagramGrid,15.0);
        bindModelData();
        setArrows();
        setMessageMap();
        baseCaseSelect.setVisible(false);
        diagramGrid.setVisible(false);
        calculatedSolution.setVisible(false);
        decompositionSelect.setVisible(false);
        solutionSelect.setVisible(false);
    }

    private void setMessageMap() {
        errorMessageMap.put("RuntimeException","Revisa el contenido y que concuerde con el formato: "+model.getInputFormatting());
        errorMessageMap.put("BaseCaseException","Revisa el contenido, no puedes introducir un caso base o una solución como parámetro");
        errorMessageMap.put("IncorrectSelectionException","Esta opción es incorrecta, elige otra");
    }

    public DiagramPresenter(BaseDiagram model) {
        this.model = model;
    }
    protected void bindModelData() {
        originalSolution.textProperty().bind(model.originalSolProperty());
        model.currentProblemSizeProperty().bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
        model.currentBaseCaseProperty().bind(baseCaseSelect.getSelectionModel().selectedIndexProperty());
        model.currentReductionProperty().bind(decompositionSelect.getSelectionModel().selectedIndexProperty());
        model.selectedSolutionProperty().bind(solutionSelect.getSelectionModel().selectedIndexProperty());
        heading.textProperty().bind(model.headingProperty());
        //diagramTitle.textProperty().bind(model.viewerValues.get("diagramTitle"));
        //baseCase.textProperty().bind(model.viewerValues.get("baseCase"));
        //recursiveCase.textProperty().bind(model.viewerValues.get("recursiveCase"));

    }
    public void onChangeProblemSize(ActionEvent actionEvent) {
        if(problemSizeSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        if(!model.getCorrectSizeChoices().contains(problemSizeSelect.getSelectionModel().getSelectedIndex())){
            showErrorInputAlert(new IncorrectSelectionException("Opción incorrecta"));
            return;
        }
        baseCaseSelect.setVisible(true);
        baseCaseSelect.getItems().clear();
        if(!problemSizeSelect.getSelectionModel().isEmpty()){
            baseCaseSelect.getItems().setAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
        }
    }

    public void onChangeBaseCase(ActionEvent actionEvent) {
        if(baseCaseSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        if(!model.getCorrectBaseCases().get(model.getCurrentProblemSize()).contains(baseCaseSelect.getSelectionModel().getSelectedIndex())){
            showErrorInputAlert(new IncorrectSelectionException("Opción incorrecta"));
            return;
        }
        model.setCurrentBaseCaseIndex(baseCaseSelect.getSelectionModel().getSelectedIndex());
        if(originalData.getChildren().isEmpty()){
            List<TextField> tfs = new ArrayList<>();
            for(String s : model.getParams().keySet()){
                TextField tf = new TextField();
                tf.setPromptText(s);
                tf.setFont(originalSolution.getFont());
                tf.setMaxWidth(100);
                tf.setMaxHeight(30);
                tfs.add(tf);
                model.getParams().get(s).bindBidirectional(tf.textProperty());
                tf.setOnAction(actionEvent1 -> {
                    if(tfs.stream().noneMatch(tf1 -> tf1.getText().isEmpty())){
                        List<String> inputs = tfs.stream().map(ele -> ele.textProperty().get()).toList();
                        try {
                            if(model.checkNotBaseCase(model.getCurrentProblemSize(),inputs)) {
                                showErrorInputAlert(new BaseCaseException("No se puede introducir un caso base en el input"));
                            }
                            else{
                                try {
                                    model.processInputs();
                                    decompositionSelect.setVisible(true);
                                } catch (Exception e) {
                                    showErrorInputAlert(new RuntimeException("Error al introducir los datos de entrada"));
                                    e.printStackTrace();
                                }
                                refreshDiagram();
                            }
                        } catch (Exception e) {
                            showErrorInputAlert(new RuntimeException("Error al introducir los datos de entrada"));
                            e.printStackTrace();
                        }
                    }
                });
            }
            originalData.getChildren().addAll(tfs);
        }
        diagramGrid.setVisible(true);
    }
    public void onDescompositionChange(ActionEvent actionEvent) {
        if(decompositionSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        subSolutions.getChildren().clear();
        subParameters.getChildren().clear();
        solutionSelect.getItems().clear();
        //model.resetSubValues();
        handleDecomposition();
    }
    protected void handleDecomposition() {
        try {
            model.processSolutions();
        } catch (Exception e) {
            showErrorInputAlert(new RuntimeException("Error al introducir los datos de entrada"));
            e.printStackTrace();
        }
        for(SimpleStringProperty ele : model.getSubParameters()){
            Label lb = new Label();
            lb.setStyle("-fx-text-fill: white;");
            lb.setFont(originalSolution.getFont());
            lb.setText(ele.get());
            lb.setTextAlignment(TextAlignment.CENTER);
            subParameters.getChildren().add(lb);
        }
        for(SimpleStringProperty ele : model.getSubSolutions()){
            Label lb = new Label();
            lb.setStyle("-fx-text-fill: white;");
            lb.setFont(originalSolution.getFont());
            lb.setText(ele.get());
            lb.setTextAlignment(TextAlignment.CENTER);
            subSolutions.getChildren().add(lb);
        }
        solutionSelect.getItems().setAll(model.getSolutionsChoices().get(model.getCurrentReductionSolutions()));
        solutionSelect.setVisible(true);
    }

    public void onSolutionChange(ActionEvent actionEvent) {
        try{
            if(solutionSelect.getSelectionModel().getSelectedIndex()<0){
                return;
            }
            String calcSol = model.calculateWithSelectedOperation(solutionSelect.getSelectionModel().getSelectedIndex());
            calculatedSolution.setVisible(true);
            if(model.checkSolutionsEqual(calcSol)){
                //if solutions are equal BUT it is not the correct solution
                if(solutionSelect.getSelectionModel().getSelectedIndex() != model.getCorrectSolutions().get(model.getCurrentReductionSolutions())){
                    calculatedSolution.setText("Incorrecto! La operación da esta solución pero no para todos los casos\nValor calculado: "+model.getCalculatedSol());
                    calculatedSolution.setStyle("-fx-text-fill: #f2433a;");
                }
                else{
                    calculatedSolution.setText("Correcto!\nValor calculado: "+model.getCalculatedSol());
                    calculatedSolution.setStyle("-fx-text-fill: #48f542;");
                }
            }
            else{
                calculatedSolution.setText("Incorrecto! Vuelve a intentarlo\nValor calculado: "+model.getCalculatedSol());
                calculatedSolution.setStyle("-fx-text-fill: #f2433a;");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showErrorInputAlert(Exception e){
        Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputErrorAlert.setTitle("Error");
        inputErrorAlert.setHeaderText(e.getMessage());
        inputErrorAlert.setContentText(errorMessageMap.get(e.getClass().getSimpleName()));
        inputErrorAlert.showAndWait();
    }
    private void refreshDiagram(){
        solutionSelect.setVisible(false);
        calculatedSolution.setVisible(false);
        decompositionSelect.getItems().clear();
        decompositionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));

    }
    @FXML
    public void returnToMenu(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de que quieres volver al menú?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get()==ButtonType.OK){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
            Pane pane = loader.load();
            diagramGrid.getScene().setRoot(pane);
        }
        else{
            alert.close();
        }
    }
    @FXML
    public void resetDiagram(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de que quieres borrar todos los valores introducidos?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get()==ButtonType.OK){
            problemSizeSelect.getSelectionModel().clearSelection();
            baseCaseSelect.getSelectionModel().clearSelection();
            subSolutions.getChildren().clear();
            subParameters.getChildren().clear();
            solutionSelect.getItems().clear();
            originalData.getChildren().clear();
            model.resetSubValues();
            calculatedSolution.setVisible(false);
            model.originalSolProperty().set("");
            diagramGrid.setVisible(false);
            decompositionSelect.setVisible(false);
            solutionSelect.setVisible(false);
        }
        else{
            alert.close();
        }

    }
    private void setArrows() {
        originalDataSolutionArrow.getChildren().add(returnArrow(60,0,250,0));
        partialDataSolutionArrow.getChildren().add(returnArrow(60,0,250,0));
        datasArrow.getChildren().add(returnArrow(0,0,0,60));
        solutionsArrow.getChildren().add(returnArrow(0,60,0,0));
    }
    private Arrow returnArrow(double startX, double startY, double endX, double endY){
        Arrow arrow = new Arrow();
        arrow.setStartX(startX);
        arrow.setStartY(startY);
        arrow.setEndX(endX);
        arrow.setEndY(endY);
        return arrow;
    }
}
