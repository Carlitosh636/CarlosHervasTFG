package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ContainsDigitDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    double n,d;

    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> sols1 = new ArrayList<>();
        sols1.add((Supplier<String>) () -> String.valueOf((n % 10 == d) || Algorithms.containsDigitTailLineal(n/10,d)));
        sols1.add((Supplier<String>) () -> String.valueOf((n % 10 == d) && Algorithms.containsDigitTailLineal(n/10,d)));
        sols1.add((Supplier<String>) () -> String.valueOf((n % 10 == 0) || Algorithms.containsDigitTailLineal(n/10,d)));
        List<Supplier> sols2 = new ArrayList<>();
        sols2.add((Supplier<String>) () -> String.valueOf(!Algorithms.containsDigitTail(n/10,d)));
        sols2.add((Supplier<String>) () -> String.valueOf((n % 10 == d) || Algorithms.containsDigitTail(n/10,d)));
        sols2.add((Supplier<String>) () -> String.valueOf(Algorithms.containsDigitTail(n/10,d)));

        return Arrays.asList(sols1, sols2);
    }

    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        n = Double.parseDouble(newValues.get("n"));
        d = Double.parseDouble(newValues.get("d"));
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
        algorithmMap.put(-1, () ->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("preSolOg","f(n,d)");
            returnVal.put("ogSol",String.valueOf(Algorithms.containsDigitTail(n, d)));
            return returnVal;
        });
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(n//10,d)");
            returnVal.put("partSol",String.valueOf(Algorithms.containsDigitTailLineal(n/10, d)));
            returnVal.put("reducedOperation",String.format("n = %.0f\nd = %.0f",n/10,d));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("prePartialSol","f'(n//10,d)");
            returnVal.put("partSol",String.valueOf(Algorithms.containsDigitTail(n/10, d)));
            returnVal.put("reducedOperation",String.format("n = %.0f\nd = %.0f",n/10,d));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
            return returnVal;
        });
    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases, List<String> inputs) throws Exception {
        return inputs.get(1).length() <= 1;
    }

    @Override
    public boolean checkNotIncorrectInput(List<String> inputs) {
        for (String input : inputs){
            try {
                double d = Double.parseDouble(input);
            } catch (NumberFormatException nfe) {
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
    public String getFunctionName(int index) {
        return switch (index) {
            case 0 -> "def contiene_digito_cola_lineal(n,d)";
            case 1 -> "def contiene_digito_cola(n,d)";
            default -> null;
        };
    }

    @Override
    public boolean checkIfMultipleCases(int selectedIndex) {
        return false;
    }
}
