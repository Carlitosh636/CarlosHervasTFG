package com.example.controller;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DiagramVisualizerData {
    StackPane originalDataSolutionArrow;
    StackPane partialDataSolutionArrow;
    StackPane datasArrow;
    StackPane solutionsArrow;
    Label originalSolution;
    Label calculatedSolution;
    VBox originalData;
    VBox subParameters;
    VBox subSolutions;

    public DiagramVisualizerData() {
    }

    public StackPane getOriginalDataSolutionArrow() {
        return originalDataSolutionArrow;
    }

    public void setOriginalDataSolutionArrow(StackPane originalDataSolutionArrow) {
        this.originalDataSolutionArrow = originalDataSolutionArrow;
    }

    public StackPane getPartialDataSolutionArrow() {
        return partialDataSolutionArrow;
    }

    public void setPartialDataSolutionArrow(StackPane partialDataSolutionArrow) {
        this.partialDataSolutionArrow = partialDataSolutionArrow;
    }

    public StackPane getDatasArrow() {
        return datasArrow;
    }

    public void setDatasArrow(StackPane datasArrow) {
        this.datasArrow = datasArrow;
    }

    public StackPane getSolutionsArrow() {
        return solutionsArrow;
    }

    public void setSolutionsArrow(StackPane solutionsArrow) {
        this.solutionsArrow = solutionsArrow;
    }

    public Label getOriginalSolution() {
        return originalSolution;
    }

    public void setOriginalSolution(Label originalSolution) {
        this.originalSolution = originalSolution;
    }

    public Label getCalculatedSolution() {
        return calculatedSolution;
    }

    public void setCalculatedSolution(Label calculatedSolution) {
        this.calculatedSolution = calculatedSolution;
    }


    public VBox getOriginalData() {
        return originalData;
    }

    public void setOriginalData(VBox originalData) {
        this.originalData = originalData;
    }

    public VBox getSubParameters() {
        return subParameters;
    }

    public void setSubParameters(VBox subParameters) {
        this.subParameters = subParameters;
    }

    public VBox getSubSolutions() {
        return subSolutions;
    }

    public void setSubSolutions(VBox subSolutions) {
        this.subSolutions = subSolutions;
    }

    public void addValueToOriginalData(HBox box) {
        this.originalData.getChildren().add(box);
    }
}
