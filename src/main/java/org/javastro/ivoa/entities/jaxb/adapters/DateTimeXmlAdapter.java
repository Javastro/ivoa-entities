/*
 * $Id: DateTimeXmlAdapter.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 14 May 2008 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2008 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jaxb.adapters;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * Simple adapter to use the Joda DateTime for xs:dates in JAXB.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 14 May 2008
 * @version $Name: not supported by cvs2svn $
 * @since VOTech Stage 7
 */
@XmlTransient
public class DateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .optionalStart()
                .appendOffsetId()
                .toFormatter();

    @Override
    public LocalDateTime unmarshal(String date) throws Exception {
        if(date != null){
        return LocalDateTime.parse(date,formatter);
        }
        else {
            return null;
        }
    }

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
	if(dateTime != null){
        return dateTime.format(formatter);
	}
	else {
	    return null;
	}
    }

}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.2  2011/06/15 13:55:51  pah
 * writing to DB almost works - seems to be a bug in eclipselink @OneToMany inside @Embeddable does not work....
 *
 * Revision 1.1.2.1  2011/06/01 15:23:41  pah
 * first pass at adding JPA for rdb as well as xml representation.
 *
 * Revision 1.2.2.1  2011/03/15 13:00:55  pah
 * jaxb now seems to supply xsd:date as a string....
 *
 * Revision 1.2  2008/09/22 22:16:56  pah
 * make deal with nulls
 *
 * Revision 1.1  2008/09/17 13:36:20  pah
 * first version of JAXB objects generated from schema
 *
 * Revision 1.2  2008/08/02 13:36:58  pharriso
 * safety checkin - on vacation
 *
 * Revision 1.1  2008/05/17 21:20:46  pharriso
 * safety checkin before interop
 *
 */
