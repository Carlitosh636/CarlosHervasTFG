package com.example;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class Diagram {
    private SimpleStringProperty inputs;
    private List<Integer> problemData;

    public Diagram() {
    }

    public Diagram(SimpleStringProperty inputs,List<Integer> problemData) {
        this.inputs = inputs;
        this.problemData = problemData;
    }

    public SimpleStringProperty getInputsProperty() {
        return inputs;
    }
    public String geInputs(){
        return this.inputs.get();
    }

    public void setInputs(SimpleStringProperty inputs) {
        this.inputs = inputs;
    }
    public List<Integer> getProblemData() {
        return problemData;
    }

    public void setProblemData(List<Integer> problemData) {
        this.problemData = problemData;
    }
    
}
