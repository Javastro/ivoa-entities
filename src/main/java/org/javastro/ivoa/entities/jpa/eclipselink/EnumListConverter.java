/*
 * $Id: EnumListConverter.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 13 Jun 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jpa.eclipselink;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.foundation.AbstractDirectMapping;
import org.eclipse.persistence.sessions.Session;

public class EnumListConverter extends BasicListConverter {

    protected Class enumClass;

    @Override
    protected Object toListItemValue(String string) {
      return  Enum.valueOf(enumClass,string);
    }

    @Override
    public void initialize(DatabaseMapping mapping, Session session) {
        //do nothing special except store the mapping.
        this.mapping = mapping;
        if (mapping.isDirectToFieldMapping()) {
            AbstractDirectMapping directMapping = (AbstractDirectMapping)mapping;

            // Allow user to specify field type to override computed value. (i.e. blob, nchar)
            if (directMapping.getFieldClassification() == null) {
                directMapping.setFieldClassification(getDataClass());
            }

            //get the generic class
            Class clazz = directMapping.getDescriptor().getJavaClass();
            String attname = directMapping.getAttributeName();
            try {
                Field field = clazz.getDeclaredField(attname);
                Type type = field.getGenericType();
                if (type instanceof ParameterizedType) {
                    ParameterizedType ptype = (ParameterizedType) type;
                    Type theType = ptype.getActualTypeArguments()[0];
                    enumClass = (Class)theType;                  
                    
                    
                }
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // should not happen
                e.printStackTrace();
            }
            
        }        
        

    }

}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.3  2011/06/15 13:55:51  pah
 * writing to DB almost works - seems to be a bug in eclipselink @OneToMany inside @Embeddable does not work....
 *
 */
