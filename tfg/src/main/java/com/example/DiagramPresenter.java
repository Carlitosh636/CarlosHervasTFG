package com.example;

public class DiagramPresenter {
    private final Diagram model;
    private final DiagramView view;

    public DiagramPresenter(Diagram model, DiagramView view) {
        this.model = model;
        this.view = view;
        attachEvents();
    }

    private void attachEvents() {
        view.confirmDataButton.setOnAction(e->handleInput());
    }

    private void handleInput() {
        System.out.println(model.geInputs());
    }

    
}
