package com.example;

import java.util.ArrayList;
import java.util.List;

public class DiagramToCodeMapper {
    private static Diagram currentDiagram;
    public Diagram getCurrentDiagram() {
        return currentDiagram;
    }
    public void setCurrentDiagram(Diagram currentDiagram) {
        DiagramToCodeMapper.currentDiagram = currentDiagram;
    }
    public static String mapDiagramName(){
        return currentDiagram.getClass().getSimpleName().replace("Diagram","");
    }
    public static String mapParameters(){
        return currentDiagram.parametersView;
    }
    public static String mapBaseCases() throws Exception {
        List<String> baseCases = new ArrayList<>();
        
        return String.format("if %s:\n return %s",
                currentDiagram.getBaseCaseChoices().get(currentDiagram.getCurrentProblemSize()).get(currentDiagram.getCurrentBaseCaseIndex()),
                mapReturnVal());
    }
    public static String mapReturnVal() throws Exception {
        //En todos los Diagrams, el -1 guarda la llamada al algoritmo para devolver el valor minimo
        return (String) currentDiagram.algorithmsMap.get(-1).call();
    }
    public static String mapRecursiveCases(){
        return null;
    }
}
