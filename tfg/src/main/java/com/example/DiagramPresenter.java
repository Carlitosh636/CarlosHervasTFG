package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagramPresenter {
    private final Diagram model;
    private final DiagramView view;

    private Map<String, List<String>> mappingReductionSolution;

    public DiagramPresenter(Diagram model, DiagramView view) {
        this.model = model;
        this.view = view;
        mappingReductionSolution = new HashMap<>();
        attachEvents();
        prepareSolutions();
        view.enunciado.setVisible(true);
        view.diagramPane.setVisible(false);
        view.inputErrorLabel.setVisible(false);
        view.solutionSelectionLabel.setVisible(false);
        view.solutionSelection.setVisible(false);
    }

    private void attachEvents() {
        view.confirmDataButton.setOnAction(e->handleInput());
        view.reductionSelect.setOnAction(e->{
            //cambiar el diagrama segun reduccion
            model.setCurrentReduction(view.reductionSelect.getSelectionModel().getSelectedIndex());
            //cambiar los valores de elegir en la solucion
            List<String> listVals = mappingReductionSolution.get(view.reductionSelect.getSelectionModel().getSelectedItem());
            for(String str : listVals){
                view.solutionSelection.getItems().add(str);
            }
        });
        view.solutionSelection.setOnAction(e->{
            //TODO: comprobar que la solución seleccionada sea la correcta.
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
    private void prepareSolutions() {
        //SE DEBERÍA LEER DE ARCHIVO EXTERNO O UN REPOSITORIO, ESTO ES TEMPORAL
        List<List<String>> listas = new ArrayList<>();
        List<String> test1 = new ArrayList<>();
        test1.add("multiplicar por 'a'");
        test1.add("multiplicar por 'b'");
        test1.add("elevar a 'a'");
        List<String> test2 = new ArrayList<>();
        test2.add("falta");
        test2.add("contenido aqui");
        int index = 0;
        listas.add(test1);
        listas.add(test2);
        //esto si está bien
        for(Object str : view.reductionSelect.getItems()){
            mappingReductionSolution.put(str.toString(),listas.get(index));
            index+=1;
        }
    }
}
