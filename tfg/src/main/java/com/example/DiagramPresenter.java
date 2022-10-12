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
    }

    private void attachEvents() {
        view.confirmDataButton.setOnAction(e->handleInput());
    }

    private void handleInput() {
        model.processInputs();
        view.inputField.setVisible(false);
        view.confirmDataButton.setVisible(false);

        view.enunciado.setVisible(false);
        view.diagramPane.setVisible(true);

        view.originalData.textProperty().set(model.getProblemData().toString());
        view.originalSolution.textProperty().set(model.getProblemData().toString());
        view.partialData.textProperty().set(model.getProblemData().toString());
        view.partialSolution.textProperty().set(model.getProblemData().toString());
        System.out.println(model.getProblemData());
    }

    
}
