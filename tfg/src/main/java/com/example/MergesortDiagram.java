package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class MergesortDiagram extends Diagram{

    public MergesortDiagram(List<Integer> correctChoices, String operation) {
        super(correctChoices, operation);
        this.problemSizeChoices.add("N");
        List<String> bCases1 = Arrays.asList("1");
        this.baseCaseChoices.add(bCases1);

        List<String> reds1 = Arrays.asList("a / 2");
        this.reductionChoices.add(reds1);

        List<String> sols1 = new ArrayList<>();
        sols1.add("c + b");
        sols1.add("concatenate c and c");
        sols1.add("merge b and c");
        this.solutionsChoices=Arrays.asList(sols1);
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        algorithmsMap.put(0, new Callable<int[][]>() {
            @Override
            public int[][] call() throws Exception {
                //0 es para la solucion final, 1 para el L y 2 para el R
                int[][] solutions = new int[3][];
                int[] data = Algorithms.stringToArrayInt(inputs.get());
                int mid = data.length / 2;
                int[] l = new int[mid];
                int[] r = new int[data.length - mid];
                for (int i = 0; i < mid; i++) {
                    l[i] = data[i];
                }
                for (int i = mid; i < data.length; i++) {
                    r[i - mid] = data[i];
                }
                partialData.get(0).set(Arrays.toString(l));
                partialData.get(1).set(Arrays.toString(r));

                solutions[0] = Algorithms.mergeSort(data);
                solutions[1] = Algorithms.mergeSort(l);
                solutions[2] = Algorithms.mergeSort(r);
                currentReductionSolutions=0;
                return solutions;
            }
        });
    }
    @Override
    public void processInputs() throws Exception {
        try{
            super.processInputs();
            algorithmIndex = currentProblemSize+currentReduction;
            int[][] values = (int[][]) algorithmsMap.get(algorithmIndex).call();
            originalData.set(this.rawData);
            originalSol.set(Arrays.toString(values[0]));
            partialSol.get(0).set(Arrays.toString(values[1]));
            partialSol.get(1).set(Arrays.toString(values[2]));
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }
}