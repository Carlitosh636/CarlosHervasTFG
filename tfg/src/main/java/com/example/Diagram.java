package com.example;

import java.util.*;
import java.util.concurrent.Callable;

import javafx.beans.property.SimpleStringProperty;

public abstract class Diagram {
    protected SimpleStringProperty inputs;
    protected String rawData;
    protected List<String> problemData;
    protected SimpleStringProperty originalData;
    protected SimpleStringProperty originalSol;
    protected List<SimpleStringProperty> partialData;
    protected List<SimpleStringProperty> partialSol;
    protected SimpleStringProperty calculatedSol;
    protected int currentProblemSize;
    protected int currentReduction;
    protected int currentReductionSolutions;
    protected String operation;
    protected String reducedOperation;
    protected List<String> problemSizeChoices;
    protected int currentBaseCaseIndex;
    protected List<List<String>> baseCaseChoices;
    protected List<List<String>> baseCaseParameters;
    protected List<List<String>> reductionChoices;
    protected List<List<MidOperation>> solutionOperations;
    protected List<List<String>> solutionsChoices;
    protected Map<Integer, Callable> algorithmsMap = new HashMap<>();
    protected int algorithmIndex;
    protected SimpleStringProperty parametersFormat;
    public SimpleStringProperty parametersFormatProperty() {
        return parametersFormat;
    }
    public List<List<String>> getReductionChoices() {
        return reductionChoices;
    }
    public int getCurrentProblemSize() {
        return currentProblemSize;
    }
    public void setCurrentProblemSize(int currentProblemSize) {
        this.currentProblemSize = currentProblemSize;
    }
    public void setCurrentReduction(int currentReduction) {
        this.currentReduction = currentReduction;
    }
    public SimpleStringProperty originalDataProperty() {
        return originalData;
    }
    public SimpleStringProperty originalSolProperty() {
        return originalSol;
    }
    public SimpleStringProperty calculatedSolProperty() {
        return calculatedSol;
    }
    public SimpleStringProperty partialDataPropertyByIndex(int i){return this.partialData.get(i);}
    public SimpleStringProperty partialSolPropertyByIndex(int i){return this.partialSol.get(i);}
    public int getCurrentReductionSolutions() {
        return currentReductionSolutions;
    }
    public List<String> getProblemSizeChoices() {
        return problemSizeChoices;
    }

    public SimpleStringProperty getInputsProperty() {
        return inputs;
    }
    public void setCurrentBaseCaseIndex(int currentBaseCaseIndex) {
        this.currentBaseCaseIndex = currentBaseCaseIndex;
    }

    public List<List<String>> getBaseCaseChoices() {
        return baseCaseChoices;
    }

    public void setBaseCaseParameters(List<List<String>> baseCaseParameters) {
        this.baseCaseParameters = baseCaseParameters;
    }

    public Diagram(String operation) {
        this.operation=operation;
        this.inputs = new SimpleStringProperty();
        this.rawData = "";
        this.problemData=new ArrayList<>();
        this.reductionChoices=new ArrayList<>();
        this.problemSizeChoices=new ArrayList<>();
        this.baseCaseChoices=new ArrayList<>();
        this.baseCaseParameters=new ArrayList<>();
        this.originalData=new SimpleStringProperty();
        this.originalSol=new SimpleStringProperty();
        this.parametersFormat = new SimpleStringProperty();
        this.calculatedSol = new SimpleStringProperty();
        this.partialData=new ArrayList<>();
        this.partialSol=new ArrayList<>();
    }
    public void processInputs() throws Exception{
        try{
            problemData = Arrays.asList(this.inputs.get().split(","));
            rawData =problemData.get(0)+operation+problemData.get(1);
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }
    public abstract boolean checkNotBaseCase(int index);

    public abstract String calculate(int index);
}
