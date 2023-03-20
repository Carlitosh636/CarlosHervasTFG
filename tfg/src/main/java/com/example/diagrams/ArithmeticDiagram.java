package com.example.diagrams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArithmeticDiagram extends BaseDiagram{
    double partSol;
    public ArithmeticDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
    }

    @Override
    public void processInputs() throws Exception {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        paramsParsed.put("partSol","0");
        diagramActions.setParams(paramsParsed);
        Map<String,String> solution = calculateSolution(-1);
        originalSol.set(String.format("%.0f", Double.parseDouble(solution.get("ogSol"))));
    }
    @Override
    public void processSolutions() throws Exception {
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        Map<String,String> solutions = calculateSolution(algorithmIndex);
        subParameters.get(0).set(solutions.get("reducedOperation"));
        subSolutions.get(0).set(String.format("%.0f", Double.parseDouble(solutions.get("partSol"))));
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        partSol = Double.parseDouble(solutions.get("partSol"));
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
    public boolean checkNotBaseCase(int index, List<String> input) throws Exception {
        return diagramActions.checkNotBaseCase(baseCaseParameters.get(index),input);
    }

    @Override
    public Map<String,String> calculateSolution(int selectedIndex) throws Exception {
        return diagramActions.calculateSolution(selectedIndex);
    }

    @Override
    public String calculateWithSelectedOperation(int index) {
        calculatedSol.set(String.format(
                "%.0f",
                (double) solutionOperations.get(currentReductionSolutions.get()).get(index).get())
        );
        return calculatedSol.get();
    }
    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get());
    }

}
