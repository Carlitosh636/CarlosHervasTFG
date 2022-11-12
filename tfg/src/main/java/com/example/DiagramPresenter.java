package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagramPresenter {
    private final Diagram model;
    private final DiagramView view;
    private Map<Integer, List<String>> mappingReductionSolution;

    public DiagramPresenter(Diagram model, DiagramView view) {
        this.model = model;
        this.view = view;
        mappingReductionSolution = new HashMap<>();
        attachEvents();
        view.reductionSelect.getItems().addAll(model.getReductionChoices());
        view.solutionSelect.setVisible(false);
    }

    private void attachEvents() {
        view.reductionSelect.setOnAction(e->{
            //cambiar el diagrama segun reduccion
            model.setCurrentReduction(view.reductionSelect.getSelectionModel().getSelectedIndex());
            handleInput();
        });
        view.solutionSelect.setOnAction(e->{
            if(view.solutionSelect.getSelectionModel().getSelectedIndex() == model.getCorrectChoices().get(model.getCurrentReductionSolutions())){
                System.out.println("Correcto!");
            }
            else{
                System.out.println("Incorrecto! Vuelve a intentarlo");
            }
        });
    }

    private void handleInput() {
        try {
            model.processInputs();
            view.solutionSelect.setVisible(true);
            view.datasArrow.textProperty().set(" |\n |\n "+model.getCurrentReductionString()+"\n |\n |");
            view.solutionsArrow.textProperty().set(" |\n |\n ??\n |\n |");
            //cambiar los valores de elegir en la solucion
            view.solutionSelect.getItems().clear();
            view.solutionSelect.getItems().addAll(model.solutionsChoices.get(model.getCurrentReductionSolutions()));

        } catch (Exception e) {
            view.showErrorInputAlert();
        }

        System.out.println(model.getRawData());
    }

}
