package com.example;

public class DiagramPresenter {
    private final Diagram model;
    private final DiagramView view;
    public DiagramPresenter(Diagram model, DiagramView view) {
        this.model = model;
        this.view = view;
        attachEvents();
        view.problemSizeSelect.getItems().addAll(model.getProblemSizeChoices());
        view.solutionSelect.setVisible(false);
        view.reductionSelect.setVisible(false);
        view.baseCaseSelect.setVisible(false);
    }

    private void attachEvents() {
        view.problemSizeSelect.setOnAction(e->{
            model.setCurrentProblemSize(view.problemSizeSelect.getSelectionModel().getSelectedIndex());
            view.baseCaseSelect.setVisible(true);
            view.baseCaseSelect.getItems().clear();
            view.baseCaseSelect.getItems().addAll(model.getBaseCaseChoices().get(model.getCurrentProblemSize()));
        });
        view.baseCaseSelect.setOnAction(e->{
            model.setCurrentBaseCaseIndex(view.baseCaseSelect.getSelectionModel().getSelectedIndex());
            view.reductionSelect.setVisible(true);
            view.reductionSelect.getItems().clear();
            view.reductionSelect.getItems().addAll(model.getReductionChoices().get(model.getCurrentProblemSize()));
        });
        view.reductionSelect.setOnAction(e->{
            model.setCurrentReduction(view.reductionSelect.getSelectionModel().getSelectedIndex());
        });
        view.confirmDataButton.setOnAction(e->{
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
    }

}
