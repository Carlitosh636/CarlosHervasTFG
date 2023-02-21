package com.example.diagrams;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.Map;

public class ArraySortingDiagram extends BaseDiagram{
    public ArraySortingDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
    }

    @Override
    protected void setSolutionOperations() {

    }

    @Override
    public void processInputs() throws Exception {

    }

    @Override
    public boolean checkNotBaseCase(int index) {
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int selectedIndex, Map<String, SimpleStringProperty> params) throws Exception {
        return null;
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol) {
        return false;
    }

    @Override
    public String calculateWithSelectedOperation(int index) {
        return null;
    }
}
