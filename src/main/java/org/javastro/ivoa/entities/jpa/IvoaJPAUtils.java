/*
 * $Id: IvoaJPAUtils.java,v 1.2 2011-09-13 13:43:33 pah Exp $
 * 
 * Created on 13 Jun 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jpa;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import javax.persistence.OneToMany;

public class IvoaJPAUtils {

    
    /**
     * Check that the join
     * @param o
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws NoSuchFieldException 
     * @throws SecurityException 
     */
    static void fixupReferences(Object o) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException{
       Class clazz = o.getClass();
       Field[] fields = clazz.getDeclaredFields();
       for (int i = 0; i < fields.length; i++) {
        OneToMany ann = fields[i].getAnnotation(javax.persistence.OneToMany.class);
        if (ann != null && ann.mappedBy() != null){
             String mappedByField = ann.mappedBy();    
             fields[i].setAccessible(true);
             List objs = (List) fields[i].get(o); //assume this is a List....
             for (Iterator iterator = objs.iterator(); iterator.hasNext();) {
                Object foreignObject = (Object) iterator.next();
                Field foreignField = foreignObject.getClass().getDeclaredField(mappedByField);
                foreignField.setAccessible(true);
                foreignField.set(foreignObject, o); // set the backlink
            }
        }
        if(!fields[i].getType().isPrimitive()){ // perhaps should check the package as well to exclude more types
            fixupReferences(fields[i].get(o));
        }
    }
        
    }
}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/06/15 13:55:49  pah
 * writing to DB almost works - seems to be a bug in eclipselink @OneToMany inside @Embeddable does not work....
 *
 */
