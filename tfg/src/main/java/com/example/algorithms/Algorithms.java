package com.example.algorithms;

import java.util.*;
import java.util.stream.IntStream;

public class Algorithms {
    public static double recursivePower1(double base, double exp, int baseCaseValue){
        if(exp==baseCaseValue){
            if(baseCaseValue==0){
                return 1;
            }
        }
        return recursivePower1(base,exp-1,baseCaseValue) * base;
    }
    public static double recursivePower2(double base, double exp, int baseCaseValue){
        if(exp==baseCaseValue){
            if(baseCaseValue==0){
                return 1;
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
    public static int[] quicksort(int[] a,int mid){
        int n = a.length;
        if(n<=1){
            return a;
        }
        else {
            int pivot = a[mid-1];
            int[] smallerElements = getSmaller(a,pivot);
            int[] greaterElements = getGreater(a,pivot);
            return IntStream.concat(IntStream.concat(Arrays.stream(quicksort(smallerElements,smallerElements.length-1)), Arrays.stream(new int[]{pivot})),Arrays.stream(quicksort(greaterElements,greaterElements.length-1))).toArray();
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
            System.arraycopy(a, 0, l, 0, mid);
            if (n - mid >= 0) System.arraycopy(a, mid, r, mid - mid, n - mid);
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
    public static int[] getGreater(int[] a, int pivot) {
        return Arrays.stream(a).filter(ele->ele>pivot).toArray();
    }

    public static int[] getSmaller(int[] a, int pivot) {
        return Arrays.stream(a).filter(ele->ele<pivot).toArray();
    }

    public static boolean equalStrings1(String input1, String input2, String baseCaseValue){
        if (input1.length() != input2.length()){
            return false;
        }
        if (Objects.equals(input1,baseCaseValue)){
            return true;
        }
        else{
            return (input1.charAt(0) == input2.charAt(0)) && (equalStrings1(input1.substring(1),input2.substring(1),baseCaseValue));
        }
    }
    public static boolean equalStrings2(String input1, String input2, String baseCaseValue){
        if (input1.length() != input2.length()){
            return false;
        }
        if (Objects.equals(input1,baseCaseValue)){
            return true;
        }
        if(input1.charAt(0) != input2.charAt(0)){
            return false;
        }
        else{
            return equalStrings2(input1.substring(1),input2.substring(1),baseCaseValue);
        }
    }

    public static boolean containsDigitTail(double n, double d) {
        n = Math.floor(n);
        if(n<10){
            return n == d;
        } else if (n % 10 == d) {
            return true;
        }
        else{
            return containsDigitTail(n/10,d);
        }
    }

    public static boolean containsDigitTailLineal(double n, double d) {
        n = Math.floor(n);
        if(n<10){
            return n == d;
        }
        else{
            return (n % 10 == d) || containsDigitTail(n/10,d);
        }
    }

    public static int isVowel(char ch)
    {
        ch = Character.toUpperCase(ch);
        if ("AEIOUÁÉÍÓÚ".indexOf(ch) != -1){
            return 1;
        }
        return 0;
    }

    public static int countVowels(String str)
    {
        int n = str.length();
        if (n == 1)
            return isVowel(str.charAt(0));
        int a = isVowel(str.charAt(0));
        return countVowels(str.substring(1)) + a;
    }

    public static Set<Integer> getDigitSet(int num){
        Set<Integer> mySet = new HashSet<>();
        while(num>0){
            int digit = num % 10;
            num = num / 10;
            mySet.add(digit);
        }
        return mySet;
    }

    public static Set<Integer> digitsSharedLineal(int[] a){
        int n = a.length;
        if (n == 1){
            return getDigitSet(a[0]);
        }
        else{
            int ele = a[0];
            int[] reducedArray = Arrays.stream(a).filter(v-> v != ele).toArray();
            Set<Integer> mySet = digitsSharedLineal(reducedArray);
            mySet.retainAll(getDigitSet(ele));
            return mySet;
        }
    }

    public static Set<Integer> digitSharedDyV(int[] a){
        int n = a.length;
        if (n == 1){
            return getDigitSet(a[0]);
        }
        else{
            int mid = n / 2;
            int[] l = new int[mid];
            int[] r = new int[n - mid];
            System.arraycopy(a, 0, l, 0, mid);
            if (n - mid >= 0) System.arraycopy(a, mid, r, mid - mid, n - mid);
            Set<Integer> mySet1 = digitsSharedLineal(l);
            Set<Integer> mySet2 = digitsSharedLineal(r);
            mySet1.retainAll(mySet2);
            return mySet1;
        }
    }

    public static int[] stringToArrayInt(String data) {
        data = data.replaceAll("[\\[\\]()\s{}]","");
        try{
            return Arrays.stream(data.split(",")).map(s->s.replace(" ","")).mapToInt(Integer::parseInt).toArray();
        }
        catch (NumberFormatException e){
                        throw e;
        }
    }
}
