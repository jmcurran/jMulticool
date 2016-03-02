package Multicool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Multicool.Multicool;
import Multicool.Compositions;
import java.util.ArrayList;
/**
 *
 * @author jcur002
 */
public class TheMain {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
   /* ArrayList<int[]> comp = Compositions.generateCompositions(6);
    
    for(int[] a : comp){
      for(int j = 0; j < a.length; j++){
        System.out.print(a[j] + " ");
      }
      System.out.println();
    }
    */
    Integer[] m = {1,1,2,0};
    Double [] d = {1.0, 2.0};
    Multicool<Double> mc = new Multicool<>(d);
    ArrayList<ArrayList<Double>> ap = mc.allPerm();
    
    for(ArrayList<Double> a : ap){
      for(int j = 0; j < a.size(); j++){
        System.out.print(a.get(j) + " ");
      }
      System.out.println();
    }
    
    
  }
  
}
