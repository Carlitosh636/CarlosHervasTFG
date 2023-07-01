package com.example.controller;

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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DiagramController implements Initializable {
    @FXML
    AnchorPane root;
    private final BaseDiagram model;
    @FXML
    GridPane diagramGrid;
    @FXML
    GridPane diagramGrid2;
    @FXML
    HBox diagramsHolder;
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
    public Label decompositionSelect2;
    @FXML
    Label baseCaseLabel;
    @FXML
    VBox codegenHolder;
    @FXML
    public ComboBox<String> solutionSelect2;
    private final Map<String,DiagramVisualizerData> diagramsVisualizers = new HashMap<>();
    @FXML
    Button showMoreFunctions;
    @FXML
    Button copyCodeButton;
    private final LinkedHashMap<String,String> generatedCodeText = new LinkedHashMap<>();
    private final LinkedHashMap<String,VBox> codeGenParts = new LinkedHashMap<>();
    private Map<String,String> genCode;
    private PresenterButtonHandler buttonHandler;
    private ExceptionHandler exceptionHandler;
    private final boolean[] allSolved = {false,false};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diagramsVisualizers.put("Visualizer 1", initializeDiagramVisualizerData(diagramGrid));
        diagramsVisualizers.put("Visualizer 2", initializeDiagramVisualizerData(diagramGrid2));
        problemSizeSelect.getItems().setAll(model.getProblemSizeChoices());
        setCellFactoryForCombobox(problemSizeSelect);
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
        baseCaseLabel.setVisible(false);
        copyCodeButton.setVisible(false);

        diagramsVisualizers.values().forEach(v->{
            v.getCalculatedSolution().setVisible(false);
            v.getOriginalData().setVisible(false);
            v.getSubParameters().setVisible(false);
            v.getSubSolutions().setVisible(false);
        });

        buttonHandler = new PresenterButtonHandler();
        exceptionHandler = new ExceptionHandler(model.getInputFormatting());

        codeGenParts.put("functionName",null);
        codeGenParts.put("baseCases",null);
        codeGenParts.put("returnValues",null);
        codeGenParts.put("auxCode",null);
        codeGenParts.put("recursiveCases",null);
        codeGenParts.put("auxFunctions",null);

        codeGenParts.keySet().forEach(this::addPartToCodeGenHolder);

        generatedCodeText.put("functionName","FUNCIÓN");
        generatedCodeText.put("baseCase","CASO(S) BASE");
        generatedCodeText.put("returnValue","CASO(S) BASE");
        generatedCodeText.put("auxCode","");
        generatedCodeText.put("recursiveCases","CASO(S) RECURSIVOS");
        generatedCodeText.put("auxFunctions","");
        setGenText();
        showMoreFunctions.setVisible(false);
    }

    private void addLabelToCodeGenPart(String key, String text){
        Label newLine = new Label();
        newLine.setStyle("-fx-background-color: #000000;" +
            "-fx-text-fill: #00cc1b;" +
            "-fx-font-family: \"consolas\", sans-serif;" +
            "-fx-line-height: 21px;" +
            "-fx-font-size: 16px;");
        String formatting = "";
        switch (key){
            case "functionName":
                break;
            case "baseCases":
                formatting = "\t";
                break;
            case "returnValues":
            case "recursiveCases":
                formatting = "\t\t";
                break;
            case "auxCode":
                formatting = "\telse:\n\t\t";
                break;
        }
        newLine.setText(formatting+text);
        newLine.setPrefHeight(30);
        newLine.setPrefWidth(codegenHolder.getPrefWidth());
        newLine.setMaxWidth(codegenHolder.getMaxWidth());
        codeGenParts.get(key).getChildren().clear();
        codeGenParts.get(key).getChildren().add(newLine);

    }

    private void addPartToCodeGenHolder(String key){
        VBox newBox = new VBox();
        newBox.setPrefHeight(30);
        newBox.setPrefWidth(codegenHolder.getPrefWidth());
        newBox.setMaxWidth(codegenHolder.getMaxWidth());
        codeGenParts.put(key,newBox);
        codegenHolder.getChildren().add(codeGenParts.get(key));
    }

    private void setGenText() {
        generatedCodeTemplate.textProperty().set("");
        generatedCodeText.values().forEach(val->{
            generatedCodeTemplate.textProperty().set(generatedCodeTemplate.getText() + val + "\n");
        });
    }

    public DiagramController(BaseDiagram model) throws IOException {
        this.model = model;
    }
    protected void bindModelData() {
        diagramsVisualizers.get("Visualizer 1").getOriginalSolution().textProperty().bind(model.originalSolProperty());
        diagramsVisualizers.get("Visualizer 2").getOriginalSolution().textProperty().bind(model.originalSol2Property());
        model.currentProblemSizeProperty().bind(problemSizeSelect.getSelectionModel().selectedIndexProperty());
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
        baseCaseLabel.setVisible(true);
        if(!problemSizeSelect.getSelectionModel().isEmpty()){
            baseCaseSelect.getItems().setAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
            setCellFactoryForCombobox(baseCaseSelect);
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
            setInputParams(diagramsVisualizers.get("Visualizer 1"),1);
            setInputParams(diagramsVisualizers.get("Visualizer 2"),2);
        }
        diagramGrid.setVisible(true);
        diagramsVisualizers.get("Visualizer 1").originalData.setVisible(true);
        diagramsVisualizers.get("Visualizer 2").originalData.setVisible(true);

        String functionName = model.processFunctionName(0);
        genCode = model.processProblemSizeAndBaseCases();

        addLabelToCodeGenPart("functionName",functionName);
        addLabelToCodeGenPart("baseCases",genCode.get("baseCase"));
        addLabelToCodeGenPart("returnValues",genCode.get("returnValue"));
        copyCodeButton.setVisible(true);

        updateGenCodeParams("functionName",functionName);
        updateGenCodeParams("baseCase","\t"+genCode.get("baseCase"));
        updateGenCodeParams("returnValue","\t\t"+genCode.get("returnValue"));
        setGenText();
    }

    private void setInputParams(DiagramVisualizerData visualizerData,int index) {
        List<TextField> tfs = new ArrayList<>();
        for(String s : model.getParams().keySet()){
            HBox box = new HBox();
            Label paramName = new Label(s+" = ");
            paramName.setFont(visualizerData.getOriginalSolution().getFont());
            paramName.setStyle("-fx-text-fill: white;-fx-font-size: 18;" +
                    "-fx-max-width: 50px;");
            paramName.setPrefWidth(50);
            paramName.setMaxWidth(75);
            paramName.setWrapText(true);
            TextField tf = new TextField();
            tf.setFont(visualizerData.getOriginalSolution().getFont());
            tf.setPrefWidth(150);
            tf.setMaxWidth(200);
            box.getChildren().addAll(paramName,tf);
            visualizerData.addValueToOriginalData(box);
            tfs.add(tf);
            if(index == 1){
                model.getParams().get(s).bindBidirectional(tf.textProperty());
                tf.setOnAction(actionEvent1 -> {
                    if(tfs.stream().noneMatch(tf1 -> tf1.getText().isEmpty())){
                        List<String> inputs = tfs.stream().map(ele -> ele.textProperty().get()).toList();
                        try {
                            if(model.checkNotBaseCase(model.getCurrentProblemSize(),inputs)) {
                                showErrorInputAlert(new BaseCaseException("No se puede introducir un caso base en los parámetros de entrada"));
                            }
                            else{
                                try {
                                    model.processInputs();
                                    decompositionSelect.setVisible(true);
                                    visualizerData.getOriginalSolution().setVisible(true);
                                    visualizerData.getSubSolutions().getChildren().clear();
                                    visualizerData.getSubParameters().getChildren().clear();
                                    model.getSubParameters().clear();
                                    model.getSubSolutions().clear();
                                } catch (Exception e) {
                                    showErrorInputAlert(new IncorrectInputException("Error al introducir los datos de entrada"));
                                    e.printStackTrace();
                                }
                                refreshDecomposition();
                            }
                        } catch (Exception e) {
                            showErrorInputAlert(new IncorrectInputException("Error al introducir los datos de entrada"));
                            e.printStackTrace();
                        }
                    }
                });
            }
            if(index == 2){
                decompositionSelect2.setVisible(true);
                tf.setEditable(false);
            }
        }
    }

    public void onDecompositionChange() {
        if(decompositionSelect.getSelectionModel().getSelectedIndex()<0){
            return;
        }
        if(model.getType() == DiagramType.valueOf("COMPLEX")){
            String updatedFunction = model.processFunctionName(decompositionSelect.getSelectionModel().getSelectedIndex()+1);
            updateGenCodeParams("functionName",updatedFunction);
        }
        if (model.hasMultipleCases(decompositionSelect.getSelectionModel().getSelectedIndex())){
            diagramsHolder.getChildren().add(diagramGrid2);
            diagramGrid2.setVisible(true);
            diagramsVisualizers.get("Visualizer 2").originalSolution.setVisible(true);
            decompositionSelect2.setVisible(true);
            solutionSelect2.setVisible(true);

            AtomicInteger i = new AtomicInteger();
            List<String> localParams =  model.setVisualizerParams();
            diagramsVisualizers.get("Visualizer 2").getOriginalData().getChildren().stream()
                    .map(ele-> (TextField) ((HBox) ele).getChildren().get(1))
                    .forEach(textField -> {
                        textField.textProperty().set(localParams.get(i.get()));
                        i.addAndGet(1);
                    });
            decompositionSelect2.setText(String.valueOf(model.getParams().get("secondDecomposition").get()));
        }
        solutionSelect.getItems().clear();
        diagramsVisualizers.get("Visualizer 1").getSubParameters().setVisible(true);
        diagramsVisualizers.get("Visualizer 1").getSubSolutions().setVisible(true);
        diagramsVisualizers.get("Visualizer 2").getSubParameters().setVisible(true);
        diagramsVisualizers.get("Visualizer 2").getSubSolutions().setVisible(true);
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
        diagramsVisualizers.get("Visualizer 2").getSubSolutions().getChildren().clear();
        diagramsVisualizers.get("Visualizer 2").getSubParameters().getChildren().clear();
        for(SimpleStringProperty ele : model.getSubParameters()){
            setVisualizerSubData(ele,diagramsVisualizers.get("Visualizer 1").getSubParameters());
        }
        for(SimpleStringProperty ele : model.getSubSolutions()){
            setVisualizerSubData(ele,diagramsVisualizers.get("Visualizer 1").getSubSolutions());
        }
        if (model.hasMultipleCases(decompositionSelect.getSelectionModel().getSelectedIndex())){
            for(SimpleStringProperty ele : model.getSubParameters2()){
                setVisualizerSubData(ele,diagramsVisualizers.get("Visualizer 2").getSubParameters());
            }
            for(SimpleStringProperty ele : model.getSubSolutions2()){
                setVisualizerSubData(ele,diagramsVisualizers.get("Visualizer 2").getSubSolutions());
            }

        }
        model.getSubParameters().clear();
        model.getSubSolutions().clear();
        solutionSelect.getItems().setAll(model.getSolutionsChoices().get(model.getCurrentReductionSolutions()));
        setCellFactoryForCombobox(solutionSelect);
        solutionSelect2.getItems().setAll(model.getSolutionsChoices().get(model.getCurrentReductionSolutions2()));
        setCellFactoryForCombobox(solutionSelect2);
        solutionSelect.setVisible(true);
    }

    private void setVisualizerSubData(SimpleStringProperty ele, VBox box) {
        Label lb = new Label();
        lb.setStyle("-fx-text-fill: white;");
        lb.setWrapText(true);
        lb.setFont(diagramsVisualizers.get("Visualizer 1").getOriginalSolution().getFont());
        lb.setText(ele.get());
        lb.setTextAlignment(TextAlignment.CENTER);
        box.getChildren().add(lb);
    }

    public void onSolutionChange() {
        try{
            if(solutionSelect.getSelectionModel().getSelectedIndex()<0){
                return;
            }
            manageCalculatedSolution(diagramsVisualizers.get("Visualizer 1"),solutionSelect,model.getCurrentReductionSolutions(),0,0);
            if(genCode.get("auxCode") != null){
                addLabelToCodeGenPart("auxCode", genCode.get("auxCode"));
                updateGenCodeParams("auxCode","\telse:\n\t\t" + genCode.get("auxCode"));
            }
            else{
                addLabelToCodeGenPart("auxCode", "");
                updateGenCodeParams("auxCode","\telse:\n\t\t");
            }
            addLabelToCodeGenPart("recursiveCases",model.getRecursiveCases().get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
            updateGenCodeParams("recursiveCases","\t\t" + model.getRecursiveCases().get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
            setGenText();
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onSolutionChange2() {
        try{
            if(solutionSelect2.getSelectionModel().getSelectedIndex()<0){
                return;
            }
            manageCalculatedSolution(diagramsVisualizers.get("Visualizer 2"),solutionSelect2,model.getCurrentReductionSolutions2(),1,3);
            updateGenCodeParams("recursiveCases","\t\t" + model.getRecursiveCases().get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
            setGenText();
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void manageCalculatedSolution(DiagramVisualizerData diagramVisualizerData,ComboBox solutionSelect,int currentReductionSolutionsIndex,int indexAllSolved, int offset){
        String calcSol = model.calculateWithSelectedOperation(solutionSelect.getSelectionModel().getSelectedIndex(),currentReductionSolutionsIndex,offset);
        diagramVisualizerData.getCalculatedSolution().setVisible(true);
        if(model.checkSolutionsEqual(calcSol,diagramVisualizerData.getOriginalSolution().getText())){
            if(solutionSelect.getSelectionModel().getSelectedIndex() != model.getCorrectSolutions().get(currentReductionSolutionsIndex)){
                diagramVisualizerData.getCalculatedSolution().setText("Incorrecto! La operación da esta solución pero no para todos los casos\nValor calculado: "+model.getCalculatedSol());
                diagramVisualizerData.getCalculatedSolution().setStyle("-fx-text-fill: #fcf049;");
                allSolved[indexAllSolved] = false;

            }
            else{
                diagramVisualizerData.getCalculatedSolution().setText("Correcto!\nValor calculado: "+model.getCalculatedSol());
                diagramVisualizerData.getCalculatedSolution().setStyle("-fx-text-fill: #03fc77;");
                if(model.getAuxFunctions() != null){
                    showMoreFunctions.setVisible(true);
                }
                allSolved[indexAllSolved] = true;
            }
        }
        else{
            diagramVisualizerData.getCalculatedSolution().setText("Incorrecto! Vuelve a intentarlo\nValor calculado: "+model.getCalculatedSol());
            diagramVisualizerData.getCalculatedSolution().setStyle("-fx-text-fill: #fcf049;");
            allSolved[indexAllSolved] = false;

        }
        if (model.hasMultipleCases(decompositionSelect.getSelectionModel().getSelectedIndex())){
            if(allSolved[0] && allSolved[1]){
                updateGenCodeParams("recursiveCases","\t\t" + model.getRecursiveCases().get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
                setGenText();
            }
        }
        else{
            updateGenCodeParams("recursiveCases","\t\t" + model.getRecursiveCases().get(model.getCurrentReductionSolutions()).get(solutionSelect.getSelectionModel().getSelectedIndex()));
            setGenText();
        }
    }
    public void showErrorInputAlert(Exception e){
        exceptionHandler.showErrorAlert(e);
    }

    private void updateGenCodeParams(String genCodeTextKey, String value) {
        generatedCodeText.put(genCodeTextKey,value);
    }

    private void refreshDecomposition(){
        solutionSelect.setVisible(false);
        diagramsVisualizers.get("Visualizer 1").getCalculatedSolution().setVisible(false);
        diagramsVisualizers.get("Visualizer 2").getCalculatedSolution().setVisible(false);
        decompositionSelect.getItems().clear();
        decompositionSelect2.setText("");
        decompositionSelect.getItems().setAll(model.getReductionChoices().get(model.getCurrentProblemSize() + model.getMultipleProblemIndexOffset()));
        setCellFactoryForCombobox(decompositionSelect);
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
            copyCodeButton.setVisible(false);
            codeGenParts.values().forEach(vBox -> vBox.getChildren().clear());
            baseCaseSelect.setVisible(false);
            solutionSelect.getItems().clear();
            solutionSelect2.getItems().clear();
            model.resetSubValues();
            model.originalSolProperty().set("");
            model.originalSol2Property().set("");
            decompositionSelect.setVisible(false);
            solutionSelect.setVisible(false);
            decompositionSelect2.setVisible(false);
            solutionSelect2.setVisible(false);
            showMoreFunctions.setVisible(false);
            diagramGrid.setVisible(false);
            diagramGrid2.setVisible(false);
            baseCaseLabel.setVisible(false);
            setGenText();
        }
    }
    @FXML
    public void copyToClipboard() {
        StringBuilder stringBuilder = new StringBuilder();
        codeGenParts.forEach((k,v)->{
            v.getChildren().forEach(ele->{
                Label theLabel = (Label) ele;
                stringBuilder.append(theLabel.getText()).append("\n");
            });
        });

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(stringBuilder.toString());
        clipboard.setContent(content);
    }
    private void setCellFactoryForCombobox(ComboBox<String> combobox) {
        combobox.setCellFactory(param-> new ListCell<>() {
            final Label label = new Label() {{
                setWrapText(true);
                setPrefWidth(175);
            }};

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
        });
    }

    private void setArrows() {
        diagramsVisualizers.get("Visualizer 1").getOriginalDataSolutionArrow().getChildren().add(returnArrow(60,0,200,0));
        diagramsVisualizers.get("Visualizer 1").getPartialDataSolutionArrow().getChildren().add(returnArrow(60,0,200,0));
        diagramsVisualizers.get("Visualizer 1").getDatasArrow().getChildren().add(returnArrow(0,0,0,100));
        diagramsVisualizers.get("Visualizer 1").getSolutionsArrow().getChildren().add(returnArrow(0,100,0,0));

        diagramsVisualizers.get("Visualizer 2").getOriginalDataSolutionArrow().getChildren().add(returnArrow(60,0,200,0));
        diagramsVisualizers.get("Visualizer 2").getPartialDataSolutionArrow().getChildren().add(returnArrow(60,0,200,0));
        diagramsVisualizers.get("Visualizer 2").getDatasArrow().getChildren().add(returnArrow(0,0,0,100));
        diagramsVisualizers.get("Visualizer 2").getSolutionsArrow().getChildren().add(returnArrow(0,100,0,0));
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
