package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class RecursivePotencyDiagram extends Diagram{
    private double partSol;
    private double a = 1;
    private double b = 1;
    public RecursivePotencyDiagram(String operation,String diagramDataName) throws IOException {
        super(operation,diagramDataName);

        List<Supplier> sols1 = new ArrayList<>();
        sols1.add((Supplier<Double>) () -> partSol*b);
        sols1.add((Supplier<Double>) () -> partSol*a);
        sols1.add((Supplier<Double>) () -> Math.pow(partSol,a));
        List<Supplier> sols2 = new ArrayList<>();
        sols2.add((Supplier<Double>) () -> Math.pow(partSol,2));
        sols2.add((Supplier<Double>) () -> partSol*b);
        List<Supplier> sols3 = new ArrayList<>();
        sols3.add((Supplier<Double>) () -> Math.pow(partSol,2));
        sols3.add((Supplier<Double>) () -> partSol*b);
        sols3.add((Supplier<Double>) () -> a * (Math.pow(partSol,2)));
        this.solutionOperations = Arrays.asList(sols1, sols2, sols3);

        //this.parametersView = "a,b";

        this.subParameters.add(new SimpleStringProperty());
        this.subSolutions.add(new SimpleStringProperty());

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

                if(Integer.parseInt(params.get("b").get())%2==0){
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
            a = Integer.parseInt(params.get("a").get());
            b = Integer.parseInt(params.get("b").get());
            storedSolutions = (ArrayList<String>) algorithmsMap.get(algorithmIndex).call();

            partSol = Double.parseDouble(storedSolutions.get(1));
            originalSol.set(storedSolutions.get(0));
            subParameters.get(0).set(reducedOperation);
            subSolutions.get(0).set(storedSolutions.get(1));
        }
        catch (RuntimeException e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNotBaseCase(int index){
        for (String baseCase : baseCaseParameters.get(index)) {
            if(Objects.equals(params.get(1), baseCase)){
                return true;
            }
        }
        return false;
    }
}
