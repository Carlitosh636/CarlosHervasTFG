package com.example.diagrams;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringDiagram extends BaseDiagram{
    public StringDiagram(IDiagramActions builder, String index) throws IOException {
        super(builder, index);
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
    }
    @Override
    public void processInputs() throws Exception {
        algorithmIndex = currentProblemSize.get() * 2 + currentReduction.get();
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        diagramActions.setParams(paramsParsed);
        Map<String,String> solutions = calculateSolution(-1);
        originalSol.set("f = "+solutions.get("ogSol"));
    }
    @Override
    public void processSolutions() throws Exception {
        algorithmIndex = currentProblemSize.get() * 2 + currentReduction.get();
        System.out.println(algorithmIndex);
        Map<String,String> solutions = calculateSolution(algorithmIndex);
        setSubData(solutions);
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        paramsParsed.put("partSol",solutions.get("partSol"));
        diagramActions.setParams(paramsParsed);
    }
    private void setSolutionOperations() {
        this.solutionOperations = diagramActions.setSolutionOperations();
    }
    @Override
    public boolean checkNotBaseCase(int index, List<String> inputs) throws Exception {
        return diagramActions.checkNotBaseCase(baseCaseParameters.get(index),inputs);
    }

    private Map<String, String> calculateSolution(int selectedIndex) throws Exception {
        return diagramActions.calculateSolution(selectedIndex);
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get().replace("f = ",""));
    }
    @Override
    public String calculateWithSelectedOperation(int index) {
        calculatedSol.set(String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).get()));
        return calculatedSol.get();
    }

    private void setSubData(Map<String, String> data) {
        data.forEach((k,v)->{
            if(k.contains("reducedOperation")){
                subParameters.add(new SimpleStringProperty(v));
            }
            if(k.contains("partSol")){
                subSolutions.add(new SimpleStringProperty("f' = "+v));
            }
        });
    }
}
