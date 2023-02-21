package com.example;

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
        stage.setMaxHeight(1200);
        stage.setMaxWidth(1600);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }
    @FXML
    public void handleMainMenuButton(javafx.event.ActionEvent actionEvent) throws IOException {
        ArithmeticDiagram model = new ArithmeticDiagram(new RecursivePowerDiagram(),"RecursiveDiagramData.json");
        loadScene(model);
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        ArithmeticDiagram model = new ArithmeticDiagram(new SlowAdditionDiagram(),"SlowAdditionData.json");
        loadScene(model);
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        ArraySortingDiagram model = new ArraySortingDiagram(new MergeSortDiagram(),"MergeSortData.json");
        loadScene(model);
    }
    private void loadScene(BaseDiagram model) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramViewer.fxml"));
        loader.setControllerFactory(controller-> new DiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
