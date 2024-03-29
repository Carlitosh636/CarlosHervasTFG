package com.example;

import com.example.algorithms.Algorithms;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiagramSelector.fxml"));
        Parent root = loader.load();
        Scene main = new Scene(root);
        stage.setScene(main);
        stage.setTitle("RecVis");
        stage.show();
        stage.setMaxHeight(1080);
        stage.setMaxWidth(1920);
        stage.setMinHeight(1000);
        stage.setMinWidth(1600);
        stage.setHeight(1080);
        stage.setWidth(1920);
        stage.setMaximized(true);
    }
}
