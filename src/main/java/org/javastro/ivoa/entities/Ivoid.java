/*
 * $Id$
 * 
 * Created on 30 Jun 2015 by Paul Harrison (paul.harrison@manchester.ac.uk)
 * Copyright 2015 Manchester University. All rights reserved.
 *
 * This software is published under the terms of the Academic 
 * Free License, a copy of which has been included 
 * with this distribution in the LICENSE.txt file.  
 *
 */ 

package org.javastro.ivoa.entities;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Represents an IVOA Identifier.
 * @author Paul Harrison (paul.harrison@manchester.ac.uk) 30 Jun 2015
 * @version $Revision$ $date$
 */
public class Ivoid {

    
    final URI value;
    
    /**
     * Create a new Ivoid.
     * 
     */
    public Ivoid(String val) throws URISyntaxException {

          value = new URI(val);

       if (!value.getScheme().equals("ivo")) {
          throw new URISyntaxException(val,"Invalid value for Ivoid scheme: " + value.getScheme());
       }

    }

   @Override
   public String toString() {
      return value.toString();
   }
}


