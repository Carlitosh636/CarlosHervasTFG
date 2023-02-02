package com.example;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class SlowAdditionDiagram extends Diagram {
    private double partSol;
    private double a;
    private double b;
    public SlowAdditionDiagram(String operation) {
        super(operation);
        this.type = DiagramType.SIMPLE;
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

        this.params.put("a",new SimpleStringProperty());
        this.params.put("b",new SimpleStringProperty());

        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<Double>) () -> partSol + 1);
        s1.add((Supplier<Double>) () -> partSol - 1);
        s1.add((Supplier<Double>) () -> partSol + a);
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<Double>) () -> partSol + 1);
        s2.add((Supplier<Double>) () -> partSol - 1);
        s2.add((Supplier<Double>) () -> partSol + Math.min(a,b));
        List<Supplier> s3 = new ArrayList<>();
        s3.add((Supplier<Double>) () -> partSol + 1);
        s3.add((Supplier<Double>) () -> partSol);
        s3.add((Supplier<Double>) () -> partSol + 2);
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
        this.correctSolutions = Arrays.asList(0,0,2);

        this.partialData.add(new SimpleStringProperty());
        this.partialSol.add(new SimpleStringProperty());
        this.parametersView = "a,b";

        algorithmsMap.put(-1, new Callable<String>() {
            @Override
            public String call() {
                return String.valueOf(Algorithms.slowAdditionOption1(
                        a,
                        Double.parseDouble(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)),
                        Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex))));
            }
        });
        algorithmsMap.put(0, new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() {
                ArrayList<String> parsedReturnVal = new ArrayList<>();
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.slowAdditionOption1(a, b, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.slowAdditionOption1(a-1, b, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                reducedOperation = a - 1 +" " +operation+" " + b;
                currentReductionSolutions.set(0);
                partialSol.get(0).set(a + " - 1 + " + b);
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
                returnVal[0] = Algorithms.slowAdditionOption2(a, b, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                //muy feo pero no se me ocurre otra forma
                if(a<b){
                    returnVal[1] = Algorithms.slowAdditionOption2(a-1, b, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));

                }
                else{
                    returnVal[1] = Algorithms.slowAdditionOption2(a, b-1, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                }
                reducedOperation = String.valueOf(Math.min(Integer.parseInt(params.get("a").get()), Integer.parseInt(params.get("b").get()))-1);
                partialSol.get(0).set(Math.min(a,b)+ " - 1 + " + Math.max(a,b));
                currentReductionSolutions.set(1);
                for(Double v : returnVal){
                    parsedReturnVal.add(String.valueOf(v));
                }
                return parsedReturnVal;
            }
        });
        algorithmsMap.put(2, new Callable<ArrayList<String>>() {
            @Override
            public ArrayList<String> call() {
                ArrayList<String> parsedReturnVal = new ArrayList<>();
                Double[] returnVal = new Double[2];
                returnVal[0] = Algorithms.slowAdditionOption3(a, b, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                returnVal[1] = Algorithms.slowAdditionOption3(a-1, b-1, Integer.parseInt(baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex)));
                reducedOperation = String.format("(%.0f - 1 , %.0f - 1)", a, b);
                partialSol.get(0).set(a+ " - 1 + " + b+ " - 1");
                currentReductionSolutions.set(2);
                for(Double v : returnVal){
                    parsedReturnVal.add(String.valueOf(v));
                }
                return parsedReturnVal;
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
            a = Integer.parseInt(params.get("a").get());
            b = Integer.parseInt(params.get("b").get());
            storedSolutions = (ArrayList<String>) algorithmsMap.get(algorithmIndex).call();
            partSol = Double.parseDouble(storedSolutions.get(1));
            originalSol.set(this.params.get("a") + " + " + this.params.get("b"));
            partialData.get(0).set(reducedOperation);

        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkNotBaseCase(int index) {
        for (String baseCase : baseCaseParameters.get(index)) {
            if ((Objects.equals(params.get(1), baseCase)) || (Objects.equals(params.get(0), baseCase))) {
                return true;
            }
        }
        return false;
    }
}
