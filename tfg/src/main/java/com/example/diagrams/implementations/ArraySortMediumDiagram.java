package com.example.diagrams.implementations;

import com.example.algorithms.Algorithms;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ArraySortMediumDiagram implements IDiagramActions, IAuxFuncsActions{
    Map<Integer, Callable<Map<String,String>>> algorithmMap= new HashMap<>();
    Map<Integer,String> functionNames = new HashMap<>(){{
        put(0,"def sort_list(a):\n\tn = len(a)");
        put(1,"def insert_sort(a):\n\tn = len(a)");
        put(2,"def select_sort(a):\n\tn = len(a)");
    }};
    static int[] array;
    static int mid;
    int[] copyArray;
    int[] reducedArray;
    int ele;
    @Override
    public void setParams(Map<String, String> newValues) throws Exception {
        try{
            array = stringToArrayInt(newValues.get("a"));
            mid = Integer.parseInt(newValues.get("mid"));
        }
        catch (Exception e){
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
        return List.of(
            "def inserta_en_lista_ordenada(list,n):\n\tindex = len(list)\n\tfor i in range(len(list)):\n\t\tif list[i] > n:\n\t\t\tindex = i\n\t\t\tbreak\n\tif index == len(list):\n\t\tlist = list[:index] + [n]\n\telse:\n\t\tlist = list[:index] + [n] + list[index:]\n\treturn list",
            "");
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
        s1.add((Supplier<String>) () -> Arrays.toString(Algorithms.insertSort(array)));
        s1.add((Supplier<String>) () -> {
            int[] nA = new int[array.length];
            System.arraycopy(Algorithms.insertSort(reducedArray), 0, nA, 1, reducedArray.length);
            nA[0] = ele;
            return Arrays.toString(nA);
        });
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<String>) () -> {
            int[] nA = new int[array.length];
            System.arraycopy(reducedArray, 0, nA, 0, reducedArray.length);
            nA[array.length - 1] = ele;
            return Arrays.toString(nA);
        });
        s2.add((Supplier<String>) () -> Arrays.toString(Algorithms.selectSort(array)));
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
        algorithmMap.put(0,()->{
            Map<String,String> returnVal = new HashMap<>();
            ele = array[mid-1];
            reducedArray = Arrays.stream(copyArray).filter(e->e!=ele).toArray();
            returnVal.put("prePartialSol1","f'(a[:-1])");
            returnVal.put("prePartialSol2","tail");
            returnVal.put("reducedOperation1","a = "+Arrays.toString(reducedArray));
            returnVal.put("reducedOperation2","tail = "+ele);
            returnVal.put("partSol1",Arrays.toString(Algorithms.insertSort(reducedArray)));
            returnVal.put("partSol2",String.valueOf(ele));
            returnVal.put("currentReductionSolutions",String.valueOf(0));
            return returnVal;
        });
        algorithmMap.put(1,()->{
            Map<String,String> returnVal = new HashMap<>();
            ele = Algorithms.getSmallest(copyArray);
            reducedArray = Arrays.stream(copyArray).filter(e->e!=ele).toArray();
            returnVal.put("prePartialSol1","f'(a)");
            returnVal.put("prePartialSol2","m");
            returnVal.put("reducedOperation1","a = "+Arrays.toString(reducedArray));
            returnVal.put("reducedOperation2","m = "+ele);
            returnVal.put("partSol1",Arrays.toString(Algorithms.selectSort(reducedArray)));
            returnVal.put("partSol2",String.valueOf(ele));
            returnVal.put("currentReductionSolutions",String.valueOf(1));
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
            throw e;
        }
    }
}
