/*
 * $Id$
 * 
 * Created on 6 Feb 2013 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2013 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.regtap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A list implementation that keeps track of the index that forms part of the composite primary key in many of the tables in regtap. 
 * Because of this it is able to suggest a suitable index.
 * This is implemented as a decorated list.
 * @TODO need to be able to null the index for some types...
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 6 Feb 2013
 * @version $Revision$ $date$
 */
public class PKIndexList<E extends PKIndex> implements List<E>, Cloneable {

    /*
     * the decorated list.
     */
    protected final List<E> decoratedList;

    protected final SortedSet<Short> indexSet;

    /**
     * 
     */
    public PKIndexList() {
        decoratedList = new ArrayList<E>();
        indexSet = new TreeSet<Short>();

    }
    
    /* (non-Javadoc)
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return decoratedList.size();
    }

    /* (non-Javadoc)
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return decoratedList.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        return decoratedList.contains(o);
    }

    /* (non-Javadoc)
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<E> iterator() {
       return decoratedList.iterator();
    }

    /* (non-Javadoc)
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        return decoratedList.toArray();
    }

    /* (non-Javadoc)
     * @see java.util.List#toArray(T[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return decoratedList.toArray(a);
    }

    /* (non-Javadoc)
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(E e) {
      boolean retval = decoratedList.add(e);
      if (retval && e.getIndex() != -1) indexSet.add(e.getIndex());
      return retval;
    }

    /* (non-Javadoc)
     * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        boolean retval = decoratedList.remove(o);
        if (retval) indexSet.remove(((PKIndex)o).getIndex());
        return retval ;
    }

    /* (non-Javadoc)
     * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return decoratedList.containsAll(c);
    }

    /* (non-Javadoc)
     * @see java.util.List#addAll(java.util.Collection)
     */ 
    @Override
    public boolean addAll(Collection<? extends E> c) {
       boolean retval = decoratedList.addAll(c);
       for (E e : c) {
        indexSet.add(e.getIndex());
    }
       return retval;
    }

    /* (non-Javadoc)
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean retval = decoratedList.addAll(index, c);
        for (E e : c) {
            indexSet.add(e.getIndex());
        }
        return retval;
    }
    
    

    /* (non-Javadoc)
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
      boolean retval = decoratedList.removeAll(c);
      for (Object object : c) {
        indexSet.remove( ((PKIndex)object).getIndex());
    }
    return retval;
    }

    /* (non-Javadoc)
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retval = decoratedList.retainAll(c);
        //IMPL this is rather inefficient
        Set<Short> retidxs = new TreeSet<Short>();
        for (Object object : c) {
            retidxs.add(((PKIndex)object).getIndex());
        }
        indexSet.retainAll(retidxs);
        return retval;
    }

    /* (non-Javadoc)
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        decoratedList.clear();
        indexSet.clear();
    }

    /* (non-Javadoc)
     * @see java.util.List#get(int)
     */
    @Override
    public E get(int index) {
        return decoratedList.get(index);
    }

    /* (non-Javadoc)
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public E set(int index, E element) {
       indexSet.remove(decoratedList.get(index).getIndex());
       indexSet.add(element.getIndex()); //IMPL need to worry about index duplicates here - however, not yet sure of best policy
      return decoratedList.set(index, element);
       
    }

    /* (non-Javadoc)
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, E element) {
        indexSet.add(element.getIndex()); //IMPL need to worry about index duplicates here - however, not yet sure of best policy
        decoratedList.add(element);
    }

    /* (non-Javadoc)
     * @see java.util.List#remove(int)
     */
    @Override
    public E remove(int index) {
        indexSet.remove(decoratedList.get(index).getIndex());
        return decoratedList.remove(index);
    }

    /* (non-Javadoc)
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf(Object o) {
       return decoratedList.indexOf(o);
    }

    /* (non-Javadoc)
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf(Object o) {
        return decoratedList.lastIndexOf(o);
    }

    /* (non-Javadoc)
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<E> listIterator() {
        return decoratedList.listIterator();
    }

    /* (non-Javadoc)
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return decoratedList.listIterator(index);
    }

    /* (non-Javadoc)
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // TODO not implemented because a little tricky - shoul really return a PKList
        throw new  UnsupportedOperationException("List<E>.subList() not implemented");
    }
    
    
    /**
     * suggest an index that could be used
     * @return
     */
    public boolean addAndSetIndex(E e) {
        boolean retval = decoratedList.add(e);
        if (retval) { 
            short idxnew = indexSet.isEmpty()? (short)1 :(short) (indexSet.last()+ 1);
            indexSet.add(idxnew);
            e.setPKIndex(idxnew);
            }
        return retval;
      }

}


/*
 * $Log$
 */
