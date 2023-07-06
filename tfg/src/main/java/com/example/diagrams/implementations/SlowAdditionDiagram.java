package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class SlowAdditionDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static double a,b,partSol;
    static int baseCaseValue;

    @Override
    public void setParams(Map<String, String> newValues) {
        a = Double.parseDouble(newValues.get("a"));
        b = Double.parseDouble(newValues.get("b"));
        partSol = Double.parseDouble(newValues.get("partSol"));
        baseCaseValue = Integer.parseInt(newValues.get("baseCaseValue"));
    }

    @Override
    public String getFunctionName(int index) {
        return "def slow_addition(a, b):";
    }

    @Override
    public boolean checkIfMultipleCases(int selectedIndex) {
        return false;
    }

    @Override
    public Map<String, String> setGenCodeParams(String baseCase,String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        returnVal.put("baseCase",String.format("if %s:",baseCase));
        returnVal.put("returnValue",String.format("return %s",returnValue));
        return returnVal;
    }

    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<Double>) () -> partSol + 1);
        s1.add((Supplier<Double>) () -> partSol - 1);
        s1.add((Supplier<Double>) () -> partSol + a);
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<Double>) () -> partSol + 1);
        s2.add((Supplier<Double>) () -> partSol);
        List<Supplier> s3 = new ArrayList<>();
        s3.add((Supplier<Double>) () -> partSol + 1);
        s3.add((Supplier<Double>) () -> partSol - 1);
        s3.add((Supplier<Double>) () -> partSol + b);
        List<Supplier> s4 = new ArrayList<>();
        s4.add((Supplier<Double>) () -> partSol + 1);
        s4.add((Supplier<Double>) () -> partSol);
        List<Supplier> s5 = new ArrayList<>();
        s5.add((Supplier<Double>) () -> partSol + 1);
        s5.add((Supplier<Double>) () -> partSol - 1);
        s5.add((Supplier<Double>) () -> partSol + Math.min(a,b));
        List<Supplier> s6 = new ArrayList<>();
        s6.add((Supplier<Double>) () -> partSol + 1);
        s6.add((Supplier<Double>) () -> partSol);
        s6.add((Supplier<Double>) () -> partSol + 2);
        return Arrays.asList(s1, s2, s3, s4, s5, s6);
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("preSolOg","f(a,b)");
            returnVal.put("ogSol",String.valueOf(Algorithms.slowAdditionOption1(a,b,baseCaseValue)));
            return returnVal;
        });
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(a-1,b)");
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption1(a-1,b,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a-1,b));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(a-1,b+1)");
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption1(a-1,b+1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a-1,b+1));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
            return returnVal;
        });
        algorithmMap.put(2, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(a,b-1)");
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption1(a,b-1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a,b-1));
            returnVal.put("currentReductionSolutions",String.valueOf(2));
            return returnVal;
        });
        algorithmMap.put(3,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(a+1,b-1)");
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption1(a-1,b+1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f",a+1,b-1));
            returnVal.put("currentReductionSolutions",String.valueOf(3));
            return returnVal;
        });
        algorithmMap.put(4, () -> {
            Map<String,String> returnVal = new HashMap<>();
            if(a<b){
                returnVal.put("prePartialSol","f'(a-1,b)");
                returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption2(a-1,b,baseCaseValue)));
            }
            else{
                returnVal.put("prePartialSol","f'(a,b-1)");
                returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption2(a,b-1,baseCaseValue)));
            }
            returnVal.put("reducedOperation",String.format("min(a,b) = %.0f", Math.min(a, b - 1)));
            returnVal.put("currentReductionSolutions",String.valueOf(4));
            return returnVal;
        });
        algorithmMap.put(5,() -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(a-1,b-1)");
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption3(a-1,b-1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("a = %.0f\nb = %.0f", a-1, b-1));
            returnVal.put("currentReductionSolutions",String.valueOf(5));
            return returnVal;
        });
    }
    @Override
    public boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) {
        for (String baseCase : baseCases) {
            if ((Objects.equals(inputs.get(0), baseCase)) || (Objects.equals(inputs.get(1), baseCase))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkNotIncorrectInput(List<String> inputs) {
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }

}
