package com.example;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class GridApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // A Label and a TextField
        Label nameLbl = new Label("Name:");
        TextField nameFld = new TextField();
        // A Label and a TextArea
        Label descLbl = new Label("Description:");
        TextArea descText = new TextArea();
        descText.setPrefColumnCount(20);
        descText.setPrefRowCount(5);
        // Two buttons
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");

        // A Label used as a status bar
        Label statusBar = new Label("Status: Ready");
        statusBar.setStyle("-fx-background-color: lavender;" +
                "-fx-font-size: 7pt;" +
                "-fx-padding: 10 0 0 0;");

        // Create a GridPane and set its background color to lightgray
        GridPane root = new GridPane();
        // Add children to the GridPane
        //span se refiere a la cantidad que ocupa.
        root.add(nameLbl, 0, 0, 1, 1); // (c0, r0, colspan=1, rowspan=1)
        root.add(nameFld, 1, 0, 1, 1); // (c1, r0, colspan=1, rowspan=1)
        root.add(descLbl, 0, 1, 3, 1); // (c0, r1, colspan=3, rowspan=1)
        root.add(descText, 0, 2, 2, 1); // (c0, r2, colspan=2, rowspan=1)
        root.add(okBtn, 2, 0, 1, 1); // (c2, r0, colspan=1, rowspan=1)
        root.add(cancelBtn, 2, 1, 1, 1); // (c2, r1, colspan=1, rowspan=1)
        root.add(statusBar, 0, 3, GridPane.REMAINING, 1);
        /* Set constraints for children to customize their resizing behavior */
        // The max width of the OK button should be big enough,
        // so it can fill the width of its cell
        okBtn.setMaxWidth(Double.MAX_VALUE);

        // The name field in the first row should grow horizontally
        GridPane.setHgrow(nameFld, Priority.ALWAYS);
        // The description field in the third row should grow vertically
        GridPane.setVgrow(descText, Priority.ALWAYS);
        // The status bar in the last should fill its cell
        statusBar.setMaxWidth(Double.MAX_VALUE);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Creating Forms Using a GridPane");
        stage.show();
        

    }
    /*
         * HBox p1 = new HBox(20);
         * p1.setPrefSize(200, 100);
         * p1.setStyle("-fx-padding: 10;" +
         * "-fx-border-style: solid inside;" +
         * "-fx-border-width: 2;" +
         * "-fx-border-insets: 5;" +
         * "-fx-border-radius: 5;" +
         * "-fx-border-color: blue;");
         * HBox p2 = new HBox(20);
         * p2.setPrefSize(200, 100);
         * p2.setStyle("-fx-padding: 10;" +
         * "-fx-border-style: solid inside;" +
         * "-fx-border-width: 2;" +
         * "-fx-border-insets: 5;" +
         * "-fx-border-radius: 5;" +
         * "-fx-border-color: blue;");
         * 
         * // Place p2 20px right to p1
         * p2.layoutYProperty().bind(p1.layoutYProperty());
         * p2.layoutXProperty().bind(p1.layoutXProperty().add(p1.widthProperty()).add(
         * 200));
         * 
         * Button okBtn = new Button("Ok");
         * Button otroBtn = new Button("otroboton");
         * Button otro = new Button("grg");
         * Button jaja = new Button("herhtvb");
         * p1.getChildren().addAll(okBtn,otroBtn);
         * 
         * p1.setPadding(new Insets(50));
         * p2.getChildren().addAll(otro,jaja);
         * Pane root = new Pane(p1,p2);
         * root.setPrefSize(1000, 500);
         * Scene scene = new Scene(root);
         * stage.setScene(scene);
         * stage.show();
         * stage.sizeToScene();
         */
}
