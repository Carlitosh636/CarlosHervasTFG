package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class EqualStringsDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static String[] inputs;
    static String baseCaseValue;
    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<String>) () -> String.valueOf((inputs[0].charAt(0) == inputs[1].charAt(0)) || (inputs[0].equals(inputs[1]))));
        s1.add((Supplier<String>) () -> String.valueOf((inputs[0].charAt(0) == inputs[1].charAt(0)) && (inputs[0].equals(inputs[1]))));
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<String>) () -> String.valueOf(!inputs[0].equals(inputs[1])));
        s2.add((Supplier<String>) () -> String.valueOf(inputs[0].equals(inputs[1])));
        return List.of(s1,s2);
    }

    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        inputs = newValues.get("input").split(",");
        baseCaseValue = newValues.get("baseCaseValue");
    }

    @Override
    public String getFunctionName(int index) {
        return "def equal_strings(s, t):";
    }
    @Override
    public Map<String, String> setGenCodeParams(String problemSize, String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        returnVal.put("baseCase",String.format("if %s == %s:",problemSize,""));
        returnVal.put("returnValue",String.format("return %s",returnValue));
        return returnVal;
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.equalStrings1(inputs[0],inputs[1],baseCaseValue)));
            return returnVal;
        });
        algorithmMap.put(0,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.equalStrings1(inputs[0],inputs[1],baseCaseValue)));
            returnVal.put("partSol",returnVal.get("ogSol"));
            returnVal.put("reducedOperation",inputs[0].substring(1)+","+inputs[1].substring(1));
            returnVal.put("currentReductionSolutions","0");
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.equalStrings2(inputs[0],inputs[1],baseCaseValue)));
            returnVal.put("partSol",returnVal.get("ogSol"));
            returnVal.put("reducedOperation",inputs[0].substring(1)+","+inputs[1].substring(1));
            returnVal.put("currentReductionSolutions","1");
            return returnVal;
        });
    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases, List<String> inputs) throws Exception {
        for (String baseCase : baseCases) {
            if(Objects.equals(inputs.get(0), baseCase)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol);
    }

}
