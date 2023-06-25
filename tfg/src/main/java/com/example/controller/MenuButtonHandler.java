package com.example.controller;

import com.example.diagrams.BaseDiagram;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuButtonHandler extends ButtonHandler {

    public void loadVisualizer(BaseDiagram model, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramViewer.fxml"));
        loader.setControllerFactory(controller-> {
            try {
                return new DiagramController(model);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Pane pane = loader.load();
        button.getScene().setRoot(pane);
    }
}