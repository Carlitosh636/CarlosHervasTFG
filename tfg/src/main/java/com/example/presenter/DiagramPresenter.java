package com.example.presenter;

import com.example.diagrams.BaseDiagram;
import com.example.exceptions.BaseCaseException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    protected BaseDiagram model;
    //private DiagramToCodeMapper mapper = new DiagramToCodeMapper();
    @FXML
    public TextArea heading;
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
    public DiagramPresenter(BaseDiagram model) {
        this.model = model;
    }
    protected void bindModelData() {
        originalSolution.textProperty().bind(model.originalSolProperty());
        calculatedSolution.textProperty().bind(model.calculatedSolProperty());
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
    @FXML
    protected void handleInput() {
        try {
            subParameters.getChildren().clear();
            partialSolutions.getChildren().clear();
            model.processInputs();
        } catch (Exception e) {
            showErrorInputAlert(e);
            System.out.println(e);
        }
        for(SimpleStringProperty ele : model.getSubParameters()){
            Label lb = new Label();
            lb.setStyle("-fx-text-fill: white;"+originalSolution.getStyle());
            lb.setFont(originalSolution.getFont());
            lb.setText(ele.get());
            subParameters.getChildren().add(lb);
        }
        for(SimpleStringProperty ele : model.getSubSolutions()){
            Label lb = new Label();
            lb.setStyle("-fx-text-fill: white;"+originalSolution.getStyle());
            lb.setFont(originalSolution.getFont());
            lb.setText(ele.get());
            partialSolutions.getChildren().add(lb);
        }
        solutionSelect.getItems().clear();
        //aquí probablemente debería haber un control con semáforos
        solutionSelect.getItems().setAll(model.getSolutionsChoices().get(model.getCurrentReductionSolutions()));
    }
    @FXML
    public void returnToMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
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
            model.getParams().get(s).bindBidirectional(tf.textProperty());
            tf.setOnAction(actionEvent1 -> {
                if(tfs.stream().anyMatch(tf1 -> tf1.getText().isEmpty())){
                    diagramPart2.forEach(ele->ele.setVisible(false));
                }
                else if(model.checkNotBaseCase(model.getCurrentProblemSize()+model.getCurrentBaseCaseIndex())) {
                    showErrorInputAlert(new BaseCaseException("Cannot introduce a base case in parameters"));
                }
                else diagramPart2.forEach(ele->ele.setVisible(true));
            });
        }
        /*tfs.forEach(v->{
            v.textProperty().addListener(((observableValue, s, t1) -> {

            }));
        });*/
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
    public void onSolutionChange(ActionEvent actionEvent) {
        try{
            String calcSol = model.calculateWithSelectedOperation(solutionSelect.getSelectionModel().getSelectedIndex());
            if(model.checkSolutionsEqual(calcSol)){
                //if solutions are equal BUT it is not the correct solution
                if(solutionSelect.getSelectionModel().getSelectedIndex() != model.getCorrectSolutions().get(model.getCurrentReductionSolutions())){
                    calculatedSolution.setStyle("-fx-text-fill: #f2433a;");
                    isCorrect.setText("Incorrecto! La operación da esta solución pero no para todos los casos");
                }
                else{
                    calculatedSolution.setStyle("-fx-text-fill: #48f542;");
                    isCorrect.setText("Correcto!");
                }

            }
            else{
                calculatedSolution.setStyle("-fx-text-fill: #f2433a;");
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
