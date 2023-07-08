package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ArraySortHardDiagram implements IDiagramActions, IAuxFuncsActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    Map<Integer,String> functionNames = new HashMap<>(){{
        put(0,"def sort_list(a):\n\tn = len(a)");
        put(1,"def merge_sort(a):\n\tn = len(a)");
        put(2,"def quick_sort(a,low,high):\n\tn = len(a)");
    }};
    static int[] array;
    static int mid;
    static int[] l;
    static int[] r;
    int[] copyArray;
    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        try{
            array = stringToArrayInt(newValues.get("a"));
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
    public List<String> getAuxFuncs() {
        return List.of("def merge(a,b):\n\tif a == []:\n\t\treturn b\n\telif b == []:\n\t\treturn a\n\telse:\n\t\tif a[0] < b[0]:\n\t\t\treturn [a[0]] + merge(a[1:], b)\n\t\telse:\n\t\t\treturn [b[0]] + merge(a, b[1:])",
            "def get_smaller_than_or_equal_to(a,pivot):\n\treturn [i for i in a if i<=pivot]\ndef get_greater_than(a,pivot):\n\t return [i for i in a if i>pivot]");
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
        s2.add((Supplier<String>) () -> Arrays.toString(IntStream.concat(Arrays.stream(new int[]{copyArray[mid-1]}),IntStream.concat(Arrays.stream(l), Arrays.stream(r))).toArray()));
        s2.add((Supplier<String>) () -> Arrays.toString(IntStream.concat(IntStream.concat(Arrays.stream(l), Arrays.stream(r)),Arrays.stream(new int[]{copyArray[mid-1]})).toArray()));
        s2.add((Supplier<String>) () -> Arrays.toString(Algorithms.quicksort(array,mid)));
        return List.of(s1,s2);
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
            returnVal.put("reducedOperation2","v2 = "+Arrays.toString(r));
            returnVal.put("prePartialSol1","f'(v1)");
            returnVal.put("prePartialSol2","f'(v2)");
            returnVal.put("partSol1",Arrays.toString(Algorithms.mergeSort(l)));
            returnVal.put("partSol2",Arrays.toString(Algorithms.mergeSort(r)));
            returnVal.put("currentReductionSolutions", String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            int pivot = copyArray[mid-1];
            int[] smallerElements = Algorithms.getSmaller(copyArray,pivot);
            int[] greaterElements = Algorithms.getGreater(copyArray,pivot);
            returnVal.put("reducedOperation1","v1 = "+Arrays.toString(smallerElements));
            returnVal.put("reducedOperation2","v2 = "+Arrays.toString(greaterElements));
            returnVal.put("prePartialSol1","f'(v1)");
            returnVal.put("prePartialSol2","f'(v2)");
            returnVal.put("partSol1",Arrays.toString(Algorithms.quicksort(smallerElements,smallerElements.length / 2)));
            returnVal.put("partSol2",Arrays.toString(Algorithms.quicksort(greaterElements,greaterElements.length / 2)));
            returnVal.put("currentReductionSolutions", String.valueOf(1));
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
