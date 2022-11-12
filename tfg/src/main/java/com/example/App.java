package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
        //recusrive potency diagram sols
        /*correctChoices.add(1);
        correctChoices.add(0);
        correctChoices.add(2);*/
        //slowadditiondiagramsols
        correctChoices.add(0);

        //Diagram model = new RecursivePotencyDiagram(correctChoices,"^");
        Diagram model = new SlowAdditionDiagram(correctChoices,",");

        DiagramView view = new DiagramView(model);
        Scene root = new Scene(view,1200,800);
        DiagramPresenter presenter = new DiagramPresenter(model, view);

        view.setAlignment(Pos.CENTER);
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