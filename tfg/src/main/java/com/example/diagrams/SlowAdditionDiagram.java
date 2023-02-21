package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class SlowAdditionDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    @Override
    public List<List<Supplier>> setSolutionOperations(Map<String, String> params) {
        double a = Double.parseDouble(params.get("a"));
        double b = Double.parseDouble(params.get("b"));
        double partialSol = Double.parseDouble(params.get("partSol"));

        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<Double>) () -> partialSol + 1);
        s1.add((Supplier<Double>) () -> partialSol - 1);
        s1.add((Supplier<Double>) () -> partialSol + a);
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<Double>) () -> partialSol + 1);
        s2.add((Supplier<Double>) () -> partialSol - 1);
        s2.add((Supplier<Double>) () -> partialSol + Math.min(a,b));
        List<Supplier> s3 = new ArrayList<>();
        s3.add((Supplier<Double>) () -> partialSol + 1);
        s3.add((Supplier<Double>) () -> partialSol);
        s3.add((Supplier<Double>) () -> partialSol + 2);
        return Arrays.asList(s1, s2, s3);
    }

    @Override
    public void setAlgorithmMap(Map<String, String> params) {
        double a = Double.parseDouble(params.get("a"));
        double b = Double.parseDouble(params.get("b"));
        int baseCaseValue = Integer.parseInt(params.get("baseCaseValue"));
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.slowAdditionOption1(a,b,baseCaseValue)));
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption1(a-1,b,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("%d, %d",(int)a-1,(int)b));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.slowAdditionOption2(a,b,baseCaseValue)));
            if(a<b){
                returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption2(a-1,b,baseCaseValue)));
            }
            else{
                returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption2(a,b-1,baseCaseValue)));
            }
            returnVal.put("reducedOperation",String.valueOf(Math.min(a, b-1)));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
            //subSolutions.get(0).set(Math.min(a,b)+ " - 1 + " + Math.max(a,b));
            return returnVal;
        });
        algorithmMap.put(2,() -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol",String.valueOf(Algorithms.slowAdditionOption3(a,b,baseCaseValue)));
            returnVal.put("partSol",String.valueOf(Algorithms.slowAdditionOption3(a-1,b-1,baseCaseValue)));
            returnVal.put("reducedOperation",String.format("(%.0f - 1 , %.0f - 1)", a, b));
            returnVal.put("currentReductionSolutions",String.valueOf(2));
            return returnVal;
        });
    }

    @Override
    public boolean checkNotBaseCase(List<String> baseCases, Map<String, String> params) {
        for (String baseCase : baseCases) {
            if ((Objects.equals(params.get("a"), baseCase)) || (Objects.equals(params.get("b"), baseCase))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int index, Map<String, String> params) throws Exception {
        return algorithmMap.get(index).call();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol);
    }
}
