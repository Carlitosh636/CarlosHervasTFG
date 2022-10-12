package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Diagram model = new Diagram();
        DiagramView view = new DiagramView(model);

        Scene root = new Scene(view,1200,600);
        DiagramPresenter presenter = new DiagramPresenter(model, view);
        
        stage.setScene(root);
        stage.setTitle("Mi TFG");
        stage.show();
    }

}