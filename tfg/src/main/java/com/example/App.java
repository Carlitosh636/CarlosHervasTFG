package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static Scene main;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DiagramSelector.fxml"));
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
        Diagram model = new RecursivePotencyDiagram("^");
        loadScene(model,"/fxml/DiagramViewer.fxml");
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        Diagram model = new SlowAdditionDiagram(",");
        loadScene(model,"/fxml/DiagramViewer.fxml");
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        Diagram model = new MergesortDiagram(null);
        loadScene(model,"/fxml/DiagramViewer.fxml");
    }
    private void loadScene(Diagram model, String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        loader.setControllerFactory(controller-> new DiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
