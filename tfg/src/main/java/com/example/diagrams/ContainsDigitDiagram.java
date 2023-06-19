package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

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
        String bC = "";
        String rV = "";
        if(baseCase.contains("\n")){
            System.out.println(Arrays.toString(baseCase.split("\n")));
            System.out.println(Arrays.toString(returnValue.split("\n")));

        }
        else{
            bC = baseCase;
            rV = returnValue;
        }
        returnVal.put("baseCase",String.format("if %s:",bC));
        returnVal.put("returnValue",String.format("return %s",rV));
        return returnVal;
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1, () ->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.containsDigitTail(n,d)));
            return returnVal;
        });
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("partSol",String.valueOf(Algorithms.containsDigitTailLineal(n/10,d)));
            returnVal.put("reducedOperation",String.format("%.0f, %.0f",n/10,d));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("partSol",String.valueOf(Algorithms.containsDigitTail(n/10,d)));
            returnVal.put("reducedOperation",String.format("%.0f, %.0f",n/10,d));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
            return returnVal;
        });
    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases, List<String> inputs) throws Exception {
        return inputs.get(1).length() <= 1;
    }

    @Override
    public Map<String, String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol);
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
