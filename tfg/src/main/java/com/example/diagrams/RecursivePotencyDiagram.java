package com.example.diagrams;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecursivePotencyDiagram implements IDiagramActions{
    //aquí es donde se implementan las ACCIONES específicas de cada método.
    @Override
    public boolean checkNotBaseCase(List<String> baseCases, Map<String, String> params) {
        for (String baseCase : baseCases) {
            if(Objects.equals(params.get(1), baseCase)){
                return true;
            }
        }
        return false;

    }

    @Override
    public String calculateSolution() {
        return null;
    }

    @Override
    public boolean checkSolutionsEqual() {
        return false;
    }
}
