package com.example.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DiagramData implements Serializable {
    private String type;
    private String heading;
    private Map<String,String> params;
    private String originalData;
    private String originalSol;
    private String calculatedSol;
    private String reducedOperation;
    private int currentProblemSize;
    private int currentBaseCase;
    private int currentReduction;
    private int currentReductionSolutions;
    private List<String> problemSizeChoices;
    private int currentBaseCaseIndex;
    private List<List<String>> baseCaseChoices;
    private List<List<String>> baseCaseParameters;
    private List<List<String>> reductionChoices;
    private List<List<String>> solutionsChoices;
    private List<Integer> correctSizeChoices;
    private List<List<Integer>> correctBaseCases;
    private List<Integer> correctSolutions;
    private String inputFormatting;
    public DiagramData(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public String getOriginalSol() {
        return originalSol;
    }

    public void setOriginalSol(String originalSol) {
        this.originalSol = originalSol;
    }

    public String getCalculatedSol() {
        return calculatedSol;
    }

    public void setCalculatedSol(String calculatedSol) {
        this.calculatedSol = calculatedSol;
    }

    public int getCurrentProblemSize() {
        return currentProblemSize;
    }

    public void setCurrentProblemSize(int currentProblemSize) {
        this.currentProblemSize = currentProblemSize;
    }

    public int getCurrentBaseCase() {
        return currentBaseCase;
    }

    public void setCurrentBaseCase(int currentBaseCase) {
        this.currentBaseCase = currentBaseCase;
    }

    public int getCurrentReduction() {
        return currentReduction;
    }

    public void setCurrentReduction(int currentReduction) {
        this.currentReduction = currentReduction;
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

    public List<List<String>> getReductionChoices() {
        return reductionChoices;
    }

    public void setReductionChoices(List<List<String>> reductionChoices) {
        this.reductionChoices = reductionChoices;
    }

    public List<List<String>> getSolutionsChoices() {
        return solutionsChoices;
    }

    public void setSolutionsChoices(List<List<String>> solutionsChoices) {
        this.solutionsChoices = solutionsChoices;
    }

    public List<Integer> getCorrectSizeChoices() {
        return correctSizeChoices;
    }

    public void setCorrectSizeChoices(List<Integer> correctSizeChoices) {
        this.correctSizeChoices = correctSizeChoices;
    }

    public List<List<Integer>> getCorrectBaseCases() {
        return correctBaseCases;
    }

    public void setCorrectBaseCases(List<List<Integer>> correctBaseCases) {
        this.correctBaseCases = correctBaseCases;
    }

    public List<Integer> getCorrectSolutions() {
        return correctSolutions;
    }

    public void setCorrectSolutions(List<Integer> correctSolutions) {
        this.correctSolutions = correctSolutions;
    }

    public String getInputFormatting() {
        return inputFormatting;
    }

    public void setInputFormatting(String inputFormatting) {
        this.inputFormatting = inputFormatting;
    }

    public String getReducedOperation() {
        return reducedOperation;
    }

    public void setReducedOperation(String reducedOperation) {
        this.reducedOperation = reducedOperation;
    }

}
