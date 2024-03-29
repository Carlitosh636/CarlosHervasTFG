package com.example.diagrams.implementations;

import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface IDiagramActions {
    List<List<Supplier>> setSolutionOperations();
    void setParams(Map<String,String> newValues) throws Exception;
    Map<String,String> setGenCodeParams(String baseCase, String returnValue);
    void setAlgorithmMap();
    boolean checkNotBaseCase(List<String> baseCases,List<String> inputs) throws Exception;
    boolean checkNotIncorrectInput(List<String> inputs);
    Map<String,String> calculateSolution(int index) throws Exception;
    String getFunctionName(int index);
    boolean checkIfMultipleCases(int selectedIndex);
}
