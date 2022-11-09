package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class RecursivePotencyDiagram extends Diagram{
    public RecursivePotencyDiagram(List<Integer> correctChoices, String operation) {
        super(correctChoices, operation);
        this.baseCase.set("1");
        this.reductionChoices.add("b - 1");
        this.reductionChoices.add("b / 2");

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
