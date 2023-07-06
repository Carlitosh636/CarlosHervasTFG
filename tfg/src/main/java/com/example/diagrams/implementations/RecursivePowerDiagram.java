package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class RecursivePowerDiagram implements IDiagramActions, IMultipleDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static double a,b,partSol,partSol2;
    int baseCaseValue;
    @Override
    public void setParams(Map<String, String> newValues) {
        a = Double.parseDouble(newValues.get("a"));
        b = Double.parseDouble(newValues.get("b"));
        partSol = Double.parseDouble(newValues.get("partSol"));
        if (newValues.containsKey("partSol2") && !newValues.get("partSol2").isEmpty()){
            partSol2 = Double.parseDouble(newValues.get("partSol2"));
        }
        baseCaseValue = Integer.parseInt(newValues.get("baseCaseValue"));
    }

    @Override
    public boolean checkIfMultipleCases(int selectedIndex) {
        return selectedIndex == 1;
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

        List<Supplier> sols4 = new ArrayList<>();
        sols4.add((Supplier<Double>) () -> partSol2 * b);
        sols4.add((Supplier<Double>) () -> partSol2 * a);
        sols4.add((Supplier<Double>) () -> Math.pow(partSol2,a));
        List<Supplier> sols5 = new ArrayList<>();
        sols5.add((Supplier<Double>) () -> Math.pow(partSol2,2));
        sols5.add((Supplier<Double>) () -> partSol2 * b);
        List<Supplier> sols6 = new ArrayList<>();
        sols6.add((Supplier<Double>) () -> Math.pow(partSol2,2));
        sols6.add((Supplier<Double>) () -> partSol2 * b);
        sols6.add((Supplier<Double>) () -> a * Math.pow(partSol2,2));
        return Arrays.asList(sols1, sols2, sols3,sols4,sols5,sols6);
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1, () ->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("preSolOg","f(a,b)");
            returnVal.put("ogSol",String.valueOf(Algorithms.recursivePower1(a,b,baseCaseValue)));
            returnVal.put("secondDecomposition",
                    b%2==0 ? "a, (b-1) // 2" : "a, b // 2"
                    );
            returnVal.put("preSolOg2","f(a,b-1)");
            returnVal.put("ogSol2",String.valueOf(Algorithms.recursivePower1(a,b-1,baseCaseValue)));
            return returnVal;
        });

        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(a,b-1)");
            returnVal.put("partSol",String.valueOf(Algorithms.recursivePower1(a,b-1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a,b-1));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1, () -> {
            Map<String,String> returnVal = new HashMap<>();
            if(b%2==0){
                returnVal.put("prePartialSol","f'(a,b//2)");
                returnVal.put("partSol",String.valueOf(Algorithms.recursivePower2(a,b/2,baseCaseValue)));
                returnVal.put("prePartialSol2","f'(a,(b-1)//2)");
                returnVal.put("partSol2",String.valueOf(Algorithms.recursivePower2(a,(b-2)/2,baseCaseValue)));
                returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a,b/2));
                returnVal.put("reducedOperation2",String.format("a = %.0f\nb = %.0f",a,(b-2)/2));
                returnVal.put("currentReductionSolutions",String.valueOf(1));
                returnVal.put("currentReductionSolutions2",String.valueOf(2));
            }
            else{
                returnVal.put("prePartialSol","f'(a,(b-1)//2)");
                returnVal.put("partSol",String.valueOf(Algorithms.recursivePower2(a,(b-1)/2,baseCaseValue)));
                returnVal.put("prePartialSol2","f'(a,b//2)");
                returnVal.put("partSol2",String.valueOf(Algorithms.recursivePower2(a,(b-1)/2,baseCaseValue)));
                returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a,(b-1)/2));
                returnVal.put("reducedOperation2",String.format("a = %.0f\nb = %.0f",a,(b-1)/2));
                returnVal.put("currentReductionSolutions",String.valueOf(2));
                returnVal.put("currentReductionSolutions2",String.valueOf(1));
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
    public boolean checkNotIncorrectInput(List<String> inputs) {
        for(String input : inputs){
            try{
                int i = Integer.parseInt(input);
            }
            catch (NumberFormatException e){
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
    public Map<String, String> setVisualizerParams() {
        Map<String, String> vals = new HashMap<>();
        vals.put("a",String.format("%.0f",a));
        vals.put("b",String.format("%.0f",b-1));
        return vals;
    }

    @Override
    public int determineMultipleDiagramKeyOffset() {
        if(b % 2 == 0)
            return 0;
        else return 1;
    }
}
