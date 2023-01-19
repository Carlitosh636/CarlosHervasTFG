package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class SlowAdditionDiagram extends Diagram {

    public SlowAdditionDiagram(String operation) {
        super(operation);
        this.problemSizeChoices.add("a + b");
        this.problemSizeChoices.add("min(a,b)");

        List<String> bCases1 = Arrays.asList("a=b=0");
        List<String> bCases2 = Arrays.asList("min(a,b)=0");
        this.baseCaseChoices.add(bCases1);
        this.baseCaseChoices.add(bCases2);
        List<String> bc1 = Arrays.asList("0");
        List<String> bc2 = Arrays.asList("0");
        this.baseCaseParameters.add(bc1);
        this.baseCaseParameters.add(bc2);

        List<String> reds1 = Arrays.asList("a - 1");
        List<String> reds2 = Arrays.asList("min(a,b) - 1", "min(a-1,b-1)");
        this.reductionChoices.add(reds1);
        this.reductionChoices.add(reds2);

        List<MidOperation> s1 = new ArrayList<>();
        s1.add((val, a, b) -> val + 1);
        s1.add((val, a, b) -> val - 1);
        s1.add((val, a, b) -> val + a);
        List<MidOperation> s2 = new ArrayList<>();
        s2.add((val, a, b) -> val + 1);
        s2.add((val, a, b) -> val - 1);
        s2.add((val, a, b) -> val + Math.min(a, b));
        List<MidOperation> s3 = new ArrayList<>();
        s3.add((val, a, b) -> val + 1);
        s3.add((val, a, b) -> val - 1 + 1);
        s3.add((val, a, b) -> val + 2);
        this.solutionOperations = Arrays.asList(s1, s2, s3);

        List<String> sols1 = new ArrayList<>();
        sols1.add(" + 1");
        sols1.add(" - 1");
        sols1.add(" + a");
        List<String> sols2 = new ArrayList<>();
        sols2.add(" + 1");
        sols2.add(" - 1");
        sols2.add(" + min(a,b)");
        List<String> sols3 = new ArrayList<>();
        sols3.add(" + 1");
        sols3.add(" - 1 + 1");
        sols3.add(" + 1 + 1");
        this.solutionsChoices = Arrays.asList(sols1, sols2, sols3);
        this.parametersFormat.set("Formato: a,b");
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        algorithmsMap.put(0, new Callable<Double[]>() {
            @Override
            public Double[] call() {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.slowAdditionOption1(Integer.parseInt(problemData.get(0)), Integer.parseInt(problemData.get(1)), Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.slowAdditionOption1(Integer.parseInt(problemData.get(0))-1, Integer.parseInt(problemData.get(1)), Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                reducedOperation = Integer.parseInt(problemData.get(0)) - 1 + operation + problemData.get(1);
                currentReductionSolutions.set(0);
                return returnVal;
            }
        });
        algorithmsMap.put(1, new Callable<Double[]>() {
            @Override
            public Double[] call() {
                Double[] returnVal = new Double[2];
                int a = Integer.parseInt(problemData.get(0));
                int b = Integer.parseInt(problemData.get(1));
                returnVal[0] = Algorithms.slowAdditionOption2(Integer.parseInt(problemData.get(0)), Integer.parseInt(problemData.get(1)), Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                //muy feo pero no se me ocurre otra forma
                if(a<b){
                    returnVal[1] = Algorithms.slowAdditionOption2(a-1, b, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));

                }
                else{
                    returnVal[1] = Algorithms.slowAdditionOption2(a, b-1, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));

                }
                reducedOperation = String.valueOf(Math.min(Integer.parseInt(problemData.get(0)), Integer.parseInt(problemData.get(1)))-1);
                currentReductionSolutions.set(1);
                return returnVal;
            }
        });
        algorithmsMap.put(2, new Callable<Double[]>() {
            @Override
            public Double[] call() {
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.slowAdditionOption3(Integer.parseInt(problemData.get(0)), Integer.parseInt(problemData.get(1)), Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.slowAdditionOption3(Integer.parseInt(problemData.get(0))-1, Integer.parseInt(problemData.get(1))-1, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                reducedOperation = String.format("(%d - 1 , %d - 1)", Integer.parseInt(problemData.get(0)), Integer.parseInt(problemData.get(1)));
                currentReductionSolutions.set(2);
                return returnVal;
            }
        });

    }

    @Override
    public void processInputs() throws Exception {
        try {
            super.processInputs();
            algorithmIndex = currentProblemSize.get() + currentReduction.get();
            if (checkNotBaseCase(currentProblemSize.get()+currentBaseCase.get())) {
                throw new BaseCaseException("Cannot introduce a base case in parameters");
            }
            trueValues = (Double[]) algorithmsMap.get(algorithmIndex).call();
            originalData.set(this.rawData);
            originalSol.set(this.problemData.get(0) + " + " + this.problemData.get(1));
            partialData.get(0).set(reducedOperation);
            partialSol.get(0).set(this.problemData.get(0) + " - 1 + " + this.problemData.get(1));
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNotBaseCase(int index) {
        for (String baseCase : baseCaseParameters.get(index)) {
            if ((Objects.equals(problemData.get(1), baseCase)) || (Objects.equals(problemData.get(0), baseCase))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String calculate(int index) {
        System.out.println("Partial sol: "+trueValues[1]);
        return String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).operateWithTwoIntegerParams(
                trueValues[1],
                Integer.parseInt(problemData.get(0)),
                Integer.parseInt(problemData.get(1))));
    }
}
