package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ComplexDiagramPresenter extends DiagramPresenter {
    @FXML
    public Label subParameters1 = new Label();;
    @FXML
    public Label subParameters2 = new Label();;
    @FXML
    public Label partialSolution1 = new Label();;
    @FXML
    public Label partialSolution2 = new Label();;


    public ComplexDiagramPresenter(Diagram model) {
        super(model);
    }

    protected void bindModelData(){
        super.bindModelData();
        subParameters1.textProperty().bind(model.partialDataPropertyByIndex(0));
        partialSolution1.textProperty().bind(model.partialSolPropertyByIndex(0));
        subParameters2.textProperty().bind(model.partialDataPropertyByIndex(1));
        partialSolution2.textProperty().bind(model.partialSolPropertyByIndex(1));
    }
}
