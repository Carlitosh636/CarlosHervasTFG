package com.example;

import java.util.ArrayList;

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

    public static int[] mergeSort(int[] a){
        int n = a.length;
        if (n<=1){
            return a;
        }
        else{
            int mid = n / 2;
            int[] l = new int[mid];
            int[] r = new int[n - mid];
            for (int i = 0; i < mid; i++) {
                l[i] = a[i];
            }
            for (int i = mid; i < n; i++) {
                r[i - mid] = a[i];
            }
            l = mergeSort(l);
            r = mergeSort(r);
            return merge(a,l,r,mid,n-mid);
        }
    }
    public static int[] merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
        return a;
    }
    static int[] stringToArrayInt(String str)
    {

        String[] splitArray = str.split(",");
        int[] array = new int[splitArray.length];

        for (int i = 0; i < splitArray.length; i++) {
            array[i] = Integer.parseInt(splitArray[i]);
        }
        return array;
    }
}
