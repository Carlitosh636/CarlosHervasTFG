package com.example.diagrams;

import com.example.algorithms.Algorithms;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class RecursivePowerDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    @Override
    public List<List<Supplier>> setSolutionOperations(Map<String,String> params) {
        double a = Double.parseDouble(params.get("a"));
        double b = Double.parseDouble(params.get("b"));
        double partSol = Double.parseDouble(params.get("partSol"));

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
    public void setAlgorithmMap(Map<String, String> params) {
        double a = Double.parseDouble(params.get("a"));
        double b = Double.parseDouble(params.get("b"));
        int baseCaseValue = Integer.parseInt(params.get("baseCaseValue"));

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
    public boolean checkNotBaseCase(List<String> baseCases, Map<String, String> params) {
        for (String baseCase : baseCases) {
            if(Objects.equals(params.get("b"), baseCase)){
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
