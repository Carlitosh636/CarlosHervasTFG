package com.example.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PresenterButtonHandler extends ButtonHandler {
    public void returnToMenu(Alert alert, String title, String content, GridPane diagramGrid) throws IOException {
        ButtonType action =  super.setAlertData(alert,title,content).get();
        if(action.equals(ButtonType.OK)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
            Pane pane = loader.load();
            diagramGrid.getScene().setRoot(pane);
        }
        else{
            alert.close();
        }
    }
    public void resetDiagram(Alert alert,String title, String content){
        //TODO: IMPLEMENT
    }
}
