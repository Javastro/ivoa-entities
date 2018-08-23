/*
 * $Id$
 * 
 * Created on 21 Jan 2013 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2013 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities.regtap.translate;

import org.javastro.ivoa.entities.resource.Resource;

/**
 * Translate a VOResource to  RegTAP XML representation.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 21 Jan 2013
 * @version $Revision$ $date$
 */
public class RegTapTranslator {

    
    
    public org.javastro.ivoa.entities.regtap.Resource translate (Resource res){
        org.javastro.ivoa.entities.regtap.Resource retval = new org.javastro.ivoa.entities.regtap.Resource();
//        retval.setCreated(res.getCreated());
        //do it with XSL
        
        return retval;
    }
}


/*
 * $Log$
 */
