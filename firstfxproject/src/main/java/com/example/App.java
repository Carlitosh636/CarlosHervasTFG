package com.example;

import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        // le podemos pasar args para que los parametros vayan a la app
        //Application.launch(ExampleApp.class);
        //Application.launch(CenteredCircle.class);
        Application.launch(GridApp.class, args);
    }

}