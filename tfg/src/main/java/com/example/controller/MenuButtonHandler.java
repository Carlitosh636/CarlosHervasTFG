package com.example.controller;

import com.example.diagrams.BaseProblem;
import com.example.exceptions.AlertTypeIndexOutOfBounds;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuButtonHandler extends ButtonHandler {

    public void loadVisualizer(BaseProblem model, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramViewer.fxml"));
        loader.setControllerFactory(controller-> {
            try {
                return new ProblemController(model);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Pane pane = loader.load();
        button.getScene().setRoot(pane);
    }

    public void quitApplication() throws AlertTypeIndexOutOfBounds {
        ButtonType action =  super.setAlertDataAndStyle(1,"Salir de la aplicación","¿Confirmas querer salir de la aplicación?").orElse(null);
        assert action != null;
        if(action.equals(okButton)){
            System.exit(0);
        }
        else{
            alert.close();
        }
    }
}
