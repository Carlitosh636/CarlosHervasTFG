package com.example.diagrams;

import com.example.algorithms.Algorithms;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class RecursivePowerDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    double a,b,partSol;
    int baseCaseValue;
    @Override
    public void setParams(Map<String, String> newValues) {
        a = Double.parseDouble(newValues.get("a"));
        b = Double.parseDouble(newValues.get("b"));
        partSol = Double.parseDouble(newValues.get("partSol"));
        baseCaseValue = Integer.parseInt(newValues.get("baseCaseValue"));
    }

    @Override
    public String getFunctionName(int index) {
        return "def power(a, b):";
    }

    @Override
    public Map<String,String> setGenCodeParams(String baseCase, String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        returnVal.put("baseCase",String.format("if %s:",baseCase));
        returnVal.put("returnValue",String.format("return %s",returnValue));
        return returnVal;
    }

    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> sols1 = new ArrayList<>();
        sols1.add((Supplier<Double>) () -> partSol * b);
        sols1.add((Supplier<Double>) () -> partSol * a);
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

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1, () ->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.recursivePower1(a,b,baseCaseValue)));
            return returnVal;
        });

        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("partSol",String.valueOf(Algorithms.recursivePower1(a,b-1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("%.0f, %.0f",a,b-1));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1, () -> {
            Map<String,String> returnVal = new HashMap<>();
            if(b%2==0){
                returnVal.put("partSol",String.valueOf(Algorithms.recursivePower2(a,b/2,baseCaseValue)));
                returnVal.put("reducedOperation",String.format("%.0f,%.0f",a,b/2));
                returnVal.put("currentReductionSolutions",String.valueOf(1));
            }
            else{
                returnVal.put("partSol",String.valueOf(Algorithms.recursivePower2(a,(b-1)/2,baseCaseValue)));
                returnVal.put("reducedOperation",String.format("%.0f,%.0f",a,(b-1)/2));
                returnVal.put("currentReductionSolutions",String.valueOf(2));
            }
            return returnVal;
        });
    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) {
        for (String baseCase : baseCases) {
            if(Objects.equals(inputs.get(1), baseCase)){
                return true;
            }
        }
        return false;

    }

    @Override
    public Map<String,String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol);
    }

}
