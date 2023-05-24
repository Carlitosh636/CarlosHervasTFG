package com.example.presenter;

import com.example.exceptions.AlertTypeIndexOutOfBounds;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Map;

public class PresenterButtonHandler extends ButtonHandler {

    public void returnToMenu(int alertTypeIndex, String title, String content, GridPane diagramGrid) throws IOException, AlertTypeIndexOutOfBounds {
        ButtonType action =  super.setAlertDataAndStyle(alertTypeIndex,title,content).orElse(null);
        assert action != null;
        if(action.equals(okButton)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
            Pane pane = loader.load();
            diagramGrid.getScene().setRoot(pane);
        }
        else{
            alert.close();
        }
    }
    public String resetDiagram(int alertTypeIndex,String title, String content, Map<String,String> genCode) throws AlertTypeIndexOutOfBounds {
        ButtonType action =  super.setAlertDataAndStyle(alertTypeIndex,title,content).orElse(null);
        assert action != null;
        if(action.equals(ButtonType.OK)){
            genCode.put("functionName","FUNCIÓN");
            genCode.put("baseCase","CASO(S) BASE");
            genCode.put("returnValue","CASO(S) BASE");
            genCode.put("recursiveCases","CASO(S) RECURSIVOS");
            genCode.put("auxFunctions","");
            return "OK";
        }
        else{
            alert.close();
            return "CLOSE";
        }
    }
}
