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
        Diagram model = new RecursivePotencyDiagram("^");
        loadScene(model,"/SimpleDiagramView.fxml",1);
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        Diagram model = new SlowAdditionDiagram(",");
        loadScene(model,"/SimpleDiagramView.fxml",1);
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        Diagram model = new MergesortDiagram(null);
        loadScene(model,"/ComplexDiagramView.fxml",2);
    }
    private void loadScene(Diagram model, String viewName, int choiceDiagram) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        switch (choiceDiagram){
            case 1:
                loader.setControllerFactory(controller->new SimpleDiagramPresenter(model));
                break;
            case 2:
                loader.setControllerFactory(controller->new ComplexDiagramPresenter(model));
                break;
        }
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
