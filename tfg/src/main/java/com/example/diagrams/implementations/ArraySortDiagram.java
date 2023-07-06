package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ArraySortDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    Map<Integer,String> functionNames = new HashMap<>(){{
        put(0,"def sort_list(a):\n\tn = len(a)");
        put(1,"def merge_sort(a):\n\tn = len(a)");
        put(2,"def insert_sort(a):\n\tn = len(a)");
        put(3,"def select_sort(a):\n\tn = len(a)");
        put(4,"def quick_sort(a,low,high):\n\tn = len(a)");
    }};
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
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String getFunctionName(int index) {
        return functionNames.get(index);
    }

    @Override
    public boolean checkIfMultipleCases(int selectedIndex) {
        return false;
    }


    @Override
    public Map<String, String> setGenCodeParams(String baseCase, String returnValue) {
        Map<String,String> returnVal = new HashMap<>();
        returnVal.put("baseCase",String.format("if %s:",baseCase));
        returnVal.put("returnValue",String.format("return %s",returnValue));
        return returnVal;
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
        List<Supplier> s3 = new ArrayList<>();
        s3.add((Supplier<String>) () -> {
            int[] nA = new int[array.length];
            System.arraycopy(reducedArray, 0, nA, 0, reducedArray.length);
            nA[array.length - 1] = ele;
            return Arrays.toString(nA);
        });
        s3.add((Supplier<String>) () -> Arrays.toString(Algorithms.selectSort(array)));
        List<Supplier> s4 = new ArrayList<>();
        s4.add((Supplier<String>) () -> Arrays.toString(IntStream.concat(Arrays.stream(new int[]{copyArray[mid-1]}),IntStream.concat(Arrays.stream(l), Arrays.stream(r))).toArray()));
        s4.add((Supplier<String>) () -> Arrays.toString(IntStream.concat(IntStream.concat(Arrays.stream(l), Arrays.stream(r)),Arrays.stream(new int[]{copyArray[mid-1]})).toArray()));
        s4.add((Supplier<String>) () -> Arrays.toString(Algorithms.quicksort(array,mid)));
        return List.of(s1,s2,s3,s4);
    }

    @Override
    public void setAlgorithmMap() {
        algorithmMap.put(-1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("preSolOg","f(a)");
            returnVal.put("ogSol",Arrays.toString(Algorithms.mergeSort(array)));
            return returnVal;
        });
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("reducedOperation1","v1 = "+Arrays.toString(l));
            returnVal.put("reducedOperation2","\nv2 = "+Arrays.toString(r));
            returnVal.put("prePartialSol1","f'(v1)");
            returnVal.put("prePartialSol2","f'(v2)");
            returnVal.put("partSol1",Arrays.toString(Algorithms.mergeSort(l)));
            returnVal.put("partSol2",Arrays.toString(Algorithms.mergeSort(r)));
            returnVal.put("currentReductionSolutions", String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            ele = copyArray[copyArray.length-1];
            reducedArray = Arrays.stream(copyArray).filter(e->e!=ele).toArray();
            returnVal.put("prePartialSol","f'(a[:-1])");
            returnVal.put("reducedOperation","a = "+Arrays.toString(reducedArray));
            returnVal.put("partSol",Arrays.toString(Algorithms.insertSort(reducedArray)));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
            return returnVal;
        });
        algorithmMap.put(2,()->{
            Map<String,String> returnVal = new HashMap<>();
            ele = Algorithms.getSmallest(copyArray);
            reducedArray = Arrays.stream(copyArray).filter(e->e!=ele).toArray();
            returnVal.put("prePartialSol","f'(a)");
            returnVal.put("reducedOperation","a = "+Arrays.toString(reducedArray));
            returnVal.put("partSol",Arrays.toString(Algorithms.selectSort(reducedArray)));
            returnVal.put("currentReductionSolutions",String.valueOf(2));
            return returnVal;
        });
        algorithmMap.put(3,()->{
            Map<String,String> returnVal = new HashMap<>();
            int pivot = copyArray[mid-1];
            int[] smallerElements = Algorithms.getSmaller(copyArray,pivot);
            int[] greaterElements = Algorithms.getGreater(copyArray,pivot);
            returnVal.put("reducedOperation1","v1 = "+Arrays.toString(smallerElements));
            returnVal.put("reducedOperation2","\nv2 = "+Arrays.toString(greaterElements));
            returnVal.put("prePartialSol1","f'(v1)");
            returnVal.put("prePartialSol2","f'(v2)");
            returnVal.put("partSol1",Arrays.toString(Algorithms.quicksort(smallerElements,smallerElements.length / 2)));
            returnVal.put("partSol2",Arrays.toString(Algorithms.quicksort(greaterElements,greaterElements.length / 2)));
            returnVal.put("currentReductionSolutions", String.valueOf(3));
            return returnVal;
        });
    }
    @Override
    public boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) throws Exception {
        int[] input = stringToArrayInt(inputs.get(0));
        copyArray = input.clone();
        if(input.length>1){
            array = input.clone();
            int[] temp = Algorithms.mergeSort(input);
            return Arrays.equals(array,temp);
        }
        else return true;
    }

    @Override
    public boolean checkNotIncorrectInput(List<String> inputs) {
        return false;
    }

    @Override
    public Map<String, String> calculateSolution(int index) throws Exception {
        return algorithmMap.get(index).call();
    }


    private int[] stringToArrayInt(String data) {
        data = data.replaceAll("[\\[\\]()\s{}]","");
        try{
            return Arrays.stream(data.split(",")).map(s->s.replace(" ","")).mapToInt(Integer::parseInt).toArray();
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            throw e;
        }
    }
}
