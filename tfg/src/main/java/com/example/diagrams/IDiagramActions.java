package com.example.diagrams;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface IDiagramActions {
    //puedo hacer incluso varias versiones de cada función (ej: calculateSolutionSimple, calculateSolutionComplex)
    //como en el baseDiagram usamos un objeto IDIagramActions, luego puedo llamar en el concreto la función que quiera.
    //aquí estarán también las funciones para calcular los valores, en el builder (ej: Arithmetic map) simplemente se llama a la función
    //de la interfaz para hacer el cálculo
    List<List<Supplier>> setSolutionOperations(Map<String,String> params);
    void setAlgorithmMap(Map<String,String> params);
    boolean checkNotBaseCase(List<String> baseCases,Map<String,String> params);
    Map<String,String> calculateSolution(int index, Map<String, String> params) throws Exception;
    boolean checkSolutionsEqual(String calcSol, String ogSol);
}
