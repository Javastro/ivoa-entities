/*
 * $Id: Ivoa.java,v 1.1.2.1 2012-02-14 10:52:41 pah Exp $
 * 
 * Created on 13 Dec 2011 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2011 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to add ivoa metadata descriptions to allow TAP schema to be produced.
 * 
 * Perhaps use org.eclipse.persistence.tools.schemaframework.DefaultTableGenerator to generate the tap schema....
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 13 Dec 2011
 * @version $Revision: 1.1.2.1 $ $date$
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ivoa {
     String description();
     String Ucd();
}


/*
 * $Log: not supported by cvs2svn $
 */
