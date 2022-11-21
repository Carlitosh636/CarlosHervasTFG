package com.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    HBox diagramSelection = new HBox();
    Button diagram1 = new Button("Recursive Potency");
    Button diagram2 = new Button("Slow Addition");
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        diagram1.setOnAction(e->{
            List<Integer> correctChoices = new ArrayList<>();
            correctChoices.add(1);
            correctChoices.add(0);
            correctChoices.add(2);
            Diagram model = new RecursivePotencyDiagram(correctChoices,"^");
            DiagramView view = new DiagramView(model);
            DiagramPresenter presenter = new DiagramPresenter(model, view);
            view.getRoot().setAlignment(Pos.CENTER);
            stage.setScene(new Scene(view.getRoot()));
            stage.close();
            stage.show();
        });
        diagram2.setOnAction(e->{
            List<Integer> correctChoices = new ArrayList<>();
            correctChoices.add(0);
            correctChoices.add(0);
            correctChoices.add(2);
            Diagram model = new SlowAdditionDiagram(correctChoices,",");
            DiagramView view = new DiagramView(model);
            DiagramPresenter presenter = new DiagramPresenter(model, view);
            view.getRoot().setAlignment(Pos.CENTER);
            stage.setScene(new Scene(view.getRoot()));
            stage.close();
            stage.show();
        });
        diagramSelection.getChildren().addAll(diagram1,diagram2);
        Scene mainMenuRoot = new Scene(diagramSelection,1200,800);

        stage.setScene(mainMenuRoot);
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1600);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());


    }

}