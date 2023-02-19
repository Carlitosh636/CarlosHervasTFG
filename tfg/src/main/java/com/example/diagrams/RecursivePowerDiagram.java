package com.example.diagrams;

import java.util.*;
import java.util.function.Supplier;

public class RecursivePowerDiagram implements IDiagramActions{
    @Override
    public List<List<Supplier>> setSolutionOperations(Map<String,String> params) {
        double a = Double.parseDouble(params.get("a"));
        double b = Double.parseDouble(params.get("b"));
        double partSol = Double.parseDouble(params.get("partSol"));

        List<Supplier> sols1 = new ArrayList<>();
        sols1.add((Supplier<Double>) () -> partSol * a);
        sols1.add((Supplier<Double>) () -> partSol * b);
        sols1.add((Supplier<Double>) () -> Math.pow(partSol,a));
        List<Supplier> sols2 = new ArrayList<>();
        sols2.add((Supplier<Double>) () -> Math.pow(partSol,2));
        sols2.add((Supplier<Double>) () -> partSol * b);
        List<Supplier> sols3 = new ArrayList<>();
        sols3.add((Supplier<Double>) () -> Math.pow(partSol,2));
        sols3.add((Supplier<Double>) () -> partSol * b);
        sols3.add((Supplier<Double>) () -> a * Math.pow(partSol,2));
        return Arrays.asList(sols1, sols2, sols3);
    }

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
        //TODO: IMPLEMENT
        return null;
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol);
    }


}
