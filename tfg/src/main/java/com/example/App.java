package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<Integer> correctChoices = new ArrayList<>();
        correctChoices.add(1);
        correctChoices.add(0);
        Diagram model = new Diagram(correctChoices,"^");
        DiagramView view = new DiagramView(model);
        Scene root = new Scene(view);
        DiagramPresenter presenter = new DiagramPresenter(model, view);

        stage.sizeToScene();
        stage.setScene(root);
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(800);
        stage.setMaxWidth(1200);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

}