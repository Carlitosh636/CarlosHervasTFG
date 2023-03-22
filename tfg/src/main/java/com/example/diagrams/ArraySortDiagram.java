package com.example.diagrams;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ArraySortDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    static int[] array;
    static int mid;
    static int[] l;
    static int[] r;
    int[] copyArray;
    int[] reducedArray;
    int ele;
    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        try{
            array = stringToArrayInt(newValues.get("array"));
            mid = Integer.parseInt(newValues.get("mid"));
            l = stringToArrayInt(newValues.get("l"));
            r = stringToArrayInt(newValues.get("r"));
            if(copyArray==null){
                copyArray = array.clone();
            }
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
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<String>) () -> Arrays.toString(Algorithms.insertSort(array)));
        s2.add((Supplier<String>) () -> {
            int[] nA = new int[array.length];
            System.arraycopy(Algorithms.insertSort(reducedArray), 0, nA, 1, reducedArray.length);
            nA[0] = ele;
            return Arrays.toString(nA);
        });
        return List.of(s1,s2);
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
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            ele = copyArray[copyArray.length-1];
            reducedArray = Arrays.stream(copyArray).filter(e->e!=ele).toArray();
            returnVal.put("reducedOperation",Arrays.toString(reducedArray));
            returnVal.put("partSol1",Arrays.toString(Algorithms.insertSort(reducedArray)));
            returnVal.put("partSol2",Arrays.toString(reducedArray));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
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
