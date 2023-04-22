package com.example.presenter;

import com.example.exceptions.AlertTypeIndexOutOfBounds;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.HashMap;
import java.util.Optional;

public abstract class ButtonHandler {
    protected Alert alert;

    public Optional<ButtonType> setAlertData(int alertTypeIndex, String title, String content) throws AlertTypeIndexOutOfBounds {
        alert = selectAlertType(alertTypeIndex);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait();
    }
    public Alert selectAlertType(int index) throws AlertTypeIndexOutOfBounds {
        return switch (index) {
            case 1 -> new Alert(Alert.AlertType.CONFIRMATION);
            case 2 -> new Alert(Alert.AlertType.ERROR);
            default -> throw new AlertTypeIndexOutOfBounds("No alert type for provided index");
        };
    }

}
