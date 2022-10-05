package com.example;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Monitor {
    //como los Property son todos objetos, para aquellos que no tendrán mucho uso
    //podemos optimizarlos
    //p ej aqui casi siempre el screenType será flat, por lo que en vez de devolver 
    //el property, devolvemos un string que es más simple.
    //solo se instancia el StringProperty cuando es verdaderamente necesitado.
    public static final String DEFAULT_SCREEN_TYPE = "flat";
    private StringProperty screenType;

    public String getScreenType() {
        return (screenType == null) ? DEFAULT_SCREEN_TYPE : screenType.get();
    }

    public void setScreenType(String newScreenType) {
        if (screenType != null || !DEFAULT_SCREEN_TYPE.equals(newScreenType)) {
            screenTypeProperty().set(newScreenType);
        }
    }
    //aqui es donde se crearía el property, si fuese necesitado.
    public StringProperty screenTypeProperty() {
        if (screenType == null) {
            screenType = new SimpleStringProperty(this, "screenType",
                    DEFAULT_SCREEN_TYPE);
        }
        return screenType;
    }
}