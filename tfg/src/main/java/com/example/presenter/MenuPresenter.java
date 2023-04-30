package com.example.presenter;

import com.example.diagrams.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class ButtonRelatedData{
    BaseDiagram baseDiagram;
    String genFilePath;
    public ButtonRelatedData(BaseDiagram baseDiagram, String genFilePath) {
        this.baseDiagram = baseDiagram;
        this.genFilePath = genFilePath;
    }
    public BaseDiagram getBaseDiagram() {
        return baseDiagram;
    }

    public String getGenFilePath() {
        return genFilePath;
    }
}
public class MenuPresenter {
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
    private final Map<Button,ButtonRelatedData> buttonsPaths = new HashMap<>();
    private BaseDiagram model;
    private MenuButtonHandler menuButtonHandler;
    public void initialize() throws IOException {
        menuButtonHandler = new MenuButtonHandler();
        buttonsPaths.put(b1,new ButtonRelatedData(new ArithmeticDiagram(new RecursivePowerDiagram(),"1"),"generatedData/RecursivePotencyGeneration.json"));
        buttonsPaths.put(b2,new ButtonRelatedData(new ArithmeticDiagram(new SlowAdditionDiagram(),"2"),"generatedData/SlowAdditionGeneration.json"));
        buttonsPaths.put(b3,new ButtonRelatedData(new ArraySortingDiagram(new ArraySortDiagram(),"3"),"generatedData/SortListGeneration.json"));
        buttonsPaths.put(b4,new ButtonRelatedData(new StringDiagram(new ReverseStringDiagram(),"4"),"generatedData/ReverseStringGeneration.json"));
        buttonsPaths.put(b5,new ButtonRelatedData(new StringDiagram(new EqualStringsDiagram(),"5"),"generatedData/ReverseStringGeneration.json"));
        buttonsPaths.forEach((k,v)-> k.setOnAction(actionEvent -> {
            model = v.getBaseDiagram();
            try {
                loadScene(model,v.getGenFilePath(),k);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
    @FXML
    private void loadScene(BaseDiagram model,String filePath, Button button) throws IOException {
        menuButtonHandler.loadScene(model, filePath, button);
    }
}
