package com.example.model;

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
    public int currentProblemSize;
    public int currentBaseCase;
    public int currentReduction;
    public int currentReductionSolutions;
    public String operation;
    public String reducedOperation;
    public List<String> problemSizeChoices;
    public int currentBaseCaseIndex;
    public List<List<String>> baseCaseChoices;
    public List<List<String>> baseCaseParameters;
    public List<List<String>> reductionChoices;
    public List<List<String>> solutionsChoices;
    public String recursiveCallParameters;
    public List<Integer> correctSolutions;
    public String inputFormatting;
    public DiagramData(){

    }
}
