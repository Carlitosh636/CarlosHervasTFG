package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(1080);
        stage.setMaxWidth(1920);
        stage.setMinHeight(800);
        stage.setMinWidth(1600);
        stage.setMaximized(true);
    }
}
