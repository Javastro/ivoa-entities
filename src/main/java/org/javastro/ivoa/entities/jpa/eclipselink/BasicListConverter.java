/*
 * $Id: BasicListConverter.java,v 1.2.2.1 2012-02-14 10:52:41 pah Exp $
 * 
 * Created on 8 Jun 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jpa.eclipselink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.exceptions.ConversionException;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.mappings.foundation.AbstractDirectMapping;
import org.eclipse.persistence.sessions.Session;

/**
 * Convert a list of an Enum type into a "/" separated string.
 * 
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 9 Jun 2011
 * @version $Revision: 1.2.2.1 $ $date$
 * @since AIDA Stage 2
 */
public abstract class BasicListConverter implements Converter {

    protected DatabaseMapping mapping;
    
    /** Field type */
    protected Class dataClass = String.class;
    protected String dataClassName = dataClass.getName();

    /** Object type */
    protected final Class objectClass = String.class;
    protected String objectClassName = objectClass.getName();
    
    
    @Override
    public Object convertObjectValueToDataValue(Object objectValue, Session session){
    //FIXME need to actually do conversion    
            try {
                List enumList = (List) objectValue; 
                StringBuffer sourceObject = new StringBuffer(255);
                if(enumList != null){
                    for (Object enum1 : enumList) {
                        sourceObject.append(enum1.toString()).append("/");
                    }
                
                return ((AbstractSession)session).getDatasourcePlatform().convertObject(sourceObject.substring(0, sourceObject.length()-1).toString(), getDataClass());
                }
                else {
                    
                return  ((AbstractSession)session).getDatasourcePlatform().convertObject("", getDataClass());
                }
            } catch (ConversionException e) {
                throw ConversionException.couldNotBeConverted(mapping, mapping.getDescriptor(), e);
            }
    }


    @Override
    public Object convertDataValueToObjectValue(Object dataValue,
            Session session) {
        Object attributeValue;
        if (dataValue != null) {
            try {
                attributeValue = ((AbstractSession)session).getDatasourcePlatform().convertObject(dataValue, getDataClass());
            } catch (ConversionException e) {
                throw ConversionException.couldNotBeConverted(mapping, mapping.getDescriptor(), e);
            }
         //superfluous I think
//            
//            try {
//                attributeValue = ((AbstractSession)session).getDatasourcePlatform().convertObject(attributeValue, getObjectClass());
//            } catch (ConversionException e) {
//                throw ConversionException.couldNotBeConverted(mapping, mapping.getDescriptor(), e);
//            }
       
        
        List retval = new ArrayList();
        String[] array = ((String)attributeValue).split("\\/");
        for (int i = 0; i < array.length; i++) {
            retval.add(toListItemValue(array[i]));
        }

        return retval;
        }
        else 
            return null;
    }

    protected abstract Object toListItemValue(String string);

    @Override
    public boolean isMutable() {
       return false;
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

            
        }
    }
    
    /**
     * PUBLIC:
     * Returns the class type of the data value.
     */
    public Class getDataClass() {
        return dataClass;
    }

    /**
     * INTERNAL:
     * Return the name of the data type for the MW usage.
     */
    public String getDataClassName() {
        if ((dataClassName == null) && (dataClass != null)) {
            dataClassName = dataClass.getName();
        }
        return dataClassName;
    }

    /**
     * PUBLIC:
     * Returns the class type of the object value.
     */
    public Class getObjectClass() {
        return objectClass;
    }

    /**
     * INTERNAL:
     * Return the name of the object type for the MW usage.
     */
    public String getObjectClassName() {
        if ((objectClassName == null) && (objectClass != null)) {
            objectClassName = objectClass.getName();
        }
        return objectClassName;
    }

 
}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.2  2011/09/13 13:43:32  pah
 * result of merge of branch ivoa_pah_2931
 *
 * Revision 1.1.2.2  2011/09/13 13:32:36  pah
 * mainly documentation updates
 *
 * Revision 1.1.2.1  2011/06/15 13:55:51  pah
 * writing to DB almost works - seems to be a bug in eclipselink @OneToMany inside @Embeddable does not work....
 *
 * Revision 1.1.2.2  2011/06/11 17:49:40  pah
 * sorted out ResourceName mappings - down to the minimum number of tables
 *
 * Revision 1.1.2.1  2011/06/09 22:18:56  pah
 * basic VOResource schema nearly done - but not got save/recall working
 *
 */
