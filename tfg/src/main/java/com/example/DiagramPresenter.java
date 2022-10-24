package com.example;

public class DiagramPresenter {
    private final Diagram model;
    private final DiagramView view;

    public DiagramPresenter(Diagram model, DiagramView view) {
        this.model = model;
        this.view = view;
        attachEvents();
        view.enunciado.setVisible(true);
        view.diagramPane.setVisible(false);
        view.inputErrorLabel.setVisible(false);
        view.solutionSelectionLabel.setVisible(false);
        view.solutionSelection.setVisible(false);
    }

    private void attachEvents() {
        view.confirmDataButton.setOnAction(e->handleInput());
        view.reductionSelect.setOnAction(e->{
            model.setCurrentReduction(view.reductionSelect.getSelectionModel().getSelectedIndex());
        });
        view.solutionSelection.setOnAction(e->{
            System.out.println(view.solutionSelection.getSelectionModel().getSelectedItem());
        });
    }

    private void handleInput() {
        try {
            model.processInputs();
            view.diagramPane.setVisible(true);
            view.lowerBox.setVisible(false);
            view.solutionSelectionLabel.setVisible(true);
            view.solutionSelection.setVisible(true);
            view.datasArrow.textProperty().set(" |\n |\n "+model.getCurrentReductionString()+"\n |\n |");
            view.solutionsArrow.textProperty().set(" |\n |\n ??\n |\n |");
            //view.originalData.textProperty().set(model.getProblemData().toString());
            //view.originalSolution.textProperty().set(model.getProblemData().toString());
            //view.partialData.textProperty().set(model.getProblemData().toString());
            //view.partialSolution.textProperty().set(model.getProblemData().toString());

        } catch (Exception e) {
            view.inputErrorLabel.setVisible(true);
        }

        System.out.println(model.getRawData());
    }
    
}
