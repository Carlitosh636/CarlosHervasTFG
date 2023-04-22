package com.example.presenter;

import com.example.exceptions.AlertTypeIndexOutOfBounds;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;

public class PresenterButtonHandler extends ButtonHandler {

    public void returnToMenu(int alertTypeIndex, String title, String content, GridPane diagramGrid) throws IOException, AlertTypeIndexOutOfBounds {
        ButtonType action =  super.setAlertData(alertTypeIndex,title,content).orElse(null);
        assert action != null;
        if(action.equals(ButtonType.OK)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
            Pane pane = loader.load();
            diagramGrid.getScene().setRoot(pane);
        }
        else{
            alert.close();
        }
    }
    public void resetDiagram(int alertTypeIndex,String title, String content){
        //TODO: IMPLEMENT
    }
}
