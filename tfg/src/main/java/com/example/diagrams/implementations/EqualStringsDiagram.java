package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
        return "def equal_strings(s, t)\n\tn=len(s):";
    }

    @Override
    public boolean checkIfMultipleCases(int selectedIndex) {
        return false;
    }

    @Override
    public Map<String, String> setGenCodeParams(String baseCase, String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        if(baseCase.contains("\n")){
            StringBuilder stringBuilder = new StringBuilder();
            List<String> baseCases = Stream.of(baseCase.split("\n")).map(s -> "\tif " + s + ":\n").toList();
            List<String> returnValues = Stream.of(returnValue.split("\n")).map(s -> "\t\treturn " + s+"\n").toList();
            for(int i = 0; i<baseCases.size();i++){
                stringBuilder.append(baseCases.get(i));
                stringBuilder.append(returnValues.get(i));
            }
            returnVal.put("baseCase",stringBuilder.toString());
            returnVal.put("returnValue","");
        }
        else{
            returnVal.put("baseCase",String.format("if %s:",baseCase));
            returnVal.put("returnValue",String.format("return %s",returnValue));
        }
        return returnVal;
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("preSolOg","f(s,t)");
            returnVal.put("ogSol",String.valueOf(Algorithms.equalStrings1(inputs[0],inputs[1],baseCaseValue)));
            return returnVal;
        });
        algorithmMap.put(0,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(s[1:], t[1:])");
            returnVal.put("partSol",String.valueOf(Algorithms.equalStrings1(inputs[0],inputs[1],baseCaseValue)));
            returnVal.put("reducedOperation",String.format("s = %s\nt = %s",inputs[0].substring(1),inputs[1].substring(1)));
            returnVal.put("currentReductionSolutions","0");
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(s[1:], t[1:])");
            returnVal.put("partSol",String.valueOf(Algorithms.equalStrings2(inputs[0],inputs[1],baseCaseValue)));
            returnVal.put("reducedOperation",String.format("s = %s\nt = %s",inputs[0].substring(1),inputs[1].substring(1)));
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
    public boolean checkNotIncorrectInput(List<String> inputs) {
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }

}
