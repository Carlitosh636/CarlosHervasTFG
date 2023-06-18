package com.example.controller;

import com.example.diagrams.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuController {
    @FXML
    AnchorPane anchor;
    @FXML
    Button b1;
    @FXML
    Button b2;
    @FXML
    Button b3;
    @FXML
    Button b4;
    @FXML
    Button b5;
    @FXML
    Button b6;
    private final Map<Button,BaseDiagram> buttonsPaths = new HashMap<>();
    private MenuButtonHandler menuButtonHandler;
    private ExceptionHandler exceptionHandler;

    public void initialize() throws IOException {
        menuButtonHandler = new MenuButtonHandler();
        exceptionHandler = new ExceptionHandler(null);
        buttonsPaths.put(b1,new ArithmeticDiagram(new RecursivePowerDiagram(), "/diagramData/RecursivePotencyData.json"));
        buttonsPaths.put(b2,new ArithmeticDiagram(new SlowAdditionDiagram(),"/diagramData/SlowAdditionData.json"));
        buttonsPaths.put(b3,new ArraySortingDiagram(new ArraySortDiagram(),"/diagramData/SortListData.json"));
        buttonsPaths.put(b4,new StringDiagram(new ReverseStringDiagram(),"/diagramData/ReverseStringData.json"));
        buttonsPaths.put(b5,new StringDiagram(new EqualStringsDiagram(),"/diagramData/EqualStringsData.json"));
        buttonsPaths.put(b6,new StringDiagram(new ContainsDigitDiagram(),"/diagramData/ContainsDigitData.json"));
        buttonsPaths.forEach((k,v)-> k.setOnAction(actionEvent -> {
            try {
                loadScene(v,k);
            } catch (IOException e) {
                e.printStackTrace();
                exceptionHandler.showErrorAlert(new IOException("Error de datos"));
            }
        }));
    }

    @FXML
    private void loadScene(BaseDiagram model, Button button) throws IOException {
        menuButtonHandler.loadVisualizer(model, button);
    }

}
