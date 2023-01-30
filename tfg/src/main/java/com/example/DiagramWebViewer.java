package com.example;

public class DiagramWebViewer {
    private String diagramTitle;
    private String parameters;
    private String baseCase;
    private String returnValue;
    private String recursiveCase;

    public DiagramWebViewer(String diagramTitle, String parameters, String baseCase, String returnValue, String recursiveCase) {
        this.diagramTitle = diagramTitle;
        this.parameters = parameters;
        this.baseCase = baseCase;
        this.returnValue = returnValue;
        this.recursiveCase = recursiveCase;
    }

    public String getDiagramTitle() {
        return diagramTitle;
    }

    public void setDiagramTitle(String diagramTitle) {
        this.diagramTitle = diagramTitle;
    }
    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
    public String getBaseCase() {
        return baseCase;
    }

    public void setBaseCase(String baseCase) {
        this.baseCase = baseCase;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getRecursiveCase() {
        return recursiveCase;
    }

    public void setRecursiveCase(String recursiveCase) {
        this.recursiveCase = recursiveCase;
    }
}
