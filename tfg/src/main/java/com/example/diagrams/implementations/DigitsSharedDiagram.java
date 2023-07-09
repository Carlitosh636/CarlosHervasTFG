package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DigitsSharedDiagram implements IDiagramActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    Map<Integer,String> functionNames = new HashMap<>(){{
        put(0,"def digits_shared(a):\n\tn = len(a)");
        put(1,"def digits_shared_lineal(a):\n\tn = len(a)");
        put(2,"def digits_shared_dyv(a):\n\tn = len(a)");
    }};

    static int[] array;
    static int mid;
    static int[] l;
    static int[] r;
    static int[] reducedArray;
    static int ele;
    int[] copyArray;
    @Override
    public List<List<Supplier>> setSolutionOperations() {
        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<String>) () -> {
            Set<Integer> ints = Algorithms.digitsSharedLineal(reducedArray);
            ints.addAll(Algorithms.getDigitSet(ele));
            return ints.stream().map(String::valueOf).collect(Collectors.toSet()).toString();
        });
        s1.add((Supplier<String>) () -> {
            Set<Integer> ints = Algorithms.digitsSharedLineal(reducedArray);
            ints.retainAll(Algorithms.getDigitSet(ele));
            return ints.stream().map(String::valueOf).collect(Collectors.toSet()).toString();
        });
        s1.add((Supplier<String>) () -> {
            Set<Integer> ints = Algorithms.digitsSharedLineal(reducedArray);
            ints.removeAll(Algorithms.getDigitSet(ele));
            return ints.stream().map(String::valueOf).collect(Collectors.toSet()).toString();
        });
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<String>) () -> {
            Set<Integer> ints = Algorithms.digitsSharedLineal(l);
            ints.addAll(Algorithms.digitsSharedLineal(r));
            return ints.stream().map(String::valueOf).collect(Collectors.toSet()).toString();
        });
        s2.add((Supplier<String>) () -> {
            Set<Integer> ints = Algorithms.digitsSharedLineal(l);
            ints.retainAll(Algorithms.digitsSharedLineal(r));
            return ints.stream().map(String::valueOf).collect(Collectors.toSet()).toString();
        });
        s2.add((Supplier<String>) () -> {
            Set<Integer> ints = Algorithms.digitsSharedLineal(l);
            ints.removeAll(Algorithms.digitsSharedLineal(r));
            return ints.stream().map(String::valueOf).collect(Collectors.toSet()).toString();
        });
        return List.of(s1,s2);
    }

    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        try{
            array = Algorithms.stringToArrayInt(newValues.get("a"));
            mid = Integer.parseInt(newValues.get("mid"));
            l = Algorithms.stringToArrayInt(newValues.get("l"));
            r = Algorithms.stringToArrayInt(newValues.get("r"));
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
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
            returnVal.put("preSolOg","f(a)");
            Set<String> ogSol = Algorithms.digitSharedDyV(array).stream().map(String::valueOf).collect(Collectors.toSet());
            returnVal.put("ogSol", ogSol.toString());
            return returnVal;
        });
        algorithmMap.put(0, () -> {
            Map<String,String> returnVal = new HashMap<>();
            ele = array[mid-1];
            reducedArray = Arrays.stream(array).filter(e->e!=ele).toArray();
            returnVal.put("prePartialSol1","f'(a)");
            returnVal.put("prePartialSol2","ele");
            returnVal.put("reducedOperation1","a = "+ Arrays.toString(reducedArray));
            returnVal.put("reducedOperation2","ele = "+ele);
            Set<String> partSol1 = Algorithms.digitsSharedLineal(reducedArray).stream().map(String::valueOf).collect(Collectors.toSet());
            returnVal.put("partSol1",partSol1.toString());
            returnVal.put("partSol2",String.valueOf(ele));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            returnVal.put("reducedOperation1","v1 = "+Arrays.toString(l));
            returnVal.put("reducedOperation2","v2 = "+Arrays.toString(r));
            returnVal.put("prePartialSol1","f'(v1)");
            returnVal.put("prePartialSol2","f'(v2)");
            Set<String> partSol1 = Algorithms.digitSharedDyV(l).stream().map(String::valueOf).collect(Collectors.toSet());
            returnVal.put("partSol1", partSol1.toString());
            Set<String> partSol2 = Algorithms.digitSharedDyV(r).stream().map(String::valueOf).collect(Collectors.toSet());
            returnVal.put("partSol2", partSol2.toString());
            returnVal.put("currentReductionSolutions", String.valueOf(1));
            return returnVal;
        });
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
    public boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) throws Exception {
        int[] input = Algorithms.stringToArrayInt(inputs.get(0));
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
}
