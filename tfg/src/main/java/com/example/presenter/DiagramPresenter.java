package com.example.presenter;

import com.example.diagrams.BaseDiagram;
import com.example.enums.DiagramType;
import com.example.exceptions.BaseCaseException;
import com.example.exceptions.IncorrectSelectionException;
import com.example.model.Arrow;
import com.example.model.GeneratorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import javafx.beans.property.SimpleStringProperty;
import com.github.mustachejava.MustacheFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.*;

public class DiagramPresenter implements Initializable {
    @FXML
    WebView generatedCodeTemplate;
    WebEngine generatedCodeWebEngine;
    @FXML
    AnchorPane root;
    protected BaseDiagram model;
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
    private final HashMap<String,String> errorMessageMap = new HashMap<>();
    private final Map<String,String> generatedCodeText = new HashMap<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
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
        subParameters.setVisible(false);
        subSolutions.setVisible(false);

        generatedCodeWebEngine = generatedCodeTemplate.getEngine();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("example.mustache");
        ObjectMapper objMapper = new ObjectMapper();
        GeneratorData data;
        try {
            data = objMapper.readValue(new File("generatedData/RecursivePotencyGeneration.json"),GeneratorData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringWriter wr = new StringWriter();
        String html;
        try {
            m.execute(wr,data).flush();
            html = wr.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generatedCodeWebEngine.loadContent(html);
    }

    private void setMessageMap() {
        errorMessageMap.put("RuntimeException","Revisa el contenido y que concuerde con el formato: "+model.getInputFormatting());
        errorMessageMap.put("BaseCaseException","Revisa el contenido, no puedes introducir un caso base o una solución como parámetro");
        errorMessageMap.put("IncorrectSelectionException","Esta opción es incorrecta, elige otra");
    }

    public DiagramPresenter(BaseDiagram model) throws IOException {
        this.model = model;
    }
    protected void bindModelData() {
        originalSolution.textProperty().bind(model.originalSolProperty());
        model.currentProblemSizeProperty().bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
        model.currentBaseCaseProperty().bind(baseCaseSelect.getSelectionModel().selectedIndexProperty());
        model.currentReductionProperty().bind(decompositionSelect.getSelectionModel().selectedIndexProperty());
        model.selectedSolutionProperty().bind(solutionSelect.getSelectionModel().selectedIndexProperty());
        heading.textProperty().bind(model.headingProperty());
    }
    public void onChangeProblemSize() {
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
        //generatedCodeText.put("functionName",generatorData.functionName.get(0));
        //generatedCode.textProperty().set(generatedCodeText.get("functionName")+generatedCodeText.get("baseCases")+generatedCodeText.get("recursiveCases"));
    }
    public void onChangeBaseCase() {
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
        //generatedCodeText.put("baseCases",generatorData.baseCases.get(model.currentProblemSizeProperty().get()).get(baseCaseSelect.getSelectionModel().getSelectedIndex()));
        //generatedCode.textProperty().set(generatedCodeText.get("functionName")+generatedCodeText.get("baseCases")+generatedCodeText.get("recursiveCases"));
    }
    public void onDecompositionChange() {
        if(decompositionSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        /*if(model.getType() == DiagramType.valueOf("COMPLEX")){
            generatedCodeText.put("functionName",generatorData.functionName.get(decompositionSelect.getSelectionModel().getSelectedIndex()+1));
        }*/
        subSolutions.getChildren().clear();
        subParameters.getChildren().clear();
        solutionSelect.getItems().clear();
        subParameters.setVisible(true);
        subSolutions.setVisible(true);
        handleDecomposition();
    }
    protected void handleDecomposition() {
        try {
            model.processSolutions();
        } catch (Exception e) {
            showErrorInputAlert(new RuntimeException("Error al introducir los datos de entrada"));
            e.printStackTrace();
            return;
        }
        subSolutions.getChildren().clear();
        subParameters.getChildren().clear();
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

    public void onSolutionChange() {
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
            /*if(model.getType() == DiagramType.valueOf("COMPLEX")){
                generatedCodeText.put("auxFunctions",generatorData.auxFunctions.get(decompositionSelect.getSelectionModel().getSelectedIndex()));
            }
            else{
                generatedCodeText.put("auxFunctions","");
            }
            generatedCodeText.put("recursiveCases",generatorData.recursiveCases.get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
            generatedCode.textProperty().set(generatedCodeText.get("functionName")+generatedCodeText.get("baseCases")+generatedCodeText.get("recursiveCases")+"\n"+generatedCodeText.get("auxFunctions"));
            */
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
    public void returnToMenu() throws IOException {
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
    public void resetDiagram() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de que quieres borrar todos los valores introducidos?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get()==ButtonType.OK){
            generatedCodeText.put("functionName","FUNCIÓN\n");
            generatedCodeText.put("baseCases","\nCASO(S) BASE");
            generatedCodeText.put("recursiveCases","\nCASO(S) RECURSIVOS");
            generatedCodeText.put("auxFunctions","");
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
            subParameters.setVisible(false);
            subSolutions.setVisible(false);
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
