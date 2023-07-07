package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class VowelsInStringDiagram implements IDiagramActions{

    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static String input;
    static int partSol;
    static char removedChar;
    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<String>) () -> String.valueOf(partSol * Algorithms.isVowel(removedChar)));
        s1.add((Supplier<String>) () -> String.valueOf(partSol - Algorithms.isVowel(removedChar)));
        s1.add((Supplier<String>) () -> String.valueOf(partSol + Algorithms.isVowel(removedChar)));
        return List.of(s1);
    }

    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        input = newValues.get("s");
        partSol = Integer.parseInt(newValues.getOrDefault("partSol","0"));
    }

    @Override
    public Map<String, String> setGenCodeParams(String baseCase, String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        returnVal.put("baseCase",String.format("if %s:",baseCase));
        returnVal.put("returnValue",String.format("return %s",returnValue));
        return returnVal;
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("preSolOg","f(s)");
            returnVal.put("ogSol",String.valueOf(Algorithms.countVowels(input)));
            return returnVal;
        });
        algorithmMap.put(0,()->{
            Map<String,String> returnVal = new HashMap<>();
            removedChar = input.charAt(0);
            returnVal.put("prePartialSol","f'(s[1:])");
            returnVal.put("partSol",String.valueOf(Algorithms.countVowels(input.substring(1))));
            returnVal.put("reducedOperation",String.format("s = %s",input.substring(1)));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
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

    @Override
    public String getFunctionName(int index) {
        return "def countVowels(s):\n\tn = len(s)";
    }

    @Override
    public boolean checkIfMultipleCases(int selectedIndex) {
        return false;
    }
}
