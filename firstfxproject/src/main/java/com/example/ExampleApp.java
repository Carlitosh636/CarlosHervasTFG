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
//import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class ExampleApp extends Application {
    // nodo raiz de la escena
    VBox root = new VBox();
    // nodo texto
    Text message = new Text("This is my first JavaFX app");
    // escena con root de raiz
    Scene scene = new Scene(root, 600, 300);

    
    Button greetButton = new Button("Greet");
    Label nameLabel = new Label("Enter your name:");
    TextField nameField = new TextField();
    Label labelmsg = new Label("I am a green label");
    Label changeLabel = new Label("");
    StringProperty changeString = new SimpleStringProperty("Hola de nuevoo soy otro binding");

    public final StringProperty changeStringProperty(){
        return this.changeString;
    }

    public final String getChangeString(){
        return changeStringProperty().get();
    }
    public final void setChangeString(String value){
        changeStringProperty().set(value);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // titulo
        stage.setTitle("Mi primera app JavaFX");
        //estilo base, fondo blanco y borde clásico de app
        stage.initStyle(StageStyle.DECORATED);
        //modalidad del stage, es decir, si permite usar otras ventanas de la app mientras está esta activa. Permite hacer dialog boxes        
        greetButton.setOnAction(e -> {
            if (nameField.getText().trim().length() > 0) {
                labelmsg.setText("Hello " + nameField.getText().trim());
            } else {
                labelmsg.setText("Hello unnamed person");
            }
        });
        labelmsg.setStyle("-fx-text-fill: green");
        // confirmación de salida
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                Alert closeConfirmation = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to exit?");
                Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                        ButtonType.OK);
                exitButton.setText("Salir");
                Button cancelButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                        ButtonType.CANCEL);
                cancelButton.setText("Cancelar");
                closeConfirmation.setHeaderText("Confirmar salida");            
                closeConfirmation.initModality(Modality.APPLICATION_MODAL);
                closeConfirmation.initOwner(stage);

                //showandwait enseña la ventana y detiene el evento actual (en este caso cerrar la app), hasta que se cierre la ventana o se procese el evento manualmente
                Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
                if (!ButtonType.OK.equals(closeResponse.get())) {
                    arg0.consume();
                }
            }

        });

        //ejemplo de uso de data binding bidireccional.
        //ojo, si quisiese pasar un INtegerProperty a texto, hay que usar un converter
        //StringConverter<? extends Number> converter = new NumberStringConverter();
        //y pasar eso como tercer parametro
        Bindings.bindBidirectional(changeLabel.textProperty(), changeString);
        //otra manera algo directa, sin usar la clase Bindings
        //changeLabel.textProperty().bindBidirectional(changeString);
        
        root.getChildren().addAll(message, greetButton, nameLabel, nameField, labelmsg,changeLabel);
        root.setSpacing(5);
        stage.setScene(scene);
        stage.show();

        
    }

    // lauch lanza varios hilos (init,start y stop cuando se para) de la app, que
    // podemos hacer override.
    @Override
    public void init() {
        String name = Thread.currentThread().getName();
        System.out.println("init() method: " + name);
    }

    // DEBE SER ESTATICA
    public static void invalidation(Observable property) {
        System.out.println("Property is invalid");
    }

    public static void changed(ObservableValue<? extends Object> prop,
            Number oldValue,
            Number newValue) {

        System.out.print("Counter changed: ");
        System.out.println("Old = " + oldValue + ", new = " + newValue);
        
    }
    
}
