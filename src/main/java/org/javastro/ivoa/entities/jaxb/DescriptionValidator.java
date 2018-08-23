/*
 * $Id: DescriptionValidator.java,v 1.2 2011-09-13 13:43:32 pah Exp $
 * 
 * Created on 18 Mar 2008 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2008 Astrogrid. All rights reserved.
 *
 * This software is published under the terms of the Astrogrid 
 * Software License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.namespace.QName;

import org.javastro.ivoa.entities.resource.Resource;

import org.javastro.ivoa.schema.Namespaces;

/**
 * Utility class to assist with validating the jaxb based descriptions.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 18 Mar 2008
 * @version $Name: not supported by cvs2svn $
 * @since VOTech Stage 7
 * @TODO make the validator use schema as well...
 */
public class DescriptionValidator {
    /**
     * Logger for this class
     */
   /** logger for this class */
private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
        .getLogger(DescriptionValidator.class);

    public static class Validation {
        public boolean valid;
        public String message;
        public Validation(boolean v, String m){
            valid = v;
            message = m;
        }
    }
    /**
     * Validates a resource object.
     * @param appdesc a java object that has suitable jaxb annotations
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Validation validate(final Resource appdesc)
    {
	JAXBElement jaxobj = new JAXBElement(new QName(Namespaces.VR.getNamespace(),"Resource"),Resource.class,appdesc);
        return validate(jaxobj);
    }
    


 

    /**
     * Validates a generic object
     * @param appdesc a java object that has suitable jaxb annotations
     * 
     * @return
     */
    public static Validation validate(final Object appdesc)
    {
	  String name = appdesc.getClass().getCanonicalName();
	  try {
	    JAXBContext jc = IvoaJAXBContextFactory.newInstance();
	         Marshaller m = jc.createMarshaller();
	          m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, 
	                Boolean.TRUE );
	          ValidationEventCollector handler  = new ValidationEventCollector();
		  m.setEventHandler(handler );
		  StringWriter sw = new StringWriter();
	          m.marshal( appdesc, sw);
	          StringBuffer message = new StringBuffer();
	          if(handler.hasEvents())
	          {
	            logger.error("invalid - "+name);
	              for (int i = 0; i < handler.getEvents().length; i++) {
			ValidationEvent array_element = handler.getEvents()[i];
		    logger.error("validation error - "
			    + array_element.toString());
		    message.append(array_element.toString());
		    message.append("\n");
		    }
	             logger.debug(sw.toString());        
	          }
	          
                  return new Validation(!handler.hasEvents(), message.toString());
	} catch (JAXBException e) {
	    logger.error("validation errror - "+name, e);
	    return new Validation(false, "validation error - "+name+" "+ e.getMessage());
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
 *
 */
