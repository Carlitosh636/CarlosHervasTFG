package com.example;

import java.util.*;
import java.util.concurrent.Callable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Diagram {
    protected DiagramType type;
    protected SimpleStringProperty inputs;
    protected String rawData;
    protected List<String> problemData;
    protected SimpleStringProperty originalData;
    protected SimpleStringProperty originalSol;
    protected List<SimpleStringProperty> partialData;
    protected List<SimpleStringProperty> partialSol;
    protected SimpleStringProperty calculatedSol;
    protected SimpleIntegerProperty currentProblemSize;
    protected SimpleIntegerProperty currentBaseCase;
    protected SimpleIntegerProperty currentReduction;
    protected SimpleIntegerProperty currentReductionSolutions;
    protected String operation;
    protected String reducedOperation;
    protected List<String> problemSizeChoices;
    protected int currentBaseCaseIndex;
    protected List<List<String>> baseCaseChoices;
    protected List<List<String>> baseCaseParameters;
    protected List<List<String>> reductionChoices;
    protected List<List<Callable>> solutionOperations;
    protected List<List<String>> solutionsChoices;
    protected String recursiveCallParameters;

    public int getSelectedSolution() {
        return selectedSolution.get();
    }

    public SimpleIntegerProperty selectedSolutionProperty() {
        return selectedSolution;
    }

    public void setSelectedSolution(int selectedSolution) {
        this.selectedSolution.set(selectedSolution);
    }

    protected SimpleIntegerProperty selectedSolution;
    protected List<Integer> correctSolutions;
    protected Map<Integer, Callable> algorithmsMap = new HashMap<>();
    protected int algorithmIndex;
    protected SimpleStringProperty parametersFormat;
    protected ArrayList<String> storedSolutions = new ArrayList<>();
    protected Map<String,SimpleStringProperty> viewerValues = new HashMap<>();
    protected String parametersView;
    public SimpleStringProperty parametersFormatProperty() {
        return parametersFormat;
    }
    public List<List<String>> getReductionChoices() {
        return reductionChoices;
    }
    public SimpleStringProperty originalDataProperty() {
        return originalData;
    }
    public SimpleStringProperty originalSolProperty() {
        return originalSol;
    }
    public SimpleStringProperty calculatedSolProperty() {
        return calculatedSol;
    }
    public SimpleStringProperty partialDataPropertyByIndex(int i){return this.partialData.get(i);}
    public SimpleStringProperty partialSolPropertyByIndex(int i){return this.partialSol.get(i);}
    public List<String> getProblemSizeChoices() {
        return problemSizeChoices;
    }

    public SimpleStringProperty getInputsProperty() {
        return inputs;
    }

    public int getCurrentBaseCaseIndex() {
        return currentBaseCaseIndex;
    }

    public void setCurrentBaseCaseIndex(int currentBaseCaseIndex) {
        this.currentBaseCaseIndex = currentBaseCaseIndex;
    }
    public List<List<String>> getBaseCaseChoices() {
        return baseCaseChoices;
    }
    public int getCurrentProblemSize() {
        return currentProblemSize.get();
    }

    public SimpleIntegerProperty currentProblemSizeProperty() {
        return currentProblemSize;
    }

    public int getCurrentBaseCase() {
        return currentBaseCase.get();
    }

    public SimpleIntegerProperty currentBaseCaseProperty() {
        return currentBaseCase;
    }

    public int getCurrentReduction() {
        return currentReduction.get();
    }

    public SimpleIntegerProperty currentReductionProperty() {
        return currentReduction;
    }

    public int getCurrentReductionSolutions() {
        return currentReductionSolutions.get();
    }

    public SimpleIntegerProperty currentReductionSolutionsProperty() {
        return currentReductionSolutions;
    }
    public List<Integer> getCorrectSolutions() {
        return correctSolutions;
    }

    public Map<String, SimpleStringProperty> getViewerValues() {
        return viewerValues;
    }

    public void setViewerValues(Map<String, SimpleStringProperty> viewerValues) {
        this.viewerValues = viewerValues;
    }

    public Diagram(String operation) {
        this.operation=operation;
        this.inputs = new SimpleStringProperty();
        this.rawData = "";
        this.problemData=new ArrayList<>();
        this.reductionChoices=new ArrayList<>();
        this.problemSizeChoices=new ArrayList<>();
        this.baseCaseChoices=new ArrayList<>();
        this.baseCaseParameters=new ArrayList<>();
        this.originalData=new SimpleStringProperty();
        this.originalSol=new SimpleStringProperty();
        this.parametersFormat = new SimpleStringProperty();
        this.calculatedSol = new SimpleStringProperty();
        this.currentProblemSize = new SimpleIntegerProperty();
        this.currentBaseCase = new SimpleIntegerProperty();
        this.currentReductionSolutions = new SimpleIntegerProperty();
        this.currentReduction = new SimpleIntegerProperty();
        this.selectedSolution = new SimpleIntegerProperty();
        this.partialData=new ArrayList<>();
        this.partialSol=new ArrayList<>();
        this.correctSolutions = new ArrayList<>();
        this.viewerValues.put("baseCase",new SimpleStringProperty());
        this.viewerValues.put("recursiveCase",new SimpleStringProperty());
    }
    public void processInputs() throws Exception{
        try{
            problemData = Arrays.asList(this.inputs.get().split(","));
            rawData =problemData.get(0)+operation+problemData.get(1);
        }
        catch (Exception e){
            System.out.println(e);
            throw new Exception();
        }
    }
    public abstract boolean checkNotBaseCase(int index);

    public String calculate(int index) throws Exception{
        //aplicamos la expresión de la solución escogida a la solución parcial
        String calcSol;
        if(this.type == DiagramType.COMPLEX){
            calcSol = Arrays.toString((int[]) solutionOperations.get(currentReductionSolutions.get()).get(index).call());
        }
        else{
            calcSol = String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).call());
        }
        calculatedSol.set(calcSol);
        viewerValues.get("recursiveCase").set(String.format(
                "return %s(%s) %s",
                this.getClass().getSimpleName(),
                this.recursiveCallParameters,
                this.solutionsChoices.get(this.currentReductionSolutions.get()).get(this.getSelectedSolution())
        ));
        return calcSol;
    };

    public boolean checkSolutionsEqual(String ourSol){
        return ourSol.equals(storedSolutions.get(0));
    }
    protected void setViewerData1(){
        this.viewerValues.put("diagramTitle",new SimpleStringProperty(String.format("def %s(%s)",
                DiagramToCodeMapper.mapDiagramName(),
                DiagramToCodeMapper.mapParameters()
        )));
    }
}
