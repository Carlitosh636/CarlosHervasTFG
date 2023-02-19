package com.example.diagrams;

import com.example.exceptions.BaseCaseException;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArithmeticDiagram extends BaseDiagram{
    private double a,b;
    private double partSol;
    private String sol;

    public ArithmeticDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        //this.a = Double.parseDouble(this.params.get("a").get());
        //this.b = Double.parseDouble(this.params.get("b").get());
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
    public void processInputs(Map<String, SimpleStringProperty> params) throws BaseCaseException {
        StringBuilder formattedValues = new StringBuilder();
        for(SimpleStringProperty value : params.values()){
            formattedValues.append(value.get()).append(", ");
        }

        originalData.set(formattedValues.toString());
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        if (checkNotBaseCase(currentProblemSize.get()+currentBaseCase.get())) {
            throw new BaseCaseException("Cannot introduce a base case in parameters");
        }
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
    public String calculateSolution(int selectedIndex) {
        return diagramActions.calculateSolution();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,storedSolutions.get(0));
    }
}
