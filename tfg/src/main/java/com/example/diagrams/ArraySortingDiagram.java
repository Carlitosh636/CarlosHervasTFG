package com.example.diagrams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArraySortingDiagram extends BaseDiagram{
    int[] array;
    int mid;
    int[] l;
    int[] r;
    String[] partSols = {"",""};
    public ArraySortingDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
    }

    @Override
    protected void setSolutionOperations() {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        paramsParsed.put("mid", String.valueOf(mid));
        paramsParsed.put("l", Arrays.toString(l));
        paramsParsed.put("r", Arrays.toString(r));
        this.solutionOperations = diagramActions.setSolutionOperations(paramsParsed);
    }

    @Override
    public void processInputs() throws Exception {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        array = Arrays.stream(params.get("array").get().split(",")).mapToInt(Integer::parseInt).toArray();
        mid = array.length / 2;
        l = new int[mid];
        r = new int[array.length - mid];
        System.arraycopy(array, 0, l, 0, mid);
        if (array.length - mid >= 0) System.arraycopy(array, mid, r, mid - mid, array.length - mid);
        paramsParsed.put("mid", String.valueOf(mid));
        paramsParsed.put("l", Arrays.toString(l));
        paramsParsed.put("r", Arrays.toString(r));
        diagramActions.setAlgorithmMap(paramsParsed);
        Map<String,String> solutions = calculateSolution(-1);
        originalSol.set(solutions.get("ogSol"));
    }
    @Override
    public void proccessSolutions() throws Exception {
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        Map<String,String> solutions = calculateSolution(algorithmIndex);
        subParameters.get(0).set(solutions.get("reducedOperation1"));
        subSolutions.get(0).set(solutions.get("partSol1"));
        subParameters.get(1).set(solutions.get("reducedOperation2"));
        subSolutions.get(1).set(solutions.get("partSol2"));
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        partSols[0] = solutions.get("partSol1");
        partSols[1] = solutions.get("partSol2");
        setSolutionOperations();
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
    public Map<String, String> calculateSolution(int selectedIndex) throws Exception {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
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
