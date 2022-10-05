package com.example;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    // el primer argumento es el objeto del que es parte, el segundo el nombre de la
    // propiedad, y el tercero el valor por defecto.
    private StringProperty title = new SimpleStringProperty(this, "title", "Unknown");

    // getter
    public final StringProperty titleProperty() {
        return title;
    }

    public final String getTitle() {
        return this.title.get();
    }

    public final void setTitle(String s) {
        this.title.set(s);
    }

    private ReadOnlyStringWrapper ISBN = new ReadOnlyStringWrapper(this, "ISBN", "Unknown");

    public final String getISBN() {
        return ISBN.get();
    }

    public final ReadOnlyStringProperty ISBNProperty() {
        return ISBN.getReadOnlyProperty();
    }
}
