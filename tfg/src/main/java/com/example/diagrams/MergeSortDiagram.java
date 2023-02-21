package com.example.diagrams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class MergeSortDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    @Override
    public List<List<Supplier>> setSolutionOperations(Map<String, String> params) {
        return null;
    }

    @Override
    public void setAlgorithmMap(Map<String, String> params) {

    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases, Map<String, String> params) {
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int index, Map<String, String> params) throws Exception {
        return null;
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return false;
    }
}
