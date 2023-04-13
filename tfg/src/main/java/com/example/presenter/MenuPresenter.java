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
    private final Map<Button,ButtonRelatedData> buttonsPaths = new HashMap<>();
    BaseDiagram model;

    public void initialize() throws IOException {
        buttonsPaths.put(b1,new ButtonRelatedData(new ArithmeticDiagram(new RecursivePowerDiagram(),"diagramData/RecursiveDiagramData.json"),"generatedData/RecursivePotencyGeneration.json"));
        buttonsPaths.put(b2,new ButtonRelatedData(new ArithmeticDiagram(new SlowAdditionDiagram(),"diagramData/SlowAdditionData.json"),"generatedData/SlowAdditionGeneration.json"));
        buttonsPaths.put(b3,new ButtonRelatedData(new ArraySortingDiagram(new ArraySortDiagram(),"diagramData/SortListData.json"),"generatedData/SortListGeneration.json"));
        buttonsPaths.put(b4,new ButtonRelatedData(new StringDiagram(new ReverseStringDiagram(),"diagramData/ReverseStringData.json"),"generatedData/ReverseStringGeneration.json"));
        buttonsPaths.forEach((k,v)-> k.setOnAction(actionEvent -> {
            model = v.getBaseDiagram();
            try {
                loadScene(model);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
    private void loadScene(BaseDiagram model) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramViewer.fxml"));
        loader.setControllerFactory(controller-> {
            try {
                return new DiagramPresenter(model);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Pane pane = loader.load();
        b1.getScene().setRoot(pane);
    }
}
