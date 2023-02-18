package com.example.builders;

public interface DiagramBuilder {
    void buildViewVals();
    void buildParams();
    void processInputs();
    boolean checkNotBaseCase();
    String calculateSolution();
    boolean checkSolutionsEqual();
}
