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
    }

    private void attachEvents() {
        view.confirmDataButton.setOnAction(e->handleInput());
    }

    private void handleInput() {
        try {
            model.processInputs();
            view.inputErrorLabel.setVisible(false);
            view.inputField.setVisible(false);
            view.confirmDataButton.setVisible(false);

            view.enunciado.setVisible(false);
            view.diagramPane.setVisible(true);

            view.originalData.textProperty().set(model.getProblemData().toString());
            view.originalSolution.textProperty().set(model.getProblemData().toString());
            view.partialData.textProperty().set(model.getProblemData().toString());
            view.partialSolution.textProperty().set(model.getProblemData().toString());
        } catch (Exception e) {
            view.inputErrorLabel.setVisible(true);
        }

        System.out.println(model.getProblemData());
    }
    
}
