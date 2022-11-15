package com.example;

import java.util.*;
import java.util.concurrent.Callable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Diagram {
    protected SimpleStringProperty inputs;
    protected String rawData;
    protected List<String> problemData;
    protected double ogSol;
    protected double partSol;
    protected SimpleStringProperty originalData;
    protected SimpleStringProperty originalSol;
    protected SimpleStringProperty partialData;
    protected SimpleStringProperty partialSol;
    protected SimpleStringProperty baseCase;
    protected int currentProblemSize;
    protected int currentReduction;
    protected int currentReductionSolutions;
    private List<Integer> correctChoices;
    protected String operation;
    protected String reducedOperation;
    protected List<String> problemSizeChoices;
    protected List<List<String>> reductionChoices;
    protected List<List<String>> solutionsChoices;
    protected Map<Integer, Callable> algorithmsMap = new HashMap<>();
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

    public String getCurrentReductionString() {
        return currentReductionString;
    }

    public void setCurrentReductionString(String currentReductionString) {
        this.currentReductionString = currentReductionString;
    }

    protected String currentReductionString;

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

    public String getBaseCase() {
        return baseCase.get();
    }

    public SimpleStringProperty baseCaseProperty() {
        return baseCase;
    }

    public void setBaseCase(String baseCase) {
        this.baseCase.set(baseCase);
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

    public String getPartialData() {
        return partialData.get();
    }

    public SimpleStringProperty partialDataProperty() {
        return partialData;
    }

    public void setPartialData(String partialData) {
        this.partialData.set(partialData);
    }

    public String getPartialSol() {
        return partialSol.get();
    }

    public SimpleStringProperty partialSolProperty() {
        return partialSol;
    }

    public void setPartialSol(String partialSol) {
        this.partialSol.set(partialSol);
    }


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


    public Diagram(List<Integer> correctChoices,String operation) {
        this.operation=operation;
        this.correctChoices=correctChoices;
        this.inputs = new SimpleStringProperty();
        this.rawData = "";
        this.problemData=new ArrayList<>();
        this.reductionChoices=new ArrayList<>();
        this.problemSizeChoices=new ArrayList<>();
        this.ogSol=0;
        this.partSol=0;
        this.originalData=new SimpleStringProperty();
        this.originalSol=new SimpleStringProperty();
        this.partialData=new SimpleStringProperty();
        this.partialSol=new SimpleStringProperty();
        this.baseCase=new SimpleStringProperty();
    }
    public void processInputs() throws Exception{
        try{
            problemData = Arrays.asList(this.inputs.get().split(","));
            rawData =problemData.get(0)+operation+problemData.get(1);
            int algorithmIndex = currentProblemSize+currentReduction;
            Double[] values = (Double[]) algorithmsMap.get(algorithmIndex).call();
            ogSol = values[0];
            partSol = values[1];
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }

}
