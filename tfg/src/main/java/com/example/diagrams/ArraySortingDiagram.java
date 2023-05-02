package com.example.diagrams;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.*;

public class ArraySortingDiagram extends BaseDiagram{
    int[] array;
    int mid;
    int[] l;
    int[] r;
    ArrayList<String> partSols = new ArrayList<>();
    public ArraySortingDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
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
        diagramActions.setParams(paramsParsed);
        Map<String,String> solutions = calculateSolution(-1);
        originalSol.set("f="+solutions.get("ogSol"));
    }
    @Override
    public void processSolutions() throws Exception {
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        Map<String,String> solutions = calculateSolution(algorithmIndex);
        setSubData(solutions);
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        paramsParsed.put("mid", String.valueOf(mid));
        if(partSols.size()>1){
            paramsParsed.put("l", partSols.get(0));
            paramsParsed.put("r", partSols.get(1));
        }
        else{
            paramsParsed.put("l", Arrays.toString(l));
            paramsParsed.put("r", Arrays.toString(r));
        }
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
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get().replace("f=",""));
    }

    @Override
    public String calculateWithSelectedOperation(int index) {
        calculatedSol.set(String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).get()));
        return calculatedSol.get();
    }
    private void setSubData(Map<String, String> data) {
        if(!partSols.isEmpty()){
            partSols.clear();
        }
        data.forEach((k,v)->{
            if(k.contains("reducedOperation")){
                subParameters.add(new SimpleStringProperty(v));
            }
            if(k.contains("partSol")){
                subSolutions.add(new SimpleStringProperty(v));
                partSols.add(v);
            }
        });
    }

}
