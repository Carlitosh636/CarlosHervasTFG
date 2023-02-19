package com.example.diagrams;

import com.example.exceptions.BaseCaseException;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArithmeticDiagram extends BaseDiagram{
    private double a,b;
    private double partSol;
    private String sol;

    public ArithmeticDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
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
    public void processInputs(Map<String, SimpleStringProperty> params) throws Exception {
        this.a = Double.parseDouble(this.params.get("a").get());
        this.b = Double.parseDouble(this.params.get("b").get());
        this.partSol = 0;
        StringBuilder formattedValues = new StringBuilder();
        for(SimpleStringProperty value : params.values()){
            System.out.println(value);
            formattedValues.append(value.get()).append(", ");
        }
        subParameters.get(0).set(formattedValues.toString());
        algorithmIndex = currentProblemSize.get() + currentReduction.get();
        if (checkNotBaseCase(currentProblemSize.get()+currentBaseCase.get())) {
            throw new BaseCaseException("Cannot introduce a base case in parameters");
        }
        //TODO: calculate the solutions. ¿How do we do it?
        //String ogSol = calculateSolution(indice);
        //String pSol = calculateSolution(otroIndice);

        String ogSol = "PLACEHOLDER";
        String pSol = "PLACEHOLDER";

        originalSol.set(ogSol);
        subParameters.get(0).set(reducedOperation);
        subSolutions.get(0).set(pSol);
        //put the solution operations now that we have params and partSol values
        setSolutionOperations();
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
        //TODO: IMPLEMENT
        return diagramActions.calculateSolution();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return diagramActions.checkSolutionsEqual(calcSol,originalSol.get());
    }
}
