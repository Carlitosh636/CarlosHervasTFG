package com.example.presenter;

import com.example.exceptions.AlertTypeIndexOutOfBounds;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public abstract class ButtonHandler {
    protected Alert alert;
    protected final ButtonType okButton = new ButtonType("Sí", ButtonBar.ButtonData.OK_DONE);
    private final ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

    public Optional<ButtonType> setAlertDataAndStyle(int alertTypeIndex, String title, String content) throws AlertTypeIndexOutOfBounds {
        alert = selectAlertType(alertTypeIndex,title);
        alert.setTitle(title);
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/css/dialog-style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        return alert.showAndWait();
    }
    public Alert selectAlertType(int index,String title) throws AlertTypeIndexOutOfBounds {
        return switch (index) {
            case 1 -> new Alert(Alert.AlertType.CONFIRMATION, title, okButton, cancelButton);
            case 2 -> new Alert(Alert.AlertType.ERROR, title, okButton, cancelButton);
            default -> throw new AlertTypeIndexOutOfBounds("No alert type for provided index");
        };
    }

}
