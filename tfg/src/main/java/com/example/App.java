package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private ScreenController screenController;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiagramSelector.fxml"));
        System.out.println(getClass().getResource("/DiagramSelector.fxml"));
        Parent root = (Parent) loader.load();
        Scene main = new Scene(root);
        screenController = new ScreenController(main);

        /*diagram1.setOnAction(e->{
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
        });*/
        stage.setScene(main);
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1600);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());


    }

}