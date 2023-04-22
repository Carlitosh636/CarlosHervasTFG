package com.example.presenter;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public abstract class ButtonHandler {
    public Optional<ButtonType> setAlertData(Alert alert,String title, String content){
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
