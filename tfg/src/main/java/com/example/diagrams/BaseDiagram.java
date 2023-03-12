package com.example.diagrams;

import com.example.enums.DiagramType;
import com.example.model.DiagramData;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class BaseDiagram {
    protected IDiagramActions diagramActions;
    protected DiagramType type;
    protected SimpleStringProperty heading;
    protected Map<String,SimpleStringProperty> params;
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
    protected SimpleIntegerProperty selectedSolution;
    protected List<Integer> correctSolutions;
    protected int algorithmIndex;
    protected Map<String,SimpleStringProperty> viewerValues = new HashMap<>();
    protected String parametersView;
    protected BaseDiagram(IDiagramActions builder, String diagramDataName) throws IOException {
        this.diagramActions =builder;
        DiagramData diagramData;
        ObjectMapper objMapper = new ObjectMapper();
        diagramData =objMapper.readValue(new File(diagramDataName),DiagramData.class);
        this.operation=diagramData.operation;
        this.params =new HashMap<>();
        this.heading = new SimpleStringProperty(diagramData.heading);
        this.type = DiagramType.valueOf(diagramData.type);
        diagramData.params.forEach((k,v)->{
            params.put(k,new SimpleStringProperty(v));
        });
        this.reductionChoices=diagramData.reductionChoices;
        this.problemSizeChoices=diagramData.problemSizeChoices;
        this.baseCaseChoices=diagramData.baseCaseChoices;
        this.baseCaseParameters=diagramData.baseCaseParameters;
        this.originalSol=new SimpleStringProperty(diagramData.originalSol);
        this.calculatedSol = new SimpleStringProperty(diagramData.calculatedSol);
        this.currentProblemSize = new SimpleIntegerProperty(diagramData.currentProblemSize);
        this.currentBaseCase = new SimpleIntegerProperty(diagramData.currentBaseCase);
        this.currentReductionSolutions = new SimpleIntegerProperty(diagramData.currentReductionSolutions);
        this.currentReduction = new SimpleIntegerProperty(diagramData.currentReduction);
        this.selectedSolution = new SimpleIntegerProperty();
        this.subParameters =new ArrayList<>();
        diagramData.subParameters.forEach(ele->{
            this.subParameters.add(new SimpleStringProperty(ele));
        });
        this.subSolutions =new ArrayList<>();
        diagramData.subSolutions.forEach(ele->{
            this.subSolutions.add(new SimpleStringProperty(ele));
        });
        this.solutionsChoices = new ArrayList<>();
        this.solutionsChoices.addAll(diagramData.solutionsChoices);
        this.correctSolutions = diagramData.correctSolutions;
    }
    protected abstract void setSolutionOperations();
    public abstract void processInputs() throws Exception;
    public abstract void processSolutions() throws Exception;
    public abstract boolean checkNotBaseCase(int index,List<String>input) throws Exception;
    public abstract Map<String,String> calculateSolution(int selectedIndex) throws Exception;
    public abstract boolean checkSolutionsEqual(String calcSol);
    public abstract String calculateWithSelectedOperation(int index);

    //GETTER SETTERS
    public IDiagramActions getDiagramActions() {
        return diagramActions;
    }

    public DiagramType getType() {
        return type;
    }

    public String getHeading() {
        return heading.get();
    }

    public SimpleStringProperty headingProperty() {
        return heading;
    }

    public Map<String, SimpleStringProperty> getParams() {
        return params;
    }

    public String getOriginalSol() {
        return originalSol.get();
    }

    public SimpleStringProperty originalSolProperty() {
        return originalSol;
    }

    public List<SimpleStringProperty> getSubParameters() {
        return subParameters;
    }

    public List<SimpleStringProperty> getSubSolutions() {
        return subSolutions;
    }

    public String getCalculatedSol() {
        return calculatedSol.get();
    }

    public SimpleStringProperty calculatedSolProperty() {
        return calculatedSol;
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

    public String getOperation() {
        return operation;
    }

    public String getReducedOperation() {
        return reducedOperation;
    }

    public List<String> getProblemSizeChoices() {
        return problemSizeChoices;
    }

    public int getCurrentBaseCaseIndex() {
        return currentBaseCaseIndex;
    }

    public List<List<String>> getBaseCaseChoices() {
        return baseCaseChoices;
    }

    public List<List<String>> getBaseCaseParameters() {
        return baseCaseParameters;
    }

    public List<List<String>> getReductionChoices() {
        return reductionChoices;
    }

    public List<List<Supplier>> getSolutionOperations() {
        return solutionOperations;
    }

    public List<List<String>> getSolutionsChoices() {
        return solutionsChoices;
    }

    public String getRecursiveCallParameters() {
        return recursiveCallParameters;
    }

    public int getSelectedSolution() {
        return selectedSolution.get();
    }

    public SimpleIntegerProperty selectedSolutionProperty() {
        return selectedSolution;
    }

    public List<Integer> getCorrectSolutions() {
        return correctSolutions;
    }

    public int getAlgorithmIndex() {
        return algorithmIndex;
    }


    public Map<String, SimpleStringProperty> getViewerValues() {
        return viewerValues;
    }

    public String getParametersView() {
        return parametersView;
    }
    public void setCurrentBaseCaseIndex(int currentBaseCaseIndex) {
        this.currentBaseCaseIndex = currentBaseCaseIndex;
    }
}
