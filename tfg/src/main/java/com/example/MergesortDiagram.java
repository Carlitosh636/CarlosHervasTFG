package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MergesortDiagram extends Diagram{
    int[] data;
    int mid;
    int[] l;
    int[] r;
    public MergesortDiagram(String operation) {
        super(operation);
        this.type = DiagramType.COMPLEX;
        this.problemSizeChoices.add("N");
        List<String> bCases1 = Arrays.asList("1");
        this.baseCaseChoices.add(bCases1);

        List<String> reds1 = Arrays.asList("a / 2");
        this.reductionChoices.add(reds1);

        List<Callable> s1 = new ArrayList<>();
        s1.add((Callable<int[]>) () -> IntStream.concat(Arrays.stream(l),Arrays.stream(r)).toArray());
        s1.add((Callable<int[]>) () -> Algorithms.merge(data,l,r,mid,data.length-mid));
        this.solutionOperations = Arrays.asList(s1);

        List<String> sols1 = new ArrayList<>();
        sols1.add("concatenate c and c");
        sols1.add("merge b and c");
        this.solutionsChoices=Arrays.asList(sols1);
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        this.parametersFormat.set("Formato: a,b,c,d,e...z");
        algorithmsMap.put(0, new Callable<int[][]>() {
            @Override
            public int[][] call() {
                //0 es para la solucion final, 1 para el L y 2 para el R
                int[][] solutions = new int[3][];
                System.arraycopy(data, 0, l, 0, mid);
                if (data.length - mid >= 0) System.arraycopy(data, mid, r, mid - mid, data.length - mid);

                partialData.get(0).set(Arrays.toString(l));
                partialData.get(1).set(Arrays.toString(r));
                solutions[0] = Algorithms.mergeSort(data);
                solutions[1] = Algorithms.mergeSort(l);
                solutions[2] = Algorithms.mergeSort(r);
                currentReductionSolutions.set(0);
                return solutions;
            }
        });
    }
    @Override
    public void processInputs() throws Exception {
        try{
            super.processInputs();
            algorithmIndex = currentProblemSize.get()+currentReduction.get();
            if(checkNotBaseCase(currentProblemSize.get()+currentBaseCase.get())){
                throw new BaseCaseException("Cannot introduce a base case in parameters");
            }
            data = Algorithms.stringToArrayInt(inputs.get());
            mid = data.length / 2;
            l = new int[mid];
            r = new int[data.length - mid];
            int[][] values = (int[][]) algorithmsMap.get(algorithmIndex).call();
            storedSolutions.add(Arrays.toString(values[0]));
            originalData.set(this.problemData.toString());
            originalSol.set(Arrays.toString(values[0]));
            partialSol.get(0).set(Arrays.toString(values[1]));
            partialSol.get(1).set(Arrays.toString(values[2]));
        }
        catch (RuntimeException e){
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNotBaseCase(int index) {
        return problemData.size() == 1;
    }

}
