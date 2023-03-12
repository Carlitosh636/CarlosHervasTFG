package com.example.presenter;

import com.example.diagrams.BaseDiagram;
import com.example.exceptions.BaseCaseException;
import com.example.model.Arrow;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    VBox partialSolutions;
    @FXML
    VBox diagramGridHolder;
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
        setArrows();
        baseCaseSelect.setVisible(false);
        diagramPart1.forEach(ele->ele.setVisible(false));
        diagramPart2.forEach(ele->ele.setVisible(false));
        diagramPart3.forEach(ele->ele.setVisible(false));
        calculatedSolution.setVisible(false);
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
    public void onChangeProblemSize(ActionEvent actionEvent) {
        baseCaseSelect.setVisible(true);
        baseCaseSelect.getItems().clear();
        if(!problemSizeSelect.getSelectionModel().isEmpty()){
            baseCaseSelect.getItems().setAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
        }
    }

    public void onChangeBaseCase(ActionEvent actionEvent) {
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
                    if(tfs.stream().anyMatch(tf1 -> tf1.getText().isEmpty())){
                        diagramPart2.forEach(ele->ele.setVisible(false));
                    }
                    else{
                        List<String> inputs = tfs.stream().map(ele -> ele.textProperty().get()).toList();
                        try {
                            if(model.checkNotBaseCase(model.getCurrentProblemSize()+model.getCurrentBaseCaseIndex(),inputs)) {
                                showErrorInputAlert(new BaseCaseException("Cannot introduce a base case in parameters"));
                            }
                            else{
                                try {
                                    model.processInputs();
                                } catch (Exception e) {
                                    showErrorInputAlert(e);
                                    System.out.println(e);
                                }
                                refreshDiagram();
                            }
                        } catch (Exception e) {
                            showErrorInputAlert(e);
                            System.out.println(e);
                        }
                    }


                });
            }
            originalData.getChildren().addAll(tfs);
        }

        diagramPart1.forEach(ele->ele.setVisible(true));
    }
    public void onDescompositionChange(ActionEvent actionEvent) {
        partialSolutions.getChildren().clear();
        subParameters.getChildren().clear();
        solutionSelect.getItems().clear();
        handleDecomposition();
        diagramPart3.forEach(ele->ele.setVisible(true));
    }
    protected void handleDecomposition() {
        try {
            model.processSolutions();
        } catch (Exception e) {
            showErrorInputAlert(e);
            System.out.println(e);
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
            partialSolutions.getChildren().add(lb);
        }
        solutionSelect.getItems().setAll(model.getSolutionsChoices().get(model.getCurrentReductionSolutions()));
    }
    public void onSolutionChange(ActionEvent actionEvent) {
        try{
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
            diagramPart2.forEach(ele->ele.setVisible(false));
            diagramPart3.forEach(ele->ele.setVisible(false));
        }
        inputErrorAlert.showAndWait();
    }
    private void refreshDiagram(){
        diagramPart2.forEach(ele->ele.setVisible(true));
        diagramPart3.forEach(ele->ele.setVisible(false));
        calculatedSolution.setVisible(false);
        decompositionSelect.getItems().clear();
        decompositionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));

    }
    @FXML
    public void returnToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
        Pane pane = loader.load();
        diagramGrid.getScene().setRoot(pane);
    }
    @FXML
    public void resetDiagram(ActionEvent actionEvent) {
        problemSizeSelect.getSelectionModel().clearSelection();
        baseCaseSelect.getSelectionModel().clearSelection();
        partialSolutions.getChildren().clear();
        subParameters.getChildren().clear();
        solutionSelect.getItems().clear();
        originalData.getChildren().clear();
        calculatedSolution.setVisible(false);
        model.originalSolProperty().set("");
        diagramPart1.forEach(ele->ele.setVisible(false));
        diagramPart2.forEach(ele->ele.setVisible(false));
        diagramPart3.forEach(ele->ele.setVisible(false));
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
