package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ReverseStringDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static String input,partSol;
    static String baseCaseValue;
    @Override
    public void setParams(Map<String, String> newValues) {
        input = newValues.get("input");
        partSol = newValues.get("partSol");
        baseCaseValue = newValues.get("baseCaseValue");
    }

    @Override
    public String getFunctionName(int index) {
        return "def reverse_string(s):";
    }

    @Override
    public Map<String, String> setGenCodeParams(String baseCase, String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        returnVal.put("baseCase",String.format("if %s:",baseCase));
        returnVal.put("returnValue",String.format("return %s",returnValue));
        returnVal.put("auxCode","char=s[0]");
        return returnVal;
    }

    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<String>) () -> input.charAt(0) + partSol);
        s1.add((Supplier<String>) () -> partSol + input.charAt(0));
        return List.of(s1);
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",Algorithms.reverseString(input,baseCaseValue));
            return returnVal;
        });
        algorithmMap.put(0,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("partSol",Algorithms.reverseString(input.substring(1),baseCaseValue));
            returnVal.put("reducedOperation",input.substring(1));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) {
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
