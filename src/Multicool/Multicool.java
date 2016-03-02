package Multicool;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jcur002
 */
public class Multicool<E> {
  protected class Node{
    private int value;
    private Node next;
    
    protected Node(){
      value = Integer.MAX_VALUE;
      next = null;
    }
    
    protected Node(int value){
      this.value = value;
      next = null;
    }
    
    protected int getValue(){
      return value;
    }
    
    protected Node getNext(){
      return next;
    }
    
    protected void setValue(int value){
      this.value = value;
    }
    
    protected void setNext(Node next){
      this.next = next;
    }
    
    protected boolean hasNext(){
      return next != null;
    }
    
    protected void copy(Node src){
      this.value = src.value;
      this.next = src.next;
    }
  }
  
  private Node h;
  private Node t;
  private Node i;
  
  private int[] m_vnInitialState; // stored so that the current state can be reset to the initial state
  private int[] m_vnCurrState;
  private HashMap<E, Integer> tbl;
  
  private int m_nLength;
  private boolean m_bFirst;

 // constructor
  public Multicool(E[] x){
		int nx = x.length;
    
    tbl = new HashMap<>();
    
    for(E elt : x){
      if(!tbl.containsKey(elt)){
        tbl.put(elt, 1);
      }else{
        tbl.put(elt, tbl.get(elt) + 1);
      }
    }
    
    int[] newx = new int[nx];
    int v = 1;
    int j = 0;
    Iterator<E> keySetIterator = tbl.keySet().iterator();

    while(keySetIterator.hasNext()){
      E key = keySetIterator.next();
      for(int k = 0; k < tbl.get(key); k++){
        newx[j++] = v;
      }
      v++;
    }
    
		i = null;
    h = null;
    
    m_vnInitialState = new int[nx];
    m_vnCurrState = new int[nx];
    m_nLength = nx;
    m_bFirst = true;
    
    for(int ctr = 0; ctr < nx; ctr++){
      t = new Node(newx[ctr]);
      m_vnCurrState[ctr] = newx[ctr];
      m_vnInitialState[ctr] = newx[ctr];
      t.setNext(h);
      h = t;
      
      if(h.hasNext() && h.getNext().getValue() > h.getValue()){
        // std::cout << "Error" << std::endl;
        // should programme some sensible action here but I don't know what
      }
      
      if(ctr + 1 == 2){
        i = h;
      }
      
//      System.out.printf("Step %d\n--------\n", ctr + 1);
//      debugPrint();
//      System.out.print("--------\n");
    }
  }
  
  public void debugPrint(){
    char strPtr[] = {'h', 't', 'i'};
    
    for(int ctr = 0; ctr  < 3; ctr++){
      Node p; 
      switch(ctr){
        case 0:
          p = h;
          break;
        case 1:
          p = t;
          break;
        case 2:
          p = i;
          break;
        default:
          p = null;
      }
      
      if(p != null){
        System.out.printf("item %c.v: %d\n",strPtr[ctr] , p.value);
        if(p.next != null){
          System.out.printf("item %c.n: %s\n",strPtr[ctr] , Integer.toString(System.identityHashCode(p.next)));
        }else{
          System.out.printf("item %c->n: NULL\n",strPtr[ctr]);
        }
      }else{
        System.out.printf("%c = NULL\n",strPtr[ctr] );
      }
    }
    
    System.out.printf("m_nLength %d\n", m_nLength);
    System.out.printf("m_bFirst %b\n", m_bFirst);
    
    System.out.printf("m_vnInitialState: ");
    for(int ctr = 0; ctr < m_nLength; ctr++){
      System.out.printf("%d ", m_vnInitialState[ctr]);
    }
    System.out.printf("\n");
    
    System.out.printf("m_vnCurrState: ");
    for(int ctr = 0; ctr < m_nLength; ctr++){
      System.out.printf("%d ", m_vnCurrState[ctr]);
    }
    System.out.printf("\n");
  }
  
	  
  public void print(){
    System.out.printf("item h.value: %d\n", h.getValue());
    System.out.printf("item t.value: %d\n", t.getValue());
    System.out.printf("item i.value: %d\n", i.getValue());
    System.out.printf("m_nLength %d\n", m_nLength);
    System.out.printf("m_bFirst %b\n", m_bFirst);
    
    System.out.printf("m_vnInitialState: ");
    for(int j = 0; j < m_nLength; j++){
      System.out.printf("%d ", m_vnInitialState[j]);
    }
    System.out.printf("\n");
    
    System.out.printf("m_vnCurrState: ");
    for(int j = 0; j < m_nLength; j++){
      System.out.printf("%d ", m_vnCurrState[j]);
    }
    System.out.printf("\n");
  }
  
   public void reset(){
    i = null;
    h = null;
    m_vnCurrState = new int[m_nLength];
    m_bFirst = true;
    
    for(int ctr = 0; ctr < m_nLength; ctr++){
      t = new Node(m_vnInitialState[ctr]);
      m_vnCurrState[ctr] = m_vnInitialState[ctr];
      t.setNext(h);
      h = t;
      
      if(h.hasNext() && h.getNext().getValue() > h.getValue()){
        //std::cout << "Error" << std::endl;
        // should programme some sensible action here but I don't know what
      }
      
      if(ctr+1 == 2){
        i = h;
      }
    }
    
  }
  
  void setState(Node b){
    Node y = b;
    int ctr = 0;
    
    while(y != null) {
      m_vnCurrState[ctr++] =  y.getValue();
      y = y.getNext() ;
    }
  }

  public int[] getState(){
		int[] vState = Arrays.copyOf(m_vnCurrState, m_nLength);
  	return vState;
	}
  
  private ArrayList<E> translate(int[] state){
    ArrayList<E> result = new ArrayList<>();
    Object[] keys = tbl.keySet().toArray();
    
    for(int k = 0; k < state.length; k++)
      result.add((E)keys[state[k] - 1]);
    
    return result;
  }
  
  public ArrayList<ArrayList<E>> allPerm(){
    this.reset();
    int[] set = getInitialState();
    
    ArrayList lResult = new ArrayList<>();
    
    if(set.length > 1){
      while( this.hasNext()){
        //pmc->print();
        lResult.add( translate(this.getState()) );
      }
    }else{
      lResult.add( translate(this.getState()) );
    }
    return lResult;
  }
  
  public int getLength(){
    return m_nLength;
  }
  
  public int[] getInitialState(){
    int[] vSet = Arrays.copyOf(m_vnInitialState, m_nLength);
    return vSet;
  }
  
  public boolean hasNext(){
    Node j;
    Node _t;
    Node _s;
    
    if(m_bFirst){
      setState(h);
      m_bFirst = false;
      return true;
    }else{
      j = i.getNext();
      
      if(j.hasNext() || j.getValue() < h.getValue()) { 
        if (j.hasNext() && i.getValue() >= j.getNext().getValue()) {
          _s = j; 
        } else {
          _s = i;
        }
        _t = _s.getNext();
        _s.setNext(_t.getNext());
        _t.setNext(h);
        if(_t.getValue() < h.getValue()) {
          i = _t;
        }
        j = i.getNext();
        h = _t;
        setState(h);
        return true;
      }else{
        return false;
      }
    }
  }
  
  public ArrayList<E[]> nextPerm(){
    ArrayList lhs = new ArrayList<E[]>();

    lhs.add(getState());
    //lhs["b"] = hasNext() ? 1 : 0;
    
    return lhs;
  }
}

