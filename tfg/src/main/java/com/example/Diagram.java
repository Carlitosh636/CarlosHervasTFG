package com.example;

import java.util.*;
import java.util.concurrent.Callable;

import javafx.beans.property.SimpleStringProperty;

public class Diagram {
    private SimpleStringProperty inputs;
    private String rawData;
    private List<String> problemData;
    private double ogSol,partSol;
    private SimpleStringProperty originalData;
    private SimpleStringProperty originalSol;
    private SimpleStringProperty partialData;
    private SimpleStringProperty partialSol;
    private SimpleStringProperty baseCase;
    private Map<Integer, Callable> algorithmsMap = new HashMap<>();
    private int currentReduction;

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





    public Diagram() {
        this.inputs = new SimpleStringProperty();
        this.rawData = "";
        this.problemData=new ArrayList<>();
        this.ogSol=0;
        this.partSol=0;
        this.originalData=new SimpleStringProperty();
        this.originalSol=new SimpleStringProperty();
        this.partialData=new SimpleStringProperty();
        this.partialSol=new SimpleStringProperty();
        this.baseCase=new SimpleStringProperty();
        algorithmsMap.put(0, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseProperty().get()));
                returnVal[1] = Algorithms.recursiveExponentOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))-1,Integer.parseInt(baseCaseProperty().get()));
                return returnVal;
            }
        });
        algorithmsMap.put(1, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption2(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseProperty().get()));
                returnVal[1] = Algorithms.recursiveExponentOption2(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))-1,Integer.parseInt(baseCaseProperty().get()));
                return returnVal;
            }
        });
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

    public void processInputs() throws Exception{
        try{
            //problemData.add(Integer.parseInt(this.inputs.get()));
            problemData = List.of(this.inputs.get().split(","));
            rawData =problemData.get(0)+'^'+problemData.get(1);
            //ogSol=Algorithms.recursiveExponentOption1(Integer.parseInt(rawData[0]),Integer.parseInt(rawData[1]), Integer.parseInt(baseCaseProperty().get()));
            //partSol=Algorithms.recursiveExponentOption1(Integer.parseInt(rawData[0]),Integer.parseInt(rawData[1])-1, Integer.parseInt(baseCaseProperty().get()));
            Double[] values = (Double[]) algorithmsMap.get(currentReduction).call();

            ogSol = values[0];
            partSol = values[1];
            originalData.set(this.rawData);
            originalSol.set(String.valueOf(ogSol));
            partialData.set(problemData.get(0)+'^'+(Integer.parseInt(problemData.get(1))-1));
            partialSol.set(String.valueOf(partSol));
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }

}
