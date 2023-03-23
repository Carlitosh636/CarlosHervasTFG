package com.example.algorithms;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Algorithms {
    public static double recursivePower1(double base, double exp, int baseCaseValue){
        if(exp==baseCaseValue){
            if(baseCaseValue==0){
                return 1;
            }
            else if (baseCaseValue==1){
                return base;
            }
        }
        return recursivePower1(base,exp-1,baseCaseValue) * base;
    }
    public static double recursivePower2(double base, double exp, int baseCaseValue){
        if(exp==baseCaseValue){
            if(baseCaseValue==0){
                return 1;
            }
            if(baseCaseValue==1){
                return base;
            }
        }
        else if(exp % 2 == 0){
            return Math.pow(recursivePower2(base,exp/2,baseCaseValue),2);
        }
        return base * Math.pow(recursivePower2(base,(exp-1)/2,baseCaseValue),2);
    }
    public static double slowAdditionOption1(double a,double b, int baseCaseValue){
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

    public static double slowAdditionOption2(double a, double b, int baseCaseValue) {
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

    public static double slowAdditionOption3(double a, double b, int baseCaseValue) {
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
    public static String reverseString(String input,String baseCaseValue){
        if(Objects.equals(input, baseCaseValue)){
            return baseCaseValue;
        }
        else{
            return reverseString(input.substring(1),baseCaseValue).concat(String.valueOf(input.charAt(0)));
        }
    }
    public static int[] selectSort(int[] a){
        if(a.length <= 1){
            return a;
        }
        else{
            int e = getSmallest(a);
            int[] reducedArray = Arrays.stream(a).filter(ele->ele!=e).toArray();
            int[] f = {e};
            return IntStream.concat(Arrays.stream(f),Arrays.stream(selectSort(reducedArray))).toArray();
        }
    }
    public static int[] insertSort(int[] a){
        if(a.length <=1){
            return a;
        }
        else{
            int tail = a[a.length-1];
            int[] reducedArray = Arrays.stream(a).filter(ele->ele!=tail).toArray();
            return insertInOrderedArray(insertSort(reducedArray),tail);
        }
    }
    private static int[] insertInOrderedArray(int[] a,int ele){
       int[] nA = new int[a.length + 1];
       System.arraycopy(a, 0, nA, 1, a.length);
       nA[0] = ele;
       Arrays.sort(nA);
       return nA;
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
    public static int getSmallest(int[] a){
        int[] b = a.clone();
        Arrays.sort(b);
        return b[0];
    }
}
