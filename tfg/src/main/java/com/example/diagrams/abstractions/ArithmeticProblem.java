package com.example.diagrams.abstractions;

import com.example.diagrams.implementations.IDiagramActions;
import com.example.diagrams.implementations.IMultipleDiagramActions;
import com.example.exceptions.IncorrectInputException;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArithmeticProblem extends BaseProblem {
    Map<String, String> paramsParsed;
    String ogSol2;
    private final IMultipleDiagramActions multipleDiagramActions;
    public ArithmeticProblem(IDiagramActions builder, IMultipleDiagramActions multipleDiagramActions, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        this.multipleDiagramActions = multipleDiagramActions;
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
    }

    @Override
    public void processInputs() throws Exception {
        if (diagramActions.checkNotIncorrectInput(params.entrySet().stream().filter(k->paramKeys.contains(k.getKey())).map(k->k.getValue().get()).toList())){
            throw new IncorrectInputException("Los valores introducidos no son válidos");
        }
        paramsParsed = new HashMap<>();
        params.forEach((k, v) -> paramsParsed.put(k, v.get()));
        paramsParsed.put("baseCaseValue", baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        paramsParsed.put("partSol", "0");
        diagramActions.setParams(paramsParsed);
        Map<String, String> solution = calculateSolution(-1);
        if(multipleDiagramActions != null){
            multipleProblemIndexOffset = multipleDiagramActions.determineMultipleDiagramKeyOffset();
        }
        params.put("secondDecomposition",new SimpleStringProperty(solution.getOrDefault("secondDecomposition","")));
        originalSol.set(String.format("%s = %.0f",solution.get("preSolOg"),Double.parseDouble(solution.get("ogSol"))));
        originalSol2.set(String.format("%s = %.0f",solution.getOrDefault("preSolOg2",""),Double.parseDouble(solution.getOrDefault("ogSol2","0"))));
        ogSol2 = solution.get("ogSol2");
    }

    @Override
    public Map<String, String> processProblemSizeAndBaseCases() {
        return diagramActions.setGenCodeParams(baseCaseChoices.get(currentProblemSize.get()).get(currentBaseCaseIndex), returnValues.get(currentProblemSize.get()).get(currentBaseCaseIndex));
    }

    @Override
    public void processSolutions() throws Exception {
        algorithmIndex = currentProblemSize.get() * 2 + currentReduction.get();
        Map<String, String> solutions = calculateSolution(algorithmIndex);
        setSubData(solutions);
        currentReductionSolutions.set(Integer.parseInt(solutions.get("currentReductionSolutions")));
        currentReductionSolutions2.set(Integer.parseInt(solutions.getOrDefault("currentReductionSolutions2","0")));
        Map<String, String> paramsParsed = new HashMap<>();
        params.forEach((k, v) -> paramsParsed.put(k, v.get()));
        paramsParsed.put("baseCaseValue", baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        paramsParsed.put("partSol", solutions.get("partSol"));
        paramsParsed.put("partSol2", solutions.getOrDefault("partSol2",""));
        diagramActions.setParams(paramsParsed);
    }

    private void setSolutionOperations() {
        this.solutionOperations = diagramActions.setSolutionOperations();
    }

    @Override
    public boolean checkNotBaseCase(int index, List<String> input) throws Exception {
        return diagramActions.checkNotBaseCase(baseCaseParameters.get(index), input);
    }

    private Map<String, String> calculateSolution(int selectedIndex) throws Exception {
        return diagramActions.calculateSolution(selectedIndex);
    }

    @Override
    public String calculateWithSelectedOperation(int index, int currentSolutions,int offset) {
        calculatedSol.set(String.format(
                "%.0f",
                (double) solutionOperations.get(currentSolutions+offset).get(index).get())
        );
        return calculatedSol.get();
    }

    @Override
    public List<String> setVisualizerParams(){
        return multipleDiagramActions.setVisualizerParams().values().stream().toList();
    }

    @Override
    public boolean isMainDiagram() {
        if( multipleDiagramActions != null){
            return multipleDiagramActions.determineMultipleDiagramKeyOffset() == 1;
        }
        return false;
    }

    private void setSubData(Map<String, String> data) {
        data.forEach((k, v) -> {
            if (k.equals("reducedOperation")) {
                subParameters.add(new SimpleStringProperty(v));
            }
            if (k.equals("partSol")) {
                subSolutions.add(new SimpleStringProperty(String.format("%s = %.0f", data.get("prePartialSol"),Double.parseDouble(v))));
            }
            if (k.equals("reducedOperation2")) {
                subParameters2.add(new SimpleStringProperty(v));
            }
            if (k.equals("partSol2")) {
                subSolutions2.add(new SimpleStringProperty(String.format("%s = %.0f", data.get("prePartialSol2"),Double.parseDouble(v))));
            }
        });
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, boolean isOther) throws Exception {
        String ogSol = diagramActions.calculateSolution(-1).get("ogSol");
        if (isOther){
            ogSol = ogSol2;
        }
        if (Double.parseDouble(ogSol) % 1 == 0){
            ogSol = String.format("%.0f",Double.parseDouble(ogSol));
        }
        return calcSol.equals(ogSol);
    }

}
