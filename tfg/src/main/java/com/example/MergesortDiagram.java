package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MergesortDiagram extends Diagram{
    int[] data;
    int mid;
    int[] l;
    int[] r;
    public MergesortDiagram(String operation,String diagramDataName) throws IOException {
        super(operation, diagramDataName);
        this.type = DiagramType.COMPLEX;
        this.problemSizeChoices.add("array.size");
        List<String> bCases1 = Arrays.asList("array.size <= 1");
        this.baseCaseChoices.add(bCases1);

        List<String> reds1 = Arrays.asList("array / 2");
        this.reductionChoices.add(reds1);

        this.params.put("array",new SimpleStringProperty());

        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<int[]>) () -> IntStream.concat(Arrays.stream(l),Arrays.stream(r)).toArray());
        s1.add((Supplier<int[]>) () -> Algorithms.merge(data,l,r,mid,data.length-mid));
        this.solutionOperations = Arrays.asList(s1);

        List<String> sols1 = new ArrayList<>();
        sols1.add("concatenate left and right arrays");
        sols1.add("merge left and right arrays");
        this.solutionsChoices=Arrays.asList(sols1);
        this.correctSolutions = Arrays.asList(1);

        this.subParameters.add(new SimpleStringProperty());
        this.subSolutions.add(new SimpleStringProperty());
        this.subParameters.add(new SimpleStringProperty());
        this.subSolutions.add(new SimpleStringProperty());
        this.parametersView = "array";

        algorithmsMap.put(-1, new Callable<String>() {
            @Override
            public String call() {
                return "array";
            }
        });
        algorithmsMap.put(0, new Callable<int[][]>() {
            @Override
            public int[][] call() {
                //0 es para la solucion final, 1 para el L y 2 para el R
                int[][] solutions = new int[3][];
                System.arraycopy(data, 0, l, 0, mid);
                if (data.length - mid >= 0) System.arraycopy(data, mid, r, mid - mid, data.length - mid);

                subParameters.get(0).set(Arrays.toString(l));
                subParameters.get(1).set(Arrays.toString(r));
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
            mid = data.length / 2;
            l = new int[mid];
            r = new int[data.length - mid];
            int[][] values = (int[][]) algorithmsMap.get(algorithmIndex).call();
            storedSolutions.add(Arrays.toString(values[0]));
            originalData.set(this.params.get("array").get());
            originalSol.set(Arrays.toString(values[0]));
            subSolutions.get(0).set(Arrays.toString(values[1]));
            subSolutions.get(1).set(Arrays.toString(values[2]));
        }
        catch (RuntimeException e){
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNotBaseCase(int index) {
        int[] input = Arrays.stream(params.get("array").get().split(",")).mapToInt(Integer::parseInt).toArray();
        if(input.length>1){
            //COMPROBAR QUE NO ESTÉ ORDENADO
            data = input.clone();
            int[] temp = Algorithms.mergeSort(input);
            return Arrays.equals(data,temp);
        }
        else return true;
    }

}
