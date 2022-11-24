package com.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
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
        Parent root = (Parent) loader.load();
        Scene main = new Scene(root);
        screenController = new ScreenController(main);
        screenController.addScreen("simpleDiagram", FXMLLoader.load(getClass().getResource("/SimpleDiagramView.fxml")));
        //TODO: ahora hay que pasar todo esto comentado a las funciones para los botones de FXML
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
    @FXML
    public void handleMainMenuButton(javafx.event.ActionEvent actionEvent) {
        //aqui algo está mal
        List<Integer> correctChoices = new ArrayList<>();
        correctChoices.add(1);
        correctChoices.add(0);
        correctChoices.add(2);
        Diagram model = new RecursivePotencyDiagram(correctChoices,"^");
        DiagramView view = new DiagramView();
        view.setModel(model);
        screenController.activate("simpleDiagram");
    }

}