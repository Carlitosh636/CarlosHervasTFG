package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SimpleDiagramPresenter extends DiagramPresenter{
    
    @FXML
    Label subParameters = new Label();
    @FXML
    Label partialSolution = new Label();
    public SimpleDiagramPresenter(Diagram model) {
        super(model);
    }

    protected void bindModelData(){
        super.bindModelData();
        subParameters.textProperty().bind(model.partialDataPropertyByIndex(0));
        partialSolution.textProperty().bind(model.partialSolPropertyByIndex(0));
    }

}
