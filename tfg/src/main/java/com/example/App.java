package com.example;

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
        /*Map<String,Supplier<Double>> fnMap = new HashMap<>();
        Supplier<Double> fn1 = (Supplier<Double> & Serializable)()
                -> 11.0;
        Supplier<Double> fn2 = (Supplier<Double> & Serializable)()
                -> Math.random();
        System.out.println("Run original function: "
                + fn2.get());
        fnMap.put("fn1",fn1);
        fnMap.put("fn2",fn2);
        String path2 = "./serialized-map";
        Serializator.serialize((Serializable)fnMap,path2);

        Map<String,Supplier<Double>> desMap =
                (Map<String, Supplier<Double>>)Serializator.deserialize(path2);
        System.out.println("Run f1: "+desMap.get("fn1").get());
        System.out.println("Run f2: "+desMap.get("fn2").get());*/
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
        Diagram model = new RecursivePotencyDiagram("RecursiveDiagramData.json");
        loadScene(model, "/view/DiagramViewer.fxml");
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        Diagram model = new SlowAdditionDiagram("SlowAdditionData.json");
        loadScene(model, "/view/DiagramViewer.fxml");
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        Diagram model = new MergesortDiagram("MergeSortData.json");
        loadScene(model, "/view/DiagramViewer.fxml");
    }
    private void loadScene(Diagram model, String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        loader.setControllerFactory(controller-> new DiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
