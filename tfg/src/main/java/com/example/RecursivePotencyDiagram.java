package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class RecursivePotencyDiagram extends Diagram{
    private double partSol;
    private double a = 1;
    private double b = 1;
    public RecursivePotencyDiagram(String operation) {
        super(operation);
        this.type = DiagramType.SIMPLE;
        this.problemSizeChoices.add("b");

        List<String> bCases1 = Arrays.asList("b == 0");
        this.baseCaseChoices.add(bCases1);
        List<String> bc1 = Arrays.asList("0");
        this.baseCaseParameters.add(bc1);

        List<String> reds1 = Arrays.asList("b - 1","b / 2");
        this.reductionChoices.add(reds1);

        List<Callable> sols1 = new ArrayList<>();
        sols1.add((Callable<Double>) () -> partSol*b);
        sols1.add((Callable<Double>) () -> partSol*a);
        sols1.add((Callable<Double>) () -> Math.pow(partSol,a));
        List<Callable> sols2 = new ArrayList<>();
        sols2.add((Callable<Double>) () -> Math.pow(partSol,2));
        sols2.add((Callable<Double>) () -> partSol*b);
        List<Callable> sols3 = new ArrayList<>();
        sols3.add((Callable<Double>) () -> Math.pow(partSol,2));
        sols3.add((Callable<Double>) () -> partSol*b);
        sols3.add((Callable<Double>) () -> a * (Math.pow(partSol,2)));
        this.solutionOperations = Arrays.asList(sols1, sols2, sols3);

        List<String> solsChoices1 = new ArrayList<>();
        solsChoices1.add("* b");
        solsChoices1.add("* a");
        solsChoices1.add("** a");
        List<String> solsChoices2 = new ArrayList<>();
        solsChoices2.add("** 2");
        solsChoices2.add("* b");
        List<String> solsChoices3 = new ArrayList<>();
        solsChoices3.add("** 2");
        solsChoices3.add("* b");
        solsChoices3.add("a * ((a^(b-1/2)))²))");
        this.solutionsChoices=Arrays.asList(solsChoices1,solsChoices2,solsChoices3);
        this.correctSolutions = Arrays.asList(1,0,2);

        this.parametersView = "a,b";
        this.parametersFormat.set("Formato: a,b");
        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());

        algorithmsMap.put(-1, new Callable<String>() {
            @Override
            public String call() {
                return String.valueOf(Algorithms.recursiveExponentOption1(
                        a,
                        Double.parseDouble(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)),
                        Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex))));
            }
        });
        algorithmsMap.put(0, new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String>call() {
                ArrayList<String> parsedReturnVal = new ArrayList<>();
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption1(a,b,Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.recursiveExponentOption1(a,b-1,Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                reducedOperation=a+operation+(b-1);
                recursiveCallParameters = "a,"+reductionChoices.get(currentProblemSize.get()).get(currentReduction.get());
                currentReductionSolutions.set(0);
                for(Double v : returnVal){
                    parsedReturnVal.add(String.valueOf(v));
                }
                return parsedReturnVal;

            }
        });
        algorithmsMap.put(1, new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() {
                ArrayList<String> parsedReturnVal = new ArrayList<>();
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.recursiveExponentOption2(a,b,Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.recursiveExponentOption2(a,Math.floor(b/2),Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));

                if(Integer.parseInt(problemData.get(1))%2==0){
                    reducedOperation=a+operation+(b/2);
                    recursiveCallParameters = "a,"+reductionChoices.get(currentProblemSize.get()).get(currentReduction.get());
                    currentReductionSolutions.set(1);
                }
                else{
                    reducedOperation=a+operation+((b-1)/2);
                    recursiveCallParameters = "a,(b-1)/2";
                    currentReductionSolutions.set(2);
                }
                for(Double v : returnVal){
                    parsedReturnVal.add(String.valueOf(v));
                }
                return parsedReturnVal;
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
            a = Integer.parseInt(problemData.get(0));
            b = Integer.parseInt(problemData.get(1));
            storedSolutions = (ArrayList<String>) algorithmsMap.get(algorithmIndex).call();

            partSol = Double.parseDouble(storedSolutions.get(1));
            originalData.set(this.rawData);
            originalSol.set(storedSolutions.get(0));
            partialData.get(0).set(reducedOperation);
            partialSol.get(0).set(storedSolutions.get(1));
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
}
