package com.example;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DiagramData implements Serializable {
    public String type;
    public String heading;
    public Map<String,String> params;
    public String originalData;
    public String originalSol;
    public List<String> subParameters;
    public List<String> subSolutions;
    public String calculatedSol;
    public String currentProblemSize;
    public String currentBaseCase;
    public String currentReduction;
    public String currentReductionSolutions;
    public String operation;
    public String reducedOperation;
    public List<String> problemSizeChoices;
    public int currentBaseCaseIndex;
    public List<List<String>> baseCaseChoices;
    public List<List<String>> baseCaseParameters;
    public List<List<String>> reductionChoices;
    public List<List<String>> solutionsChoices;
    public String partSol;
    public String recursiveCallParameters;

    public DiagramData(){

    }

    public DiagramData(String type, String heading, Map<String, String> params, String originalData, String originalSol, List<String> subParameters, List<String> subSolutions, String calculatedSol, String currentProblemSize, String currentBaseCase, String currentReduction, String currentReductionSolutions, String operation, String reducedOperation, List<String> problemSizeChoices, int currentBaseCaseIndex, List<List<String>> baseCaseChoices, List<List<String>> baseCaseParameters, List<List<String>> reductionChoices, List<List<String>> solutionsChoices, String partSol, String recursiveCallParameters) {
        this.type = type;
        this.heading = heading;
        this.params = params;
        this.originalData = originalData;
        this.originalSol = originalSol;
        this.subParameters = subParameters;
        this.subSolutions = subSolutions;
        this.calculatedSol = calculatedSol;
        this.currentProblemSize = currentProblemSize;
        this.currentBaseCase = currentBaseCase;
        this.currentReduction = currentReduction;
        this.currentReductionSolutions = currentReductionSolutions;
        this.operation = operation;
        this.reducedOperation = reducedOperation;
        this.problemSizeChoices = problemSizeChoices;
        this.currentBaseCaseIndex = currentBaseCaseIndex;
        this.baseCaseChoices = baseCaseChoices;
        this.baseCaseParameters = baseCaseParameters;
        this.reductionChoices = reductionChoices;
        this.solutionsChoices = solutionsChoices;
        this.partSol = partSol;
        this.recursiveCallParameters = recursiveCallParameters;
    }
}
