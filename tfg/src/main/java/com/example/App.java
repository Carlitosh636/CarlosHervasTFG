package com.example;

import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    private static Scene main;
    @Override
    public void start(Stage stage) throws Exception {
        DiagramData data = new DiagramData(
                "SIMPLE",
                "Enunciado del recursive power",
                new HashMap<String,String>(){{
                    put("a","");
                    put("b","");
                }},
                "",
                "",
                new ArrayList<>(){{
                    add("");
                }},
                new ArrayList<>(){{
                    add("");
                }},
                "",0,0,0,0,
                ",",
                "",
                new ArrayList<>(){{
                    add("b");
                }},
                0,
                new ArrayList<>(){{
                    add(new ArrayList<>(){{
                        add("b == 0");
                    }});
                }},
                new ArrayList<>(){{
                    add(new ArrayList<>(){{
                        add("0");
                    }});
                }},
                new ArrayList<>(){{
                    add(Arrays.asList("b - 1","b / 2"));
                }},
                new ArrayList<>(){{
                    add(new ArrayList<>(){{
                        add("* b");
                        add("* a");
                        add("** a");
                    }});
                    add(new ArrayList<>(){{
                        add("** 2");
                        add("* b");
                    }});
                    add(new ArrayList<>(){{
                        add("** 2");
                        add("* b");
                        add("a * ((a^(b-1/2)))²))");
                    }});
                }},
                "",
                "",
                Arrays.asList(1,0,2)
        );
        ObjectMapper objMapper = new ObjectMapper();
        ObjectWriter writer = objMapper.writerWithDefaultPrettyPrinter();
        writer.writeValue(new File("DiagramExample.json"),data);

        Supplier<Double> fn2 = (Supplier<Double> & Serializable)()
                -> Math.random();
        System.out.println("Run original function: "
                + fn2.get());
        String path2 = "./serialized-fn2";
        Serializator.serialize((Serializable)fn2,path2);

        Supplier<Double> desFn2
                = (Supplier<Double>)Serializator.deserialize(path2);
        System.out.println("Deserialized function from "
                + path2);
        System.out.println("Run deserialized function: "
                + desFn2.get());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DiagramSelector.fxml"));
        Parent root = (Parent) loader.load();
        main = new Scene(root);
        stage.setScene(main);
        stage.setTitle("Mi TFG");
        stage.show();
        stage.setMaxHeight(1200);
        stage.setMaxWidth(1600);
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }
    @FXML
    public void handleMainMenuButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Diagram model = new RecursivePotencyDiagram("^","DiagramExample.json");
        loadScene(model,"/fxml/DiagramViewer.fxml");
    }
    @FXML
    public void handleMainMenuButton2(ActionEvent actionEvent) throws IOException{
        Diagram model = new SlowAdditionDiagram(",","DiagramExample.json");
        loadScene(model,"/fxml/DiagramViewer.fxml");
    }
    @FXML
    public void handleMainMenuButton3(ActionEvent actionEvent) throws IOException {
        Diagram model = new MergesortDiagram(null,"DiagramExample.json");
        loadScene(model,"/fxml/DiagramViewer.fxml");
    }
    private void loadScene(Diagram model, String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
        loader.setControllerFactory(controller-> new DiagramPresenter(model));
        Pane pane = loader.load();
        main.setRoot(pane);
    }
}
