package com.example.diagrams;

import com.example.exceptions.BaseCaseException;
import javafx.beans.property.SimpleStringProperty;

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
        StringBuilder formattedValues = new StringBuilder();
        for(SimpleStringProperty value : params.values()){
            formattedValues.append(value.get()).append(", ");
        }
        subParameters.get(0).set(formattedValues.toString());
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        if (checkNotBaseCase(currentProblemSize.get()+currentBaseCase.get())) {
            throw new BaseCaseException("Cannot introduce a base case in parameters");
        }
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        paramsParsed.put("baseCaseValue",baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        diagramActions.setAlgorithmMap(paramsParsed);
        Map<String,String> solutions = calculateSolution(algorithmIndex,params);

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
    protected boolean checkNotBaseCase(int index) {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        return diagramActions.checkNotBaseCase(baseCaseParameters.get(index),paramsParsed);
    }

    @Override
    public Map<String,String>  calculateSolution(int selectedIndex, Map<String,SimpleStringProperty> params) throws Exception {
        Map<String,String> paramsParsed = new HashMap<>();
        params.forEach((k,v)->{
            paramsParsed.put(k,v.get());
        });
        return diagramActions.calculateSolution(selectedIndex,paramsParsed);
    }

    @Override
    public String calculateWithSelectedOperation(int index) {
        System.out.println(solutionOperations.get(currentReductionSolutions.get()).get(index).get());
        return String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).get());
    }
    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get());
    }

}
