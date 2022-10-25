package com.example;

public class Algorithms {
    public static double recursiveExponentOption1(int base, int exp, int baseCaseValue){
        if(exp==baseCaseValue){
            if(baseCaseValue==0){
                return 1;
            }
            else if (baseCaseValue==1){
                return base;
            }
        }
        return recursiveExponentOption1(base,exp-1,baseCaseValue) * base;
    }
    public static double recursiveExponentOption2(int base, int exp, int baseCaseValue){
        if(exp==baseCaseValue){
            if(baseCaseValue==0){
                return 1;
            }
            if(baseCaseValue==1){
                return base;
            }
        }
        else if(exp % 2 == 0){
            return Math.pow(recursiveExponentOption2(base,exp/2,baseCaseValue),2);
        }
        return base * Math.pow(recursiveExponentOption2(base,(exp-1)/2,baseCaseValue),2);
    }
}
