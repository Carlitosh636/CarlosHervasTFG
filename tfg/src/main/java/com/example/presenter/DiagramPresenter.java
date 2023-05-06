package com.example.presenter;

import com.example.diagrams.BaseDiagram;
import com.example.enums.DiagramType;
import com.example.exceptions.AlertTypeIndexOutOfBounds;
import com.example.exceptions.BaseCaseException;
import com.example.exceptions.IncorrectSelectionException;
import com.example.model.Arrow;
import com.example.model.GeneratorData;
import com.example.utils.FileUtils;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import javafx.beans.property.SimpleStringProperty;
import com.github.mustachejava.MustacheFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private BaseDiagram model;
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
    private final HashMap<String,String> generatedCodeText = new HashMap<>();
    private GeneratorData generatorData;
    private MustacheFactory mf;
    private Mustache m;
    private PresenterButtonHandler buttonHandler;
    private PresenterInputHandler inputHandler;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        AnchorPane.setLeftAnchor(SizeAndBaseCaseBox,15.0);
        AnchorPane.setRightAnchor(diagramGrid,15.0);
        bindModelData();
        setArrows();
        baseCaseSelect.setVisible(false);
        diagramGrid.setVisible(false);
        calculatedSolution.setVisible(false);
        decompositionSelect.setVisible(false);
        solutionSelect.setVisible(false);
        originalSolution.setVisible(false);
        subParameters.setVisible(false);
        subSolutions.setVisible(false);

        buttonHandler = new PresenterButtonHandler();
        inputHandler = new PresenterInputHandler(model.getInputFormatting());
        generatedCodeWebEngine = generatedCodeTemplate.getEngine();
        mf = new DefaultMustacheFactory();
        m = mf.compile("generatedCodeTemplate.mustache");

        generatedCodeText.put("functionName","FUNCIÓN");
        generatedCodeText.put("baseCase","\nCASO(S) BASE");
        generatedCodeText.put("returnValue","\nCASO(S) BASE");
        generatedCodeText.put("recursiveCases","\nCASO(S) RECURSIVOS");
        generatedCodeText.put("auxFunctions","\n");
        updateMustacheTemplate();
    }
    private void updateMustacheTemplate(){
        StringWriter wr = new StringWriter();
        String html;
        try {
            m.execute(wr,generatedCodeText).flush();
            html = wr.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        generatedCodeWebEngine.loadContent(html);
    }

    public DiagramPresenter(BaseDiagram model,String pathName) throws IOException {
        this.model = model;
        generatorData = FileUtils.returnGeneratorDataFromInputStream(pathName);
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
                                    originalSolution.setVisible(true);

                                    subSolutions.getChildren().clear();
                                    subParameters.getChildren().clear();
                                    model.getSubParameters().clear();
                                    model.getSubSolutions().clear();

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
        Map<String,String> genValues = model.processProblemSizeAndBaseCases();
        updateGenCodeParams("functionName",genValues.get("functionName"));
        updateGenCodeParams("baseCase",genValues.get("baseCase"));
        updateGenCodeParams("returnValue",genValues.get("returnValue"));
    }

    public void onDecompositionChange() {
        if(decompositionSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        if(model.getType() == DiagramType.valueOf("COMPLEX")){
            generatedCodeText.put("functionName",generatorData.functionName.get(decompositionSelect.getSelectionModel().getSelectedIndex()+1));
        }
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
        model.getSubParameters().clear();
        model.getSubSolutions().clear();
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
            if(model.getType() == DiagramType.valueOf("COMPLEX")){
                generatedCodeText.put("auxFunctions",generatorData.auxFunctions.get(decompositionSelect.getSelectionModel().getSelectedIndex()));
            }
            else{
                generatedCodeText.put("auxFunctions","");
            }
            generatedCodeText.put("recursiveCases",generatorData.recursiveCases.get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
            updateMustacheTemplate();
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showErrorInputAlert(Exception e){
        inputHandler.showErrorAlert(e);
    }

    private void updateGenCodeParams(String genCodeTextKey, String value) {
        generatedCodeText.put(genCodeTextKey,value);
        updateMustacheTemplate();
    }

    private void refreshDiagram(){
        solutionSelect.setVisible(false);
        calculatedSolution.setVisible(false);
        decompositionSelect.getItems().clear();
        decompositionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
    }
    @FXML
    public void returnToMenu() throws IOException, AlertTypeIndexOutOfBounds {
        buttonHandler.returnToMenu(1,"Confirmación","¿Estas seguro de que quieres volver al menú?",diagramGrid);
    }

    @FXML
    public void resetDiagram() throws AlertTypeIndexOutOfBounds {
        String action = buttonHandler.resetDiagram(1,"Confirmación","¿Estas seguro de que quieres borrar todos los valores introducidos y reinciar el diagrama?",generatedCodeText);
        if(action.equals("OK")){
            problemSizeSelect.getSelectionModel().clearSelection();
            baseCaseSelect.getSelectionModel().clearSelection();
            subSolutions.getChildren().clear();
            subParameters.getChildren().clear();
            solutionSelect.getItems().clear();
            originalData.getChildren().clear();
            model.resetSubValues();
            calculatedSolution.setVisible(false);
            model.originalSolProperty().set("");
            decompositionSelect.setVisible(false);
            solutionSelect.setVisible(false);
            subParameters.setVisible(false);
            subSolutions.setVisible(false);
            originalSolution.setVisible(false);
            updateMustacheTemplate();
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
