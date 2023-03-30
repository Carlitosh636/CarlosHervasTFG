package com.example.diagrams;

import com.example.enums.DiagramType;
import com.example.model.DiagramData;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class BaseDiagram {
    protected IDiagramActions diagramActions;
    protected DiagramType type;
    private final DiagramData diagramData;
    protected SimpleStringProperty heading;
    protected Map<String,SimpleStringProperty> params;
    protected SimpleStringProperty originalSol;
    protected List<SimpleStringProperty> subParameters;
    protected List<SimpleStringProperty> subSolutions;
    protected SimpleStringProperty calculatedSol;
    protected SimpleIntegerProperty currentProblemSize;
    protected SimpleIntegerProperty currentBaseCase;
    protected SimpleIntegerProperty currentReduction;
    protected SimpleIntegerProperty currentReductionSolutions;
    protected String operation;
    protected List<String> problemSizeChoices;
    protected int currentBaseCaseIndex;
    protected List<List<String>> baseCaseChoices;
    protected List<List<String>> baseCaseParameters;
    protected List<List<String>> reductionChoices;
    protected List<List<Supplier>> solutionOperations;
    protected List<List<String>> solutionsChoices;
    protected SimpleIntegerProperty selectedSolution;
    protected List<Integer> correctSizeChoices;
    protected List<List<Integer>> correctBaseCases;
    protected List<Integer> correctSolutions;
    protected int algorithmIndex;
    protected String inputFormatting;
    protected BaseDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        this.diagramActions =builder;
        ObjectMapper objMapper = new ObjectMapper();
        diagramData =objMapper.readValue(new File(diagramDataName),DiagramData.class);
        this.operation=diagramData.operation;
        this.params =new HashMap<>();
        this.heading = new SimpleStringProperty(diagramData.heading);
        this.type = DiagramType.valueOf(diagramData.type);
        diagramData.params.forEach((k,v)-> params.put(k,new SimpleStringProperty(v)));
        this.reductionChoices=diagramData.reductionChoices;
        this.problemSizeChoices=diagramData.problemSizeChoices;
        this.baseCaseChoices=diagramData.baseCaseChoices;
        this.baseCaseParameters=diagramData.baseCaseParameters;
        this.originalSol=new SimpleStringProperty(diagramData.originalSol);
        this.calculatedSol = new SimpleStringProperty(diagramData.calculatedSol);
        this.currentProblemSize = new SimpleIntegerProperty(diagramData.currentProblemSize);
        this.currentBaseCase = new SimpleIntegerProperty(diagramData.currentBaseCase);
        this.currentReductionSolutions = new SimpleIntegerProperty(diagramData.currentReductionSolutions);
        this.currentReduction = new SimpleIntegerProperty(diagramData.currentReduction);
        this.selectedSolution = new SimpleIntegerProperty();
        this.subParameters =new ArrayList<>();
        this.subSolutions =new ArrayList<>();
        this.solutionsChoices = new ArrayList<>();
        this.solutionsChoices.addAll(diagramData.solutionsChoices);
        this.correctSizeChoices = diagramData.correctSizeChoices;
        this.correctBaseCases = diagramData.correctBaseCases;
        this.correctSolutions = diagramData.correctSolutions;
        this.inputFormatting = diagramData.inputFormatting;
    }

    public abstract void processInputs() throws Exception;
    public abstract void processSolutions() throws Exception;
    public abstract boolean checkNotBaseCase(int index,List<String>input) throws Exception;

    public abstract boolean checkSolutionsEqual(String calcSol);
    public abstract String calculateWithSelectedOperation(int index);

    public void resetSubValues(){
        this.subParameters.clear();
        this.subSolutions.clear();
        this.params.clear();
        diagramData.params.forEach((k,v)-> params.put(k,new SimpleStringProperty(v)));
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

    public SimpleIntegerProperty currentBaseCaseProperty() {
        return currentBaseCase;
    }

    public SimpleIntegerProperty currentReductionProperty() {
        return currentReduction;
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
}
