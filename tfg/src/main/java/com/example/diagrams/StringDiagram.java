package com.example.diagrams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringDiagram extends BaseDiagram{
    String partSol;
    public StringDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
    }
    @Override
    public void processInputs() throws Exception {
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        diagramActions.setParams(paramsParsed);
        Map<String,String> solutions = calculateSolution(-1);
        originalSol.set(solutions.get("ogSol"));
    }
    @Override
    public void processSolutions() throws Exception {
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        Map<String,String> solutions = calculateSolution(algorithmIndex);
        subParameters.get(0).set(solutions.get("reducedOperation"));
        subSolutions.get(0).set(solutions.get("partSol"));
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        partSol = solutions.get("partSol");
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        paramsParsed.put("partSol",solutions.get("partSol"));
        diagramActions.setParams(paramsParsed);
    }
    @Override
    protected void setSolutionOperations() {
        this.solutionOperations = diagramActions.setSolutionOperations();
    }
    @Override
    public boolean checkNotBaseCase(int index, List<String> inputs) {
        return diagramActions.checkNotBaseCase(baseCaseParameters.get(index),inputs);
    }

    @Override
    public Map<String, String> calculateSolution(int selectedIndex) throws Exception {
        return diagramActions.calculateSolution(selectedIndex);
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get());
    }
    @Override
    public String calculateWithSelectedOperation(int index) {
        calculatedSol.set(String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).get()));
        return calculatedSol.get();
    }
}
