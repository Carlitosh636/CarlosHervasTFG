package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class RecursivePotencyDiagram extends Diagram{
    public RecursivePotencyDiagram(String operation) {
        super(operation);
        this.problemSizeChoices.add("b");

        List<String> bCases1 = Arrays.asList("b<1");
        this.baseCaseChoices.add(bCases1);
        List<String> bc1 = Arrays.asList("0");

        this.baseCaseParameters.add(bc1);

        List<String> reds1 = Arrays.asList("b - 1","b / 2");
        this.reductionChoices.add(reds1);

        List<MidOperation> sols1 = new ArrayList<>();
        sols1.add((val, a, b) -> val * b);
        sols1.add((val, a, b) -> val * a);
        sols1.add((val, a, b) -> Math.pow(val, a));
        List<MidOperation> sols2 = new ArrayList<>();
        sols2.add((val, a, b) -> Math.pow(val, 2));
        sols2.add((val, a, b) -> val * b);
        List<MidOperation> sols3 = new ArrayList<>();
        sols3.add((val, a, b) -> Math.pow(val, 2));
        sols3.add((val, a, b) -> val * b);
        sols3.add((val, a, b) -> a * (Math.pow(val, 2)));
        this.solutionOperations = Arrays.asList(sols1, sols2, sols3);

        List<String> solsChoices1 = new ArrayList<>();
        solsChoices1.add("multiplicar por 'b'");
        solsChoices1.add("multiplicar por 'a'");
        solsChoices1.add("elevar a 'a'");
        List<String> solsChoices2 = new ArrayList<>();
        solsChoices2.add("elevar al cuadrado");
        solsChoices2.add("multiplicar por 'b'");
        List<String> solsChoices3 = new ArrayList<>();
        solsChoices3.add("elevar al cuadrado");
        solsChoices3.add("multiplicar por 'b'");
        solsChoices3.add("a*((a^(b-1/2)))²))");
        this.solutionsChoices=Arrays.asList(solsChoices1,solsChoices2,solsChoices3);

        this.parametersFormat.set("Formato: a,b");
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        algorithmsMap.put(0, new Callable<Double[]>() {
            @Override
            public Double[] call() {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.recursiveExponentOption1(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))-1,Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                reducedOperation=problemData.get(0)+operation+(Integer.parseInt(problemData.get(1))-1);
                currentReductionSolutions.set(0);
                return returnVal;

            }
        });
        algorithmsMap.put(1, new Callable<Double[]>() {
            @Override
            public Double[] call() {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption2(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1)),Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.recursiveExponentOption2(Integer.parseInt(problemData.get(0)),Integer.parseInt(problemData.get(1))/2,Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));

                if(Integer.parseInt(problemData.get(1))%2==0){
                    reducedOperation=problemData.get(0)+operation+(Integer.parseInt(problemData.get(1))/2);
                    currentReductionSolutions.set(1);
                }
                else{
                    reducedOperation=problemData.get(0)+operation+((Integer.parseInt(problemData.get(1))-1)/2);
                    currentReductionSolutions.set(2);
                }
                return returnVal;
            }
        });
    }

    @Override
    public void processInputs() throws Exception{
        try{
            super.processInputs();
            algorithmIndex = currentProblemSize.get()+currentReduction.get();
            if(checkNotBaseCase(currentProblemSize.get()+currentBaseCase.get())){
                throw new BaseCaseException("Cannot introduce a base case in parameters");
            }
            trueValues = (Double[]) algorithmsMap.get(algorithmIndex).call();
            originalData.set(this.rawData);
            originalSol.set(String.valueOf(trueValues[0]));
            partialData.get(0).set(reducedOperation);
            partialSol.get(0).set(String.valueOf(trueValues[1]));
        }
        catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNotBaseCase(int index){
        for (String baseCase : baseCaseParameters.get(index)) {
            if(Objects.equals(problemData.get(1), baseCase)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String calculate(int index) {
        //aplicamos la expresión de la solución escogida a la solución parcial
        return String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).operateWithTwoIntegerParams(
                trueValues[1],//partial sol
                Integer.parseInt(problemData.get(0)), //a
                Integer.parseInt(problemData.get(1))));//b
    }
}
