/*
 * $Id: StringListConverter.java,v 1.2 2011-09-13 13:43:32 pah Exp $
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

public class StringListConverter extends BasicListConverter {

    @Override
    protected Object toListItemValue(String string) {
         return string;
    }

}


/*
 * $Log: not supported by cvs2svn $
 * Revision 1.1.2.1  2011/06/15 13:55:51  pah
 * writing to DB almost works - seems to be a bug in eclipselink @OneToMany inside @Embeddable does not work....
 *
 */
