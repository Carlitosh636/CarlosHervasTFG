package com.example.diagrams.abstractions;

import com.example.diagrams.implementations.IAuxFuncsActions;
import com.example.diagrams.implementations.IDiagramActions;
import com.example.exceptions.IncorrectInputException;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.*;

public class ArrayProblem extends BaseProblem {
    int[] array;
    int mid;
    int[] l;
    int[] r;
    ArrayList<String> partSols = new ArrayList<>();
    private final IAuxFuncsActions auxFuncsActions;
    public ArrayProblem(IDiagramActions builder, String diagramDataName, IAuxFuncsActions auxFuncsActions) throws IOException {
        super(builder, diagramDataName);
        this.auxFuncsActions = auxFuncsActions;
        diagramActions.setAlgorithmMap();
        this.auxFunctions = List.of("def merge(a,b):\n\tif a == []:\n\t\treturn b\n\telif b == []:\n\t\treturn a\n\telse:\n\t\tif a[0] < b[0]:\n\t\t\treturn [a[0]] + merge(a[1:], b)\n\t\telse:\n\t\t\treturn [b[0]] + merge(a, b[1:])",
                "def inserta_en_lista_ordenada(list,n)\n\tindex = len(list)\n\tfor i in range(len(list)):\n\t\tif list[i] > n:\n\t\t\tindex = i\n\t\t\tbreak\n\tif index == len(list):\n\t\tlist = list[:index] + [n]\n\telse:\n\t\tlist = list[:index] + [n] + list[index:]\n\treturn a",
                "",
                "def get_smaller_than_or_equal_to(a,pivot):\n\treturn [i for i in a if i<=pivot]\ndef get_greater_than(a,pivot):\n\t return [i for i in a if i>pivot]");
        setSolutionOperations();
    }
    @Override
    public void processInputs() throws Exception {
        if (diagramActions.checkNotIncorrectInput(params.values().stream().map(SimpleStringProperty::get).toList())){
            throw new IncorrectInputException("Los valores introducidos no son válidos");
        }
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)-> paramsParsed.put(k,v.get()));
        array = Arrays.stream(params.get("a").get().split(",")).map(s->s.replace(" ","")).mapToInt(Integer::parseInt).toArray();
        mid = array.length / 2;
        l = new int[mid];
        r = new int[array.length - mid];
        System.arraycopy(array, 0, l, 0, mid);
        if (array.length - mid >= 0) System.arraycopy(array, mid, r, mid - mid, array.length - mid);
        paramsParsed.put("mid", String.valueOf(mid));
        paramsParsed.put("l", Arrays.toString(l));
        paramsParsed.put("r", Arrays.toString(r));
        diagramActions.setParams(paramsParsed);
        this.auxFunctions = auxFuncsActions.getAuxFuncs();
        Map<String,String> solutions = calculateSolution(-1);
        originalSol.set(solutions.get("preSolOg")+"="+solutions.get("ogSol"));
    }

    @Override
    public Map<String, String> processProblemSizeAndBaseCases() {
        return diagramActions.setGenCodeParams(baseCaseChoices.get(currentProblemSize.get()).get(currentBaseCaseIndex), returnValues.get(currentProblemSize.get()).get(currentBaseCaseIndex));
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
        paramsParsed.put("l", partSols.get(0));
        paramsParsed.put("r", partSols.get(1));
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
        if(!partSols.isEmpty()){
            partSols.clear();
        }
        subParameters.add(new SimpleStringProperty(data.get("reducedOperation1")));
        if(data.containsKey("pivot")){
            subParameters.add(new SimpleStringProperty(data.get("reducedOperation2")+"\npivot = "+data.get("pivot")));
        }
        else{
            subParameters.add(new SimpleStringProperty(data.get("reducedOperation2")));
        }
        subSolutions.add(new SimpleStringProperty(data.get("prePartialSol1") +" = " + data.get("partSol1")));
        if(data.containsKey("pivot")){
            subSolutions.add(new SimpleStringProperty(data.get("prePartialSol2") +" = " + data.get("partSol2")));
        }
        else{
            subSolutions.add(new SimpleStringProperty(data.get("prePartialSol2") +" = " + data.get("partSol2")));
        }
        partSols.add(data.get("partSol1"));
        partSols.add(data.get("partSol2"));
    }

}
