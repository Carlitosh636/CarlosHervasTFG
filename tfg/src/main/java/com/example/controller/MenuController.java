package com.example.controller;

import com.example.diagrams.abstractions.*;
import com.example.diagrams.implementations.*;
import com.example.exceptions.AlertTypeIndexOutOfBounds;
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
    @FXML
    Button b7;
    @FXML
    Button b8;
    @FXML
    Button b9;
    private final Map<Button, BaseProblem> buttonsPaths = new HashMap<>();
    private MenuButtonHandler menuButtonHandler;
    private ExceptionHandler exceptionHandler;

    public void initialize() throws IOException {
        menuButtonHandler = new MenuButtonHandler();
        exceptionHandler = new ExceptionHandler(null);
        RecursivePowerDiagram recursivePowerDiagram = new RecursivePowerDiagram();
        ArraySortHardDiagram arraySortHardDiagram = new ArraySortHardDiagram();
        ArraySortMediumDiagram arraySortMediumDiagram = new ArraySortMediumDiagram();
        DigitsSharedDiagram digitsSharedDiagram = new DigitsSharedDiagram();
        buttonsPaths.put(b1,new ArithmeticProblem(recursivePowerDiagram,recursivePowerDiagram, "/diagramData/RecursivePotencyData.json"));
        buttonsPaths.put(b2,new ArithmeticProblem(new SlowAdditionDiagram(),null,"/diagramData/SlowAdditionData.json"));
        buttonsPaths.put(b3,new ArrayProblem(arraySortHardDiagram, "/diagramData/SortListHardData.json", arraySortHardDiagram));
        buttonsPaths.put(b4,new StringProblem(new ReverseStringDiagram(),"/diagramData/ReverseStringData.json"));
        buttonsPaths.put(b5,new BooleanProblem(new EqualStringsDiagram(),"/diagramData/EqualStringsData.json"));
        buttonsPaths.put(b6,new BooleanProblem(new ContainsDigitDiagram(),"/diagramData/ContainsDigitData.json"));
        buttonsPaths.put(b7,new StringProblem(new VowelsInStringDiagram(),"/diagramData/VowelsInStringData.json"));
        buttonsPaths.put(b8,new ArrayProblem(arraySortMediumDiagram,"/diagramData/SortListMediumData.json", arraySortMediumDiagram));
        buttonsPaths.put(b9,new ArrayProblem(digitsSharedDiagram,"/diagramData/DigitsSharedData.json", digitsSharedDiagram));

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
    private void loadScene(BaseProblem model, Button button) throws IOException {
        menuButtonHandler.loadVisualizer(model, button);
    }
    @FXML
    private void quitApplication(){
        try {
            menuButtonHandler.quitApplication();
        } catch (AlertTypeIndexOutOfBounds e) {
            exceptionHandler.showErrorAlert(e);
        }
    }

}
