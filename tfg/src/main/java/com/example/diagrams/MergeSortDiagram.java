package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MergeSortDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static int[] array;
    static int mid;
    static int[] l;
    static int[] r;

    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        try{
            array = stringToArrayInt(newValues.get("array"));
            mid = Integer.parseInt(newValues.get("mid"));
            l = stringToArrayInt(newValues.get("l"));
            r = stringToArrayInt(newValues.get("r"));
        }
        catch (Exception e){
            throw e;
        }

    }

    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<String>) () -> Arrays.toString(IntStream.concat(Arrays.stream(l), Arrays.stream(r)).toArray()));
        s1.add((Supplier<String>) () -> Arrays.toString(Algorithms.merge(array, l, r, mid, array.length - mid)));
        return List.of(s1);
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("ogSol", Arrays.toString(Algorithms.mergeSort(array)));
            return returnVal;
        });
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("reducedOperation1",Arrays.toString(l));
            returnVal.put("reducedOperation2",Arrays.toString(r));
            returnVal.put("partSol1",Arrays.toString(Algorithms.mergeSort(l)));
            returnVal.put("partSol2",Arrays.toString(Algorithms.mergeSort(r)));
            returnVal.put("currentReductionSolutions", String.valueOf(0));
            return returnVal;
        });
    }
    @Override
    public boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) throws Exception {
        int[] input = stringToArrayInt(inputs.get(0));
        if(input.length>1){
            array = input.clone();
            int[] temp = Algorithms.mergeSort(input);
            return Arrays.equals(array,temp);
        }
        else return true;
    }

    @Override
    public Map<String, String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol);
    }
    private int[] stringToArrayInt(String data) throws Exception{
        data = data.replaceAll("[\\[\\]()\s{}]","");
        try{
            return Arrays.stream(data.split(",")).mapToInt(Integer::parseInt).toArray();
        }
        catch (NumberFormatException e){
            throw e;
        }
    }
}
