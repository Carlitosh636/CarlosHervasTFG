package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ReverseStringDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    @Override
    public List<List<Supplier>> setSolutionOperations(Map<String, String> params) {
        String partSol = params.get("partSol");
        char a = params.get("input").charAt(0);
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<String>) () -> a + partSol);
        s1.add((Supplier<String>) () -> partSol + a);
        return List.of(s1);
    }

    @Override
    public void setAlgorithmMap(Map<String, String> params) {
        String input = params.get("input");
        String baseCaseValue = params.get("baseCaseValue");
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
    public boolean checkNotBaseCase(List<String> baseCases, Map<String, String> params) {
        for (String baseCase : baseCases) {
            if(Objects.equals(params.get("input"), baseCase)){
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
