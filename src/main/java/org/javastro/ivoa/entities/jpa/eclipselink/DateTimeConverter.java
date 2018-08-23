/*
 * $Id: DateTimeConverter.java,v 1.2 2011-09-13 13:43:32 pah Exp $
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

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
//FIXME - is this really needed in java8?
@javax.persistence.Converter
public class DateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * {@inheritDoc}
     * overrides @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
     */
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
       return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
        
    }

    /**
     * {@inheritDoc}
     * overrides @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
     */
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
        
    }

 
}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/06/09 22:18:56  pah
 * basic VOResource schema nearly done - but not got save/recall working
 *
 */
