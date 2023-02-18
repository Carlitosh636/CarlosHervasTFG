package com.example.diagrams;

import com.example.enums.DiagramType;
import com.example.exceptions.BaseCaseException;
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
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public abstract class BaseDiagram {
    protected IDiagramActions diagramActions;
    protected DiagramType type;
    protected SimpleStringProperty heading;
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
        this.originalData=new SimpleStringProperty(diagramData.originalData);
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
        diagramData.solutionsChoices.forEach(ele->{
            this.solutionsChoices.add(ele);
        });
        this.correctSolutions = diagramData.correctSolutions;
    }
    protected abstract void processInputs(Map<String,SimpleStringProperty> params) throws BaseCaseException;
    protected abstract boolean checkNotBaseCase(int index);
    protected abstract String calculateSolution();
    protected abstract boolean checkSolutionsEqual();
}
