package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class SlowAdditionDiagram extends Diagram{
    public SlowAdditionDiagram(List<Integer> correctChoices, String operation) {
        super(correctChoices, operation);
        //En este problema concreto es interesante ya que si en los parametros de entrada ponemos A o B = 0 entonces no hay cosa, hayq ue comprobar no solo
        //que sea un numero positivo, sino que sea mayor a 0
        this.problemSizeChoices.add("a + b");
        this.problemSizeChoices.add("min(a,b)");
        this.baseCase.set("0");
        List<String> reds1 = Arrays.asList("a - 1");
        List<String> reds2 = Arrays.asList("min(a,b) - 1","min(a-1,b-1)");
        this.reductionChoices.add(reds1);
        this.reductionChoices.add(reds2);
        List<String> sols1 = new ArrayList<>();
        sols1.add(" + 1");
        sols1.add(" - 1");
        sols1.add(" + a");
        this.solutionsChoices=Arrays.asList(sols1);
        algorithmsMap.put(0, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.slowAdditionOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseProperty().get()));
                returnVal[1] = Algorithms.slowAdditionOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))-1,Integer.parseInt(baseCaseProperty().get()));
                reducedOperation=problemData.get(0)+operation+(Integer.parseInt(problemData.get(1))-1);
                currentReductionString="a-1";
                currentReductionSolutions=0;
                return returnVal;
            }
        });
        algorithmsMap.put(1, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                //TODO
                return null;
            }
        });
        algorithmsMap.put(2, new Callable<Double[]>() {
            @Override
            public Double[] call() throws Exception {
                //TODO
                return null;
            }
        });

    }

    @Override
    public void processInputs() throws Exception {
        try{
            super.processInputs();
            originalData.set(this.rawData);
            originalSol.set(this.problemData.get(0)+" + "+this.problemData.get(1));
            partialData.set(reducedOperation);
            partialSol.set(this.problemData.get(0)+" - 1 + "+this.problemData.get(1));
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }
}
