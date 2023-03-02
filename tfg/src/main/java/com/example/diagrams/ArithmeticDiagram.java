package com.example.diagrams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArithmeticDiagram extends BaseDiagram{
    double partSol;
    public ArithmeticDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
    }

    @Override
    public void processInputs() throws Exception {
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        diagramActions.setAlgorithmMap(paramsParsed);
        Map<String,String> solutions = calculateSolution(algorithmIndex);

        originalSol.set(solutions.get("ogSol"));
        subParameters.get(0).set(solutions.get("reducedOperation"));
        subSolutions.get(0).set(solutions.get("partSol"));
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        partSol = Double.parseDouble(solutions.get("partSol"));
        setSolutionOperations();
    }
    @Override
    protected void setSolutionOperations() {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        paramsParsed.put("partSol",String.valueOf(partSol));
        this.solutionOperations = diagramActions.setSolutionOperations(paramsParsed);
    }
    @Override
    public boolean checkNotBaseCase(int index) {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        return diagramActions.checkNotBaseCase(baseCaseParameters.get(index),paramsParsed);
    }

    @Override
    public Map<String,String> calculateSolution(int selectedIndex) throws Exception {
        return diagramActions.calculateSolution(selectedIndex);
    }

    @Override
    public String calculateWithSelectedOperation(int index) {
        calculatedSol.set(String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).get()));
        return calculatedSol.get();
    }
    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get());
    }

}
