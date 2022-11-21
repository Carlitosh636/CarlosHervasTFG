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
    public static double slowAdditionOption1(int a,int b, int baseCaseValue){
        if(a==baseCaseValue){
            return b;
        }
        if(b==baseCaseValue){
            return a;
        }
        else{
            return slowAdditionOption1(a-1,b,baseCaseValue)+1;
        }
    }

    public static double slowAdditionOption2(int a, int b, int baseCaseValue) {
        if(a==baseCaseValue){
            return b;
        }
        if(b==baseCaseValue){
            return a;
        }
        else if (a<b){
            return slowAdditionOption2(a-1,b,baseCaseValue)+1;
        }
        else{
            return slowAdditionOption2(a,b-1,baseCaseValue)+1;
        }
    }

    public static double slowAdditionOption3(int a, int b, int baseCaseValue) {
        if(a==baseCaseValue){
            return b;
        }
        if(b==baseCaseValue){
            return a;
        }
        else{
            return slowAdditionOption2(a-1,b-1,baseCaseValue)+1+1;
        }
    }
}
