package com.example.diagrams;

import com.example.enums.DiagramType;
import com.example.model.DiagramData;
import com.example.utils.FileUtils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseDiagram {
    protected IDiagramActions diagramActions;
    protected DiagramType type;
    private final DiagramData diagramData;
    protected SimpleStringProperty heading;
    protected Map<String, SimpleStringProperty> params;
    protected SimpleStringProperty originalSol;
    protected List<SimpleStringProperty> subParameters;
    protected List<SimpleStringProperty> subSolutions;
    protected SimpleStringProperty calculatedSol;
    protected SimpleStringProperty calculatedSol2;
    protected SimpleIntegerProperty currentProblemSize;
    protected SimpleIntegerProperty currentReduction;
    protected SimpleIntegerProperty currentReductionSolutions;
    protected SimpleIntegerProperty currentReductionSolutions2;
    protected List<String> problemSizeChoices;
    protected int currentBaseCaseIndex;
    protected List<List<String>> returnValues;
    protected List<List<String>> baseCaseChoices;
    protected List<List<String>> baseCaseParameters;
    protected List<List<String>> reductionChoices;
    protected List<List<String>> recursiveCases;
    protected List<List<Supplier>> solutionOperations;
    protected List<List<String>> solutionsChoices;
    protected SimpleIntegerProperty selectedSolution;
    protected List<Integer> correctSizeChoices;
    protected List<List<Integer>> correctBaseCases;
    protected List<Integer> correctSolutions;
    protected int algorithmIndex;
    protected String inputFormatting;
    protected List<String> auxFunctions;
    protected List<String> paramKeys;
    protected int multipleProblemIndexOffset;

    protected BaseDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        this.diagramActions = builder;
        diagramData = FileUtils.returnObjectFromInputStream(diagramDataName, DiagramData.class);
        this.params = new HashMap<>();
        this.heading = new SimpleStringProperty(diagramData.getHeading());
        this.type = DiagramType.valueOf(diagramData.getType());
        diagramData.getParams().forEach((k, v) -> params.put(k, new SimpleStringProperty(v)));
        this.reductionChoices = diagramData.getReductionChoices();
        this.problemSizeChoices = diagramData.getProblemSizeChoices();
        this.baseCaseChoices = diagramData.getBaseCaseChoices();
        this.baseCaseParameters = diagramData.getBaseCaseParameters();
        this.returnValues = diagramData.getReturnValues();
        this.originalSol = new SimpleStringProperty("");
        this.calculatedSol = new SimpleStringProperty("");
        this.calculatedSol2 = new SimpleStringProperty("");
        this.currentProblemSize = new SimpleIntegerProperty(diagramData.getCurrentProblemSize());
        this.currentReductionSolutions = new SimpleIntegerProperty(diagramData.getCurrentReductionSolutions());
        this.currentReductionSolutions2 = new SimpleIntegerProperty(diagramData.getCurrentReductionSolutions());
        this.currentReduction = new SimpleIntegerProperty(diagramData.getCurrentReduction());
        this.selectedSolution = new SimpleIntegerProperty();
        this.subParameters = new ArrayList<>();
        this.subSolutions = new ArrayList<>();
        this.solutionsChoices = new ArrayList<>();
        this.solutionsChoices.addAll(diagramData.getSolutionsChoices());
        this.correctSizeChoices = diagramData.getCorrectSizeChoices();
        this.correctBaseCases = diagramData.getCorrectBaseCases();
        this.correctSolutions = diagramData.getCorrectSolutions();
        this.inputFormatting = diagramData.getInputFormatting();
        this.recursiveCases = diagramData.getRecursiveCases();
        this.paramKeys = new ArrayList<>(diagramData.getParams().keySet());
        this.multipleProblemIndexOffset = 0;
    }

    public abstract void processInputs() throws Exception;

    public String processFunctionName(int index) {
        return diagramActions.getFunctionName(index);
    }

    public abstract Map<String, String> processProblemSizeAndBaseCases();

    public abstract void processSolutions() throws Exception;

    public abstract boolean checkNotBaseCase(int index, List<String> input) throws Exception;

    public abstract boolean checkSolutionsEqual(String calcSol);

    public abstract String calculateWithSelectedOperation(int index);

    public abstract List<String> setVisualizerParams();

    public void resetSubValues() {
        this.subParameters.clear();
        this.subSolutions.clear();
        this.params.clear();
        diagramData.getParams().forEach((k, v) -> params.put(k, new SimpleStringProperty(v)));
    }

    public boolean hasMultipleCases(int selectedIndex) {
        return  diagramActions.checkIfMultipleCases(selectedIndex);
    }

    public DiagramType getType() {
        return type;
    }

    public SimpleStringProperty headingProperty() {
        return heading;
    }

    public Map<String, SimpleStringProperty> getParams() {
        return params;
    }

    public SimpleStringProperty originalSolProperty() {
        return originalSol;
    }

    public List<SimpleStringProperty> getSubParameters() {
        return subParameters;
    }

    public List<SimpleStringProperty> getSubSolutions() {
        return subSolutions;
    }

    public String getCalculatedSol() {
        return calculatedSol.get();
    }

    public int getCurrentProblemSize() {
        return currentProblemSize.get();
    }

    public SimpleIntegerProperty currentProblemSizeProperty() {
        return currentProblemSize;
    }


    public SimpleIntegerProperty currentReductionProperty() {
        return currentReduction;
    }
    public List<List<String>> getRecursiveCases() {
        return recursiveCases;
    }
    public int getCurrentReductionSolutions() {
        return currentReductionSolutions.get();
    }

    public List<String> getProblemSizeChoices() {
        return problemSizeChoices;
    }

    public List<List<String>> getBaseCaseChoices() {
        return baseCaseChoices;
    }

    public List<List<String>> getReductionChoices() {
        return reductionChoices;
    }

    public List<List<String>> getSolutionsChoices() {
        return solutionsChoices;
    }

    public SimpleIntegerProperty selectedSolutionProperty() {
        return selectedSolution;
    }

    public List<Integer> getCorrectSolutions() {
        return correctSolutions;
    }

    public List<Integer> getCorrectSizeChoices() {
        return correctSizeChoices;
    }

    public List<List<Integer>> getCorrectBaseCases() {
        return correctBaseCases;
    }

    public void setCurrentBaseCaseIndex(int currentBaseCaseIndex) {
        this.currentBaseCaseIndex = currentBaseCaseIndex;
    }

    public String getInputFormatting() {
        return inputFormatting;
    }

    public List<String> getAuxFunctions() {
        return auxFunctions;
    }

    public int getCurrentReductionSolutions2() {
        return currentReductionSolutions2.get();
    }

    public SimpleIntegerProperty currentReductionSolutions2Property() {
        return currentReductionSolutions2;
    }

    public void setCurrentReductionSolutions2(int currentReductionSolutions2) {
        this.currentReductionSolutions2.set(currentReductionSolutions2);
    }

    public String getCalculatedSol2() {
        return calculatedSol2.get();
    }

    public SimpleStringProperty calculatedSol2Property() {
        return calculatedSol2;
    }

    public void setCalculatedSol2(String calculatedSol2) {
        this.calculatedSol2.set(calculatedSol2);
    }

    public int getMultipleProblemIndexOffset() {
        return multipleProblemIndexOffset;
    }
}
