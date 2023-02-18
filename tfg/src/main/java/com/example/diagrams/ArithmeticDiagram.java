package com.example.diagrams;

import com.example.exceptions.BaseCaseException;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.Map;

public class ArithmeticDiagram extends BaseDiagram{
    private double a,b;
    private double partSol;
    private String sol;

    protected ArithmeticDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        this.a = Double.parseDouble(this.params.get("a").get());
        this.b = Double.parseDouble(this.params.get("b").get());
    }

    @Override
    protected void processInputs(Map<String, SimpleStringProperty> params) throws BaseCaseException {
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
        //return diagramActions.checkNotBaseCase();
        return false;
    }

    @Override
    protected String calculateSolution() {
        return diagramActions.calculateSolution();
    }

    @Override
    protected boolean checkSolutionsEqual() {
        return diagramActions.checkSolutionsEqual();
    }
}
