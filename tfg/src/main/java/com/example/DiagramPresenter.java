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
        prepareSolutions();
        view.solutionSelection.setVisible(false);
    }

    private void attachEvents() {
        view.reductionSelect.setOnAction(e->{
            //cambiar el diagrama segun reduccion
            model.setCurrentReduction(view.reductionSelect.getSelectionModel().getSelectedIndex());
            handleInput();
        });
        view.solutionSelection.setOnAction(e->{
            if(view.solutionSelection.getSelectionModel().getSelectedIndex() == model.getCorrectChoices().get(model.getCurrentReductionSolutions())){
                //view.solutionStatus.textProperty().set("Correcto!");
                System.out.println("Correcto!");
            }
            else{
                //view.solutionStatus.textProperty().set("Incorrecto! Vuelve a intentarlo.");
                System.out.println("Incorrecto! Vuelve a intentarlo");
            }
        });
    }

    private void handleInput() {
        try {
            model.processInputs();
            view.solutionSelection.setVisible(true);
            view.datasArrow.textProperty().set(" |\n |\n "+model.getCurrentReductionString()+"\n |\n |");
            view.solutionsArrow.textProperty().set(" |\n |\n ??\n |\n |");
            //cambiar los valores de elegir en la solucion
            List<String> listVals = mappingReductionSolution.get(model.getCurrentReductionSolutions());
            for(String str : listVals){
                view.solutionSelection.getItems().add(str);
            }
            //view.originalData.textProperty().set(model.getProblemData().toString());
            //view.originalSolution.textProperty().set(model.getProblemData().toString());
            //view.partialData.textProperty().set(model.getProblemData().toString());
            //view.partialSolution.textProperty().set(model.getProblemData().toString());

        } catch (Exception e) {
            //view.inputErrorLabel.setVisible(true);
            System.out.println("Error en el input");
        }

        System.out.println(model.getRawData());
    }
    //para el tema de los problemas que divergen según su solución, lo que he hecho temporalmente es que haya varios sets de soluciones, cada uno ligado a un integer
    //de la clase diagrama. se selecciona el set de soluciones posibles (y la correcta) dependiendo de si el diagrama será uno u otro.
    //en este ejemplo hay 2 reducciones pero 3 posibles casos (la segunda se divide en caso par y caso impar) asi que según el expediente
    //tiene que dar un set de soluciones u otro, y modificar el diagrama (lo cual se hace en el callable)
    private void prepareSolutions() {
        //SE DEBERÍA LEER DE ARCHIVO EXTERNO O UN REPOSITORIO, ESTO ES TEMPORAL
        List<List<String>> listas = new ArrayList<>();
        List<String> test1 = new ArrayList<>();
        test1.add("multiplicar por 'b'");
        test1.add("multiplicar por 'a'");
        test1.add("elevar a 'a'");
        List<String> test2 = new ArrayList<>();
        test2.add("elevar al cuadrado");
        test2.add("multiplicar por 'b'");
        List<String> test3 = new ArrayList<>();
        test3.add("elevar al cuadrado");
        test3.add("multiplicar por 'b'");
        test3.add("las 2 anteriores juntas");
        listas.add(test1);
        listas.add(test2);
        listas.add(test3);
        for(int index = 0;index<listas.size();index++){
            mappingReductionSolution.put(index,listas.get(index));

        }
    }
}
