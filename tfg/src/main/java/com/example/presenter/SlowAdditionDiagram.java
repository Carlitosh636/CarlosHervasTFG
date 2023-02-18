package com.example.presenter;

import com.example.algorithms.Algorithms;
import com.example.presenter.Diagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class SlowAdditionDiagram extends Diagram {
    private double partialSol;
    private double a;
    private double b;
    public SlowAdditionDiagram(String diagramDataName) throws IOException {
        super(diagramDataName);
        partialSol = Double.parseDouble(partSol);
        a = Double.parseDouble(params.get("a").get());
        b = Double.parseDouble(params.get("b").get());

        List<Supplier> s1 = new ArrayList<>();
        s1.add((Supplier<Double>) () -> partialSol + 1);
        s1.add((Supplier<Double>) () -> partialSol - 1);
        s1.add((Supplier<Double>) () -> partialSol + a);
        List<Supplier> s2 = new ArrayList<>();
        s2.add((Supplier<Double>) () -> partialSol + 1);
        s2.add((Supplier<Double>) () -> partialSol - 1);
        s2.add((Supplier<Double>) () -> partialSol + Math.min(a,b));
        List<Supplier> s3 = new ArrayList<>();
        s3.add((Supplier<Double>) () -> partialSol + 1);
        s3.add((Supplier<Double>) () -> partialSol);
        s3.add((Supplier<Double>) () -> partialSol + 2);
        this.solutionOperations = Arrays.asList(s1, s2, s3);

        //this.parametersView = "a,b";

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
                subSolutions.get(0).set(a + " - 1 + " + b);
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
                subSolutions.get(0).set(Math.min(a,b)+ " - 1 + " + Math.max(a,b));
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
                subSolutions.get(0).set(a+ " - 1 + " + b+ " - 1");
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
            a = Integer.parseInt(params.get("a").get());
            b = Integer.parseInt(params.get("b").get());
            storedSolutions = (ArrayList<String>) algorithmsMap.get(algorithmIndex).call();
            partialSol = Double.parseDouble(storedSolutions.get(1));
            originalSol.set(this.params.get("a").get() + " + " + this.params.get("b").get());
            subParameters.get(0).set(reducedOperation);

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
