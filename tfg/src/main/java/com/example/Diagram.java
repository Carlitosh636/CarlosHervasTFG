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
    protected int currentProblemSize;
    protected int currentReduction;
    protected int currentReductionSolutions;
    private List<Integer> correctChoices;
    protected String operation;
    protected String reducedOperation;
    protected List<String> problemSizeChoices;
    protected int currentBaseCaseIndex;
    protected List<List<String>> baseCaseChoices;
    protected List<List<String>> baseCaseParameters;
    protected List<List<String>> reductionChoices;
    protected List<List<String>> solutionsChoices;
    protected Map<Integer, Callable> algorithmsMap = new HashMap<>();
    protected int algorithmIndex;
    public List<Integer> getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(List<Integer> correctChoices) {
        this.correctChoices = correctChoices;
    }


    public List<List<String>> getReductionChoices() {
        return reductionChoices;
    }

    public void setReductionChoices(List<List<String>> reductionChoices) {
        this.reductionChoices = reductionChoices;
    }


    public int getCurrentProblemSize() {
        return currentProblemSize;
    }

    public void setCurrentProblemSize(int currentProblemSize) {
        this.currentProblemSize = currentProblemSize;
    }

    public int getCurrentReduction() {
        return currentReduction;
    }

    public void setCurrentReduction(int currentReduction) {
        this.currentReduction = currentReduction;
    }

    public String getOriginalData() {
        return originalData.get();
    }

    public SimpleStringProperty originalDataProperty() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData.set(originalData);
    }

    public String getOriginalSol() {
        return originalSol.get();
    }

    public SimpleStringProperty originalSolProperty() {
        return originalSol;
    }

    public void setOriginalSol(String originalSol) {
        this.originalSol.set(originalSol);
    }

    public SimpleStringProperty partialDataPropertyByIndex(int i){return this.partialData.get(i);}
    public SimpleStringProperty partialSolPropertyByIndex(int i){return this.partialSol.get(i);}
    public int getCurrentReductionSolutions() {
        return currentReductionSolutions;
    }

    public void setCurrentReductionSolutions(int currentReductionSolutions) {
        this.currentReductionSolutions = currentReductionSolutions;
    }

    public List<String> getProblemSizeChoices() {
        return problemSizeChoices;
    }

    public void setProblemSizeChoices(List<String> problemSizeChoices) {
        this.problemSizeChoices = problemSizeChoices;
    }

    public SimpleStringProperty getInputsProperty() {
        return inputs;
    }
    public String getInputs(){
        return this.inputs.get();
    }

    public void setInputs(SimpleStringProperty inputs) {
        this.inputs = inputs;
    }
    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public int getCurrentBaseCaseIndex() {
        return currentBaseCaseIndex;
    }

    public void setCurrentBaseCaseIndex(int currentBaseCaseIndex) {
        this.currentBaseCaseIndex = currentBaseCaseIndex;
    }

    public List<List<String>> getBaseCaseChoices() {
        return baseCaseChoices;
    }

    public void setBaseCaseChoices(List<List<String>> baseCaseChoices) {
        this.baseCaseChoices = baseCaseChoices;
    }

    public List<List<String>> getBaseCaseParameters() {
        return baseCaseParameters;
    }

    public void setBaseCaseParameters(List<List<String>> baseCaseParameters) {
        this.baseCaseParameters = baseCaseParameters;
    }

    public Diagram(List<Integer> correctChoices, String operation) {
        this.operation=operation;
        this.correctChoices=correctChoices;
        this.inputs = new SimpleStringProperty();
        this.rawData = "";
        this.problemData=new ArrayList<>();
        this.reductionChoices=new ArrayList<>();
        this.problemSizeChoices=new ArrayList<>();
        this.baseCaseChoices=new ArrayList<>();
        this.baseCaseParameters=new ArrayList<>();
        this.originalData=new SimpleStringProperty();
        this.originalSol=new SimpleStringProperty();
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

}
