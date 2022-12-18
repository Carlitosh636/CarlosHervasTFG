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
import java.util.List;

public class App extends Application {
    private static ScreenController screenController;
    public static void main(String[] args) {
        launch(args);
    }
    private static Scene main;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiagramSelector.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        stage.setScene(main);
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(1000);
        stage.setMaxWidth(1600);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }
    @FXML
    public void handleMainMenuButton(javafx.event.ActionEvent actionEvent) throws IOException {
        List<Integer> correctChoices = new ArrayList<>();
        correctChoices.add(1);
        correctChoices.add(0);
        correctChoices.add(2);
        Diagram model = new RecursivePotencyDiagram(correctChoices,"^");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SimpleDiagramView.fxml"));
        loader.setControllerFactory(controller->new SimpleDiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        List<Integer> correctChoices = new ArrayList<>();
        correctChoices.add(0);
        correctChoices.add(0);
        correctChoices.add(2);
        Diagram model = new SlowAdditionDiagram(correctChoices,",");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SimpleDiagramView.fxml"));
        loader.setControllerFactory(controller->new SimpleDiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        List<Integer> correctChoices = new ArrayList<>();
        correctChoices.add(2);
        Diagram model = new MergesortDiagram(correctChoices,null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ComplexDiagramView.fxml"));
        loader.setControllerFactory(controller->new ComplexDiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
