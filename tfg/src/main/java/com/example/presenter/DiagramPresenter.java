package com.example.presenter;

import com.example.diagrams.BaseDiagram;
import com.example.enums.DiagramType;
import com.example.exceptions.*;
import com.example.model.Arrow;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DiagramPresenter implements Initializable {
    @FXML
    AnchorPane root;
    private final BaseDiagram model;
    @FXML
    GridPane diagramGrid;
    @FXML
    GridPane diagramGrid2;
    @FXML
    TextArea heading;
    @FXML
    TextArea generatedCodeTemplate;
    @FXML
    VBox SizeAndBaseCaseBox;
    @FXML
    ComboBox<String> problemSizeSelect;
    @FXML
    ComboBox<String> baseCaseSelect;
    @FXML
    ComboBox<String> decompositionSelect;
    @FXML
    ComboBox<String> solutionSelect;
    @FXML
    public ComboBox<String> decompositionSelect2;
    @FXML
    public ComboBox<String> solutionSelect2;
    private Map<String,DiagramVisualizerData> diagramsVisualizers = new HashMap<>();

    @FXML
    Button showMoreFunctions;
    private final LinkedHashMap<String,String> generatedCodeText = new LinkedHashMap<>();
    private Map<String,String> genCode;
    private PresenterButtonHandler buttonHandler;
    private ExceptionHandler exceptionHandler;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diagramsVisualizers.put("Visualizer 1", initializeDiagramVisualizerData(diagramGrid));
        diagramsVisualizers.put("Visualizer 2", initializeDiagramVisualizerData(diagramGrid2));
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        AnchorPane.setLeftAnchor(SizeAndBaseCaseBox,15.0);
        AnchorPane.setRightAnchor(diagramGrid,15.0);
        bindModelData();
        setArrows();

        baseCaseSelect.setVisible(false);
        diagramGrid.setVisible(false);
        diagramGrid2.setVisible(false);
        decompositionSelect.setVisible(false);
        solutionSelect.setVisible(false);
        decompositionSelect2.setVisible(false);
        solutionSelect2.setVisible(false);

        diagramsVisualizers.values().forEach(v->{
            v.getCalculatedSolution().setVisible(false);
            v.getOriginalData().setVisible(false);
            v.getSubParameters().setVisible(false);
            v.getSubSolutions().setVisible(false);
        });

        buttonHandler = new PresenterButtonHandler();
        exceptionHandler = new ExceptionHandler(model.getInputFormatting());

        generatedCodeText.put("functionName","FUNCIÓN");
        generatedCodeText.put("baseCase","CASO(S) BASE");
        generatedCodeText.put("returnValue","CASO(S) BASE");
        generatedCodeText.put("auxCode","");
        generatedCodeText.put("recursiveCases","CASO(S) RECURSIVOS");
        generatedCodeText.put("auxFunctions","");
        setGenText();
        showMoreFunctions.setVisible(false);

    }


    private void setGenText() {
        generatedCodeTemplate.textProperty().set("");
        generatedCodeText.values().forEach(val->{
            generatedCodeTemplate.textProperty().set(generatedCodeTemplate.getText() + val + "\n");
        });
    }

    public DiagramPresenter(BaseDiagram model) throws IOException {
        this.model = model;
    }
    protected void bindModelData() {
        diagramsVisualizers.get("Visualizer 1").getOriginalSolution().textProperty().bind(model.originalSolProperty());
        model.currentProblemSizeProperty().bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
        model.currentBaseCaseProperty().bind(baseCaseSelect.getSelectionModel().selectedIndexProperty());
        model.currentReductionProperty().bind(decompositionSelect.getSelectionModel().selectedIndexProperty());
        model.selectedSolutionProperty().bind(solutionSelect.getSelectionModel().selectedIndexProperty());
        heading.setWrapText(true);
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
        if(diagramsVisualizers.get("Visualizer 1").getOriginalData().getChildren().isEmpty()){
            List<TextField> tfs = new ArrayList<>();
            for(String s : model.getParams().keySet()){
                HBox box = new HBox();
                Label paramName = new Label(s+" = ");
                paramName.setFont(diagramsVisualizers.get("Visualizer 1").getOriginalSolution().getFont());
                paramName.setStyle("-fx-text-fill: white;-fx-font-size: 18;" +
                        "-fx-max-width: 50px;");
                paramName.setWrapText(true);
                TextField tf = new TextField();
                tf.setFont(diagramsVisualizers.get("Visualizer 1").getOriginalSolution().getFont());
                tf.setMaxWidth(100);
                tf.setMaxHeight(30);
                box.getChildren().addAll(paramName,tf);
                diagramsVisualizers.get("Visualizer 1").addValueToOriginalData(box);
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
                                    diagramsVisualizers.get("Visualizer 1").getOriginalSolution().setVisible(true);
                                    diagramsVisualizers.get("Visualizer 1").getSubSolutions().getChildren().clear();
                                    diagramsVisualizers.get("Visualizer 1").getSubParameters().getChildren().clear();
                                    model.getSubParameters().clear();
                                    model.getSubSolutions().clear();

                                } catch (Exception e) {
                                    showErrorInputAlert(new IncorrectInputException("Error al introducir los datos de entrada"));
                                    e.printStackTrace();
                                }
                                refreshDiagram();
                            }
                        } catch (Exception e) {
                            showErrorInputAlert(new IncorrectInputException("Error al introducir los datos de entrada"));
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        diagramGrid.setVisible(true);
        diagramGrid2.setVisible(true );
        diagramsVisualizers.get("Visualizer 1").originalData.setVisible(true);
        String functionName = model.processFunctionName(0);
        genCode = model.processProblemSizeAndBaseCases();
        updateGenCodeParams("functionName",functionName);
        updateGenCodeParams("baseCase","\t"+genCode.get("baseCase"));
        updateGenCodeParams("returnValue","\t\t"+genCode.get("returnValue"));
        setGenText();
    }

    public void onDecompositionChange() {
        if(decompositionSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        if(model.getType() == DiagramType.valueOf("COMPLEX")){
            String updatedFunction = model.processFunctionName(decompositionSelect.getSelectionModel().getSelectedIndex()+1);
            updateGenCodeParams("functionName",updatedFunction);
        }
        solutionSelect.getItems().clear();
        diagramsVisualizers.get("Visualizer 1").getSubParameters().setVisible(true);
        diagramsVisualizers.get("Visualizer 1").getSubSolutions().setVisible(true);
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

        diagramsVisualizers.get("Visualizer 1").getSubSolutions().getChildren().clear();
        diagramsVisualizers.get("Visualizer 1").getSubParameters().getChildren().clear();
        for(SimpleStringProperty ele : model.getSubParameters()){
            Label lb = new Label();
            lb.setStyle("-fx-text-fill: white;");
            lb.setFont(diagramsVisualizers.get("Visualizer 1").getOriginalSolution().getFont());
            lb.setText(ele.get());
            lb.setTextAlignment(TextAlignment.CENTER);
            diagramsVisualizers.get("Visualizer 1").getSubParameters().getChildren().add(lb);
        }
        for(SimpleStringProperty ele : model.getSubSolutions()){
            Label lb = new Label();
            lb.setStyle("-fx-text-fill: white;");
            lb.setFont(diagramsVisualizers.get("Visualizer 1").getOriginalSolution().getFont());
            lb.setText(ele.get());
            lb.setTextAlignment(TextAlignment.CENTER);
            diagramsVisualizers.get("Visualizer 1").getSubSolutions().getChildren().add(lb);
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
            diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setVisible(true);
            if(model.checkSolutionsEqual(calcSol)){
                if(solutionSelect.getSelectionModel().getSelectedIndex() != model.getCorrectSolutions().get(model.getCurrentReductionSolutions())){
                    diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setText("Incorrecto! La operación da esta solución pero no para todos los casos\nValor calculado: "+model.getCalculatedSol());
                    diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setStyle("-fx-text-fill: #fff317;");
                }
                else{
                    diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setText("Correcto!\nValor calculado: "+model.getCalculatedSol());
                    diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setStyle("-fx-text-fill: #03fc77;");
                    if(model.getAuxFunctions() != null){
                        showMoreFunctions.setVisible(true);
                    }
                }
            }
            else{
                diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setText("Incorrecto! Vuelve a intentarlo\nValor calculado: "+model.getCalculatedSol());
                diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setStyle("-fx-text-fill: #ff0015;");
            }
            if(genCode.get("auxCode") != null){
                updateGenCodeParams("auxCode","\telse:\n\t\t" + genCode.get("auxCode"));
            }
            else{
                updateGenCodeParams("auxCode","\telse:\n\t\t");
            }
            updateGenCodeParams("recursiveCases","\t\t" + model.getRecursiveCases().get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));

            setGenText();
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showErrorInputAlert(Exception e){
        exceptionHandler.showErrorAlert(e);
    }

    private void updateGenCodeParams(String genCodeTextKey, String value) {
        generatedCodeText.put(genCodeTextKey,value);
    }

    private void refreshDiagram(){
        solutionSelect.setVisible(false);
        diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setVisible(false);
        decompositionSelect.getItems().clear();
        decompositionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
    }
    @FXML
    public void returnToMenu() {
        try {
            buttonHandler.returnToMenu(1,"Confirmación","¿Estas seguro de que quieres volver al menú?",diagramGrid);
        } catch (AlertTypeIndexOutOfBounds | InternallyCausedRuntimeException e) {
            showErrorInputAlert(e);
        }
    }

    @FXML
    public void showMoreFuncs() {
        updateGenCodeParams("auxFunctions",model.getAuxFunctions().get(decompositionSelect.getSelectionModel().getSelectedIndex()));
        setGenText();
    }

    @FXML
    public void resetDiagram() throws AlertTypeIndexOutOfBounds {
        String action = buttonHandler.resetDiagram(1,"Confirmación","¿Estas seguro de que quieres borrar todos los valores introducidos y reinciar el diagrama?",generatedCodeText);
        if(action.equals("OK")){
            problemSizeSelect.getSelectionModel().clearSelection();
            baseCaseSelect.getSelectionModel().clearSelection();
            diagramsVisualizers.values().forEach(v->{
                v.getSubSolutions().getChildren().clear();
                v.getSubParameters().getChildren().clear();
                v.getOriginalData().getChildren().clear();
                v.getCalculatedSolution().setVisible(false);
                v.getSubParameters().setVisible(false);
                v.getSubSolutions().setVisible(false);
                v.getOriginalSolution().setVisible(false);
            });

            solutionSelect.getItems().clear();
            model.resetSubValues();
            model.originalSolProperty().set("");
            decompositionSelect.setVisible(false);
            solutionSelect.setVisible(false);
            decompositionSelect2.setVisible(false);
            solutionSelect2.setVisible(false);
            showMoreFunctions.setVisible(false);
            diagramGrid.setVisible(false);
            diagramGrid2.setVisible(false);
            setGenText();
        }
    }

    private void setArrows() {
        diagramsVisualizers.get("Visualizer 1").getOriginalDataSolutionArrow().getChildren().add(returnArrow(60,0,250,0));
        diagramsVisualizers.get("Visualizer 1").getPartialDataSolutionArrow().getChildren().add(returnArrow(60,0,250,0));
        diagramsVisualizers.get("Visualizer 1").getDatasArrow().getChildren().add(returnArrow(0,0,0,100));
        diagramsVisualizers.get("Visualizer 1").getSolutionsArrow().getChildren().add(returnArrow(0,100,0,0));
    }

    private Arrow returnArrow(double startX, double startY, double endX, double endY){
        Arrow arrow = new Arrow();
        arrow.setStartX(startX);
        arrow.setStartY(startY);
        arrow.setEndX(endX);
        arrow.setEndY(endY);
        return arrow;
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private DiagramVisualizerData initializeDiagramVisualizerData(GridPane diagramGrid) {
        DiagramVisualizerData digVisualData = new DiagramVisualizerData();
        digVisualData.setOriginalData((VBox) getNodeByRowColumnIndex(1,1,diagramGrid));
        digVisualData.setOriginalDataSolutionArrow((StackPane) getNodeByRowColumnIndex(1,2,diagramGrid));
        digVisualData.setOriginalSolution((Label) getNodeByRowColumnIndex(1,3,diagramGrid));
        digVisualData.setCalculatedSolution((Label) getNodeByRowColumnIndex(1,4,diagramGrid));
        digVisualData.setDatasArrow((StackPane) getNodeByRowColumnIndex(3,1,diagramGrid));
        digVisualData.setSolutionsArrow((StackPane) getNodeByRowColumnIndex(3,3,diagramGrid));
        digVisualData.setSubParameters((VBox) getNodeByRowColumnIndex(5,1,diagramGrid));
        digVisualData.setPartialDataSolutionArrow((StackPane) getNodeByRowColumnIndex(5,2,diagramGrid));
        digVisualData.setSubSolutions((VBox) getNodeByRowColumnIndex(5,3,diagramGrid));
        return digVisualData;
    }
}
