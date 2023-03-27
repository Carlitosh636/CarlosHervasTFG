package com.example;

import com.example.algorithms.Algorithms;
import com.example.diagrams.*;
import com.example.presenter.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static Scene main;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        stage.setScene(main);
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(1080);
        stage.setMaxWidth(1920);
        stage.setMinHeight(700);
        stage.setMinWidth(1200);
        stage.setMaximized(true);
    }
    @FXML
    public void handleMainMenuButton(javafx.event.ActionEvent actionEvent) throws IOException {
        ArithmeticDiagram model = new ArithmeticDiagram(new RecursivePowerDiagram(),"diagramData/RecursiveDiagramData.json");
        loadScene(model,"generatedData/RecursivePotencyGeneration.json");
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        ArithmeticDiagram model = new ArithmeticDiagram(new SlowAdditionDiagram(),"diagramData/SlowAdditionData.json");
        loadScene(model,"generatedData/SlowAdditionGeneration.json");
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        ArraySortingDiagram model = new ArraySortingDiagram(new ArraySortDiagram(),"diagramData/SortListData.json");
        loadScene(model,"generatedData/RecursivePotencyGeneration.json");
    }
    @FXML
    public void handleMainMenuButton4(ActionEvent actionEvent) throws IOException {
        StringDiagram model = new StringDiagram(new ReverseStringDiagram(),"diagramData/ReverseStringData.json");
        loadScene(model,"generatedData/RecursivePotencyGeneration.json");
    }
    private void loadScene(BaseDiagram model,String genFilePath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramViewer.fxml"));
        loader.setControllerFactory(controller-> {
            try {
                return new DiagramPresenter(model,genFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
