package com.example.presenter;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

import java.util.HashMap;

public class ExceptionHandler {
    private final HashMap<String,String> errorMessageMap = new HashMap<>();
    public ExceptionHandler(String inputFormatting) {
        errorMessageMap.put("IncorrectInputException","Revisa el contenido y que concuerde con el formato: "+inputFormatting);
        errorMessageMap.put("BaseCaseException","Revisa el contenido, no puedes introducir un caso base o una solución como parámetro");
        errorMessageMap.put("IncorrectSelectionException","Esta opción es incorrecta, elige otra");
        errorMessageMap.put("InternallyCausedRuntimeException","Ha ocurrido un error interno en la aplicación.");
    }
    public void showErrorAlert(Exception exceptionType){
        Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputErrorAlert.setTitle("Error");
        inputErrorAlert.setHeaderText(exceptionType.getMessage());
        inputErrorAlert.setContentText(errorMessageMap.get(exceptionType.getClass().getSimpleName()));
        DialogPane dialogPane = inputErrorAlert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/dialog-style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        inputErrorAlert.showAndWait();
    }
}
