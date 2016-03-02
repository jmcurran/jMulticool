package Multicool;


import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jcur002
 */
public class Compositions {
  public static ArrayList<Integer[]> generateCompositions(int n){
    // Adapted from Python code and algorithm by
    // Jerome Kelleher (c) 2009
    // http://jeromekelleher.net/partitions.php
    ArrayList<Integer[]> A  = new ArrayList<>();
    Integer[] a = new Integer[n+1];

    Arrays.fill(a, 0);

    int k = 1;
    int y = n - 1;

    while(k != 0){
      int x = a[k - 1] + 1;
      k -= 1;
      while(2 * x <= y){
        a[k] = x;
        y -= x;
        k += 1;
      }
      int l = k + 1;
      while(x <= y){
        a[k] = x;
        a[l] = y;
        /*for(int i = 0; i < k + 2; i++)
                cout << a[i] << ' '; */
        Integer[] r = Arrays.copyOfRange(a, 0, k + 2);
        A.add(r);
        //cout << endl;
        x += 1;
        y -= 1;
      }
      a[k] = x + y;
      y = x + y - 1;
      /*for(int i = 0; i < k + 1; i++)
              cout << a[i] << ' ';
       cout << endl;*/
      A.add(Arrays.copyOfRange(a, 0,  k + 1));
    }

    return A;
  }
  
  private static Integer[] regularize(Integer[] x, int regLen){
    if(x.length == regLen){
      return x;
    }
    Integer[] r = Arrays.copyOf(x, regLen);
    for(int i = x.length; i < regLen; i++)
      r[i] = 0;
    return r;
  }
  
  public static ArrayList<Integer[]> genRestrictedCompositions(int n, int len, boolean addZeros){
    ArrayList<Integer[]> l = generateCompositions(n);
    
    if(len <= n){ // generate all compostions that are of length n
      ArrayList<Integer[]> toReturn = new ArrayList<>();
      
      for(Integer[] x : l){
        if(x.length <= len){
          if(addZeros){
            toReturn.add(regularize(x, len));
          }else{
            toReturn.add(x);
          }
        }
      }
      
      return toReturn;
    }
    return l;
  }
      
  public static ArrayList<Integer[]> genRegularizedCompositions(int n){
    ArrayList<Integer[]> l = generateCompositions(n);
    ArrayList<Integer[]> toReturn = new ArrayList<>();
      
    for(Integer[] x : l){
      if(x.length < n){
        toReturn.add(regularize(x, n));
      }else{
        toReturn.add(x);
      }
    }
    return toReturn;
  }

  // Calculate Stirling numbers of the second kind S2(n, k)
  public static long Stirling2C(int n, int k){
    if((n == 0 && k == 0) || (n > 0 && k == 1) || (n > 0 && n == k))
      return 1;

    if((n == 0 || k == 0)) // n = k = 0 should get caught by the previous if
      return 0;

    return k * Stirling2C(n - 1, k) + Stirling2C(n - 1, k - 1);
  }

  // Calculate Bell numbers
  public static long BellC(int n){
    long sum = 0;

    for(int k = 1; k <= n; k++){
      sum += Stirling2C(n, k);
    }

    return sum;
  }  
}
