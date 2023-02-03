package com.example;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Diagram {
    protected DiagramType type;
    protected SimpleStringProperty inputs;
    protected Map<String,SimpleStringProperty> params;
    protected SimpleStringProperty originalData;
    protected SimpleStringProperty originalSol;
    protected List<SimpleStringProperty> subParameters;
    protected List<SimpleStringProperty> subSolutions;
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
    protected List<List<Supplier>> solutionOperations;
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

    public Map<String,SimpleStringProperty> getParams() {
        return params;
    }

    protected SimpleIntegerProperty selectedSolution;
    protected List<Integer> correctSolutions;
    protected Map<Integer, Callable> algorithmsMap = new HashMap<>();
    protected int algorithmIndex;
    protected ArrayList<String> storedSolutions = new ArrayList<>();
    protected Map<String,SimpleStringProperty> viewerValues = new HashMap<>();
    protected String parametersView;
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

    public List<SimpleStringProperty> getSubParameters() {
        return subParameters;
    }

    public List<SimpleStringProperty> getSubSolutions() {
        return subSolutions;
    }
    public Diagram(String operation) {
        this.operation=operation;
        this.inputs = new SimpleStringProperty();
        this.params =new HashMap<>();
        this.reductionChoices=new ArrayList<>();
        this.problemSizeChoices=new ArrayList<>();
        this.baseCaseChoices=new ArrayList<>();
        this.baseCaseParameters=new ArrayList<>();
        this.originalData=new SimpleStringProperty();
        this.originalSol=new SimpleStringProperty();
        this.calculatedSol = new SimpleStringProperty();
        this.currentProblemSize = new SimpleIntegerProperty();
        this.currentBaseCase = new SimpleIntegerProperty();
        this.currentReductionSolutions = new SimpleIntegerProperty();
        this.currentReduction = new SimpleIntegerProperty();
        this.selectedSolution = new SimpleIntegerProperty();
        this.subParameters =new ArrayList<>();
        this.subSolutions =new ArrayList<>();
        this.correctSolutions = new ArrayList<>();
        this.viewerValues.put("baseCase",new SimpleStringProperty());
        this.viewerValues.put("recursiveCase",new SimpleStringProperty());
    }
    public void processInputs() throws Exception{
        try{
            /*params = Arrays.asList(this.inputs.get().split(","));
            rawData = params.get(0)+operation+ params.get(1);*/
            String formattedValues = "";
            for(SimpleStringProperty simpleStringProperty : params.values()){
                formattedValues+=simpleStringProperty.get()+", ";
            }
            originalData.set(formattedValues);
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
            calcSol = Arrays.toString((int[]) solutionOperations.get(currentReductionSolutions.get()).get(index).get());
        }
        else{
            calcSol = String.valueOf(solutionOperations.get(currentReductionSolutions.get()).get(index).get());
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
