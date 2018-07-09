package com.scan.framework.customerportal.sandbox;

import java.util.*;

public class ArrayCollection<T> implements Collection<T> {
  
  private T[] m = (T[]) new Object[0];
  private int size;
  
  @Override
  public int size() {
    return this.size;
  }
  
  @Override
  public boolean isEmpty() {
    return this.size == 0;
  }
  
  @Override
  public boolean contains(Object o) {
    return false;
  }
  
  @Override
  public Iterator<T> iterator() {
    return null;
  }
  
  @Override
  public Object[] toArray() {
    return new Object[0];
  }
  
  @Override
  public <T1> T1[] toArray(T1[] a) {
    return null;
  }
  
  @Override
  public boolean add(T t) {
    return false;
  }
  
  @Override
  public boolean remove(Object o) {
    return false;
  }
  
  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }
  
  @Override
  public boolean addAll(Collection<? extends T> c) {
    return false;
  }
  
  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }
  
  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }
  
  @Override
  public void clear() {
  
  }
}
