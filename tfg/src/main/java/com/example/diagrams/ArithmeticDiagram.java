package com.example.diagrams;

import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArithmeticDiagram extends BaseDiagram {
    Map<String, String> paramsParsed;
    private final IMultipleDiagramActions multipleDiagramActions;
    public ArithmeticDiagram(IDiagramActions builder, IMultipleDiagramActions multipleDiagramActions, String diagramDataName) throws IOException {
        super(builder, diagramDataName);
        this.multipleDiagramActions = multipleDiagramActions;
        diagramActions.setAlgorithmMap();
        setSolutionOperations();
    }

    @Override
    public void processInputs() throws Exception {
        paramsParsed = new HashMap<>();
        params.forEach((k, v) -> paramsParsed.put(k, v.get()));
        paramsParsed.put("baseCaseValue", baseCaseParameters.get(currentProblemSize.get()).get(currentBaseCaseIndex));
        paramsParsed.put("partSol", "0");
        diagramActions.setParams(paramsParsed);
        Map<String, String> solution = calculateSolution(-1);
        multipleProblemIndexOffset = multipleDiagramActions.determineMultipleDiagramKeyOffset();
        params.put("secondDecomposition",new SimpleStringProperty(solution.getOrDefault("secondDecomposition","")));
        originalSol.set(String.format("f = %.0f", Double.parseDouble(solution.get("ogSol"))));
        originalSol2.set(String.format("f = %.0f", Double.parseDouble(solution.getOrDefault("ogSol2",""))));
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

    private void setSubData(Map<String, String> data) {
        data.forEach((k, v) -> {
            if (k.equals("reducedOperation")) {
                subParameters.add(new SimpleStringProperty(v));
            }
            if (k.equals("partSol")) {
                subSolutions.add(new SimpleStringProperty(String.format("f' = %.0f", Double.parseDouble(v))));
            }
            if (k.equals("reducedOperation2")) {
                subParameters2.add(new SimpleStringProperty(v));
            }
            if (k.equals("partSol2")) {
                subSolutions2.add(new SimpleStringProperty(String.format("f' = %.0f", Double.parseDouble(v))));
            }
        });
    }

    @Override
    public boolean checkSolutionsEqual(String calcSol, String ogSol) {
        return calcSol.equals(ogSol.replace("f = ", ""));
    }

}
