package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class RecursivePotencyDiagram extends Diagram{
    public RecursivePotencyDiagram(List<Integer> correctChoices, String operation) {
        super(correctChoices, operation);
        this.problemSizeChoices.add("b");
        this.baseCase.set("1");
        List<String> reds1 = Arrays.asList("b - 1","b / 2");
        this.reductionChoices.add(reds1);
        List<String> sols1 = new ArrayList<>();
        sols1.add("multiplicar por 'b'");
        sols1.add("multiplicar por 'a'");
        sols1.add("elevar a 'a'");
        List<String> sols2 = new ArrayList<>();
        sols2.add("elevar al cuadrado");
        sols2.add("multiplicar por 'b'");
        List<String> sols3 = new ArrayList<>();
        sols3.add("elevar al cuadrado");
        sols3.add("multiplicar por 'b'");
        sols3.add("las 2 anteriores juntas");
        this.solutionsChoices=Arrays.asList(sols1,sols2,sols3);

        algorithmsMap.put(0, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseProperty().get()));
                returnVal[1] = Algorithms.recursiveExponentOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))-1,Integer.parseInt(baseCaseProperty().get()));
                reducedOperation=problemData.get(0)+operation+(Integer.parseInt(problemData.get(1))-1);
                currentReductionString="b-1";
                currentReductionSolutions=0;
                return returnVal;

            }
        });
        algorithmsMap.put(1, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption2(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseProperty().get()));
                returnVal[1] = Algorithms.recursiveExponentOption2(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))/2,Integer.parseInt(baseCaseProperty().get()));

                if(Integer.parseInt(problemData.get(1))%2==0){
                    currentReductionString="b/2";
                    reducedOperation=problemData.get(0)+operation+(Integer.parseInt(problemData.get(1))/2);
                    currentReductionSolutions=1;
                }
                else{
                    currentReductionString="(b-1)/2";
                    reducedOperation=problemData.get(0)+operation+((Integer.parseInt(problemData.get(1))-1)/2);
                    currentReductionSolutions=2;
                }
                return returnVal;
            }
        });
    }

    @Override
    public void processInputs() throws Exception{
        try{
            super.processInputs();
            originalData.set(this.rawData);
            originalSol.set(String.valueOf(ogSol));
            partialData.set(reducedOperation);
            partialSol.set(String.valueOf(partSol));
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }
}
