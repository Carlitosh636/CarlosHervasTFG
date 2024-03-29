package com.example.diagrams.abstractions;

import com.example.diagrams.implementations.IDiagramActions;
import com.example.exceptions.IncorrectInputException;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringProblem extends BaseProblem {
    public StringProblem(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
    }
    @Override
    public void processInputs() throws Exception {
        LinkedHashMap<String,String> paramsParsed = new LinkedHashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        diagramActions.setParams(paramsParsed);
        Map<String,String> solution = calculateSolution(-1);
        originalSol.set(String.format("%s = %s",solution.get("preSolOg"),solution.get("ogSol")));
    }

    @Override
    public Map<String, String> processProblemSizeAndBaseCases() {
        return diagramActions.setGenCodeParams(baseCaseChoices.get(currentProblemSize.get()).get(currentBaseCaseIndex), returnValues.get(currentProblemSize.get()).get(currentBaseCaseIndex));
    }

    @Override
    public void processSolutions() throws Exception {
        if (diagramActions.checkNotIncorrectInput(params.values().stream().map(SimpleStringProperty::get).toList())){
            throw new IncorrectInputException("Los valores introducidos no son válidos");
        }
        algorithmIndex = currentProblemSize.get() + currentReduction.get() + currentBaseCaseIndex;
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
    public boolean checkSolutionsEqual(String calcSol, boolean isOther) throws Exception {
        String ogSol = diagramActions.calculateSolution(-1).get("ogSol");
        return calcSol.equals(ogSol);
    }
    @Override
    public String calculateWithSelectedOperation(int index, int currentSolutions,int offset) {
        calculatedSol.set(String.valueOf(solutionOperations.get(currentSolutions).get(index).get()));
        return calculatedSol.get();
    }

    @Override
    public List<String> setVisualizerParams() {
        return null;
    }

    @Override
    public boolean isMainDiagram() {
        return false;
    }


    private void setSubData(Map<String, String> data) {
        data.forEach((k,v)->{
            if(k.contains("reducedOperation")){
                subParameters.add(new SimpleStringProperty(v));
            }
            if(k.contains("partSol")){
                subSolutions.add(new SimpleStringProperty(String.format("%s = %s", data.get("prePartialSol"),v)));
            }
        });
    }
}
