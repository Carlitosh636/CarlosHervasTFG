package com.example.presenter;

import javafx.scene.control.Alert;

import java.util.HashMap;

public class PresenterInputHandler {
    private final HashMap<String,String> errorMessageMap = new HashMap<>();
    public PresenterInputHandler(String inputFormatting) {
        errorMessageMap.put("RuntimeException","Revisa el contenido y que concuerde con el formato: "+inputFormatting);
        errorMessageMap.put("BaseCaseException","Revisa el contenido, no puedes introducir un caso base o una solución como parámetro");
        errorMessageMap.put("IncorrectSelectionException","Esta opción es incorrecta, elige otra");
    }
    public void showErrorAlert(Exception exceptionType){
        Alert inputErrorAlert = new Alert(Alert.AlertType.ERROR);
        inputErrorAlert.setTitle("Error");
        inputErrorAlert.setHeaderText(exceptionType.getMessage());
        inputErrorAlert.setContentText(errorMessageMap.get(exceptionType.getClass().getSimpleName()));
        inputErrorAlert.showAndWait();
    }
}
